package simple;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

/**
 * Created by Mag.
 */
public class SimpleSelenium {

    public static void main(String [ ] args) {
        //otw�rz przegladark�
        WebDriver driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        //otw�rz stron� Google
        driver.get("https://www.google.pl/");
        //wyszukaj "selenium"
        driver.findElement(By.id("lst-ib")).clear();
        driver.findElement(By.id("lst-ib")).sendKeys("selenium");
        //otw�rz stron� z wynik�w
        driver.findElement(By.linkText("Selenium - Web Browser Automation")).click();
        sleep(3);
        //zamknij przegl�dark�
        driver.quit();
    }

    private static void sleep(int seconds){
        try {
            Thread.currentThread().wait(seconds * 1000);
        }catch (Exception e) {}
    }

}
