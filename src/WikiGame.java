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
        var startPage = "Information transfer";
        var endPage = "Information";

        try {
            var crawler = new BreadthWiseCrawler(
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
