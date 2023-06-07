package com.mjc.school.repository.utils;

import com.mjc.school.repository.impl.AuthorRepository;
import com.mjc.school.repository.impl.CommentRepository;
import com.mjc.school.repository.impl.NewsRepository;
import com.mjc.school.repository.impl.TagRepository;
import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.repository.model.CommentModel;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.repository.model.TagModel;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@Component
public class DataBaseLoader implements InitializingBean {

    public static final int DEFAULT_NEWS_COUNT_TO_GENERATE = 20;
    public static final int DEFAULT_NUMBER_OF_COMMENTS = 3;
    public static final int DEFAULT_NUMBER_OF_TAGS = 3;

    private final AuthorRepository authorRepo;
    private final TagRepository tagRepo;
    private final NewsRepository newsRepo;

    private final CommentRepository commentRepo;

    @Autowired
    public DataBaseLoader(AuthorRepository authorRepo, TagRepository tagRepo, NewsRepository newsRepo, CommentRepository commentRepo) {
        this.authorRepo = authorRepo;
        this.tagRepo = tagRepo;
        this.newsRepo = newsRepo;
        this.commentRepo = commentRepo;
    }

    private List<AuthorModel> writeAuthorsFromFileToDB(String path) {
        List<String> lines = readFromFile(path);
        List<AuthorModel> authors = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        for (String line : lines) {
            AuthorModel authorModel = authorRepo.saveAuthorToDB(new AuthorModel(line, now, now));
            authors.add(authorModel);
        }
        return authors;
    }

    private List<TagModel> writeTagsFromFileToDB(String path) {
        List<String> lines = readFromFile(path);
        List<TagModel> tags = new ArrayList<>();
        for (String line : lines) {
            TagModel tagFromDb = tagRepo.saveTagToDB(new TagModel(line));
            tags.add(tagFromDb);
        }
        return tags;
    }

    private List<String> readTitlesFromFiles(String titlePath) {
        return readFromFile(titlePath);
    }

    private List<String> readContentsFromFile(String contentPath) {
        return readFromFile(contentPath);
    }

    private List<String> readCommentsFromFile(String commentPath) {
        return readFromFile(commentPath);
    }

    private List<String> readFromFile(String contentPath) {
        try {
            URL resource = Objects.requireNonNull(NewsRepository.class.getClassLoader().getResource(contentPath));
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.openStream()))) {
                return new ArrayList<>(reader.lines().toList());
            }
        } catch (IOException e) {
            throw new RuntimeException("Error while reading file " + contentPath, e);
        }
    }

    private void loadNewsToDB(String titlesPath, String authorsPath, String contentPath, String tagPath, String commentPath) {
        List<String> titlesFromFile = readTitlesFromFiles(titlesPath);
        List<AuthorModel> authorsFromDb = writeAuthorsFromFileToDB(authorsPath);
        List<String> contentFromFile = readContentsFromFile(contentPath);
        List<TagModel> tagsFromDb = writeTagsFromFileToDB(tagPath);
        List<String> commentsFromFile = readCommentsFromFile(commentPath);

        Random rand = new Random();
        for (int i = 1; i < DEFAULT_NEWS_COUNT_TO_GENERATE; i++) {
            LocalDateTime now = LocalDateTime.now();
            if (contentFromFile.get(i).length() < 255 && titlesFromFile.get(i).length() < 255) {

                List<TagModel> tags = generateTagsList(tagsFromDb, rand);
                NewsModel news = new NewsModel(titlesFromFile.get(i), contentFromFile.get(i), now, now, authorsFromDb.get(i), tags);
                NewsModel savedNews = newsRepo.saveNewsToDB(news);
                generateCommentsToNews(commentsFromFile, rand, now, savedNews);
            }
        }
    }

    private void generateCommentsToNews(List<String> commentsFromFile, Random rand, LocalDateTime now, NewsModel savedNews) {
        for (int k = 0; k < DEFAULT_NUMBER_OF_COMMENTS; k++) {
            int commentId = rand.nextInt(commentsFromFile.size());
            CommentModel comment = new CommentModel(commentsFromFile.get(commentId), now, now, savedNews);
            commentRepo.saveCommentToDB(comment);
        }
    }

    private static List<TagModel> generateTagsList(List<TagModel> tagsFromDb, Random rand) {
        List<TagModel> tags = new ArrayList<>();
        for (int j = 0; j < DEFAULT_NUMBER_OF_TAGS; j++) {
            int tagsId = rand.nextInt(tagsFromDb.size());
            TagModel t = tagsFromDb.get(tagsId);
            tags.add(t);
        }
        return tags;
    }

    @Override
    public void afterPropertiesSet() {
        loadNewsToDB("news800", "authors1000", "content300", "tags1000", "comments1000");
    }
}
