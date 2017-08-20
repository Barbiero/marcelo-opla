package arquitetura.io;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 * @author edipofederle<edipofederle@gmail.com>
 */
public class CopyFile {

    private static Logger LOGGER = LogManager.getLogger(CopyFile.class.getName());

    public static void copyFile(File source, File destFile) {
        LOGGER.info("copyFile(File sourceFile, File destFile) - Enter");
        LOGGER.info("SourceFile: " + source);
        LOGGER.info("DestFile: " + destFile);
        Path src = source.toPath();
        Path dest = destFile.toPath();

        try {
            Files.copy(src, dest, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        LOGGER.info("copyFile(File sourceFile, File destFile) - Exit");

    }

}