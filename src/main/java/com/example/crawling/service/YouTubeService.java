package com.example.crawling.service;

import com.example.crawling.domain.CommentInfo;
import com.google.api.services.youtube.model.Comment;
import com.google.api.services.youtube.model.CommentThread;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.CommentThreadListResponse;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class YouTubeService {

    @Value("${youtube.apiKey}")
    private String apiKey;
    private static final String APPLICATION_NAME = "API code samples";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    public String getVideoId(String videoUrl) {
        // 동영상 URL에서 동영상 ID 추출
        return videoUrl.substring(videoUrl.indexOf("=") + 1);
    }

    public static YouTube getService() throws GeneralSecurityException, IOException {
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        return new YouTube.Builder(httpTransport, JSON_FACTORY, null)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    private CommentThreadListResponse getComments(String videoId, String nextPageToken) throws GeneralSecurityException, IOException {
        YouTube youtubeService = getService();

        // Define and execute the API request for each page
        YouTube.CommentThreads.List request = youtubeService.commentThreads()
                .list(Collections.singletonList("snippet,replies"));
        CommentThreadListResponse response = request.setKey(apiKey)
                .setVideoId(videoId)
                .setMaxResults(100L)
                .setOrder("relevance")
                .setPageToken(nextPageToken)
                .execute();
        return response;
    }

    public List<CommentInfo> getCommentList(String videoUrl) throws GeneralSecurityException, IOException {
        List<CommentInfo> commentInfoList = new ArrayList<>();
        String videoId = getVideoId(videoUrl);
        String nextPageToken = null;
        //댓글 전부 불러오기
        do {
            CommentThreadListResponse response = getComments(videoId, nextPageToken);
            List<CommentThread> commentThreads = response.getItems();

            for (CommentThread commentThread : commentThreads) {
                Comment topLevelComment = commentThread.getSnippet().getTopLevelComment();
                String authorProfileImageUrl = topLevelComment.getSnippet().getAuthorProfileImageUrl();
                String authorDisplayName = topLevelComment.getSnippet().getAuthorDisplayName();
                String textOriginal = topLevelComment.getSnippet().getTextOriginal();
                String publishedAt = topLevelComment.getSnippet().getPublishedAt().toString();
                long likeCount = topLevelComment.getSnippet().getLikeCount();

                CommentInfo commentInfo = new CommentInfo(authorProfileImageUrl, authorDisplayName, textOriginal, publishedAt, likeCount);
                commentInfoList.add(commentInfo);
            }

            // Get the nextPageToken for the next iteration
            nextPageToken = response.getNextPageToken();

        } while (nextPageToken != null);

        return commentInfoList;
    }


}
