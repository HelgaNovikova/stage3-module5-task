package com.mjc.school.service.impl;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.ExtraTagRepository;
import com.mjc.school.repository.model.TagModel;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.ExtraTagService;
import com.mjc.school.service.dto.NewsMapper;
import com.mjc.school.service.dto.TagCreateDto;
import com.mjc.school.service.dto.TagResponseDto;
import com.mjc.school.service.exception.DuplicateEntityException;
import com.mjc.school.service.exception.TagNotFoundException;
import com.mjc.school.service.utils.EntitiesValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImp implements BaseService<TagCreateDto, TagResponseDto, Long>, ExtraTagService {

    private final BaseRepository<TagModel, Long> tagRepository;
    private final ExtraTagRepository extraTagRepository;

    public TagServiceImp(BaseRepository<TagModel, Long> tagRepository, ExtraTagRepository extraTagRepository) {
        this.tagRepository = tagRepository;
        this.extraTagRepository = extraTagRepository;
    }

    @Override
    @Transactional
    public List<TagResponseDto> readAll(Integer page, Integer size, String sortBy) {
        var allTags = tagRepository.readAll(page, size, sortBy);
        List<TagResponseDto> tagDto = new ArrayList<>();
        for (TagModel item : allTags) {
            tagDto.add(NewsMapper.INSTANCE.tagToTagResponseDto(item));
        }
        return tagDto;
    }

    @Override
    @Transactional
    public TagResponseDto readById(Long id) {
        Optional<TagModel> tagModel = tagRepository.readById(id);
        TagModel tag = tagModel.orElseThrow(() -> new TagNotFoundException(id));
        return NewsMapper.INSTANCE.tagToTagResponseDto(tag);
    }

    @Override
    @Transactional
    public TagResponseDto create(TagCreateDto createRequest) {
        if (!extraTagRepository.readByName(createRequest.getName()).isEmpty()) {
            throw new DuplicateEntityException();
        }
        TagModel tagModel = NewsMapper.INSTANCE.createTagDtoToTag(createRequest);
        EntitiesValidator.validateTag(createRequest.getName());
        return NewsMapper.INSTANCE.tagToTagResponseDto(tagRepository.create(tagModel));
    }

    @Override
    @Transactional
    public TagResponseDto update(Long id, TagCreateDto updateRequest) {
        if (!tagRepository.existById(id)) {
            throw new TagNotFoundException(id);
        }
        if (!extraTagRepository.readByName(updateRequest.getName()).isEmpty()) {
            throw new DuplicateEntityException();
        }
        EntitiesValidator.validateTag(updateRequest.getName());
        TagModel tagModel = NewsMapper.INSTANCE.createTagDtoToTag(updateRequest);
        tagModel.setId(id);
        return NewsMapper.INSTANCE.tagToTagResponseDto(tagRepository.update(tagModel));
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) {
        if (!tagRepository.existById(id)) {
            throw new TagNotFoundException(id);
        }
        return tagRepository.deleteById(id);
    }

    @Override
    @Transactional
    public TagResponseDto patchById(Long id, TagCreateDto updateRequest) {
        return update(id, updateRequest);
    }

    @Override
    @Transactional
    public List<TagResponseDto> readByName(String name) {
        var allTags = extraTagRepository.readByName(name);
        List<TagResponseDto> tagDto = new ArrayList<>();
        for (TagModel item : allTags) {
            tagDto.add(NewsMapper.INSTANCE.tagToTagResponseDto(item));
        }
        return tagDto;
    }
}
