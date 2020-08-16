package com.zhang.library.utils.utils.context;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * 文件工具类
 *
 * @author ZhangXiaoMing 2020-06-19 10:26 星期五
 */
public class FileUtils extends ContextUtils {

    /** 获取应用的缓存路径 */
    public static String getCachePath() {
        try {
            return Objects.requireNonNull(get().getExternalCacheDir()).getPath() + File.separator;
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }

    /** 获取外存路径 */
    public static String getExternalStoreageDirectory() {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
            throw new RuntimeException("External storage state must be mounted!");

        return Environment.getExternalStorageDirectory().getPath() + File.separator;
    }

    /**
     * 获取指定类型的文件夹
     *
     * @param type 指定的文件类型，例如 {@linkplain Environment#DIRECTORY_MOVIES}，{@linkplain
     *             Environment#DIRECTORY_MUSIC}等
     */
    public static String getExternalStoreageDirectory(String type) {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
            throw new RuntimeException("External storage state must be mounted!");

        return Environment.getExternalStoragePublicDirectory(type).getPath() + File.separator;
    }

    /**
     * 获得指定路径的文件/文件夹的上一级路径
     *
     * @param path 指定路径的文件/文件夹
     */
    public static String getParentPath(String path) {
        File file = new File(path);
        if (!file.exists()) {
            throw new NullPointerException("The path[=" + path + "] does exists!");
        }
        return file.getParent();
    }

    /**
     * 删除问阿金
     *
     * @param path 文件路径
     */
    public void deleteFile(String path) {
        deleteFile(new File(path));
    }

    /**
     * 删除文件
     *
     * @param file 文件
     */
    public void deleteFile(File file) {
        if (file == null || !file.exists()) {
            return;
        }
        if (file.isFile()) {
            file.delete();
        } else {
            File[] files = file.listFiles();
            if (files != null && files.length > 0) {
                for (File child : files) {
                    deleteFile(child);
                }
            }
        }
    }

    /**
     * 扫描文件
     *
     * @param filePath 文件路径
     */
    public static void fileScan(String filePath, Activity activity) {
        Uri data = Uri.parse("file://" + filePath);
        activity.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, data));
    }

    /**
     * 获取指定目录的子目录
     *
     * @param path 指定目录
     */
    public static List<File> getSubdirectory(String path) {
        File fileDir = new File(path);
        if (!fileDir.exists()) {
            throw new IllegalArgumentException("The file path does not exists!");
        }
        if (fileDir.isFile()) {
            throw new IllegalArgumentException("The file must be a directory!");
        }

        List<File> dirList = new ArrayList<>();
        File[] dirs = fileDir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File child) {
                return !child.isFile();
            }
        });

        if (dirs != null && dirs.length > 0) {
            dirList.addAll(Arrays.asList(dirs));
        }

        Collections.sort(dirList);
        return dirList;
    }

    /**
     * 获取指定目录下所有的文件
     *
     * @param path 指定目录
     */
    public static List<File> getChildFile(String path) {
        File fileDir = new File(path);

        if (!fileDir.exists()) {
            throw new IllegalArgumentException("The file path does not exists!");
        }
        if (fileDir.isFile()) {
            throw new IllegalArgumentException("The file must be a directory!");
        }

        List<File> fileList = new ArrayList<>();
        File[] dirs = fileDir.listFiles();

        if (dirs != null && dirs.length > 0) {
            fileList.addAll(Arrays.asList(dirs));
        }

        Collections.sort(fileList, new Comparator<File>() {

            @Override
            public int compare(File o1, File o2) {
                boolean isFile1 = o1.isFile();
                boolean isFile2 = o2.isFile();
                if ((isFile1 && isFile2) || (!isFile1 && !isFile2)) {
                    // 两个都是文件 或者 两个都是目录
                    String name1 = o1.getName();
                    String name2 = o2.getName();
                    return name1.compareToIgnoreCase(name2);
                } else if (!isFile1) {
                    // o1是目录，o2是文件
                    return -1;
                } else {
                    // o1是文件，o2是目录
                    return 1;
                }
            }
        });
        return fileList;
    }
}
