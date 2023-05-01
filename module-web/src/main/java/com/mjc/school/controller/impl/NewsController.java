package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.ExtraNewsController;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.ExtraNewsService;
import com.mjc.school.service.dto.AuthorResponseDto;
import com.mjc.school.service.dto.NewsCreateDto;
import com.mjc.school.service.dto.NewsResponseDto;
import com.mjc.school.service.dto.TagResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/news")
public class NewsController implements BaseController<NewsCreateDto, NewsResponseDto, Long>, ExtraNewsController {

    private final BaseService<NewsCreateDto, NewsResponseDto, Long> newsService;
    private final ExtraNewsService extraNewsService;

    @Autowired
    public NewsController(BaseService<NewsCreateDto, NewsResponseDto, Long> newsService, ExtraNewsService extraNewsService) {
        this.newsService = newsService;
        this.extraNewsService = extraNewsService;
    }

    @Override
    @GetMapping
    @Operation(summary = "Get all news")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved news"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters supplied")})
    @ResponseStatus(HttpStatus.OK)
    public List<NewsResponseDto> readAll(@RequestParam(defaultValue = "1", required = false) Integer page,
                                         @RequestParam(defaultValue = "10", required = false) Integer size,
                                         @RequestParam(defaultValue = "createDate,desc", required = false) String sortBy) {
        return this.newsService.readAll(page, size, sortBy);
    }

    @Override
    @GetMapping(value = "/{id:\\d+}")
    @Operation(summary = "Get news by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the news"),
            @ApiResponse(responseCode = "404", description = "News was not found")})
    @ResponseStatus(HttpStatus.OK)
    public NewsResponseDto readById(@PathVariable Long id) {
        return this.newsService.readById(id);
    }

    @Override
    @PostMapping()
    @Operation(summary = "Create a news")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created the news"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters supplied")})
    @ResponseStatus(HttpStatus.CREATED)
    public NewsResponseDto create(@Valid @RequestBody NewsCreateDto createRequest) {
        return this.newsService.create(createRequest);
    }

    @Override
    @PutMapping(value = "/{id:\\d+}")
    @Operation(summary = "Update the news")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the news"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters supplied"),
            @ApiResponse(responseCode = "404", description = "News does not exist")})
    @ResponseStatus(HttpStatus.OK)
    public NewsResponseDto update(@PathVariable Long id, @Valid @RequestBody NewsCreateDto updateRequest) {
        return this.newsService.update(id, updateRequest);
    }

    @Override
    @DeleteMapping(value = "/{id:\\d+}")
    @Operation(summary = "Delete the news")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted the news"),
            @ApiResponse(responseCode = "404", description = "News does not exist")})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        this.newsService.deleteById(id);
    }

    @Override
    @PatchMapping(value = "/{id:\\d+}")
    @Operation(summary = "Update the news")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the news"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters supplied"),
            @ApiResponse(responseCode = "404", description = "News does not exist")})
    @ResponseStatus(HttpStatus.OK)
    public NewsResponseDto patchById(@PathVariable Long id, @Valid @RequestBody NewsCreateDto createRequest) {
        return this.newsService.patchById(id, createRequest);
    }

    @Override
    @GetMapping(value = "/{id:\\d+}/authors")
    @Operation(summary = "Get author by newsId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the author"),
            @ApiResponse(responseCode = "404", description = "News does not exist")})
    @ResponseStatus(HttpStatus.OK)
    public AuthorResponseDto readAuthorByNewsId(@PathVariable Long id) {
        return this.extraNewsService.readAuthorByNewsId(id);
    }

    @Override
    @GetMapping(value = "/{id:\\d+}/tags")
    @Operation(summary = "Get tags by newsId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the tags"),
            @ApiResponse(responseCode = "404", description = "News does not exist")})
    @ResponseStatus(HttpStatus.OK)
    public List<TagResponseDto> readTagsByNewsId(@PathVariable Long id) {
        return this.extraNewsService.readTagsByNewsId(id);
    }

    @Override
    @GetMapping(value = "/get-news-by-parameters")
    public List<NewsResponseDto> readNewsByParams(@PathVariable List<Long> tagId,
                                                  @PathVariable String tagName,
                                                  @PathVariable String authorName,
                                                  @PathVariable String title,
                                                  @PathVariable String content) {
        return this.extraNewsService.readNewsByParams(tagId, tagName, authorName, title, content);
    }

}
