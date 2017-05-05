import java.io.IOException;

public interface IFileObjectStream<T> {
    void readObject(IFileSerializable object) throws IOException;
    void writeObject(IFileSerializable<T> object) throws IOException;
}
