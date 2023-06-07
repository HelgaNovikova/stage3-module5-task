package com.mjc.school.repository.impl;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.ExtraTagRepository;
import com.mjc.school.repository.model.TagModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Repository
public class TagRepository implements BaseRepository<TagModel, Long>, ExtraTagRepository {

    private static final String SELECT_ALL_TAGS_BY_PART_OF_NAME = "select t from TagModel t where name like '%";
    private static final String SELECT_ALL_TAGS_ORDER_BY = "select t from TagModel t order by ";
    private final TransactionTemplate transactionTemplate;
    EntityManager entityManager;

    @Autowired
    public TagRepository(EntityManager entityManager, TransactionTemplate transactionTemplate) {
        this.entityManager = entityManager;
        this.transactionTemplate = transactionTemplate;
    }

    public TagModel saveTagToDB(TagModel tag) {
        return transactionTemplate.execute(s -> entityManager.merge(tag)
        );
    }

    @Override
    public List<TagModel> readAll(Integer page, Integer size, String sortBy) {
        List<String> sorting = List.of(sortBy.toLowerCase(Locale.ROOT).split(","));
        String request = SELECT_ALL_TAGS_ORDER_BY + sorting.get(0) + " " + sorting.get(1);
        return entityManager.createQuery(request, TagModel.class)
                .setFirstResult((page - 1) * size)
                .setMaxResults(page * size)
                .getResultList();
    }

    @Override
    public Optional<TagModel> readById(Long id) {
        TagModel tag = entityManager.find(TagModel.class, id);
        return Optional.ofNullable(tag);
    }

    @Override
    public TagModel create(TagModel entity) {
        return transactionTemplate.execute(s -> entityManager.merge(entity));
    }

    @Override
    public TagModel update(TagModel entity) {
        TagModel tag = entityManager.find(TagModel.class, entity.getId());
        tag.setName(entity.getName());
        entityManager.persist(tag);
        entityManager.flush();
        return entity;
    }

    @Override
    public boolean deleteById(Long id) {
        entityManager.remove(readById(id).orElseThrow());
        return !existById(id);
    }

    @Override
    public boolean existById(Long id) {
        return readById(id).isPresent();
    }

    @Override
    public List<TagModel> readByName(String name) {
        String request = SELECT_ALL_TAGS_BY_PART_OF_NAME + name + "%'";
        return entityManager.createQuery(request, TagModel.class).getResultList();
    }
}
