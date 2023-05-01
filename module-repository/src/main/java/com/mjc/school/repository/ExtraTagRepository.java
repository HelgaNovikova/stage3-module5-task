package com.mjc.school.repository;

import com.mjc.school.repository.model.TagModel;

import java.util.List;

public interface ExtraTagRepository {

    List<TagModel> readByName(String name);
}
