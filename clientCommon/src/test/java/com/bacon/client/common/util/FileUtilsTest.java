package com.bacon.client.common.util;

import java.io.File;
import java.util.List;

/**
 * Created by bacon on 2017/4/6.
 */
public class FileUtilsTest {
    public static void main(String[] args){
        //测试获取MD5
        String filePath = "G:\\大三下\\Message Systems\\clientThrift\\clientCommon\\src\\test\\java\\com\\bacon\\client\\common\\util\\FileUtilsTest.java";
        System.out.println(FileUtils.getFileMD5(filePath));

        //测试获取全部文件列表
        String path1 = "G:\\大三下\\Message Systems\\clientThrift\\clientCommon\\src\\main\\java\\com\\bacon\\client\\common";
        List<String> list1 = FileUtils.getAllFiles(path1);
        System.out.println("Path1:");
        for (int i = 0; i<list1.size();i++)
        {
            System.out.println(list1.get(i));
        }

        String path2 = "G:\\大三下\\Message Systems\\clientThrift\\clientCommon\\src\\main\\java\\com\\bacon\\client\\common\\entity";
        List<String> list2 = FileUtils.getAllFiles(path2);
        System.out.println("Path2:");
        for (int i = 0; i<list2.size();i++)
        {
            System.out.println(list2.get(i));
        }

        String path3 = "G:\\大三下\\Message Systems\\文档";
        List<String> list3 = FileUtils.getAllFiles(path3);
        System.out.println("before sorted, Path3:");
        for (String path : list3)
        {
            File file = new File(path);
            System.out.println(path + "     " + file.length());
        }
        FileUtils.sortFileList(list3,true);
        System.out.println("after sorted, Path3:");
        for (String path : list3)
        {
            File file = new File(path);
            System.out.println(path + "     " + file.length());
        }
    }
}
