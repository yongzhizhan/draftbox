import java.io.*;

public class FileObjectStream<T> implements IFileObjectStream<T> {
    private InputStream inputStream;
    private OutputStream outputStream;

    public FileObjectStream(File dataFile) throws FileNotFoundException {
        inputStream = new FileInputStream(dataFile);
        outputStream = new FileOutputStream(dataFile);
    }

    public void readObject(IFileSerializable object) throws IOException {
        object.readObject(inputStream);
    }

    public void writeObject(IFileSerializable<T> object) throws IOException {
        object.writeObject(outputStream);
    }
}
