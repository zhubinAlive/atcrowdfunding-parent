package com.atguigu.atcrowdfunding.service.impl;

import com.atguigu.atcrowdfunding.bean.TMenu;
import com.atguigu.atcrowdfunding.mapper.TMenuMapper;
import com.atguigu.atcrowdfunding.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private TMenuMapper menuMapper;

    @Override
    public List<TMenu> listMenus() {
//        查询所有菜单
        List<TMenu> menus = menuMapper.selectByExample(null);

//        保存所有父节点
        ArrayList<TMenu> parentMenus = new ArrayList<>();
        for (TMenu menu : menus) {
            if(menu.getPid() == 0){//父节点为0 ,说明为父节点
                parentMenus.add(menu);
            }
        }

//        找出子节点,再和父节点关联
        for (TMenu menu2 : menus) {
//            如果该节点为子节点
            if(menu2.getPid() != 0){
//                寻找其父节点,并进行组装
                for (TMenu parentMenu : parentMenus) {
//                    子节点的父id 为父节点的id
                    if(menu2.getPid() == parentMenu.getId()){
//                        将该子节点加入该父节点的子节点中
                        parentMenu.getChildMenus().add(menu2);
                    }
                }
            }
        }
        return parentMenus;
    }
}
