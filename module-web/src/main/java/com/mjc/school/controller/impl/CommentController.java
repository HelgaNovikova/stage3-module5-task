package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.ExtraCommentController;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.ExtraCommentService;
import com.mjc.school.service.dto.CommentCreateDto;
import com.mjc.school.service.dto.CommentResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Get all comments")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved comments"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters supplied")})
    @ResponseStatus(HttpStatus.OK)
    public List<CommentResponseDto> readAll(@RequestParam(defaultValue = "1", required = false) Integer page,
                                            @RequestParam(defaultValue = "10", required = false) Integer size,
                                            @RequestParam(defaultValue = "createDate,desc", required = false) String sortBy) {
        return this.commentService.readAll(page, size, sortBy);
    }

    @Override
    @GetMapping(value = "/{id:\\d+}")
    @Operation(summary = "Get comment by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the comment"),
            @ApiResponse(responseCode = "404", description = "Comment was not found")})
    @ResponseStatus(HttpStatus.OK)
    public CommentResponseDto readById(@PathVariable Long id) {
        return this.commentService.readById(id);
    }

    @Override
    @PostMapping()
    @Operation(summary = "Create a comment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created the comment"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters supplied")})
    @ResponseStatus(HttpStatus.CREATED)
    public CommentResponseDto create(@Valid @RequestBody CommentCreateDto createRequest) {
        return this.commentService.create(createRequest);
    }

    @Override
    @PutMapping(value = "/{id:\\d+}")
    @Operation(summary = "Update the comment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the comment"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters supplied"),
            @ApiResponse(responseCode = "404", description = "Comment does not exist")})
    @ResponseStatus(HttpStatus.OK)
    public CommentResponseDto update(@PathVariable Long id, @Valid @RequestBody CommentCreateDto updateRequest) {
        return this.commentService.update(id, updateRequest);
    }

    @Override
    @DeleteMapping(value = "/{id:\\d+}")
    @Operation(summary = "Delete the comment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted the comment"),
            @ApiResponse(responseCode = "404", description = "Comment does not exist")})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        this.commentService.deleteById(id);
    }

    @Override
    @PatchMapping(value = "/{id:\\d+}")
    @Operation(summary = "Update the comment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the comment"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters supplied"),
            @ApiResponse(responseCode = "404", description = "Comment does not exist")})
    @ResponseStatus(HttpStatus.OK)
    public CommentResponseDto patchById(@PathVariable Long id, @Valid @RequestBody CommentCreateDto createRequest) {
        return this.commentService.patchById(id, createRequest);
    }

    @Override
    @GetMapping(value = "/by-news-id/{id:\\d+}")
    @Operation(summary = "Get comments by newsId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the comments"),
            @ApiResponse(responseCode = "404", description = "News does not exist")})
    @ResponseStatus(HttpStatus.OK)
    public List<CommentResponseDto> getCommentsByNewsId(@PathVariable Long id) {
        return this.extraCommentService.getCommentsByNewsId(id);
    }
}
