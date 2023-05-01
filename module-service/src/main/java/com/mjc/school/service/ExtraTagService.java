package com.mjc.school.service;

import com.mjc.school.service.dto.TagResponseDto;

import java.util.List;

public interface ExtraTagService {

    List<TagResponseDto> readByName(String name);
}
