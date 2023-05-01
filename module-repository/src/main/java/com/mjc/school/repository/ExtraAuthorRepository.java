package com.mjc.school.repository;

import com.mjc.school.repository.model.AuthorModel;

import java.util.List;

public interface ExtraAuthorRepository {

    List<AuthorModel> readByName(String name);
}
