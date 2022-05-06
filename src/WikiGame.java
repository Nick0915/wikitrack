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
        var startPage = "AEX cfiXML";
        var endPage = "Information Transfer";

        try {
            var crawler = new GameCrawler(
                Utility.searchToWikiURL(startPage),
                Utility.searchToWikiURL(endPage)
            );

            crawler.crawl();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
