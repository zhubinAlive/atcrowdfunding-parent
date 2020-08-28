package com.atguigu.atcrowdfunding.service.impl;

import com.atguigu.atcrowdfunding.bean.TAdmin;
import com.atguigu.atcrowdfunding.bean.TAdminExample;
import com.atguigu.atcrowdfunding.mapper.TAdminMapper;
import com.atguigu.atcrowdfunding.service.AdminService;
import com.atguigu.atcrowdfunding.util.Const;
import com.atguigu.atcrowdfunding.util.DateUtil;
import com.atguigu.atcrowdfunding.util.MD5Util;
import com.atguigu.atcrowdfunding.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private TAdminMapper adminMapper;

    @Override
    public TAdmin getAdminLogin(String loginacct, String userpswd) {
//       创建一个条件类 example
        TAdminExample example = new TAdminExample();
//        创建一个查询条件
        TAdminExample.Criteria criteria = example.createCriteria();
//        查询条件为 帐号 匹配
        criteria.andLoginacctEqualTo(loginacct);

//        密码加密后进行匹配
        String digest = MD5Util.digest(userpswd);
        criteria.andUserpswdEqualTo(digest);
//        根据给定条件类example 查询
        List<TAdmin> listAdmin = adminMapper.selectByExample(example);
//        如果查回来用户为空,返回错误信息
        if(listAdmin == null || listAdmin.size()== 0){
            throw new RuntimeException("帐号或密码错误");
        }

        return listAdmin.get(0);
    }

    @Override
    public List<TAdmin> listAdminByPage(String keyWord) {
        TAdminExample example = new TAdminExample();

        if(StringUtil.isNotEmpty(keyWord)){//keyWord 可以是帐号,名字,邮箱
//            分别创建三个条件类,分别是帐号,姓名,邮箱条件
            TAdminExample.Criteria criteria = example.createCriteria();
            criteria.andLoginacctLike("%"+keyWord+"%");

            TAdminExample.Criteria criteria2 = example.createCriteria();
            criteria2.andUsernameLike("%"+keyWord+"%");

            TAdminExample.Criteria criteria3 = example.createCriteria();
            criteria3.andEmailLike("%"+keyWord+"%");

            example.or(criteria);
            example.or(criteria2);
            example.or(criteria3);

       }
        List<TAdmin> admins = adminMapper.selectByExample(example);
        return admins;
    }

    @Override
    public void saveAdmin(TAdmin admin) {
//        设置创建用户时间
        admin.setCreatetime(DateUtil.getFormatTime());
//        设置用户密码 (默认密码123123)
//        admin.setUserpswd(MD5Util.digest(Const.DEFALUT_PASSWORD));
        admin.setUserpswd(new BCryptPasswordEncoder().encode(Const.DEFALUT_PASSWORD));
        adminMapper.insertSelective(admin);

    }

    @Override
    public TAdmin getAdminById(Integer id) {
        TAdmin admin = adminMapper.selectByPrimaryKey(id);
        return admin;
    }

    @Override
    public void updateAdmin(TAdmin admin) {
         adminMapper.updateByPrimaryKeySelective(admin);
    }

    @Override
    public void deleteAdminById(Integer id) {
        adminMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int deleteBatchByIds(List<Integer> listId) {
        TAdminExample example = new TAdminExample();
        TAdminExample.Criteria criteria = example.createCriteria();
        criteria.andIdIn(listId);
        int row = adminMapper.deleteByExample(example);
        return row;
    }
}
