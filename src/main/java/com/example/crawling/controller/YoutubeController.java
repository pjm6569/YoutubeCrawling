package com.example.crawling.controller;

import com.example.crawling.domain.CommentInfo;
import com.example.crawling.service.YouTubeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.List;

@Controller
public class YoutubeController {
    private final YouTubeService youTubeService;

    @Autowired
    public YoutubeController(YouTubeService youTubeService) {
        this.youTubeService = youTubeService;
    }

    @GetMapping("comment-crawling")
    public String getComment(Model model, @RequestParam("videoUrl") URL url) throws GeneralSecurityException, IOException {
        model.addAttribute("loading", true); // 로딩 속성을 true로 설정
        model.addAttribute("comment", youTubeService.getCommentList(String.valueOf(url)));
        return "comments";
    }

    @PostMapping("download")
    public ResponseEntity<?> handleDownloadRequest(@RequestBody List<CommentInfo> comments) {
        System.out.println(comments.size());
        return ResponseEntity.ok().body("File download successful");
    }
}

