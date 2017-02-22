package cn.zhanyongzhi.draftbox;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class Master {
    static ZooKeeper zk = null;

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        final CountDownLatch latch = new CountDownLatch(1);
        zk = new ZooKeeper("localhost:2181", 2000, new Watcher()
        {
            // 监控所有被触发的事件
            public void process(WatchedEvent event)
            {
                latch.countDown();
                System.out.println(event.toString() +  sdf.format(new Date().getTime()));
            }
        });

        latch.await(5, TimeUnit.SECONDS);

        Stat stat = zk.exists("/ha", false);
        if(null == stat) {
            zk.create("/ha", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }

        zk.create("/ha/node", "master".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);

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
