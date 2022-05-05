import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;

import java.net.*;
import java.io.*;

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
     */
    public static String getWikiHTMLText(URL url, int timeout_ms) throws IOException {
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
        return output.toString();
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
    private static String buildHttpParamString(Map<String, String> map) throws UnsupportedEncodingException {
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
}

