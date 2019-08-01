package com.jun.springbootstorm.util;

import com.alibaba.fastjson.JSON;
import com.jun.springbootstorm.dao.mapper.bo.TestUser;
import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

public class IoUtil {

    private static String encoding = "UTF-8";

    public static List<String> normalFileRead(String srcPathName){
        List<String> list = new ArrayList<>();
        InputStream in = null;
        BufferedReader reader = null;
        String[] fieldNames = null;
        Long readStartTime = System.currentTimeMillis();
        try {
            String str = null;
            in = new FileInputStream(FileUtils.getFile(new String[]{srcPathName}));
            reader = new BufferedReader(new InputStreamReader(in, Charsets.toCharset(encoding)));
            StringBuilder fileContent = new StringBuilder();
            while ((str = reader.readLine())!= null) {
                list.add(str);
            }
            //writeFile(destPathName,fileContent.toString());
            reader.close();
            Long readEndTime = System.currentTimeMillis();
            System.out.println(readEndTime - readStartTime);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                //
                System.out.println("close bad!");
            }
        }
        return list;
    }

    /**
     * 写文件
     * @param filePath
     * @param fileContent
     */
    public static void writeFile(String filePath, String fileContent) {
        File file = new File(filePath);
        // if file doesnt exists, then create it
        FileWriter fw = null;
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(fileContent);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param compressFile need decompress file
     * @param destDir      解压tar 文件到当前目录
     */
    public static String decompress(String pathName, String fileName) {
        File filed = new File(pathName + fileName);
        int index = fileName.indexOf(".");
        String suffixName = fileName.substring(0, index);
        if (!filed.exists()) {
            return "";
        }
        BufferedInputStream bufferedInputStream = null;
        BufferedOutputStream bufferedOutputStream = null;
        ArchiveInputStream in = null;
        GZIPInputStream is = null;
        try {
            is = new GZIPInputStream(new BufferedInputStream(new FileInputStream(filed)));
            in = new ArchiveStreamFactory().createArchiveInputStream("tar", is);
            ArchiveEntry entry = null;
            while ((entry = in.getNextEntry()) != null) {
                String name = entry.getName();
                if (!name.equals(suffixName)) {
                    continue;
                }
                bufferedInputStream = new BufferedInputStream(in);
                bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(pathName + suffixName));
                int b = 0;
                while ((b = bufferedInputStream.read()) != -1) {
                    bufferedOutputStream.write(b);
                }
                bufferedOutputStream.flush();
            }
            bufferedOutputStream.close();
            is.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedInputStream != null) {
                    bufferedInputStream.close();
                }
                if (bufferedOutputStream != null) {
                    bufferedOutputStream.close();
                }
                if (is != null) {
                    is.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return pathName + suffixName;
    }

    public static void main(String[] args) {
        String record = "{\"userId\": \"1\",\"userName\": \"jun\",\"userAge\": \"27\"}";
        TestUser user = JSON.parseObject(record, TestUser.class);
        Map<String,String> a = new BeanMap(user);
        System.out.println(user.getUserId());
    }
}
