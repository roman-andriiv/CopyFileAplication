import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author Roman_Andriiv
 */
public class CopyFileThread implements Task, Runnable {
    private final long sourceSize;
    private final Path sourcePath;
    private final Path destinationPath;
    private final Thread thread;
    private volatile long copied;

    public CopyFileThread(Path sourcePath, Path destinationPath) throws IOException {
        super();
        this.sourcePath = sourcePath;
        this.destinationPath = destinationPath;
        this.sourceSize = Files.size(sourcePath);
        this.copied = 0;
        this.thread = new Thread(this, "CopyFileTaskThread");
    }


    @Override
    public void start() {
        if (thread.getState()!=Thread.State.NEW)
            throw new IllegalArgumentException("CopyFileTask is executed already");
        this.thread.start();
    }

    @Override
    public void interrupt() {
        this.thread.interrupt();
    }

    @Override
    public int gerPercentProgress() {
        return (int) (this.copied * 100 / sourceSize);
    }

    @Override
    public void run() {
        byte[] buffer = new byte[8192];
        try (InputStream in = new FileInputStream(sourcePath.toFile());
             OutputStream out = new FileOutputStream(destinationPath.toFile())) {
            while (!Thread.interrupted()) {
                int read = in.read(buffer);
                if (read == -1)
                    break;
                out.write(buffer, 0, read);
                copied += read;
            }
        } catch (IOException e) {
            System.err.println("Copying is failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
