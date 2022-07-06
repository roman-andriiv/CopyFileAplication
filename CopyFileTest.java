import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * @author Roman_Andriiv
 */
public class CopyFileTest {
    public static void main(String[] args) throws IOException {
        Task copyFile = new CopyFileExecutorService(Paths.get("largeFile.txt"), Paths.get("largeFile-copied.txt"));
        copyFile.start();
        System.out.println("Started");
        while (true){
            String cmd = new Scanner(System.in).nextLine();
            if ("i".equalsIgnoreCase(cmd)){
                copyFile.interrupt();
                System.out.println("Interrupted: " + copyFile.gerPercentProgress() + " %");
                break;
            } else if ("p".equalsIgnoreCase(cmd)) {
                System.out.println("Current progress: " + copyFile.gerPercentProgress() + " %");
            } else if ("q".equalsIgnoreCase(cmd)) {
                break;
            }
        }
    }
}
