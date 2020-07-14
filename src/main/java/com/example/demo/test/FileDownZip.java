package com.example.demo.test;

import com.example.demo.jpa.CityRepository;
import com.example.demo.jpa.Dept;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author zyl
 * @date 2020/7/14 13:54
 */
@Controller
@RequestMapping("/test")
@Slf4j
public class FileDownZip {

    @GetMapping("/txt")
    public void txt(HttpServletResponse response) {
        InputStream is = null;
        try {
            is = this.getClass().getClassLoader().getResourceAsStream("2.xlsx");
            XSSFWorkbook source = new XSSFWorkbook(is);
            OutputStream os = response.getOutputStream();
            source.write(os);
        } catch(Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    @GetMapping("/xlsx")
    public void xlsx(HttpServletResponse response) {
        InputStream is = null;
        try {
            is = this.getClass().getClassLoader().getResourceAsStream("2.xlsx");
            XSSFWorkbook source = new XSSFWorkbook(is);
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=file.xlsx");
            OutputStream os = response.getOutputStream();
            source.write(os);
        } catch(Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    @Autowired
    private CityRepository repository;
    @GetMapping("/download")
    public void txtDown(HttpServletResponse response) throws IOException {
        Streamable<Dept> depts = repository.findByDeptIdLessThan(40);
        List<Dept> deptList = depts.toList();

        // 上面的两句代码只是通过jpa获取数据而已，可以删除，然后把下面的for循环的append改成普通的sb.append("我要测试输出的内容")就可以了
        StringBuilder sb = new StringBuilder();
        response.addHeader("Content-Disposition","attachment;filename=20160629.txt");
        ServletOutputStream outputStream = response.getOutputStream();
        BufferedOutputStream bos = new BufferedOutputStream(outputStream);
        for (Dept dept : deptList) {
            sb.append(dept);
        }
        bos.write(sb.toString().getBytes("UTF-8"));
        bos.flush();
        bos.close();
    }

    @GetMapping("/download/zip")
    public void txtDownZip(HttpServletResponse response) throws IOException {
        Streamable<Dept> depts = repository.findByDeptIdLessThan(40);
        List<Dept> deptList = depts.toList();

        // 上面的两句代码只是通过jpa获取数据而已，可以删除，然后把下面的for循环的append改成普通的sb.append("我要测试输出的内容")就可以了
        StringBuilder sb = new StringBuilder();
        response.addHeader("Content-Disposition","attachment;filename=20160629.zip");
        ServletOutputStream outputStream = response.getOutputStream();
        BufferedOutputStream bos = new BufferedOutputStream(outputStream);
        for (Dept dept : deptList) {
            sb.append(dept);
        }
        ZipOutputStream zos = new ZipOutputStream(bos);
        zos.putNextEntry(new ZipEntry("aa.txt"));
        zos.write(sb.toString().getBytes("UTF-8"));

        zos.putNextEntry(new ZipEntry("aa1.txt"));
        zos.write(sb.toString().getBytes("UTF-8"));

        zos.putNextEntry(new ZipEntry("aa2.txt"));
        zos.write(sb.toString().getBytes("UTF-8"));

        zos.putNextEntry(new ZipEntry("aa3.txt"));
        zos.write(sb.toString().getBytes("UTF-8"));

        zos.flush();
        zos.close();
    }
}
