package com.fangxuele.ocs.web.coms.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

public class FileZipUtils {
    /***
     *
     * @Title: createZipFile
     * @Description: 生成zip的压缩文件
     * @param rootPath 生成文件的目录
     * @param moudel   zip文件名
     * @param fileNames  待压缩的文件列表
     * @throws Exception
     * @author zyp(添加方法的人)
     * @Date 2016年11月25日 下午2:43:54
     */
    public static void createZipFile(String rootPath, String moudel, List<String> fileNames) throws Exception {

        File zip = new File(rootPath.toString() + moudel + ".zip");
        File srcfile[] = new File[fileNames.size()];

        for (int i = 0, n = fileNames.size(); i < n; i++) {

            srcfile[i] = new File(fileNames.get(i));

        }
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(rootPath.toString() + moudel + ".zip");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        FileZip.ZipFiles(srcfile, zip);
        FileInputStream inStream = new FileInputStream(zip);
        byte[] buf = new byte[4096];
        int readLength;
        while (((readLength = inStream.read(buf)) != -1)) {
            out.write(buf, 0, readLength);
        }
        inStream.close();
        out.close();
    }
}
