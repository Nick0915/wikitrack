import java.util.*;

import java.net.URL;

public class IterativeDeepeningCrawler extends Crawler {
    protected Stack<URL> searchStack;

    public IterativeDeepeningCrawler(URL startUrl, URL endUrl) {
        super(startUrl, endUrl);
    }

    @Override
    public void crawl() {
        // for now, only search with a max depth of 10
        for (int i = 1; i < 10; i++) {
            searchStack = new Stack<>();
            searchStack.add(startUrl);
            if (dfs(i)) {
                System.out.println("Found!!!");
                
                return;
            }
        }
    }

    private boolean dfs(int depth) {

        return false;
    }
}
