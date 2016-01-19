package tests.google.search;

import google.content.components.searchresults.WebSearchResultsList.WebResultRow;
import google.content.pages.MainPage;
import google.content.pages.external.ExternalPage;
import google.content.pages.search.WebSearchResultsPage;
import org.apache.log4j.Logger;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tests.google.GoogleTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.fail;

/**
 * Test wyszukiwania w google
 * Created by Mag.
 */
public class SimpleSearchTest extends GoogleTest {

    private final static Logger logger = Logger.getLogger(SimpleSearchTest.class);

    @DataProvider(name = "searchText")
    protected Object[][] getDataSearchText() {
        return new Object[][]{
                {"selenium", "Selenium - Web Browser Automation"},
        };
    }

    /**
     * Test prostego wyszukiwania w Google
     *
     * @param searchText        - tekst do wyszukania
     * @param expectedPageTitle - oczekiwany Tytuł znalezionej strony
     */
    @Test(dataProvider = "searchText")
    public void testSearchWeb(String searchText, String expectedPageTitle) {
        //otwarcie strony google
        MainPage mainPage = goToGoogle();
        //wyszukanie zadanego tesktu w wyszukiwarce google
        WebSearchResultsPage resultsPage = mainPage.search(searchText);
        //spr. czy są jakieś wyniki wyszukiwania
        assertThat("Brak tabeli wyników wyszukiwania", resultsPage.isTableResultsPresent(), equalTo(true));
        //otwarcie pierwszej z znalezionych stron
        WebResultRow row = resultsPage.getResultsList().getFirstRow();
        ExternalPage externalPage = row.open();
        //spr. czy to otwarto zamierzoną stronę (po jej tytule)
        assertThat("Tytuł strony", externalPage.getTitle(), equalTo(expectedPageTitle));
    }

    /**
     * Test, który zawsze nie przechodzi
     */
    @Test
    public void testFail() {
        MainPage mainPage = goToGoogle();
        fail("OK, test miał nie przejść");
    }

    @Test
    public void testOpenMainPage() {
        MainPage mainPage = goToGoogle();
    }

    @Test
    public void skipTest() {
        MainPage mainPage = goToGoogle();
        throw new SkipException("Test został pominięty celowo");
    }
}
