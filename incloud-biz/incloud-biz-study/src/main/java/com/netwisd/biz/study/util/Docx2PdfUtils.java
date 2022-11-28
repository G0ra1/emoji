package com.netwisd.biz.study.util;

import com.aspose.words.Document;
import com.aspose.words.License;
import com.netwisd.common.core.exception.IncloudException;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

@Slf4j
public class Docx2PdfUtils {

    /**
     * 验证aspose授权
     *
     * @return
     */
    public static boolean getAsposeLicense() {
        boolean result = false;
        try {
            InputStream is = Docx2PdfUtils.class.getClassLoader()
                    .getResourceAsStream("aspose-license.xml");
            License license = new License();
            license.setLicense(is);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 使用aspose转换word为pdf
     *
     * @param wordPath
     * @param pdfPath
     */
    public static void wordToPdfByAspose(String wordPath, String pdfPath) {
        if (!getAsposeLicense()) {// 验证License 若不验证则转化出的pdf文档会有水印
            return;
        }
        try {
            long startTime = System.currentTimeMillis();
            File file = new File(pdfPath);  //新建一个pdf文档
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            FileOutputStream os = new FileOutputStream(file);
            Document doc = new Document(wordPath);//Address是将要被转化的word文档
            doc.save(os, com.aspose.words.SaveFormat.PDF);//全面支持DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF, EPUB, XPS, SWF 相互转换
            long endTime = System.currentTimeMillis();
            os.close();
            log.info("use aspose word turn to pdf token：{}s.", (endTime - startTime) / 1000.0);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IncloudException("aspose转换文件失败");
        }
    }


}
