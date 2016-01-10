package google.content;

import google.content.pages.Page;
import google.errors.AppException;
import google.utils.ContentUtil;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Kontent (strona lub fragment strony)
 * Created by Mag.
 */
public abstract class Content {

    private final static Logger logger = Logger.getLogger(Content.class);
    protected final WebDriver driver;

    //----------------

    protected Content(WebDriver driver) {
        this.driver = driver;
    }

    protected Content(Content content) {
        this(content.driver);
    }

    //----------------

    protected static <T extends Page> T loadPage(WebDriver webDriver, Class<? extends Page> klassPage) {
        return (T) ContentUtil.loadPage(webDriver, klassPage);
    }

    protected <T extends Page> T loadPage(Class<? extends Page> klassPage) {
        return (T) ContentUtil.loadPage(driver, klassPage);
    }

    protected void refresh() {
        try {
            PageFactory.initElements(driver, this);
        } catch (Exception e) {
            throw new AppException("Error on load " + getClass(), e);
        }
    }

    //----------------

    protected abstract WebElement findElement(By by);

    protected abstract List<WebElement> findElements(By by);

    protected WebElement findElement(Content content, By by) {
        return content.findElement(by);
    }

    protected WebElement findElement(By by, boolean expected) {
        WebElement element = findElement(by);
        if (element == null && expected) {
            throw new AppException("Bral elementu strony www (" + by + ")");
        }
        return element;
    }

    //----------------

    public boolean isPresent(By by) {
        return findElement(by) != null;
    }

    protected boolean isDisplayed(By by) {
        WebElement element = findElement(by);
        return element != null && element.isDisplayed();
    }

    protected boolean isPresentAndDisplayed(By by) {
        WebElement element = findElement(by);
        return element != null && element.isDisplayed();
    }

    //----------------

    protected void sleep(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
        }
    }

}
