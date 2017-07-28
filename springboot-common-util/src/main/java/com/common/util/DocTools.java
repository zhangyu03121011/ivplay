package com.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.POIXMLTextExtractor;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;

/**
 * POI 读取 word 2003 和 word 2007 中文字内容的测试类<br />
 * 
 * @createDate 2009-07-25
 * @author Carl He
 */
public class DocTools {
    public static void main(String[] args) {
        try {
            // //word 2003： 图片不会被读取
            InputStream is = new FileInputStream(new File(
                    "/home/huijunluo/Desktop/zl/智联招聘_姚丽冷_招聘经理_中文_20151014_75344088.doc"));
            WordExtractor ex = new WordExtractor(is);// is是WORD文件的InputStream
            String text2003 = ex.getText();
            System.out.println(text2003);
            //
             // word 2007 图片不会被读取， 表格中的数据会被放在字符串的最后
            // OPCPackage opcPackage =
            // POIXMLDocument.openPackage("/home/huijunluo/Desktop/zl/智联招聘_姚丽冷_招聘经理_中文_20151014_75344088.doc");
            // POIXMLTextExtractor extractor = new
            // XWPFWordExtractor(opcPackage);
            // String text2007 = extractor.getText();
            // System.out.println(text2007);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}