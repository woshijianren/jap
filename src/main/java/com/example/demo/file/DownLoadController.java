package com.example.demo.file;

import com.example.demo.jpa.Dept;
import com.example.demo.jpa.DeptRepository;
import org.apache.poi.hssf.converter.ExcelToHtmlConverter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author zyl
 * @date 2020/7/14 10:58
 */
@Controller
@RequestMapping("/down")
public class DownLoadController {

    @Autowired
    private DeptRepository deptRepository;

    @RequestMapping("/downLoad")
    public void down(HttpServletRequest request,
                     HttpServletResponse response) {
        Iterable<Dept> depts = deptRepository.findAll();
        List<Dept> deptList = new ArrayList<>();
        Iterator<Dept> iterator = depts.iterator();
        while (iterator.hasNext()) {
            deptList.add(iterator.next());
        }
        exportText(response, deptList);
    }

    public void exportText(HttpServletResponse response, List<Dept> depts) {
//        response.setContentType("text/plain");// 一下两行关键的设置
        response.addHeader("Content-Disposition", "attachment;filename=20160629.txt");
        BufferedOutputStream buff = null;
        StringBuffer write = new StringBuffer();
        String tab = "  ";
        String enter = "\r\n";
        ServletOutputStream outSTr = null;
        try {
            outSTr = response.getOutputStream();
            buff = new BufferedOutputStream(outSTr);
            write.append("ID" + tab + "姓名" + tab + "年龄" + enter);
            for (int i = 0; i < depts.size(); i++) {
                Dept s = depts.get(i);
                write.append(s.getDeptId() + tab);
                write.append(s.getName() + tab);
                write.append(enter);
            }
            buff.write(write.toString().getBytes("UTF-8"));
            buff.flush();
            buff.close();
        } catch (IOException e) {
            e.printStackTrace();
        }// 建立
    }

    public static void main(String[] args) throws IOException {
        File file = new File("d:" + File.separator + "demo.txt");
//        定义压缩文件的名称
        File zipFile = new File("d:" + File.separator + "demo.zip");
//         定义输入文件流
        InputStream input = new FileInputStream(file);
//         定义压缩输出流
        ZipOutputStream zipOut = null;
//         实例化压缩输出流,并制定压缩文件的输出路径  就是D盘下,名字叫 demo.zip
        zipOut = new ZipOutputStream(new FileOutputStream(zipFile));
        zipOut.putNextEntry(new ZipEntry(file.getName()));
//         设置注释
        zipOut.setComment("www.demo.com");
        int temp = 0;
        while ((temp = input.read()) != -1) {
            zipOut.write(temp);
        }
        input.close();
        zipOut.close();
    }

    @ResponseBody
@GetMapping("/dsf")
public String dsf() throws ParserConfigurationException, TransformerException, InvalidFormatException, IOException {
        String path = "2.xls";
        HSSFWorkbook workBook = null;
        String content = null;
        StringWriter writer = null;
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(path);
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
//            converter.processWorkbook(workBook);

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
