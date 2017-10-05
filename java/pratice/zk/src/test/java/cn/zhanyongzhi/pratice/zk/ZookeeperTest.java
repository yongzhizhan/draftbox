package cn.zhanyongzhi.pratice.zk;

import junit.framework.Assert;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.*;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ZookeeperTest {
    String connectString = "10.10.17.117";

    @Test
    public void testConnect() throws IOException, InterruptedException, KeeperException {
        int sessionTimeoutS = 10;

        final CountDownLatch downLatch = new CountDownLatch(1);
        final CountDownLatch createLatch = new CountDownLatch(1);
        ZooKeeper zooKeeper = new ZooKeeper(connectString, sessionTimeoutS * 1000, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                //
                System.out.println("process");
                downLatch.countDown();
            }
        });

        downLatch.await();

        List<String> children = zooKeeper.getChildren("/", null);
        zooKeeper.getData("/test3", new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                createLatch.countDown();
            }
        }, null);

        zooKeeper.create("/test3", "data".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        createLatch.await();
        zooKeeper.close();
    }

    @Test
    public void testIfReconnectWatchWillReset() throws IOException, KeeperException, InterruptedException {
        ZooKeeper zooKeeper = connect();

        String nodePath = "/watch_test";
        try {
            zooKeeper.delete(nodePath, -1);
        }catch (KeeperException.NoNodeException ignore){
            //
        }

        zooKeeper.create(nodePath, "data".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        final CountDownLatch createLatch = new CountDownLatch(1);
        zooKeeper.getData(nodePath, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                System.out.println("getdata watcher: " + watchedEvent.toString());
                createLatch.countDown();
            }
        }, null);

        //disconnect
        //zooKeeper.close();

        //connect
        //zooKeeper = connect();

        //zooKeeper.setData(nodePath, "123".getBytes(), -1);

        if(false == createLatch.await(3, TimeUnit.SECONDS)){
            Assert.fail("wait timeout");
        }

        zooKeeper.close();

    }

    private ZooKeeper connect() throws IOException {
        int sessionTimeoutS = 10;

        final CountDownLatch downLatch = new CountDownLatch(1);
        ZooKeeper zooKeeper = new ZooKeeper(connectString, sessionTimeoutS * 1000, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                System.out.println("watched event: " + watchedEvent.toString());
                downLatch.countDown();
            }
        });

        try {
            downLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }

        return zooKeeper;
    }


    @Test
    public void testCurator() throws Exception {
        final String path = "/test2";

        CuratorFramework client = CuratorFrameworkFactory.newClient(connectString, new RetryNTimes(10, 100));
        client.start();

        try {
            client.delete().deletingChildrenIfNeeded().forPath(path);
        }catch (Exception ignore){
            ignore.printStackTrace();
            //
        }

        client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(path, "123".getBytes());
        client.getData().usingWatcher(new CuratorWatcher() {
            @Override
            public void process(WatchedEvent event) throws Exception {
                System.out.println("getdata watche: " + event.toString());
            }
        }).forPath(path);

        //client.setData().forPath(path, "456".getBytes());
        //client.setData().forPath(path, "789".getBytes());

        client.close();

        System.out.println("client close.");
    }


}