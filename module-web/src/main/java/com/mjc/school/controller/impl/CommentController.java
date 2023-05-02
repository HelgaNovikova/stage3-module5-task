package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.ExtraCommentController;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.ExtraCommentService;
import com.mjc.school.service.dto.CommentCreateDto;
import com.mjc.school.service.dto.CommentResponseDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/comments")
public class CommentController implements BaseController<CommentCreateDto, CommentResponseDto, Long>, ExtraCommentController {

    private final BaseService<CommentCreateDto, CommentResponseDto, Long> commentService;
    private final ExtraCommentService extraCommentService;


    @Autowired
    public CommentController(BaseService<CommentCreateDto, CommentResponseDto, Long> commentService, ExtraCommentService extraCommentService, ExtraCommentService extraCommentService1) {
        this.commentService = commentService;
        this.extraCommentService = extraCommentService1;
    }

    @Override
    @GetMapping
    @ApiOperation(value = "Get all comments")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved comments"),
            @ApiResponse(code = 400, message = "Invalid parameters supplied")})
    @ResponseStatus(HttpStatus.OK)
    public List<CommentResponseDto> readAll(@RequestParam(defaultValue = "1", required = false) Integer page,
                                            @RequestParam(defaultValue = "10", required = false) Integer size,
                                            @RequestParam(defaultValue = "createDate,desc", required = false) String sortBy) {
        return this.commentService.readAll(page, size, sortBy);
    }

    @Override
    @GetMapping(value = "/{id:\\d+}")
    @ApiOperation(value = "Get comment by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved the comment"),
            @ApiResponse(code = 404, message = "Comment was not found")})
    @ResponseStatus(HttpStatus.OK)
    public CommentResponseDto readById(@PathVariable Long id) {
        return this.commentService.readById(id);
    }

    @Override
    @PostMapping()
    @ApiOperation(value = "Create a comment")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created the comment"),
            @ApiResponse(code = 400, message = "Invalid parameters supplied")})
    @ResponseStatus(HttpStatus.CREATED)
    public CommentResponseDto create(@Valid @RequestBody CommentCreateDto createRequest) {
        return this.commentService.create(createRequest);
    }

    @Override
    @PutMapping(value = "/{id:\\d+}")
    @ApiOperation(value = "Update the comment")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated the comment"),
            @ApiResponse(code = 400, message = "Invalid parameters supplied"),
            @ApiResponse(code = 404, message = "Comment does not exist")})
    @ResponseStatus(HttpStatus.OK)
    public CommentResponseDto update(@PathVariable Long id, @Valid @RequestBody CommentCreateDto updateRequest) {
        return this.commentService.update(id, updateRequest);
    }

    @Override
    @DeleteMapping(value = "/{id:\\d+}")
    @ApiOperation(value = "Delete the comment")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully deleted the comment"),
            @ApiResponse(code = 404, message = "Comment does not exist")})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        this.commentService.deleteById(id);
    }

    @Override
    @PatchMapping(value = "/{id:\\d+}")
    @ApiOperation(value = "Update the comment")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated the comment"),
            @ApiResponse(code = 400, message = "Invalid parameters supplied"),
            @ApiResponse(code = 404, message = "Comment does not exist")})
    @ResponseStatus(HttpStatus.OK)
    public CommentResponseDto patchById(@PathVariable Long id, @Valid @RequestBody CommentCreateDto createRequest) {
        return this.commentService.patchById(id, createRequest);
    }

    @Override
    @GetMapping(value = "/by-news-id/{id:\\d+}")
    @ApiOperation(value = "Get comments by newsId")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved the comments"),
            @ApiResponse(code = 404, message = "News does not exist")})
    @ResponseStatus(HttpStatus.OK)
    public List<CommentResponseDto> getCommentsByNewsId(@PathVariable Long id) {
        return this.extraCommentService.getCommentsByNewsId(id);
    }
}
