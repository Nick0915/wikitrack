import java.net.URL;
import java.net.MalformedURLException;

/**
 * Utility for the WikiGame application.
 *
 * @author Nikhil Ivaturi.
 */
public /* static */ class Utility {
    /**
     * Given a search term, returns a URL to the corresponding Wikipedia page.
     *
     * @param search the term to search.
     * @return a URL to the corresponding Wikipedia page.
     * @throws MalformedURLException if the URL 
     */
    public static URL searchToWikiURL(String search) throws MalformedURLException {
        var sanitized = search.trim().replace(' ', '_');
        return new URL("https://en.wikipedia.org/wiki/" + sanitized);
    }

    public static String getWikiHTMLText(URL url) {
        return null;
    }
}
