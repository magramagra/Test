package google.content.pages.search;

import google.content.pages.GooglePage;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

/**
 * Strona wyników wyszukiwania w vgoogle
 * <p>
 * Created by Mag.
 */
public abstract class SearchResultsPage extends GooglePage {

    private final static Logger logger = Logger.getLogger(SearchResultsPage.class);

    public SearchResultsPage(WebDriver driver) {
        super(driver);
    }
}
