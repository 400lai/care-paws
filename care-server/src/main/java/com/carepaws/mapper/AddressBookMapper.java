package com.carepaws.mapper;

import com.carepaws.entity.AddressBook;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface AddressBookMapper {

    /**
     * 条件查询
     */
    List<AddressBook> list(AddressBook addressBook);

    /**
     * 新增地址
     */
    void insert(AddressBook addressBook);

    /**
     * 根据id查询地址簿
     */
    AddressBook getById(Long id);

    /**
     * 根据id修改
     */
    void update(AddressBook addressBook);

    /**
     * 根据id删除地址
     */
    void deleteById(Long id);

    /**
     * 根据 用户id修改 是否默认地址
     */
    void updateIsDefaultByUserId(AddressBook addressBook);
}
