package com.carepaws.service.impl;

import com.carepaws.context.BaseContext;
import com.carepaws.entity.Pet;
import com.carepaws.mapper.PetMapper;
import com.carepaws.service.PetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class PetServiceImpl implements PetService {

    @Autowired
    private PetMapper petMapper;

    /**
     * 查询当前登录用户的所有宠物档案
     */
    public List<Pet> list(Pet pet) {
        return petMapper.list(pet);
    }

    /**
     * 新增宠物档案
     */
    public void save(Pet pet) {
        // 设置当前登录用户ID
        pet.setUserId(BaseContext.getCurrentId());
        // 保存宠物档案
        petMapper.save(pet);
    }

    /**
     * 根据id查询宠物档案
     */
    public Pet getById(Long id) {
        return petMapper.getById(id);
    }

    /**
     * 修改宠物档案
     */
    public void update(Pet pet) {
        // 修改宠物档案
        petMapper.update(pet);
    }

    /**
     * 删除宠物档案
     */
    public void deleteById(Long id) {
        petMapper.deleteById(id);
    }
}
