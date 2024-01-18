package com.example.crawling.repository;

import com.example.crawling.domain.CommentInfo;

import java.util.List;

public interface CommentRepository {
    CommentInfo save(CommentInfo commentInfo);
    List<CommentInfo> findAll();
}
