package com.example.demo.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.poi.hssf.converter.ExcelToHtmlConverter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


/**
 * <p>
 * @ClassName: FilePreview
 * </p>
 * <p>
 * Description: Excel文件转为html元素
 * </p>
 *
 * @author 周宣
 * @date 2015年12月21日
 */
public class FilePreview {
    /**
     * <p>
     * Title: convertExceltoHtml
     * </p>
     * <p>
     * Description: 将2003版Excel文件转为html标签元素
     * </p>
     *
     * @author 周宣
     * @return
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws TransformerException
     * @throws InvalidFormatException
     */
    public static String convertExceltoHtml(String path) throws IOException,ParserConfigurationException, TransformerException,InvalidFormatException {
        HSSFWorkbook workBook = null;
        String content = null;
        StringWriter writer = null;
        File excelFile = new File(path);
        InputStream is = FilePreview.class.getClassLoader().getResourceAsStream(path);
        //判断Excel文件是2003版还是2007版
        String suffix = path.substring(path.lastIndexOf("."));
//        if(suffix.equals(".xlsx")){
//            //将07版转化为03版
//            Xssf2Hssf xlsx2xls = new Xssf2Hssf();
//            XSSFWorkbook xSSFWorkbook = new XSSFWorkbook(is);
//            workBook = new HSSFWorkbook();
//            xlsx2xls.transformXSSF(xSSFWorkbook, workBook);
//
//        }else{
            workBook = new HSSFWorkbook(is);
//        }
        try {
            ExcelToHtmlConverter converter = new ExcelToHtmlConverter(DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());
            converter.setOutputColumnHeaders(false);// 不显示列的表头
            converter.setOutputRowNumbers(false);// 不显示行的表头
            converter.processWorkbook(workBook);

            writer = new StringWriter();
            Transformer serializer = TransformerFactory.newInstance().newTransformer();
            serializer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            serializer.setOutputProperty(OutputKeys.INDENT, "yes");
            serializer.setOutputProperty(OutputKeys.METHOD, "html");
            serializer.transform(new DOMSource(converter.getDocument()),
                    new StreamResult(writer));

            content = writer.toString();
            writer.close();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return content;
    }



}
