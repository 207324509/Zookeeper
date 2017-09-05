package cn.kenenjoy.zookeeper.demo;

import org.apache.zookeeper.*;

import java.io.IOException;

/**
 * Created by hefa on 2017/9/5.
 */
public class BasicDemo1 {
    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        // 创建一个与服务器的连接
        ZooKeeper zooKeeper = new ZooKeeper("127.0.0.1:2181", 60000, new Watcher() {
            // 监控所有触发的事件
            public void process(WatchedEvent watchedEvent) {
                System.out.println("Event:" + watchedEvent.getType());
            }
        });

        // 查看根节点
        System.out.println("ls / => " + zooKeeper.getChildren("/", true));

        // 创建一个目录节点
        if (zooKeeper.exists("/node", true) == null) {
            zooKeeper.create("/node", "hefa".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            System.out.println("create /node hefa");

            // 查看/node节点数据
            System.out.println("get /node => " + new String(zooKeeper.getData("/node", false, null)));

            // 查看根节点
            System.out.println("ls / => " + zooKeeper.getChildren("/", true));
        }

        //  修改节点数据
        if (zooKeeper.exists("/node", true) != null) {
            zooKeeper.setData("/node", "kenenjoy".getBytes(), -1);
            // 查看node节点数据
            System.out.println("get /node => " + new String(zooKeeper.getData("/node", false, null)));
        }

        // 删除节点
        if (zooKeeper.exists("/node/sub1", true) != null) {
            zooKeeper.delete("/node/sub1",-1);
            zooKeeper.delete("/node", -1);
            // 查看根节点
            System.out.println("ls / => " + zooKeeper.getChildren("/",true));
        }

        // 关闭连接
        zooKeeper.close();

    }
}
