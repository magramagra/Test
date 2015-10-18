package myapi.logger;


import org.apache.log4j.*;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;


/**
 * Created by Magdalena on 18.10.2015.
 */
public class MyLogger {

    static private FileHandler fileTxt;
    static private SimpleFormatter formatterTxt;

    static public void setup() throws IOException {
        Logger logger = Logger.getRootLogger();
        logger.setLevel(Level.ALL);

        fileTxt = new FileHandler("results/logs/Logging.txt");

        //Define log pattern layout
        PatternLayout layout = new PatternLayout("%d{ISO8601} [%t] %-5p %c %x - %m%n");

        //Add console appender to root logger
        logger.addAppender(new ConsoleAppender(layout));

        try {
            //Define file appender with layout and output log file name
            RollingFileAppender fileAppender = new RollingFileAppender(layout, "results/logs/Logging.txt");

            //Add the appender to root logger
            logger.addAppender(fileAppender);
        } catch (IOException e) {
            System.out.println("Failed to add appender !!");
        }

    }

}
