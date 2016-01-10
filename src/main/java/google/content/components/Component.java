package google.content.components;

import google.content.Content;
import google.content.pages.Page;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Komponent strony www
 * <p>
 * Created by Mag.
 */
public class Component extends Content {

    private final static Logger logger = Logger.getLogger(Component.class);

    private By by;
    private WebElement webElement;
    private final Content parent;

    //---------------------------

    public Component(Content parent, By by) {
        super(parent);
        this.by = by;
        this.parent = parent;
    }

    public Component(Content parent, WebElement webElement) {
        super(parent);
        this.webElement = webElement;
        this.parent = parent;
    }

    @Override
    protected WebElement findElement(By by) {
        return getWebElement().findElement(by);
    }

    @Override
    protected List<WebElement> findElements(By by) {
        return getWebElement().findElements(by);
    }

    //-------------------------------------------------------------------------------

    protected WebElement getWebElement() {
        if (webElement != null) {
            return webElement;
        }
        return findElement(getParent(), by);
    }

    //---------------------------

    protected Content getParent() {
        return parent;
    }

    public <T extends Page> T getPage() {
        Content parent = getParent();
        while (!(parent instanceof Page)) {
            parent = ((Component) parent).getParent();
        }
        return (T) parent;
    }

    //---------------------------

    public String getText() {
        return getWebElement().getText();
    }

    //---------------------------

}
