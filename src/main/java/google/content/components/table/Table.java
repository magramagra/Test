package google.content.components.table;

import google.content.Content;
import google.content.components.Component;
import google.errors.AppException;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

/**
 * Komponent tabeli
 * <p>
 * Created by Mag.
 */
public class Table extends Component {

    private final static Logger logger = Logger.getLogger(Table.class);

    protected Data data;

    public static class Data {
        private Class<? extends Row> rowClass = Row.class;
        private By robBy = By.cssSelector("tbody tr");

        public Data setRowClass(Class<? extends Row> rowClass) {
            this.rowClass = rowClass;
            return this;
        }

        public Data setRobBy(By robBy) {
            this.robBy = robBy;
            return this;
        }
    }

    //-------------------------------------------------

    public Table(Content parent, By by) {
        this(parent, by, new Data());
    }

    public Table(Content parent, By by, Data data) {
        super(parent, by);
        this.data = data;
    }

    //----------------------------------

    public <T extends Row> List<T> getRows() {
        return (List<T>) getRows(data.rowClass);
    }

    public <T extends Row> List<T> getRows(Class<T> klass) {
        List<T> list = new ArrayList<T>();
        List<WebElement> elements = findElements(data.robBy);
        int i = 0;
        for (WebElement element : elements) {
            try {
                Constructor<T> constructor = klass.getConstructor(Content.class, WebElement.class);
                list.add(constructor.newInstance(this, element));
            } catch (Exception e) {
                throw new AppException("Error on getRows(" + klass.getSimpleName() + ") (on " + (i++) + ")", e);
            }
        }
        return list;
    }

    //----------------------------------

    public int getRowsCount() {
        return getRows().size();
    }

    public List<String> getRowsText() {
        return getRowsText(getRows());
    }

    public static <T extends Row> List<String> getRowsText(List<T> rows) {
        List<String> list = new ArrayList<String>();
        for (T row : rows) {
            list.add(row.getText());
        }
        return list;
    }

    //----------------------------------

    public <T extends Row> T getFirstRow() {
        return (T) getRows().get(0);
    }

}
