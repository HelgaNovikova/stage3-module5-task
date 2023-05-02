package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.ExtraTagController;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.ExtraTagService;
import com.mjc.school.service.dto.TagCreateDto;
import com.mjc.school.service.dto.TagResponseDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/tags")
public class TagController implements BaseController<TagCreateDto, TagResponseDto, Long>, ExtraTagController {

    private final BaseService<TagCreateDto, TagResponseDto, Long> tagService;
    private final ExtraTagService extraTagService;

    public TagController(BaseService<TagCreateDto, TagResponseDto, Long> tagService, ExtraTagService extraTagService) {
        this.tagService = tagService;
        this.extraTagService = extraTagService;
    }

    @Override
    @GetMapping
    @ApiOperation(value = "Get all tags")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved tags"),
            @ApiResponse(code = 400, message = "Invalid parameters supplied")})
    @ResponseStatus(HttpStatus.OK)
    public List<TagResponseDto> readAll(@RequestParam(defaultValue = "1", required = false) Integer page,
                                        @RequestParam(defaultValue = "10", required = false) Integer size,
                                        @RequestParam(defaultValue = "id,asc", required = false) String sortBy) {
        return this.tagService.readAll(page, size, sortBy);
    }

    @Override
    @GetMapping(value = "/{id:\\d+}")
    @ApiOperation(value = "Get tag by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved the tag"),
            @ApiResponse(code = 404, message = "Tag was not found")})
    @ResponseStatus(HttpStatus.OK)
    public TagResponseDto readById(@PathVariable Long id) {
        return this.tagService.readById(id);
    }

    @Override
    @GetMapping(value = "/byName/{name}")
    @ApiOperation(value = "Get tag by name")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved the tag"),
            @ApiResponse(code = 404, message = "Tag was not found")})
    @ResponseStatus(HttpStatus.OK)
    public List<TagResponseDto> readByName(@PathVariable String name) {
        return this.extraTagService.readByName(name);
    }

    @Override
    @PostMapping()
    @ApiOperation(value = "Create a tag")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created the tag"),
            @ApiResponse(code = 400, message = "Invalid parameters supplied")})
    @ResponseStatus(HttpStatus.CREATED)
    public TagResponseDto create(@Valid @RequestBody TagCreateDto createRequest) {
        return this.tagService.create(createRequest);
    }

    @Override
    @PutMapping(value = "/{id:\\d+}")
    @ApiOperation(value = "Update the tag")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated the tag"),
            @ApiResponse(code = 400, message = "Invalid parameters supplied"),
            @ApiResponse(code = 404, message = "Tag does not exist")})
    @ResponseStatus(HttpStatus.OK)
    public TagResponseDto update(@PathVariable Long id, @Valid @RequestBody TagCreateDto updateRequest) {
        return this.tagService.update(id, updateRequest);
    }

    @Override
    @DeleteMapping(value = "/{id:\\d+}")
    @ApiOperation(value = "Delete the tag")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully deleted the tag"),
            @ApiResponse(code = 404, message = "Tag does not exist")})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        this.tagService.deleteById(id);
    }

    @Override
    @PatchMapping(value = "/{id:\\d+}")
    @ApiOperation(value = "Update the tag")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated the tag"),
            @ApiResponse(code = 400, message = "Invalid parameters supplied"),
            @ApiResponse(code = 404, message = "Tag does not exist")})
    @ResponseStatus(HttpStatus.OK)
    public TagResponseDto patchById(@PathVariable Long id, @Valid @RequestBody TagCreateDto updateRequest) {
        return this.tagService.update(id, updateRequest);
    }
}
