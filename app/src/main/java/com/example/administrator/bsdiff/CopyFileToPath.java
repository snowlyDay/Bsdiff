package com.example.administrator.bsdiff;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Administrator on 2017/8/28.
 */

public class CopyFileToPath {

    private static int BUFFER_SIZE = 4096;

    public static final boolean copyToPath(File source, File dest) {
        if (source == null || dest == null) {
            return false;
        }
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(source);
            final int fileSize = is.available();
            if (dest.exists()) {
                if (dest.length() == fileSize) {
                    return true;
                } else {
                    dest.delete();
                }
            }
            dest.getParentFile().mkdirs();
            if (dest.createNewFile()
                    && dest.exists()) {
                os = new FileOutputStream(dest);
                int size = 0;
                byte[] buffer = new byte[BUFFER_SIZE];
                while ((size = is.read(buffer)) > 0) {
                    os.write(buffer, 0, size);
                }
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

//    public static File getSDCardCachePath() {
//
//    }
}

