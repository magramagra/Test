package google.utils;

import google.content.pages.GooglePage;
import google.content.pages.Page;
import google.errors.AppException;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by Mag.
 */
public class ContentUtil {

    private final static Logger logger = Logger.getLogger(ContentUtil.class);

    public static <T extends Page> T loadPage(WebDriver webDriver, Class<? extends Page> klassPage){
        try{
            return (T) PageFactory.initElements(webDriver, klassPage);
        }catch (Exception e){
            throw new AppException("Error on load page "+klassPage,e);
        }
    }
}
