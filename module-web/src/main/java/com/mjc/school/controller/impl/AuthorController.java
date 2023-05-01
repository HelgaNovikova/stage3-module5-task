package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.ExtraAuthorController;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.ExtraAuthorService;
import com.mjc.school.service.dto.AuthorCreateDto;
import com.mjc.school.service.dto.AuthorResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
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
    @Operation(summary = "Get all authors")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved authors"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters supplied")})
    @ResponseStatus(HttpStatus.OK)
    public List<AuthorResponseDto> readAll(@RequestParam(defaultValue = "1", required = false) Integer page,
                                           @RequestParam(defaultValue = "10", required = false) Integer size,
                                           @RequestParam(defaultValue = "id,asc", required = false) String sortBy) {
        return this.authorService.readAll(page, size, sortBy);
    }

    @Override
    @GetMapping(value = "/{id:\\d+}")
    @Operation(summary = "Get author by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the author"),
            @ApiResponse(responseCode = "404", description = "Author was not found")})
    @ResponseStatus(HttpStatus.OK)
    public AuthorResponseDto readById(@PathVariable Long id) {
        return this.authorService.readById(id);
    }

    @Override
    @GetMapping(value = "/byName/{name}")
    @Operation(summary = "Get author by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the author"),
            @ApiResponse(responseCode = "404", description = "Author was not found")})
    @ResponseStatus(HttpStatus.OK)
    public List<AuthorResponseDto> readByName(@PathVariable String name) {
        return this.extraAuthorService.readByName(name);
    }

    @Override
    @PostMapping()
    @Operation(summary = "Create an author")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created the author"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters supplied")})
    @ResponseStatus(HttpStatus.CREATED)
    public AuthorResponseDto create(@Valid @RequestBody AuthorCreateDto createRequest) {
        return this.authorService.create(createRequest);
    }

    @Override
    @PutMapping(value = "/{id:\\d+}")
    @Operation(summary = "Update the author")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the author"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters supplied"),
            @ApiResponse(responseCode = "404", description = "Author does not exist")})
    @ResponseStatus(HttpStatus.OK)
    public AuthorResponseDto update(@PathVariable Long id, @Valid @RequestBody AuthorCreateDto updateRequest) {
        return this.authorService.update(id, updateRequest);
    }

    @Override
    @DeleteMapping(value = "/{id:\\d+}")
    @Operation(summary = "Delete the author")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted the author"),
            @ApiResponse(responseCode = "404", description = "Author does not exist")})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        this.authorService.deleteById(id);
    }

    @Override
    @PatchMapping(value = "/{id:\\d+}")
    @Operation(summary = "Update the author")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the author"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters supplied"),
            @ApiResponse(responseCode = "404", description = "Author does not exist")})
    @ResponseStatus(HttpStatus.OK)
    public AuthorResponseDto patchById(@PathVariable Long id, @Valid @RequestBody AuthorCreateDto updateRequest) {
        return this.authorService.update(id, updateRequest);
    }
}
