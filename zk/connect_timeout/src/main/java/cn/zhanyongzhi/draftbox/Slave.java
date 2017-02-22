package cn.zhanyongzhi.draftbox;

import org.apache.log4j.helpers.DateTimeDateFormat;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class Slave {
    static ZooKeeper zk = null;

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        final CountDownLatch latch = new CountDownLatch(1);
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        zk = new ZooKeeper("10.10.8.243:2181", 2000, new Watcher()
        {
            // 监控所有被触发的事件
            public void process(WatchedEvent event)
            {
                System.out.println(event.toString() +  sdf.format(new Date().getTime()));
                latch.countDown();
            }
        });

        latch.await(5, TimeUnit.SECONDS);

        Stat stat = zk.exists("/ha", false);
        if(null == stat) {
            zk.create("/ha", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }

        zk.create("/ha/node", "slave".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);

        List<String> children = zk.getChildren("/ha", false);
        Collections.sort(children);

        if(children.isEmpty()){
            System.out.println("children empty.");
            return;
        }

        String minNode = "/ha/" + children.get(0);
        zk.exists(minNode, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                System.out.println(event.toString() +  sdf.format(new Date().getTime()));
            }
        });

        final CountDownLatch shutdownWait = new CountDownLatch(1);
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                shutdownWait.countDown();
            }
        }));

        shutdownWait.await();
    }
}
