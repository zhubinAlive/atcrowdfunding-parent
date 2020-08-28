package com.atguigu.atcrowdfunding.service;

import com.atguigu.atcrowdfunding.bean.TAdmin;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AdminService {
    public TAdmin getAdminLogin(String loginacct, String userpswd );

    List<TAdmin> listAdminByPage(String keyWord);

    void saveAdmin(TAdmin admin);

    TAdmin getAdminById(Integer id);

    void updateAdmin(TAdmin admin);

    void deleteAdminById(Integer id);

    int deleteBatchByIds(List<Integer> listId);
}
