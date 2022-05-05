import java.util.Arrays;
import java.io.IOException;

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
        try {
            var kevinBaconHTML = Utility.getWikiHTMLText(
                Utility.searchToWikiURL("Kevin Bacon"), 10000
            );
            // System.out.println(Utility.getAllLinksOnPage(kevinBaconHTML));
            for (var link : Utility.linkSetToURLSet(
                Utility.getAllWikiLinksOnPage(kevinBaconHTML)
            )) {
                System.out.println(link);
                System.out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
