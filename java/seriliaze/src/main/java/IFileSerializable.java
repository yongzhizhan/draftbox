import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface IFileSerializable<T> {
    void writeObject(OutputStream out) throws IOException;
    void readObject(InputStream in) throws IOException;
    T getObject();
}
