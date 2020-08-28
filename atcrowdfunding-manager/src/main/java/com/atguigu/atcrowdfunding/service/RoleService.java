package com.atguigu.atcrowdfunding.service;

import com.atguigu.atcrowdfunding.bean.TRole;

import java.util.List;


public interface RoleService {
    public List<TRole> listRole(String keyWord);

    Integer saveRole(TRole role);

    Integer deleteBatch(List<Integer> listId);
}
