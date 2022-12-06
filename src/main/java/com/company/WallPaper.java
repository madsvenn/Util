package com.company;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;


public class WallPaper {

    private static final ExecutorService executor = Executors.newFixedThreadPool(6);

    public static void main(String[] args) throws ParseException, InterruptedException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
        List<String> list = Collections.synchronizedList(new ArrayList<String>());

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("请输入wallpaper下载的地址");
            String str = scanner.next();
            File sfile = new File(str);
            System.out.println("请输入时间(格式为:年-月-日-小时-分钟)");
            String time = scanner.next();
            Date date = sdf.parse(time);
            System.out.println("请输入需要存放的地址");
            String str2 = scanner.next();
            File tfile = new File(str2);
            if (!tfile.exists() && !tfile.isDirectory()) {
                tfile = new File(tfile.getParentFile(), "wallpaper下载的视频");
                System.out.println(tfile.getAbsolutePath());
                tfile.mkdirs();
            }

            if (!sfile.exists() && !sfile.isDirectory()) {
                System.out.println("地址不对哦");
                return;
            }
            if (sfile.isDirectory()) {
                File[] fs = sfile.listFiles();
                File finalTfile = tfile;
                int tmp = 0;

                assert fs != null;
                /**
                 * *先统计文件的个数
                 */

                for (File x : fs) {
                    Date date1 = new Date(x.lastModified());
                    if (date1.compareTo(date) > 0) {
                        boolean flag = false;
                        String needFilename = x.getAbsolutePath() + "\\project.json";
                        if (new File(needFilename).exists()) {
                            flag = isNeedFile(needFilename);
                        }
                        if (flag) {
                            tmp++;
                        }
                    }
                }

                CountDownLatch cdl = new CountDownLatch(tmp);
                /**
                 *多线程进行复制
                 */
                Date startDate = new Date();
                for (File x : fs) {
                    executor.submit(
                            () -> {
                                Date date1 = new Date(x.lastModified());
                                if (date1.compareTo(date) > 0) {
                                    boolean flag = false;
                                    String needFilename = x.getAbsolutePath() + "\\project.json";
                                    if (new File(needFilename).exists()) {
                                        flag = isNeedFile(needFilename);
                                    }
                                    if (flag) {
                                        String filename = x.getAbsolutePath() + "\\" + Filename(needFilename);
                                        File tf = new File(finalTfile.getAbsoluteFile() + "\\" + Filename(needFilename));
                                        try {
                                            copyfile(new File(filename), tf);
                                            synchronized (list){
                                                list.add("成功");
                                            }
                                            System.out.println("将 " + Filename(needFilename) + "  复制到 " + finalTfile.getAbsolutePath() + " 中");
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                            System.out.println(Filename(needFilename) + "复制失败");
                                        }

                                        cdl.countDown();
                                    }
                                }
                            });
                }

                cdl.await();
                executor.shutdown();
                System.out.println("复制完成，共" + list.size() + "个文件");
                Date endDate = new Date();
                System.out.println(endDate.getTime()-startDate.getTime());
            }
            else {
                System.out.println("地址不对哦");
            }
        }

    }

    //复制文件
    public synchronized static void  copyfile(File fs, File ft) throws IOException {

        if (ft.exists()) {
            System.out.println(ft.getName() + "重复...." + "正在删除");
            Files.delete(ft.toPath());
        }
        Files.copy(fs.toPath(),ft.toPath(),StandardCopyOption.REPLACE_EXISTING);
//        FileInputStream fi = new FileInputStream(fs);
//        FileOutputStream fo = new FileOutputStream(ft);
//        BufferedInputStream bfi = null;
//        BufferedOutputStream bfo = null;
//        bfi = new BufferedInputStream(fi);
//        bfo = new BufferedOutputStream(fo);
//        int count = -1;
//        while ((count = bfi.read()) != -1) {
//            bfo.write(count);
//        }
//        bfo.flush();
//        bfo.close();
//        bfi.close();
    }


    //查找是否是所需的视频文件
    public static boolean isNeedFile(String filename) {

        String s = readJsonFile(filename);
        JSONObject jobj = JSON.parseObject(s);
        if (jobj.get("contentrating") != null && !jobj.get("contentrating").equals(""))
            return jobj.get("contentrating").toString().equals("Mature") && (jobj.get("type").toString().equals("Video") || jobj.get("type").toString().equals("video"));
        return false;
    }


    public static String Filename(String filename) {

        String s = readJsonFile(filename);
        JSONObject jobj = JSON.parseObject(s);
        return jobj.get("file").toString();
    }

    //读取json文件
    public static String readJsonFile(String fileName) {
        String jsonStr = "";
        try {
            File jsonFile = new File(fileName);
            FileReader fileReader = new FileReader(jsonFile);
            Reader reader = new InputStreamReader(new FileInputStream(jsonFile), "utf-8");
            int ch = 0;
            StringBuffer sb = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            jsonStr = sb.toString();
            return jsonStr;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


}


