package com.mjc.school.service;

import com.mjc.school.service.dto.AuthorResponseDto;

import java.util.List;

public interface ExtraAuthorService {

    List<AuthorResponseDto> readByName(String name);
}
