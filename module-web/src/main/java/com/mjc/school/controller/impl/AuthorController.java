package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.ExtraAuthorController;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.ExtraAuthorService;
import com.mjc.school.service.dto.AuthorCreateDto;
import com.mjc.school.service.dto.AuthorResponseDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping(value = "/authors")
public class AuthorController implements BaseController<AuthorCreateDto, AuthorResponseDto, Long>, ExtraAuthorController {

    private final BaseService<AuthorCreateDto, AuthorResponseDto, Long> authorService;
    private final ExtraAuthorService extraAuthorService;

    @Autowired
    public AuthorController(BaseService<AuthorCreateDto, AuthorResponseDto, Long> authorService, ExtraAuthorService extraAuthorService) {
        this.authorService = authorService;
        this.extraAuthorService = extraAuthorService;
    }

    @Override
    @GetMapping
    @ApiOperation(value = "Get all authors")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved authors"),
            @ApiResponse(code = 400, message = "Invalid parameters supplied")})
    @ResponseStatus(HttpStatus.OK)
    public List<AuthorResponseDto> readAll(@RequestParam(defaultValue = "1", required = false) Integer page,
                                           @RequestParam(defaultValue = "10", required = false) Integer size,
                                           @RequestParam(defaultValue = "id,asc", required = false) String sortBy) {
        return this.authorService.readAll(page, size, sortBy);
    }

    @Override
    @GetMapping(value = "/{id:\\d+}")
    @ApiOperation(value = "Get author by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved the author"),
            @ApiResponse(code = 404, message = "Author was not found")})
    @ResponseStatus(HttpStatus.OK)
    public AuthorResponseDto readById(@PathVariable Long id) {
        return this.authorService.readById(id);
    }

    @Override
    @GetMapping(value = "/byName/{name}")
    @ApiOperation(value = "Get author by name")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved the author"),
            @ApiResponse(code = 404, message = "Author was not found")})
    @ResponseStatus(HttpStatus.OK)
    public List<AuthorResponseDto> readByName(@PathVariable String name) {
        return this.extraAuthorService.readByName(name);
    }

    @Override
    @PostMapping()
    @ApiOperation(value = "Create an author")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created the author"),
            @ApiResponse(code = 400, message = "Invalid parameters supplied")})
    @ResponseStatus(HttpStatus.CREATED)
    public AuthorResponseDto create(@Valid @RequestBody AuthorCreateDto createRequest) {
        return this.authorService.create(createRequest);
    }

    @Override
    @PutMapping(value = "/{id:\\d+}")
    @ApiOperation(value = "Update the author")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated the author"),
            @ApiResponse(code = 400, message = "Invalid parameters supplied"),
            @ApiResponse(code = 404, message = "Author does not exist")})
    @ResponseStatus(HttpStatus.OK)
    public AuthorResponseDto update(@PathVariable Long id, @Valid @RequestBody AuthorCreateDto updateRequest) {
        return this.authorService.update(id, updateRequest);
    }

    @Override
    @DeleteMapping(value = "/{id:\\d+}")
    @ApiOperation(value = "Delete the author")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully deleted the author"),
            @ApiResponse(code = 404, message = "Author does not exist")})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        this.authorService.deleteById(id);
    }

    @Override
    @PatchMapping(value = "/{id:\\d+}")
    @ApiOperation(value = "Update the author")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated the author"),
            @ApiResponse(code = 400, message = "Invalid parameters supplied"),
            @ApiResponse(code = 404, message = "Author does not exist")})
    @ResponseStatus(HttpStatus.OK)
    public AuthorResponseDto patchById(@PathVariable Long id, @Valid @RequestBody AuthorCreateDto updateRequest) {
        return this.authorService.update(id, updateRequest);
    }
}
