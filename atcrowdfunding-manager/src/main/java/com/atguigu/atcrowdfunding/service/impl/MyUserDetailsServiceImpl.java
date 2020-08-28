package com.atguigu.atcrowdfunding.service.impl;

import com.atguigu.atcrowdfunding.bean.TAdmin;
import com.atguigu.atcrowdfunding.bean.TAdminExample;
import com.atguigu.atcrowdfunding.bean.TPermission;
import com.atguigu.atcrowdfunding.bean.TRole;
import com.atguigu.atcrowdfunding.mapper.TAdminMapper;
import com.atguigu.atcrowdfunding.mapper.TPermissionMapper;
import com.atguigu.atcrowdfunding.mapper.TRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class MyUserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private TAdminMapper adminMapper;

    @Autowired
    private TRoleMapper roleMapper;

    @Autowired
    private TPermissionMapper permissionMapper;

    @Override
    public UserDetails loadUserByUsername(String loginacct) throws UsernameNotFoundException {
//        按名查询用户
        TAdminExample example = new TAdminExample();
        TAdminExample.Criteria criteria = example.createCriteria();
        criteria.andLoginacctEqualTo(loginacct);
        List<TAdmin> admins = adminMapper.selectByExample(example);
        if(admins == null || admins.size() == 0){
            return  null;
        }
        TAdmin admin = admins.get(0);
//        查询用户角色信息
        List<TRole> listRole = roleMapper.getRoleByAdminId(admin.getId());

//        查询用户权限信息
       List<TPermission> listPermission = permissionMapper.getPermissionByAdminId(admin.getId());

//      存放所有的角色和权限信息
        Set<GrantedAuthority> authorities = new HashSet<>();

//        将角色信息放到权限中
        for (TRole role : listRole) {
            authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getName()));
        }

//        将权限放到权限集合中
        for (TPermission permission : listPermission) {
            authorities.add(new SimpleGrantedAuthority(permission.getName()));
        }

        return new User(admin.getLoginacct().toString(),admin.getUserpswd().toString(), authorities);
    }
}

