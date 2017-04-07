package com.bacon.client.common.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;

import java.io.*;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by bacon on 2017/3/29.
 */
public class FileUtils {

    Logger logger = Logger.getLogger(FileUtils.class);

    //判断路径是文件还是文件夹，返回true为文件夹，返回false为文件
    public static boolean checkIsFolder(String path)
    {
        File dir = new File(path);
        if (dir.exists())
        {
            if (dir.isDirectory())
            {
                return true;
            }else {
                return false;
            }
        }
        return false;
    }

    //扫描文件夹路径下所有文件，返回文件列表
    public static List<String> getAllFiles(String path)
    {
        File rootDir = new File(path);
        String[] fileList = rootDir.list();
        List<String> allFileList = new ArrayList<>();
        for (int i = 0; i < fileList.length; i++)
        {
            String newPath = rootDir.getAbsolutePath()+"\\"+fileList[i];
            File childDir = new File(newPath);
            if (childDir.isFile())
            {
                allFileList.add(newPath);
            }else if (childDir.isDirectory())
            {
                List<String> childFileList = getAllFiles(newPath);
                allFileList.addAll(childFileList);
            }
        }
        return allFileList;
    }

    //获取特定路径文件的md5标识
    public static String getFileMD5(String path)
    {
        try {
            File file = new File(path);
            FileInputStream fileInputStream = new FileInputStream(file);
            MappedByteBuffer byteBuffer = fileInputStream.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(byteBuffer);
            BigInteger bigInteger = new BigInteger(1,md.digest());
            return bigInteger.toString(16);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //排序文件列表
    public static void sortFileList(List<String> list, boolean asc)
    {
        Collections.sort(list, new Comparator<String>() {
            @Override
            public int compare(String path1, String path2) {
                File file1 = new File(path1);
                File file2 = new File(path2);
                return (int) (file1.length() - file2.length());
            }
        });
    }
}
