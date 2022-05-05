import java.util.Arrays;

/**
 * Entry point for the WikiGame Application.
 *
 * @author Nikhil Ivaturi.
 */
public /* static */ class WikiGame {
    /**
     * Main method.
     *
     * @param args the command line args.
     */
    public static void main(String[] args) {
        // check command line args
        if (args.length != 2) {
            System.err.println("Usage: java WikiGame <start page> <end page>");
            System.exit(1);
        }

        System.out.printf("args=%s\n", Arrays.toString(args));

        String searchStart = args[0];
        String searchEnd = args[1];
    }
}
