package tests.examples;

import org.apache.log4j.Logger;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * Annotation Test
 * Created by Mag.
 */
public class AnnotationTest {
    private final static Logger logger = Logger.getLogger(AnnotationTest.class);

    @BeforeSuite
    public void beforeSuite() {
        logger.info("BeforeSuite");
    }

    @AfterSuite
    public void afterSuite() {
        logger.info("AfterSuite");
    }

    @BeforeTest
    public void beforeTest() {
        logger.info("BeforeTest");
    }

    @AfterTest
    public void afterTest() {
        logger.info("AfterTest");
    }

    @BeforeClass
    public void beforeClass() {
        logger.info("BeforeClass");
    }

    @AfterClass
    public void afterClass() {
        logger.info("AfterClass");
    }

    @BeforeMethod
    public void beforeMethod() {
        logger.info("BeforeMethod");
    }

    @AfterMethod
    public void afterMethod() {
        logger.info("AfterMethod");
    }

    @Test
    public void test2() {
        logger.info("test2");
    }

    @Test
    public void test1() {
        logger.info("test1");
    }
}
