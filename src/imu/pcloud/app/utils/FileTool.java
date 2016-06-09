package imu.pcloud.app.utils;

import android.os.Environment;

import java.io.File;

/**
 * Created by guyu on 2016/6/8.
 */
public class FileTool {

    public  static void deleteDir() {
        String path= Environment.getExternalStorageDirectory()+"/ClipHeadPhoto";
        File file = new File(path);
        deleteAllFiles(file);
    }

    public static void delete(String path) {
        File file = new File(path);
        deleteAllFiles(file);
    }
    private static void deleteAllFiles(File root) {
        File files[] = root.listFiles();
        if (files != null)
            for (File f : files) {
                if (f.isDirectory()) { // 判断是否为文件夹
                    deleteAllFiles(f);
                    try {
                        f.delete();
                    } catch (Exception e) {
                    }
                } else {
                    if (f.exists()) { // 判断是否存在
                        deleteAllFiles(f);
                        try {
                            f.delete();
                        } catch (Exception e) {
                        }
                    }
                }
            }
    }
}
