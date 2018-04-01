package com.fangxuele.ocs.web.coms.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * *************************************************
 * 文件名：FileZip.java
 * 包名：com.fangxuele.foms.util
 * 时间：2016年11月25日
 * 模块名：ZIP文件
 * 作者：  zyp
 * 简要描述:压缩成zip
 *
 * @version :1.0
 * 变更历史:
 * --------------------------------------------------
 * 序号      变更人     时间     变更原因
 * 1
 * 2
 * **************************************************
 */

public class FileZip {

    /**
     * @param srcfile 压缩前的文件list<文件名>
     * @param zipfile 压缩后的文件
     * @Title: ZipFiles
     * @Description: zip文件工具类
     * @author zyp(添加方法的人)
     * @Date 2016年11月25日 下午2:41:50
     */

    public static void ZipFiles(java.io.File[] srcfile, java.io.File zipfile) {

        byte[] buf = new byte[1024];

        try {

            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(

                    zipfile));

            for (int i = 0; i < srcfile.length; i++) {

                FileInputStream in = new FileInputStream(srcfile[i]);

                out.putNextEntry(new ZipEntry(srcfile[i].getName()));

                int len;

                while ((len = in.read(buf)) > 0) {

                    out.write(buf, 0, len);

                }

                out.closeEntry();

                in.close();

            }

            out.close();

        } catch (IOException e) {

            e.printStackTrace();

        }

    }


}
