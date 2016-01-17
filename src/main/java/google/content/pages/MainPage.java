package google.content.pages;

import google.content.pages.search.WebSearchResultsPage;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Strona g�. Google
 * Created by Mag.
 */
public class MainPage extends GooglePage {

    private final static Logger logger = Logger.getLogger(MainPage.class);

    @FindBy(css = "input#lst-ib")
    private WebElement searchInput;

    public MainPage(WebDriver driver) {
        super(driver);
    }

    public boolean isCurrentPage() {
        return isCurrentPage(driver);
    }

    public static boolean isCurrentPage(WebDriver webDriver) {
        return !webDriver.findElements(By.cssSelector("#hplogo")).isEmpty();
    }

    /**
     * Otwarcie g�. strony google
     *
     * @param webDriver
     * @return
     */
    public static MainPage open(WebDriver webDriver) {
        webDriver.get("https://www.google.pl/");
        return loadPage(webDriver, MainPage.class);
    }

    /**
     * Wyszukanie w google
     * @param text - tekst do wyszukania
     * @return - zwraca stron� wynik�w wyszukiwania
     */
    public WebSearchResultsPage search(String text) {
        logger.info("Wyszukiwanie '"+text+"'");
        //wpisanie szukanego tekstu do wyszukiwarki
        searchInput.clear();
        searchInput.sendKeys(text + Keys.ENTER);
        //odczekanie 5s na za�adowanie strony wynik�w
        sleep(5);
        //zwraca stron� wynik�w wyszukiwania
        return loadPage(WebSearchResultsPage.class);
    }
}
