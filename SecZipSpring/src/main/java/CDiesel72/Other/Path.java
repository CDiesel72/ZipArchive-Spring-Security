package CDiesel72.Other;

import java.io.File;
import java.io.IOException;

/**
 * Created by Diesel on 06.04.2019.
 */
public class Path {

    public static void createDir(String path) throws IOException {
        File file = new File(path);

        if (!file.exists() || !file.isDirectory()) {
            if (!file.mkdirs()) {
                throw new IOException();
            }
        }
    }

    public static File createFile(String path, String fileName) throws IOException {
        createDir(path);
        return new File(path, fileName);
    }
}
