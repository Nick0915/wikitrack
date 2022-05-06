import java.util.*;
import java.util.stream.*;
import java.net.*;
import java.io.*;

import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

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
     * @throws MalformedURLException if the resulting URL is malformed.
     */
    public static URL searchToWikiURL(String search) throws MalformedURLException {
        var sanitized = search.trim().replace(' ', '_');
        return new URL("https://en.wikipedia.org/wiki/" + sanitized);
    }

    /**
     * Given a URL, returns the content of the html response of that
     * page.
     *
     * @param url        the URL to send an http request to.
     * @param timeout_ms the amount of milliseconds to wait before aborting the
     *                   connection.
     * @return the content of the response page.
     * @throws IOException if something goes wrong during the
     *                     connection to the page.
     * @throws SocketTimeoutException if the page took longer to load than is allowed by {@code timeout_ms}.
     */
    public static String getHTMLContent(URL url, int timeout_ms) {
        try {
            int status;
            // open the connection
            var connection = (HttpURLConnection) url.openConnection();
            do {
                // configure the connection
                connection.setRequestMethod("GET"); // we want GET request
                connection.setReadTimeout(timeout_ms); // set timeout
                connection.setConnectTimeout(timeout_ms);
                connection.setInstanceFollowRedirects(true); // follow redirects to proper page

                status = connection.getResponseCode();
                var loc = connection.getHeaderField("Location");
                if (loc != null)
                    connection = (HttpURLConnection) new URL(loc).openConnection();
            } while (status == HttpURLConnection.HTTP_MOVED_PERM || status == HttpURLConnection.HTTP_MOVED_TEMP);

            var responseScanner = new Scanner(connection.getInputStream());
            var output = new StringBuilder();
            while (responseScanner.hasNextLine()) {
                output.append(responseScanner.nextLine());
            }
            responseScanner.close();
            connection.disconnect();
            // avoid ddos-ing 0_0
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.exit(1);
            }
            return output.toString();
        } catch (SocketTimeoutException e) {
            System.out.println("Server didn't respond fast enough");
            e.printStackTrace();
            System.exit(1);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }

    /**
     * Given a map of http parameters, returns a string encoding the request parameters properly.
     *
     * <p>
     *   Credit: <a href="https://www.baeldung.com/java-http-request">Baeldung</a>
     * </p>
     *
     * @param map a map of the form <code>{param1: value, param2: value, ...}</code>;
     * @return a string of the form {@code "param1=value&param2=value..."}
     * @throws UnsupportedEncodingException if a parameter cannot be encoded properly into UTF-8.
     */
    public static String buildHttpParamString(Map<String, String> map) throws UnsupportedEncodingException {
        var out = new StringBuilder();
        var first = true;

        for (var entry : map.entrySet()) {
            if (first) {
                out.append("&");
                first = false;
            }

            out
                .append(URLEncoder.encode(entry.getKey(), "UTF-8"))
                .append("=")
                .append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return out.toString();
    }

    public static URL linkToURL(String link) {
        if (linkToURL_memo.containsKey(link)) {
            // System.out.println("linkToURL: cache hit!");
            return linkToURL_memo.get(link);
        }
        try {
            linkToURL_memo.put(link, new URL(link));
            return linkToURL_memo.get(link);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }

    private static Map<URL, Set<URL>> getAllWikiLinksOnPage_memo;
    private static Map<String, URL> linkToURL_memo;

    static {
        getAllWikiLinksOnPage_memo = new HashMap<>(100_000);
        linkToURL_memo = new HashMap<>(100_000);
    }

    /**
     * Extracts a set of all URLs on the given page.
     *
     * @param pageContent a string containing the HTML source of
     *                    the page.
     * @return a set containing all the URLs in the page.
     * @throws MalformedURLException if any found URL is malformed.
     */
    public static Set<URL> getLinkedPages(URL page) {
        if (getAllWikiLinksOnPage_memo.containsKey(page)) {
            System.out.println("getLinkedPages: cache hit!");
            return getAllWikiLinksOnPage_memo.get(page);
        }

        var pageContent = getHTMLContent(page, 10000);

        var linkedPages = Jsoup.parse(pageContent).select("a[href]")
            .stream()
            .map(e -> e.attr("href"))
            .filter(e -> e.startsWith("/wiki/"))
            .filter(e -> !e.contains(":"))
            .map(e -> "https://en.wikipedia.org" + e)
            // .filter(e -> {
            //     System.out.println("Found valid link: " + e);
            //     return true;
            // })
            .map(e -> linkToURL(e))
            .collect(Collectors.toCollection(HashSet<URL>::new)); // only want elements with <a href=""></a> tags

        getAllWikiLinksOnPage_memo.put(page, linkedPages);
        return getAllWikiLinksOnPage_memo.get(page);
    }
}

