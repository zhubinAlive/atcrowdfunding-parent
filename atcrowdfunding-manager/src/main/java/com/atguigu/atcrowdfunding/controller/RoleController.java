package com.atguigu.atcrowdfunding.controller;

import com.atguigu.atcrowdfunding.bean.TRole;
import com.atguigu.atcrowdfunding.service.RoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;


@Controller
public class RoleController {

    @Autowired
    private RoleService roleService;


    @RequestMapping("/role/index")
    public String index(){
        return "role/index";
    }


    @ResponseBody//返回json格式
    @PreAuthorize("hasRole('大师')")
    @RequestMapping("/role/loadData")
    public Object loadData(@RequestParam(name = "pageNum",required = false,defaultValue = "1") Integer pageNum,
                           @RequestParam(name = "pageSize",required = false,defaultValue = "2") Integer pageSize,
                           @RequestParam(name = "keyWord",required = false,defaultValue = "") String keyWord){
        /**
         * startPage 开始分页
         * @param pageNum  页码
         * @param pageSize 每页显示数量
         */
        PageHelper.startPage(pageNum,pageSize);
        List<TRole> listRole = roleService.listRole(keyWord);
//        对listRole对象进行分页,每页5条数据
        PageInfo<TRole> pageInfo = new PageInfo<>(listRole,5);
        return pageInfo;
    }

    @RequestMapping("/role/save")
    @ResponseBody
    public Object save(TRole role){
        Integer row = roleService.saveRole(role);
        if(row ==1){
            return "yes";
        }else{
            return "no";
        }
    }

    @RequestMapping("/role/deleteBatch")
    public Object deleteBatch(String ids){//ids 字符串格式 "1,2"
        String[] idStr = ids.split(",");
        List<Integer> listId = new ArrayList<>();
        for (String id : idStr) {
            int idInt = Integer.parseInt(id);
            listId.add(idInt);
        }
        Integer row =  roleService.deleteBatch(listId);
       return "yes";
    }
}
