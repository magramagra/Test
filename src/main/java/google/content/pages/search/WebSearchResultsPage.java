package google.content.pages.search;

import google.content.components.searchresults.WebSearchResultsList;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Strona wyników wyszukiwania stron www w Google
 * Created by Mag.
 */
public class WebSearchResultsPage extends SearchResultsPage {

    private final static Logger logger = Logger.getLogger(WebSearchResultsPage.class);

    private final By RESULTS_LIST = By.cssSelector("div#search");

    public WebSearchResultsPage(WebDriver driver) {
        super(driver);
    }

    //----------------------------------

    public boolean isTableResultsPresent() {
        return isPresent(RESULTS_LIST);
    }

    public WebSearchResultsList getResultsList() {
        if(!isTableResultsPresent()) {
            throw new RuntimeException("Brak elementu z listą wyników");
        }
        return new WebSearchResultsList(this,RESULTS_LIST);
    }

}
