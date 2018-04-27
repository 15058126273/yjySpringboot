package com.yjy.test.game.util;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * 文件管理的工具类
 *
 * @author wdy
 * @version ：2016年12月8日 上午11:54:28
 */
public class FileUtils {

    /**
     * 遍历文件夹下所有的文件
     *
     * @param path
     * @author wdy
     * @version ：2016年12月8日 上午11:57:44
     */
    public static List<File> traverseFolder(String path) {
        int fileNum = 0, folderNum = 0;
        List<File> allFile = new ArrayList<File>();
        File file = new File(path);
        if (file.exists()) {
            LinkedList<File> list = new LinkedList<File>();
            File[] files = file.listFiles();
            for (File file2 : files) {
                if (file2.isDirectory()) {
//					System.out.println("文件夹:" + file2.getAbsolutePath());
                    list.add(file2);
                    folderNum++;
                } else {
                    allFile.add(file2);
                    fileNum++;
                }
            }
            File temp_file;
            while (!list.isEmpty()) {
                temp_file = list.removeFirst();
                files = temp_file.listFiles();
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                        list.add(file2);
                        folderNum++;
                    } else {
                        allFile.add(file2);
                        fileNum++;
                    }
                }
            }
        }
        System.out.println("文件夹共有:" + folderNum + ",文件共有:" + fileNum);
        return allFile;
    }

    /**
     * 读取文件内容
     *
     * @param file
     * @return
     * @author wdy
     * @version ：2016年12月9日 上午9:41:32
     */
    public static String txt2String(File file) {
        StringBuilder result = new StringBuilder();
        try {
//	            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            FileInputStream fInputStream = new FileInputStream(file);
            //code为上面方法里返回的编码方式
            InputStreamReader inputStreamReader = new InputStreamReader(fInputStream, "utf-8");
            BufferedReader br = new BufferedReader(inputStreamReader);

            String s = null;
            while ((s = br.readLine()) != null) {//使用readLine方法，一次读一行
                if (StringUtils.isNotBlank(s)) {
                    s += System.getProperty("line.separator");
                }
                result.append(s);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    /**
     * 将文件file 移动到target文件夹
     *
     * @param file
     * @param target
     * @return
     * @author wdy
     * @version ：2016年12月15日 下午4:19:19
     */
    public static boolean move2file(File file, String target) {
        if (null == file)
            return false;

        File targetFile = new File(target + file.getName());
        if (targetFile.exists()) {
            long times = new Date().getTime();
            targetFile = new File(target + times + file.getName());
        }
        boolean success = file.renameTo(targetFile);
        return success;
    }

    /**
     * 创建目录
     *
     * @param folder
     * @return
     * @author wdy
     * @version ：2016年12月15日 下午5:12:33
     */
    public static boolean createFolder(String folder) {
        File dir = new File(folder);
        if (dir.exists()) {
            return true;
        }
        if (!folder.endsWith(File.separator)) {
            folder = folder + File.separator;
        }
        // 创建目录
        if (dir.mkdirs()) {
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) {
//		String path = "E://songs";
//		traverseFolder(path);

        File file = new File("D:/tools/exported/exported_sns.json");
        String json = txt2String(file);
        System.out.println(json);

    }
}
