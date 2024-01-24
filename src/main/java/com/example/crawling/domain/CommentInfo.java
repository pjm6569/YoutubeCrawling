package com.example.crawling.domain;

import java.net.URL;
import java.util.Date;

public class CommentInfo {
    private String authorProfileImageUrl;
    private String authorDisplayName;
    private String textOriginal;
    private String publishedAt;
    private long likeCount;

    public CommentInfo(){

    }
    public CommentInfo(String authorProfileImageUrl, String authorDisplayName, String textOriginal, String publishedAt, long likeCount) {
        this.authorProfileImageUrl = authorProfileImageUrl;
        this.authorDisplayName = authorDisplayName;
        this.textOriginal = textOriginal;
        this.publishedAt = publishedAt;
        this.likeCount = likeCount;
    }

    public String getAuthorProfileImageUrl() {
        return authorProfileImageUrl;
    }

    public void setAuthorProfileUrl(String authorProfileUrl) {
        this.authorProfileImageUrl = authorProfileImageUrl;
    }

    public String getAuthorDisplayName() {
        return authorDisplayName;
    }

    public void setAuthorDisplayName(String authorDisplayName) {
        this.authorDisplayName = authorDisplayName;
    }

    public String getTextOriginal() {
        return textOriginal;
    }

    public void setTextOriginal(String textOriginal) {
        this.textOriginal = textOriginal;
    }
    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(long likeCount) {
        this.likeCount = likeCount;
    }

}
