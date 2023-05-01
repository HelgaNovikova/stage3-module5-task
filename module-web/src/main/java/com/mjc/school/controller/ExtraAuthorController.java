package com.mjc.school.controller;

import com.mjc.school.service.dto.AuthorResponseDto;

import javax.validation.Valid;
import java.util.List;

public interface ExtraAuthorController {
    List<AuthorResponseDto> readByName(@Valid String name);
}
