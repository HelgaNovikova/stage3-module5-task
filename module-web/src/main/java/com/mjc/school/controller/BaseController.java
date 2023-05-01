package com.mjc.school.controller;

import com.mjc.school.service.dto.AuthorResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;
import java.util.List;

public interface BaseController<T, R, K> {

    List<R> readAll(Integer pageNo, Integer pageSize, String sortBy);

    R readById(K id);

    R create(@Valid T createRequest);

    R update(K id, @Valid T updateRequest);

    void deleteById(K id);

    R patchById(K id,@Valid T createRequest);
}
