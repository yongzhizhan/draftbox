package co.paralleluniverse.javafs;

import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Paths;

public class Main {
    public static void main(final String... args) throws Exception {
        try {
            if (args.length < 1 || args.length > 2)
                throw new IllegalArgumentException();

            final String mountPoint = args[0];
            FuseFileSystemProvider.basePath = args[1];

            URI uri = new URI("file:///");
            final FileSystem fs = FileSystems.getFileSystem(uri);

            System.out.println("Mounting " + FuseFileSystemProvider.basePath + " at " + mountPoint);

            JavaFS.mount(fs, Paths.get(mountPoint), false, true);

            Thread.sleep(Long.MAX_VALUE);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            System.err.println("Usage: JavaFS [-r] <mountpoint> [<dest path>]");
            System.exit(1);
        }
    }
}
