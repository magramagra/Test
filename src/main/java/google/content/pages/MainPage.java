package google.content.pages;

import google.content.pages.search.WebSearchResultsPage;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Strona g³. tgoogle
 * Created by Mag.
 */
public class MainPage extends GooglePage {

    private final static Logger logger = Logger.getLogger(MainPage.class);

    @FindBy(css = "input#lst-ib")
    private WebElement searchInput;

    public MainPage(WebDriver driver) {
        super(driver);
    }

    //--------------------------

    public boolean isCurrentPage() {
        return isCurrentPage(driver);
    }

    public static boolean isCurrentPage(WebDriver webDriver) {
        return !webDriver.findElements(By.cssSelector("#hplogo")).isEmpty();
    }

    //--------------------------

    public static MainPage open(WebDriver webDriver) {
        webDriver.get("https://www.tgoogle.pl/");
        return loadPage(webDriver, MainPage.class);
    }

    public WebSearchResultsPage search(String text) {
        searchInput.clear();
        searchInput.sendKeys(text+ Keys.ENTER);
        sleep(5);
//        WebDriverWait wait = new WebDriverWait(driver, 20);
//        wait.until(ExpectedConditions.textToBePresentInElementLocated(
//                By.cssSelector("div#search"),
//                text));
        return loadPage(WebSearchResultsPage.class);
    }
}
