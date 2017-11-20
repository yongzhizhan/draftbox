package cn.zhanyongzhi.docker.test;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.Ports;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileLock;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;

public class MysqlDocker {
    private static final Logger logger = LoggerFactory.getLogger(MysqlDocker.class);

    FileLock lock;
    FileOutputStream outputStream;

    private String containerName = "7a919192-7faa-4159-b2bf-6c8d17d5524b";
    private DockerClient docker;
    private int port = 53306;

    public MysqlDocker(){
        //
    }

    public MysqlDocker(String containerName, int port) {
        this.containerName = containerName;
        this.port = port;
    }

    public void start() throws IOException {
        if (isLocked()) {
            System.out.println(String.format("container %s is running...", containerName));
            return;
        }

        String containerId = null;

        DockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder().build();
        docker = DockerClientBuilder.getInstance(config).build();

        List<Container> containers = docker.listContainersCmd().withShowAll(true).exec();
        for (Container container : containers) {
            boolean isContainName = false;
            for (String name : container.getNames()) {
                if (name.equals("/" + containerName)) {
                    isContainName = true;
                    break;
                }
            }

            if (!isContainName)
                continue;

            containerId = container.getId();
            break;
        }

        if (null == containerId) {
            //123.56.168.19:5000/yueyuso-mainserverdb
            Ports portBindings = new Ports();
            portBindings.bind(ExposedPort.tcp(3306), Ports.Binding.bindPort(port));

            CreateContainerResponse container = docker.createContainerCmd("123.56.168.19:5000/yueyuso-mainserverdb")
                    .withName(containerName)
                    .withPortBindings(portBindings)
                    .withEnv("MYSQL_ROOT_PASSWORD=12345")
                    .exec();
        }

        if (!isRunning()) {
            docker.startContainerCmd(containerId).exec();
        }

        outputStream.write(containerId.getBytes());
    }

    public void stop() throws IOException {
        if (!isLocked()) {
            return;
        }

        lock.release();
        outputStream.close();

        File file = new File("/tmp/" + containerName);
        String id = IOUtils.toString(new FileInputStream(file));

        docker.stopContainerCmd(id).exec();
        docker.waitContainerCmd(id).exec(null);

        file.delete();
    }

    protected void waitDBReady() throws IOException, InterruptedException {
        Properties properties = new Properties();
        properties.load(getClass().getClassLoader().getResourceAsStream("docker-java.properties"));

        String host = properties.getProperty("HOST");
        String url = String.format("jdbc:mysql://%s:%d/", host, port);
        String driver = "com.mysql.jdbc.Driver";

        for (int i = 0; i < 10; i++) {
            try {
                Class.forName(driver).newInstance();
                Connection conn = DriverManager.getConnection(url, "root", "12345");

                Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                String sql = "show databases";
                ResultSet rs = stmt.executeQuery(sql);

                break;
            } catch (Exception e) {
                logger.info("connect error", e);
            }

            //每次停一秒
            Thread.sleep(10 * 1000);
        }
    }

    protected boolean isLocked() throws IOException {
        if (null != lock)
            return true;

        //Create lock
        File file = new File("/tmp/" + containerName);
        if (!file.exists())
            file.createNewFile();

        try {
            outputStream = new FileOutputStream(file);
            lock = outputStream.getChannel().tryLock();
            if (null == lock)
                return true;

        } catch (Exception e) {
            return true;
        }

        return false;
    }

    private boolean isRunning() {
        List<Container> containers = docker.listContainersCmd().withShowAll(false).exec();
        for (Container container : containers) {
            for (String name : container.getNames()) {
                if (name.equals("/" + containerName)) {
                    return true;
                }
            }
        }

        return false;
    }
}
