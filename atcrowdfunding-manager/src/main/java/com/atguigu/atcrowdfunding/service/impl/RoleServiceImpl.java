package com.atguigu.atcrowdfunding.service.impl;

import com.atguigu.atcrowdfunding.bean.TRole;
import com.atguigu.atcrowdfunding.bean.TRoleExample;
import com.atguigu.atcrowdfunding.mapper.TRoleMapper;
import com.atguigu.atcrowdfunding.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private TRoleMapper roleMapper;

    @Override
    public List<TRole> listRole(String keyWord) {
        TRoleExample example = new TRoleExample();
        TRoleExample.Criteria criteria = example.createCriteria();
        criteria.andNameLike( "%" + keyWord + "%");
        return  roleMapper.selectByExample(example);
    }

    @Override
    public Integer saveRole(TRole role) {
       return roleMapper.insertSelective(role);
    }

    @Override
    public Integer deleteBatch(List<Integer> listId) {
        TRoleExample example = new TRoleExample();
        TRoleExample.Criteria criteria = example.createCriteria();
        criteria.andIdIn(listId);
        int row = roleMapper.deleteByExample(example);
        return row;
    }
}
