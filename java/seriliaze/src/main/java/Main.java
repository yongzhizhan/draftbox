import java.io.*;

class Foo implements IFileSerializable {
    static final long serialVersionUID = 123L;

    public void writeObject(OutputStream out) throws IOException {

    }

    public void readObject(InputStream in) throws IOException {

    }

    public Object getObject() {
        return null;
    }
}

public class Main {
    class Bar<T>{
        void output(Class<T> clazz) throws IllegalAccessException, InstantiationException {
            System.out.printf(String.valueOf(clazz.newInstance()));
        }
    }
    public static void main(String[] args){

    }
}
