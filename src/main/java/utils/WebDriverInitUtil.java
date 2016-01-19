package utils;

import google.errors.AppException;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * @author Magda Sielewicz
 */
public class WebDriverInitUtil {

    private final static Logger logger = Logger.getLogger(WebDriverInitUtil.class);
    private static String DISPLAY = ":0"; // ekran
    public static final int BROWSER_TIMEOUT = (int) SECONDS.toMillis(60 * 5);
    public static final int TIMEOUT = (int) SECONDS.toMillis(60 * 3);
    public static final String FIREFOX_URL = "https://ftp.mozilla.org/pub/mozilla.org/firefox/releases";
    public static final String FIREFOX_VERSION = "41.0.2";
    public static final String FIREFOX_LANG = "pl";
    private static final long DEFAULT_LONG_TIMEOUT_IN_SECONDS = 120;

    public static WebDriver initDriverType(Class<? extends WebDriver> klass) {
        logger.info(" initDriverType('" + klass + "')");
        WebDriver driver;
        if (klass.equals(FirefoxDriver.class)) {
            driver = initDriverFirefox();
        } else {
            throw new AppException("initDriverType() - to do " + klass);
        }
        //powiększ
        driver.manage().window().maximize();
        if (false) {
            // uwaga: rozmiar okna na jenkinsie jest mały (przy maksymalizacji chyba 768, 548)
            // czasem trzeba włączyć takie małe okno, żeby lokalnie odtworzyć problem
            driver.manage().window().setSize(new Dimension(768, 548));
        }

        //logger.debug("window.getSize: {}", driver.manage().window().getSize());
        driver.manage().timeouts().pageLoadTimeout(DEFAULT_LONG_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS);
//        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
//        driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
        return driver;
    }


    public static WebDriver initDriverFirefox() {
        //---- Pobranie wersji przeglądarki z netu --------
        String version = ""; //todo
        String language = "";
        FirefoxBinary firefox = getFirefoxBinary(false, FIREFOX_VERSION, FIREFOX_LANG);

        //------------- ustawienia ------------------------
        firefox.setTimeout(BROWSER_TIMEOUT);
        if (DISPLAY != null) {
            firefox.setEnvironmentProperty("DISPLAY", DISPLAY);
            //firefox.setEnvironmentProperty(ServerJsonValues.BROWSER_TIMEOUT.getKey(), "" + BROWSER_TIMEOUT);
            //firefox.setEnvironmentProperty(ServerJsonValues.CLIENT_TIMEOUT.getKey(), "" + TIMEOUT);
        }

        //------------- ustawienie profilu -----------------
        FirefoxProfile firefoxProfile = new FirefoxProfile();
        firefoxProfile.setPreference("app.update.enabled", false);
        firefoxProfile.setPreference("browser.tabs.autoHide", true);
//        //Firebug
//        boolean ifAddFireBug = ApplicationProperties.FIREFOX_FIREBUG.get(Boolean.class);
//        if (ifAddFireBug) {
//            addFirebugHidden(firefoxProfile, false);
//        }

        //------------- uruchomienie przegladarki ----------
        return new FirefoxDriver(firefox, firefoxProfile);
    }

    private static FirefoxBinary getFirefoxBinary(boolean refresh, String version, String language) {
        final String system = AppUtil.getNameOS() + "-" + AppUtil.getVersionOS(); // OS
        final String dirMainName = System.getProperty("java.io.tmpdir");
        final String SEPERATOR = "/";
        final String extension = ".tar.bz2";

        String descDirName = "firefox-" + version + "." + system;
        String descPath = dirMainName + SEPERATOR + descDirName;
        File descDir = new File(descPath);
        String binaryPath = descPath + SEPERATOR + "firefox" + SEPERATOR + "firefox-bin";
        File ffBinary = new File(binaryPath);

        logger.info("getFirefoxBinary " + version + " " + language + " " + refresh);

        if (refresh || !descDir.exists() || !ffBinary.exists() || !ffBinary.canExecute()) {
            // albo robimy refresh, albo coś jest nie tak i powtarzamy pobieranie i rozpakowywanie ff

            // usuń folder DESC, jeżeli jest
            if (descDir.exists()) {
                try {
                    FileUtils.deleteDirectory(descDir);
                    logger.debug("Directory " + descDir + " deleted");
                } catch (IOException e) {
                    throw new AppException("Failed to delete directory " + descDir + ", exception: " + e, e);
                }
            }

            // pobierze firefoxa z ftp
            File archiveFile = downloadFirefox(version, system, language, true);

            // rozpakuj
            FileUtil.unpack(archiveFile, descDir);
            logger.debug(archiveFile + " unpacked to directory " + descDir);

            // spr. czy jest binarka
            if (!ffBinary.exists()) {
                throw new AppException("Binary " + ffBinary.getAbsolutePath() + " does not exist");
            }

            // uprawnienia do uruchomienia dajemy dla wszystkich (drugi parametr=false)
            if (!ffBinary.setExecutable(true, false)) {
                throw new AppException("Failed to make executable " + ffBinary);
            }
        }

        return new FirefoxBinary(ffBinary);
    }

    private static File downloadFirefox(String version, String system, String language, boolean refresh) {
        logger.info("downloadFirefox " + version + " " + language + " " + refresh);
        String fileNameSave = "firefox-" + version + "." + system + ".tar.bz2";
        String dirNameSave = System.getProperty("java.io.tmpdir");
        String filePath = dirNameSave + "/" + fileNameSave;
        String url = FIREFOX_URL
                + "/" + version + "/" + system + "/" + language + "/firefox-" + version + ".tar.bz2";

        return FileUtil.download(url, filePath, refresh);
    }
}
