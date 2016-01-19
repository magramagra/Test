package google.content.components.searchresults;

import google.content.Content;
import google.content.components.table.Row;
import google.content.components.table.Table;
import google.content.pages.external.ExternalPage;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Komponent : tabela wyników wyszukiwania stron www
 * Created by Mag.
 */
public class WebSearchResultsList extends Table {

    private final static Logger logger = Logger.getLogger(WebSearchResultsList.class);

    /**
     * Wiersz wyników wyszukiwania
     */
    public static class WebResultRow extends Row {

        private final By TITLE = By.cssSelector("h3 a");

        public WebResultRow(Content parent, WebElement webElement) {
            super(parent, webElement);
        }

        public String getTitle() {
            return findElement(TITLE, true).getText();
        }

        public ExternalPage open() {
            WebElement element = findElement(TITLE);
            element.click();
            sleep(5);
            return loadPage(ExternalPage.class);
        }
    }

    //-------------------

    public WebSearchResultsList(Content parent, By by) {

        super(parent, by, new Table.Data()
                        .setRobBy(By.cssSelector("li.ads-ad, div.g:not(#imagebox_bigimages)"))
                        .setRowClass(WebResultRow.class)
        );

    }

}
