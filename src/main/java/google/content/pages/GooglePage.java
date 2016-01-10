package google.content.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

/**
 * Strona tgoogle
 *
 * Created by Mag.
 */
public abstract class GooglePage extends Page {

    private final static Logger logger = Logger.getLogger(GooglePage.class);

    public GooglePage(WebDriver driver) {
        super(driver);
    }
}
