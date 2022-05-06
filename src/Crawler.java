import java.util.*;

import java.net.URL;

public abstract class Crawler {
    protected final Set<URL> visitedSites;
    protected final Map<URL, URL> parentMap;

    protected final Map<URL, Integer> levels;

    protected final URL startUrl, endUrl;
    protected URL currentUrl;

    public Crawler(URL startUrl, URL endUrl) {
        this.startUrl = startUrl;
        this.endUrl = endUrl;
        this.currentUrl = startUrl;
        this.visitedSites = new HashSet<>();
        this.parentMap = new HashMap<>();
        this.levels = new HashMap<>();
    }

    protected Set<URL> getNeighbors() {
        return Utility.getLinkedPages(currentUrl);
    }

    protected void displayPath() {
        // build path
        System.out.println("FOUND!!!");
        Stack<URL> path = new Stack<>();
        path.add(endUrl);
        while (true) {
            var parent = parentMap.get(path.peek());
            path.add(parent);
            if (parent.equals(startUrl))
                break;
        }

        // print path
        boolean first = true;
        for (var site : path) {
            if (first) {
                first = false;
            } else {
                System.out.print(" -> ");
            }
            System.out.print(site);
        }
        System.out.println();
        System.exit(0);
    }

    public abstract void crawl();
}
