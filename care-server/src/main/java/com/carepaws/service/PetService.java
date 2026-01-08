package com.carepaws.service;

import com.carepaws.entity.Pet;

import java.util.List;

public interface PetService {


    List<Pet> list(Pet pet);

    void save(Pet pet);

    Pet getById(Long id);

    void update(Pet pet);

    void deleteById(Long id);
}
