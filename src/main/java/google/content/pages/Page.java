package google.content.pages;

import google.content.Content;
import google.errors.AppException;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Strona www
 * Created by Mag.
 */
public abstract class Page extends Content {

    private final static Logger logger = Logger.getLogger(Page.class);

    public Page(WebDriver driver) {
        super(driver);
    }

    //----------------------------------------

    protected <T extends Page> T loadPage(Class<? extends Page> klassPage) {
        return super.loadPage(klassPage);
    }

    //------------------

    @Override
    protected WebElement findElement(By by) {

        try {
            WebElement element = driver.findElement(by);
            element.isDisplayed();
            return element;
        }catch (Exception e) {
            throw new AppException("Brak elementu www ("+by+")", e);
        }
    }

    @Override
    protected List<WebElement> findElements(By by) {
        return driver.findElements(by);
    }

    //------------------

    public String getTitle() {
        return driver.getTitle();
    }

}
