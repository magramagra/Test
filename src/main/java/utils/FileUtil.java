package utils;

import google.errors.AppException;
import org.apache.log4j.Logger;
import org.codehaus.plexus.archiver.tar.TarBZip2UnArchiver;
import org.codehaus.plexus.logging.console.ConsoleLogger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author Magda Sielewicz
 */
public class FileUtil {

    private final static Logger logger = Logger.getLogger(FileUtil.class);
    private static final String SEPERATOR = "/";

    private static final String NEW_LINE_SEPARATOR = "\n";
    private static final char COLUMN_DELIMITER = ';';

    public static void mkdirs(String name) {
        try {
            File file = new File(name);
            file.mkdirs();//foldery
        } catch (Exception e) {
            throw new AppException("Error on create folder '" + name + "'", e);
        }
    }

    public static void createFile(String name) {
        try {
            //file - ostatni człon to nazwa plik, nie katalog
            File file = new File(name);
            if (!file.exists()) {
                //katalogi
                int nr = name.lastIndexOf(SEPERATOR);
                if (nr != -1) {
                    String filder = name.substring(0, nr);
                    name = name.substring(nr + 1);
                    mkdirs(filder);
                }
                //plik
                file.createNewFile();
            }
        } catch (Exception e) {
            throw new AppException("Error on create file '" + name + "'", e);
        }
    }

    public static File download(String downloadUrl, String pathSave, boolean refresh) {
        File fileSave = new File(pathSave);
        if (refresh || !fileSave.exists()) {
            logger.info("download " + downloadUrl);
            final int BUFFER_SIZE = 4096;
            long startTime = System.currentTimeMillis();
            try {
                URL url = new URL(downloadUrl);
                URLConnection conn = url.openConnection();
                InputStream inputStream = conn.getInputStream();
                long filesize = conn.getContentLength();
                logger.info("Size of the file (" + fileSave + ") to download in kb is: " + filesize / 1024);

                //Jeżeli jest już taki plik i chcemy pobrać nową wersje -> usuń starą
                if (refresh) {
                    if (fileSave.exists() && !fileSave.delete()) {
                        throw new AppException("File was not deleted (" + fileSave + ")");
                    }
                }

                if (!fileSave.exists()) {
                    FileUtil.createFile(fileSave.getAbsolutePath());
                }
                FileOutputStream outputStream = new FileOutputStream(fileSave);

                byte[] buffer = new byte[BUFFER_SIZE];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                long endTime = System.currentTimeMillis();
                logger.info("File (" + fileSave + ") downloaded, Download time " + ((endTime - startTime) / 1000) + " s");
                outputStream.close();
                inputStream.close();
            } catch (IOException ex) {
                ex.printStackTrace();
                throw new AppException("Error on downloading (" + fileSave + ")", ex);
            }
        }
        //spr. czy jest plik
        if (!fileSave.exists()) {
            throw new AppException("No " + fileSave);
        }
        return fileSave;
    }

    public static File unpack(File archiveFile, File destDir) {
        logger.info("unpack " + archiveFile);

        //Folder do którego mamy rozpakować (najlepiej jakiś folder wyjątkowy tylko po to)
        File dirUnique = destDir;
        if (!destDir.exists()) {
            destDir.mkdirs();
            if (!destDir.exists()) {
                throw new AppException("No dest. directory" + destDir);
            }
        } else {
            dirUnique = new File(destDir.getAbsolutePath() + SEPERATOR
                    + DateUtil.getCurrentDateAndTime("yyyy.MM.dd HH.mm.ss"));
            dirUnique.mkdir();
            if (!dirUnique.exists()) {
                throw new AppException("No dest. directory" + dirUnique);
            }
        }

        //rozpakuj
        TarBZip2UnArchiver ua = new TarBZip2UnArchiver(archiveFile);
        ua.enableLogging(new ConsoleLogger(5, ""));
        ua.setIgnorePermissions(true);
        ua.setOverwrite(true);
        ua.setDestDirectory(dirUnique);
        ua.setUseJvmChmod(true);
        ua.extract();

        //czy są jakieś pliki rozpakowane
        if (dirUnique.listFiles().length == 0) {
            throw new AppException("No files unpacked in '" + dirUnique + "'");
        }

        return dirUnique;
    }

}
