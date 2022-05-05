import java.util.Set;
import java.util.HashSet;

import java.net.URL;

/**
 * Web crawler that crawls pages from a given start page to the end page.
 *
 * @author Nikhil Ivaturi.
 */
public class GameCrawler {
    /**
     * Set of sites that have already been visited.
     */
    private Set<URL> visitedSites;

    private URL startUrl, endUrl;

    /**
     * Constructs a {@link GameCrawler} with a given start page and end page.
     *
     * @param startUrl the URL to start the search from.
     * @param endUrl the URL to end the search at.
     */
    public GameCrawler(URL startUrl, URL endUrl) {
        this.startUrl = startUrl;
        this.endUrl = endUrl;
    }
}
