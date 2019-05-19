package com.courseSite.util;

import com.courseSite.ResponseResult.Result;

import java.io.*;

public class Util {
    public static Result download(String filename, String path, OutputStream outputStream){
        Result result = new Result();
        try {
            String storePath = path+"/"+filename;
            System.out.println(storePath);
            //result.setMessage(path);

            File tempFile =new File(storePath);

            InputStream inputStream = new FileInputStream(tempFile);
            OutputStream os = outputStream;
            byte[] b = new byte[2048];
            int length;
            while ((length = inputStream.read(b)) > 0) {
                os.write(b, 0, length);
            }
            os.flush();
            os.close();
            inputStream.close();
            result.setOK("下载成功",storePath);
        } catch (IOException e) {
            e.printStackTrace();
            result.setFail(400,"文件下载失败");
            System.out.println("文件下载失败");
        }
        return result;
    }

    public static void remove(String filepath){
        File file = new File(filepath);
        if (file.isFile() || file.list() == null){
            file.delete();
            System.out.println("删除了"+file.getName());
        }else{
            File[] files = file.listFiles();
            for (File afile : files){
                remove(afile.getAbsolutePath());
            }
            file.delete();
        }
    }
}
