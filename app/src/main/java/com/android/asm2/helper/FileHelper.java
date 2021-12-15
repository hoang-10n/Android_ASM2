package com.android.asm2.helper;

import java.io.FileOutputStream;
import java.io.IOException;

public class FileHelper {
    private static final String FILE_NAME = "volunteer_list.txt";

    static public void save(String str) {
        FileOutputStream os = null;
        try {
            os = new FileOutputStream(FILE_NAME);
            os.write(str.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
