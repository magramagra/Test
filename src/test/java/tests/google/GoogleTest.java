package tests.google;

import google.content.pages.MainPage;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import utils.WebDriverInitUtil;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Nadklasa dla testów google
 * Created by Mag.
 */
public abstract class GoogleTest {

    private final static Logger logger = Logger.getLogger(GoogleTest.class);
    private WebDriver driver;

    /**
     * Operacje wykonywane przed uruchomieniem testów z danej klasy
     * - Otwarcie przeglądarki
     */
    @BeforeClass
    public void beforeClass() {
        //driver = new FirefoxDriver();
        driver = WebDriverInitUtil.initDriverType(FirefoxDriver.class);
        driver.manage().window().maximize();
    }

    /**
     * Operacje wykonywane po zakończeniu testów z danej klasy
     * - Zamknięcie przeglądarki
     */
    @AfterClass
    public void afterClass() {
        driver.quit();
    }

    /**
     * Operacje wykonywane po metodzie testowej
     * - Wykonanie zdjęcia, gdy test nie przeszedł
     *
     * @param result
     */
    @AfterMethod
    public void afterMethod(ITestResult result) {
        Reporter.setCurrentTestResult(result);
        String methodName = result.getName();
        if (!result.isSuccess()) {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");

            File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            try {
                String folderName = "screenshots";
                String path = "build/reports/tests/" + folderName;

                File folder = new File(path);
                if (!folder.exists()) {
                    folder.mkdir();
                    logger.debug("folder created = " + folder);
                }
                String ssName = methodName + "_" + formater.format(calendar.getTime()) + ".png";
                File file = new File(folder, ssName);
                FileUtils.copyFile(scrFile, file);

                System.setProperty("org.uncommons.reportng.escape-output", "false");
                Reporter.setEscapeHtml(false);
                String href = "./../" + folderName + "/" + file.getName();
                logger.info("<a href='" + href + "'>Image error</a>");
                Reporter.log("<a " +
                        "href='" + href + "'> " +
                        "<img src=\"" + href + "\" height=\"50%\" width=\"50%\"/>" +
                        " </a>");
                Reporter.setEscapeHtml(false);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

        Reporter.setCurrentTestResult(null);
    }

    /**
     * Przejście do gł. stony Google
     *
     * @return
     */
    protected MainPage goToGoogle() {
        return MainPage.open(driver);
    }
}
