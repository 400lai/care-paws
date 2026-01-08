package com.carepaws.controller.owner;

import com.carepaws.context.BaseContext;
import com.carepaws.entity.Pet;
import com.carepaws.result.Result;
import com.carepaws.service.PetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/pet")
@Tag(name = "C端宠物档案接口")
@Slf4j
public class PetController {

    @Autowired
    private PetService petService;

    /**
     * 查询当前登录用户所有的宠物档案
     */
    @GetMapping("/list")
    @Operation(summary = "查询当前登录用户的所有宠物档案")
    public Result<List<Pet>> list() {
        log.info("查询当前登录用户的所有宠物档案");
        Pet pet = new Pet();
        pet.setUserId(BaseContext.getCurrentId());
        List<Pet> list = petService.list(pet);
        return Result.success(list);
    }

    /**
     * 新增宠物档案
     */
    @PostMapping
    @Operation(summary = "新增宠物档案")
    public Result save(@RequestBody Pet pet) {
        log.info("新增宠物档案: {}", pet);
        petService.save(pet);
        return Result.success();
    }

    /**
     * 根据id查询宠物档案
     */
    @GetMapping("/{id}")
    @Operation(summary = "根据id查询宠物档案")
    public Result<Pet> getById(@PathVariable Long id) {
        log.info("根据id查询宠物档案: {}", id);
        Pet pet = petService.getById(id);
        return Result.success(pet);
    }

    /**
     * 修改宠物档案
     */
    @PutMapping
    @Operation(summary = "修改宠物档案")
    public Result update(@RequestBody Pet pet) {
        log.info("修改宠物档案: {}", pet);
        petService.update(pet);
        return Result.success();
    }

    /**
     * 删除宠物档案
     */
    @DeleteMapping
    @Operation(summary = "删除宠物档案")
    public Result delete(Long id) {
        log.info("删除宠物档案: {}", id);
        petService.deleteById(id);
        return Result.success();
    }

}
