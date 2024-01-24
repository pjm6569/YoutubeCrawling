package com.example.crawling.controller;

import com.example.crawling.domain.CommentInfo;
import com.example.crawling.service.ExcelService;
import com.example.crawling.service.YouTubeService;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.List;

@Controller
public class YoutubeController {
    private final YouTubeService youTubeService;
    private final ExcelService excelService;
    @Autowired
    public YoutubeController(YouTubeService youTubeService, ExcelService excelService) {
        this.youTubeService = youTubeService;
        this.excelService = excelService;
    }

    @GetMapping("comment-crawling")
    public String getComment(Model model, @RequestParam("videoUrl") URL url) throws GeneralSecurityException, IOException {
        model.addAttribute("loading", true); // 로딩 속성을 true로 설정
        model.addAttribute("comment", youTubeService.getCommentList(String.valueOf(url)));
        return "comments";
    }

    @PostMapping("download")
    public ResponseEntity<?> handleDownloadRequest(@RequestBody List<CommentInfo> comments) throws IOException {
        byte[] excelBytes = excelService.createExcel(comments);
        Workbook workbook = new XSSFWorkbook(new ByteArrayInputStream(excelBytes));
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(excelBytes, headers, HttpStatus.OK);
    }
}

