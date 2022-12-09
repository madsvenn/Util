package com.company;

import java.io.File;
import java.util.Scanner;

public class FileCount {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入路径");
        String str = scanner.next();
        File file = new File(str);

        File[] files = file.listFiles();
        int count =0;
        System.out.println(file);
        System.out.println(FileC(file));
    }

    public static int FileC(File file){
        int count = 0;
        System.out.println(file.getName());
        File[] files = file.listFiles();
        for (File x: files){
            if(x.isDirectory()){
                count += FileC(x);
            }
            if (x.isFile()&&!x.getName().equals("1")){
                count++;
            }
        }
        return count;
    }
}
