package com.carepaws.service.impl;

import com.carepaws.context.BaseContext;
import com.carepaws.entity.AddressBook;
import com.carepaws.mapper.AddressBookMapper;
import com.carepaws.service.AddressBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class AddressBookServiceImpl implements AddressBookService {
    @Autowired
    private AddressBookMapper addressBookMapper;

    /**
     * 实现查询地址簿列表
     */
    public List<AddressBook> list(AddressBook addressBook) {
        return addressBookMapper.list(addressBook);
    }

    /**
     * 实现保存地址簿
     */
    public void save(AddressBook addressBook) {
        addressBook.setUserId(BaseContext.getCurrentId());
        addressBook.setIsDefault((byte) 0); // 修改为byte类型
        addressBookMapper.insert(addressBook);
    }

    /**
     * 实现根据id查询地址簿
     */
    public AddressBook getById(Long id) {
        return addressBookMapper.getById(id);
    }

    /**
     * 实现根据id更新地址簿
     */
    public void update(AddressBook addressBook) {
        addressBookMapper.update(addressBook);
    }

    /**
     * 实现设置默认地址簿
     */
    @Transactional
    public void setDefault(AddressBook addressBook) {
        //1、将当前用户的所有地址修改为非默认地址 update address_book set is_default = ? where user_id = ?
        addressBook.setIsDefault((byte) 0);    // 修改为byte类型
        addressBook.setUserId(BaseContext.getCurrentId());  // 获取当前登录用户的ID并设置到 AddressBook 对象
        addressBookMapper.updateIsDefaultByUserId(addressBook);

        //2、将当前地址改为默认地址 update address_book set is_default = ? where id = ?
        addressBook.setIsDefault((byte) 1);   // 修改为byte类型
        addressBookMapper.update(addressBook);
    }

    /**
     * 实现根据id删除地址簿
     */
    public void deleteById(Long id) {
        addressBookMapper.deleteById(id);
    }

}