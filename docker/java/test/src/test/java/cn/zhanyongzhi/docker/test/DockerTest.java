package cn.zhanyongzhi.docker.test;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.Info;
import com.github.dockerjava.api.model.SearchItem;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class DockerTest {
    @Test
    public void testConn(){
        DockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder().build();
        DockerClient docker = DockerClientBuilder.getInstance(config).build();
        Info info = docker.infoCmd().exec();
        System.out.print(info);
    }

    @Test
    public void testConnByEvn() throws IOException, InterruptedException {
        DockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder().build();
        DockerClient docker = DockerClientBuilder.getInstance(config).build();
        Info info = docker.infoCmd().exec();
        System.out.print(info);
    }

    @Test
    public void testGetActiveContainer(){
        DockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder().build();
        DockerClient docker = DockerClientBuilder.getInstance(config).build();
        List<Container> containers = docker.listContainersCmd().exec();
        for(Container container : containers){
            System.out.println(container.getNames()[0]);
        }
    }

    @Test
    public void testStartMySql() throws IOException {
        MysqlDocker mysqlDocker = new MysqlDocker();
        mysqlDocker.start();
    }

    @Test
    public void testStopMySql() throws IOException {
        MysqlDocker mysqlDocker = new MysqlDocker();
        mysqlDocker.start();

        mysqlDocker.stop();
    }

    @Test
    public void testLock() throws IOException {
        {
            MysqlDocker mysqlDocker = new MysqlDocker();
            mysqlDocker.start();
        }

        {
            MysqlDocker mysqlDocker = new MysqlDocker();
            Assert.assertTrue(mysqlDocker.isLocked());
        }
    }

    @Test
    public void testWaitDBReady() throws IOException, InterruptedException {
        MysqlDocker mysqlDocker = new MysqlDocker();
        mysqlDocker.start();
        mysqlDocker.waitDBReady();
    }

    @Test
    public void testSearch(){
        DockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder().build();
        DockerClient docker = DockerClientBuilder.getInstance(config).build();
        List<SearchItem> searchItemList = docker.searchImagesCmd("123.56.168.19:5000/yueyuso-mainserverdb").exec();
        for(SearchItem searchItem : searchItemList){
            System.out.println(searchItem.toString());
        }
    }
}
