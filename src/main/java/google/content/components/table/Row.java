package google.content.components.table;

import google.content.Content;
import google.content.components.Component;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;

/**
 * Komponent wiersza (rekordu) tabeli
 */
public class Row extends Component {

    private final static Logger logger = Logger.getLogger(Row.class);

    public Row(Content parent, WebElement webElement) {
        super(parent, webElement);
    }



}
