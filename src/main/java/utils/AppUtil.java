package utils;

import google.errors.AppException;

/**
 * @author Magda Sielewicz
 */
public class AppUtil {

    public static String getNameOS() {
        return System.getProperty("os.name").toLowerCase();
    }

    public static String getVersionOS() {
        String osname = getNameOS();
        String osarch = System.getProperty("os.arch"); // wersja systemu
        if (osname.equals("linux")) {
            //Linux has i386 or amd64
            return (osarch.equals("i386") ? "i686" : "x86_64");
        } else if (osname.contains("win")) {
            throw new AppException("TO DO AppUtil.getVersionOS " + osname);
        } else {
            throw new AppException("TO DO AppUtil.getVersionOS " + osname);
        }
    }

}
