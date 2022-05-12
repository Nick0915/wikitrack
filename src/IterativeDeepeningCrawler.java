import java.util.*;

import java.net.URL;

public class IterativeDeepeningCrawler extends Crawler {
    protected Stack<URL> searchStack;
    protected Set<URL> deadEnds;

    public IterativeDeepeningCrawler(URL startUrl, URL endUrl) {
        super(startUrl, endUrl);
        deadEnds = new HashSet<>();
    }

    @Override
    public void crawl() {
        // for now, only search with a max depth of 10
        for (int i = 0; i < 10; i++) {
            System.out.println("depth=" + i);
            deadEnds.clear();
            if (dfs(startUrl, i)) {
                displayPath(); // <- process ends here
            }
        }
        System.out.println("Not found :(");
    }

    private boolean dfs(URL curr, int depth) {
        // if previously searched without finding, don't bother
        if (deadEnds.contains(curr))
            return false;

        if (depth == 0)
            return curr.equals(endUrl);

        var neighbors = getNeighbors();
        for (var n : neighbors) {
            // if not found from here at all, take not
            if (!dfs(n, depth - 1)) {
                deadEnds.add(n);
            } else {
                parentMap.put(n, curr);
                return true;
            }
        }

        return false;
    }
}
