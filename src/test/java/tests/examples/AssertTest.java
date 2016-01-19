package tests.examples;

import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.number.OrderingComparison.greaterThan;
import static org.testng.Assert.assertTrue;

/**
 * Created by Mag.
 */
public class AssertTest {

    @Test
    public void test1() {
        int total = -1;
        assertTrue(total > 0, "Wartość");
    }

    @Test
    public void test2() {
        int total = -1;
        assertThat("Wartość", total, greaterThan(0));
    }
}
