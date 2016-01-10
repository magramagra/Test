package matchers;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * Matcher do spr. czy w string zawiera substring
 * bez brania pod uwagê wielkoœci liter
 * Created by Mag.
 */
public class CaseInsensitiveSubstringMatcher extends TypeSafeMatcher<String> {

    private final String subString;

    private CaseInsensitiveSubstringMatcher(final String subString) {
        this.subString = subString;
    }

    @Override
    protected boolean matchesSafely(final String actualString) {
        return actualString.toLowerCase().contains(this.subString.toLowerCase());
    }

    public void describeTo(final Description description) {
        description.appendText("containing substring \"" + this.subString + "\"");
    }

    @Factory
    public static Matcher<String> containsIgnoringCase(final String subString) {
        return new CaseInsensitiveSubstringMatcher(subString);
    }
}
