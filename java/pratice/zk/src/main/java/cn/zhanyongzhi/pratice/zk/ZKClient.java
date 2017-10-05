package cn.zhanyongzhi.pratice.zk;

import java.util.List;

public interface ZKClient {
    boolean connect(String connectString);

    boolean disconnect();

    boolean isConnected();

    boolean create(String nodePath, boolean isEphemeral);

    boolean delete(String nodePath);

    byte[] getData(String nodePath);

    boolean setData(String nodePath, byte[] data);

    boolean exist(String nodePath);

    List<String> getChildren(String nodePath);
}
