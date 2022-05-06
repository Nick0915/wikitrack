import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;

import org.junit.*;
import org.junit.runner.JUnitCore;

import static org.junit.Assert.*;

/**
 * Tests the Utility class.
 */
public class Tester {
    static Map<String, String> expectMap_searchToWikiURL;

    static {
        expectMap_searchToWikiURL = new HashMap<>();
        expectMap_searchToWikiURL.put("ed sheeran", "https://en.wikipedia.org/wiki/Ed_Sheeran");
        expectMap_searchToWikiURL.put("Ed Sheeran", "https://en.wikipedia.org/wiki/Ed_Sheeran");
    }

    public static void main(String[] args) {
        JUnitCore.main("Tester");
    }

    @Test
    public void testSearchToWikiURL() throws MalformedURLException {
        for (var key : expectMap_searchToWikiURL.keySet()) {
            var actual = Utility.searchToWikiURL(key).toString().toLowerCase();
            assertEquals(expectMap_searchToWikiURL.getOrDefault(key, "null").toLowerCase(), actual);
        }
    }

    @Test
    public void testKevinBaconWikiHTML() {
        try {
            var actual = Utility.getHTMLContent(Utility.searchToWikiURL("Kevin Bacon"), 2000);
            var pw = new PrintWriter(new File("../res/actual_kevin_bacon_wiki.html"));
            pw.print(actual.toString());
            pw.close();
        } catch (IOException e) {
            System.out.println("Exception occurred.");
            assertTrue(false);
        }
    }
}
