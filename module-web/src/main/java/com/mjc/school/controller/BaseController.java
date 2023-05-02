package com.mjc.school.controller;

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
