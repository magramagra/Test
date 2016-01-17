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

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Nadklasa dla testów goole
 * Created by Mag.
 */
public abstract class GoogleTest {

    private final static Logger logger = Logger.getLogger(GoogleTest.class);
    private WebDriver driver;

    /**
     * Operacje wykonywane przed uruchomieniem testów z danej klasy
     * - Otwarcie przegl¹darki
     */
    @BeforeClass
    public void beforeClass() {
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
    }

    /**
     * Operacje wykonywane po zakoñczeniu testów z danej klasy
     * - Zamkniêcie przegl¹darki
     */
    @AfterClass
    public void afterClass() {
        driver.quit();
    }

    @AfterMethod
    public void afterMethod(ITestResult result) {
        Reporter.setCurrentTestResult(result);
        String methodName = result.getName();
        if (!result.isSuccess()) {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");

            File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            try {
                String path = "screenshots";
                File file = new File(path + "/" + methodName + "_" + formater.format(calendar.getTime()) + ".png");
                FileUtils.copyFile(scrFile, file);
                System.setProperty("org.uncommons.reportng.escape-output", "false");
                Reporter.setEscapeHtml(false);
                //Reporter.log("<a href='" + file.getAbsolutePath() + "'> <img src='" + file.getAbsolutePath() + "' height='100' width='100'/> </a>");
                Reporter.log("<a " +
                        "href='" + file.getAbsolutePath() + "'> " +
                        "<img src=\"./../../../../../" + path + "/" + file.getName() + "\" height=\"50%\" width=\"50%\"/>" +
                        " </a>");
                Reporter.setEscapeHtml(false);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

        Reporter.setCurrentTestResult(null);
    }

    /**
     * Przejœcie do g³. stony Google
     *
     * @return
     */
    protected MainPage goToGoogle() {
        return MainPage.open(driver);
    }
}
