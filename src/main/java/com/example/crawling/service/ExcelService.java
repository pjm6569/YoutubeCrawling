package com.example.crawling.service;

import com.example.crawling.domain.CommentInfo;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExcelService {

    public byte[] createExcel(List<CommentInfo> comments) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Comments");

        // Header
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Author Display Name");
        headerRow.createCell(1).setCellValue("Text Original");
        headerRow.createCell(2).setCellValue("Published At");
        headerRow.createCell(3).setCellValue("Like Count");

        // Data
        int rowNum = 1;
        for (CommentInfo comment : comments) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(comment.getAuthorDisplayName());
            row.createCell(1).setCellValue(comment.getTextOriginal());
            row.createCell(2).setCellValue(comment.getPublishedAt());
            row.createCell(3).setCellValue(comment.getLikeCount());
        }

        // Write to byte array
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);

        // Close the workbook to release resources
        workbook.close();

        return outputStream.toByteArray();
    }
}


