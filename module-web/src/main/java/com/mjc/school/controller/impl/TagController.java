package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.ExtraTagController;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.ExtraTagService;
import com.mjc.school.service.dto.TagCreateDto;
import com.mjc.school.service.dto.TagResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Get all tags")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved tags"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters supplied")})
    @ResponseStatus(HttpStatus.OK)
    public List<TagResponseDto> readAll(@RequestParam(defaultValue = "1", required = false) Integer page,
                                        @RequestParam(defaultValue = "10", required = false) Integer size,
                                        @RequestParam(defaultValue = "id,asc", required = false) String sortBy) {
        return this.tagService.readAll(page, size, sortBy);
    }

    @Override
    @GetMapping(value = "/{id:\\d+}")
    @Operation(summary = "Get tag by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the tag"),
            @ApiResponse(responseCode = "404", description = "Tag was not found")})
    @ResponseStatus(HttpStatus.OK)
    public TagResponseDto readById(@PathVariable Long id) {
        return this.tagService.readById(id);
    }

    @Override
    @GetMapping(value = "/byName/{name}")
    @Operation(summary = "Get tag by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the tag"),
            @ApiResponse(responseCode = "404", description = "Tag was not found")})
    @ResponseStatus(HttpStatus.OK)
    public List<TagResponseDto> readByName(@PathVariable String name) {
        return this.extraTagService.readByName(name);
    }

    @Override
    @PostMapping()
    @Operation(summary = "Create a tag")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created the tag"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters supplied")})
    @ResponseStatus(HttpStatus.CREATED)
    public TagResponseDto create(@Valid @RequestBody TagCreateDto createRequest) {
        return this.tagService.create(createRequest);
    }

    @Override
    @PutMapping(value = "/{id:\\d+}")
    @Operation(summary = "Update the tag")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the tag"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters supplied"),
            @ApiResponse(responseCode = "404", description = "Tag does not exist")})
    @ResponseStatus(HttpStatus.OK)
    public TagResponseDto update(@PathVariable Long id, @Valid @RequestBody TagCreateDto updateRequest) {
        return this.tagService.update(id, updateRequest);
    }

    @Override
    @DeleteMapping(value = "/{id:\\d+}")
    @Operation(summary = "Delete the tag")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted the tag"),
            @ApiResponse(responseCode = "404", description = "Tag does not exist")})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        this.tagService.deleteById(id);
    }

    @Override
    @PatchMapping(value = "/{id:\\d+}")
    @Operation(summary = "Update the tag")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the tag"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters supplied"),
            @ApiResponse(responseCode = "404", description = "Tag does not exist")})
    @ResponseStatus(HttpStatus.OK)
    public TagResponseDto patchById(@PathVariable Long id, @Valid @RequestBody TagCreateDto updateRequest) {
        return this.tagService.update(id, updateRequest);
    }
}
