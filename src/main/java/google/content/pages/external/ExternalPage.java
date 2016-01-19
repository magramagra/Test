package google.content.pages.external;

import google.content.pages.Page;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

/**
 * Strona zewnętrzna (nie podstrona google)
 * Created by Mag.
 */
public class ExternalPage extends Page {

    private final static Logger logger = Logger.getLogger(ExternalPage.class);

    public ExternalPage(WebDriver driver) {
        super(driver);
    }
}
