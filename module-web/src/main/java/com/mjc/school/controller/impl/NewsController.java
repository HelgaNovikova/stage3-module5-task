package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.ExtraNewsController;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.ExtraNewsService;
import com.mjc.school.service.dto.AuthorResponseDto;
import com.mjc.school.service.dto.NewsCreateDto;
import com.mjc.school.service.dto.NewsResponseDto;
import com.mjc.school.service.dto.TagResponseDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
    @ApiOperation(value = "Get all news")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved news"),
            @ApiResponse(code = 400, message = "Invalid parameters supplied")})
    @ResponseStatus(HttpStatus.OK)
    public List<NewsResponseDto> readAll(@RequestParam(defaultValue = "1", required = false) Integer page,
                                         @RequestParam(defaultValue = "10", required = false) Integer size,
                                         @RequestParam(defaultValue = "createDate,desc", required = false) String sortBy) {
        return this.newsService.readAll(page, size, sortBy);
    }

    @Override
    @GetMapping(value = "/{id:\\d+}")
    @ApiOperation(value = "Get news by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved the news"),
            @ApiResponse(code = 404, message = "News was not found")})
    @ResponseStatus(HttpStatus.OK)
    public NewsResponseDto readById(@PathVariable Long id) {
        return this.newsService.readById(id);
    }

    @Override
    @PostMapping()
    @ApiOperation(value = "Create a news")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created the news"),
            @ApiResponse(code = 400, message = "Invalid parameters supplied")})
    @ResponseStatus(HttpStatus.CREATED)
    public NewsResponseDto create(@Valid @RequestBody NewsCreateDto createRequest) {
        return this.newsService.create(createRequest);
    }

    @Override
    @PutMapping(value = "/{id:\\d+}")
    @ApiOperation(value = "Update the news")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated the news"),
            @ApiResponse(code = 400, message = "Invalid parameters supplied"),
            @ApiResponse(code = 404, message = "News does not exist")})
    @ResponseStatus(HttpStatus.OK)
    public NewsResponseDto update(@PathVariable Long id, @Valid @RequestBody NewsCreateDto updateRequest) {
        return this.newsService.update(id, updateRequest);
    }

    @Override
    @DeleteMapping(value = "/{id:\\d+}")
    @ApiOperation(value = "Delete the news")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully deleted the news"),
            @ApiResponse(code = 404, message = "News does not exist")})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        this.newsService.deleteById(id);
    }

    @Override
    @PatchMapping(value = "/{id:\\d+}")
    @ApiOperation(value = "Update the news")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated the news"),
            @ApiResponse(code = 400, message = "Invalid parameters supplied"),
            @ApiResponse(code = 404 , message = "News does not exist")})
    @ResponseStatus(HttpStatus.OK)
    public NewsResponseDto patchById(@PathVariable Long id, @Valid @RequestBody NewsCreateDto createRequest) {
        return this.newsService.patchById(id, createRequest);
    }

    @Override
    @GetMapping(value = "/{id:\\d+}/authors")
    @ApiOperation(value = "Get author by newsId")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved the author"),
            @ApiResponse(code = 404, message = "News does not exist")})
    @ResponseStatus(HttpStatus.OK)
    public AuthorResponseDto readAuthorByNewsId(@PathVariable Long id) {
        return this.extraNewsService.readAuthorByNewsId(id);
    }

    @Override
    @GetMapping(value = "/{id:\\d+}/tags")
    @ApiOperation(value = "Get tags by newsId")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved the tags"),
            @ApiResponse(code = 404, message = "News does not exist")})
    @ResponseStatus(HttpStatus.OK)
    public List<TagResponseDto> readTagsByNewsId(@PathVariable Long id) {
        return this.extraNewsService.readTagsByNewsId(id);
    }

}
