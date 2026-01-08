package com.carepaws.mapper;

import com.carepaws.entity.Pet;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PetMapper {

    /**
     * 条件查询
     */
    List<Pet> list(Pet pet);

    /**
     * 保存宠物档案
     */
    void save(Pet pet);

    /**
     * 根据id查询宠物档案
     */
    Pet getById(Long id);

    /**
     * 修改宠物档案
     */
    void update(Pet pet);

    /**
     * 删除宠物档案
     */
    void deleteById(Long id);
}
