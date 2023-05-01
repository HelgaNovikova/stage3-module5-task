package com.mjc.school.controller;

import com.mjc.school.service.dto.TagResponseDto;

import java.util.List;

public interface ExtraTagController {
    List<TagResponseDto> readByName(String name);
}
