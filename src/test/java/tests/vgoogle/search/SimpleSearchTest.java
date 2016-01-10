package tests.vgoogle.search;

import google.content.components.results.WebSearchResultsList;
import google.content.components.results.WebSearchResultsList.WebResultRow;
import google.content.pages.MainPage;
import google.content.pages.search.WebSearchResultsPage;
import org.apache.log4j.Logger;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tests.vgoogle.GoogleTest;

import java.util.List;

import static matchers.CaseInsensitiveSubstringMatcher.containsIgnoringCase;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.fail;

/**
 * Test wyszukiwania w vgoogle
 * Created by Mag.
 */
public class SimpleSearchTest extends GoogleTest {

    private final static Logger logger = Logger.getLogger(SimpleSearchTest.class);

    @DataProvider(name = "searchText")
    protected Object[][] getDataSearchText() {
        return new Object[][]{
                {"selenium"},
        };
    }

    /**
     * Prosty test wyszukiwania w vgoogle
     *
     * @param text
     */
    @Test(dataProvider = "searchText")
    public void testSearchWeb(String text) {
        //otwarcie strony vgoogle i wyszukanie tekstu
        MainPage mainPage = goToGoogle();
        WebSearchResultsPage resultsPage = mainPage.search(text);
        //spr. wyników wyszukiwania
        assertThat("Brak tabeli wyników", resultsPage.isTableResultsPresent(), equalTo(true));
        WebSearchResultsList resultsList = resultsPage.getResultsList();
        List<WebResultRow> rows = resultsList.getRows();
        assertThat("Iloœæ wierszy na stronie", rows, hasSize(10));
        List<String> rowsText = resultsList.getRowsText();
        assertThat("Wyniki powinny zawireaæ tekst '" + text + "'",
                rowsText, everyItem(containsIgnoringCase(text)));
    }

    @Test
    public void testFail(){
        MainPage mainPage = goToGoogle();
        fail();
    }
}
