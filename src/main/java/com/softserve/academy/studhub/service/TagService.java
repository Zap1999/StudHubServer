package com.softserve.academy.studhub.service;

import com.softserve.academy.studhub.entity.Tag;

import java.util.List;

public interface TagService {

    Tag save(Tag tag);

    Tag findById(Integer id);

    Tag findByName(String name);

    Tag update(Tag tag);

    void deleteById(Integer id);

    List<Tag> findAll();
}