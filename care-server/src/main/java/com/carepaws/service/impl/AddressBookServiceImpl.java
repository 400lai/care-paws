package com.carepaws.service.impl;

import com.carepaws.context.BaseContext;
import com.carepaws.entity.AddressBook;
import com.carepaws.mapper.AddressBookMapper;
import com.carepaws.service.AddressBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AddressBookServiceImpl implements AddressBookService {
    @Autowired
    private AddressBookMapper addressBookMapper;

    public List<AddressBook> list(AddressBook addressBook) {
        // TODO:实现查询地址簿列表的逻辑
        return addressBookMapper.list(addressBook);
    }

    public void save(AddressBook addressBook) {
        // TODO:实现保存地址簿的逻辑
        addressBookMapper.insert(addressBook);
    }

    public void update(AddressBook addressBook) {
        // TODO:实现更新地址簿的逻辑
        addressBookMapper.update(addressBook);
    }

    public void deleteById(Long id) {
        // TODO：实现根据ID删除地址簿的逻辑
        addressBookMapper.deleteById(id);
    }

    public AddressBook getById(Long id) {
        // TODO:实现根据ID查询地址簿的逻辑
        return addressBookMapper.getById(id);
    }

    @Override
    public void setDefault(AddressBook addressBook) {
        // TODO:实现设置默认地址簿的逻辑
        //1、将当前用户的所有地址修改为非默认地址 update address_book set is_default = ? where user_id = ?
        addressBook.setIsDefault(0);
        addressBook.setUserId(BaseContext.getCurrentId());
        addressBookMapper.updateIsDefaultByUserId(addressBook);

        //2、将当前地址改为默认地址 update address_book set is_default = ? where id = ?
        addressBook.setIsDefault(1);
        addressBookMapper.update(addressBook);
    }
}