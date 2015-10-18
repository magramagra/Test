package tests.examples;

import org.testng.annotations.Test;
import tests.BaseTest;

/**
 * Created by Magdalena on 20.09.2015.
 */
public class TestParameters extends BaseTest {

    @Test
    public void test1() {
        System.out.println("test");
        LOGGER.info("TEST 1");
    }

    @Test
    public void test2() {
        System.out.println("test");
        LOGGER.info("TEST 2");
    }
}
