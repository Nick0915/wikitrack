import java.util.*;

import java.net.URL;

/**
 * Web crawler that crawls pages from a given start page to the end page.
 *
 * @author Nikhil Ivaturi.
 */
public class BreadthWiseCrawler extends Crawler {
    // possibly making a child class with multithreading.
    protected Queue<URL> searchQueue;

    /**
     * Constructs a {@link BreadthWiseCrawler} with a given start page and end page.
     *
     * @param startUrl the URL to start the search from.
     * @param endUrl the URL to end the search at.
     */
    public BreadthWiseCrawler(URL startUrl, URL endUrl) {
        super(startUrl, endUrl);
        searchQueue = new LinkedList<>();
        searchQueue.add(startUrl);
    }

    public void crawl() {
        int i = 0;

        levels.put(currentUrl, 0);

        // find path
        while (!currentUrl.equals(endUrl)) {
            currentUrl = searchQueue.remove();

            // System.out.printf("Processing: %s (queue size: %d)\n", currentUrl, searchQueue.size());

            // if (currentUrl.toString().toLowerCase().contains("transfer")) {
            //     System.out.println("Potential: " + currentUrl);
            // }
            if (currentUrl.equals(endUrl)) {
                break;
            }
            var neighbors = getNeighbors();

            for (var n : neighbors) {
                if (visitedSites.add(n)) {
                    // if first time visiting site, set parent to current
                    parentMap.put(n, currentUrl);
                    searchQueue.add(n);
                    levels.put(n, levels.get(currentUrl) + 1);
                }
            }

            if (i % 10 == 0) {
                System.out.println("i = " + i + ", Level counts: ");
                var counts = new HashMap<Integer, Long>();
                for (Map.Entry<URL, Integer> e : levels.entrySet()) {
                    counts.merge(e.getValue(), 1L, Long::sum);
                }
                System.out.println(counts);
            }

            i++;
        }

        displayPath();

        // // build path
        // System.out.println("FOUND!!!");
        // Stack<URL> path = new Stack<>();
        // path.add(endUrl);
        // while (true) {
        //     var parent = parentMap.get(path.peek());
        //     path.add(parent);
        //     if (parent.equals(startUrl))
        //         break;
        // }

        // // print path
        // boolean first = true;
        // for (var site : path) {
        //     if (first) {
        //         first = false;
        //     } else {
        //         System.out.print(" -> ");
        //     }
        //     System.out.print(site);
        // }
        // System.out.println();
    }
}
