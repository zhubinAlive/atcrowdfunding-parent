package com.atguigu.atcrowdfunding.controller;

import com.atguigu.atcrowdfunding.bean.TAdmin;
import com.atguigu.atcrowdfunding.bean.TMenu;
import com.atguigu.atcrowdfunding.service.AdminService;
import com.atguigu.atcrowdfunding.service.MenuService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private AdminService adminService ;

    @Autowired
    private MenuService menuService;

    @RequestMapping("/atcrowdfunding/main")
//    跳转到主页面
    public String main(HttpSession session){
        List<TMenu> parentMenus = menuService.listMenus();
        session.setAttribute("parentMenus",parentMenus);
        return "main";
    }


    @RequestMapping("/unauth.html")
    public String unauth(){
        return "unauth";
    }


    /*@RequestMapping("/login")
    public String login(String loginacct, String userpswd, HttpSession session){
        try{
            TAdmin adminLogin = adminService.getAdminLogin(loginacct, userpswd);
//            登录成功,在session域中设置用户
            session.setAttribute("admin",adminLogin);
        }catch (Exception e){
//       登录失败,设置错误信息,返回登录页面
            session.setAttribute("err",e.getMessage());
            return "login";
        }
//        重定向到main方法, 避免刷新时,由于采取转发机制,重新进行登录.
        return "redirect:/atcrowdfunding/main";
    }*/

    /**通用方法
     * 查询用户信息
     * 分页查询(pageNum当前页码,pageSize每页显示的行数)
     * @return
     */
    @PreAuthorize("hasRole('学徒')")
    @RequestMapping("/admin/index")
    public String index(@RequestParam(name="pageNum",required = false,defaultValue = "1") Integer pageNum,
                        @RequestParam(name="pageSize",required = false,defaultValue = "2") Integer pageSize,
                        @RequestParam(name="keyWord",required = false,defaultValue = "") String keyWord,
                        HttpSession session){

//        pageNum为当前分页页码,pageSize为当前页面的行数
        PageHelper.startPage(pageNum,pageSize);
        List<TAdmin> admins =  adminService.listAdminByPage(keyWord);
//        pageInfo中保存分页的信息和数据库中查询的结果
        PageInfo<TAdmin> pageInfo = new PageInfo<>(admins,5);//5代表分页的页码数量
        session.setAttribute("pageInfo",pageInfo);

        return "admin/index";
    }

    /*@RequestMapping("/admin/logout")
    public String logout(HttpSession session){
        if(session != null){
            session.invalidate();
        }
        return "login";
    }*/

//    跳转到添加页面
    @RequestMapping("/admin/toAdd")
    public String toAdd(){

        return "admin/add";
    }

//    添加用户信息
    @RequestMapping("/admin/add")
    public String add(TAdmin admin){

        adminService.saveAdmin(admin);
        return "redirect:/admin/index?pageNum=" + Integer.MAX_VALUE;
    }

    /**
     * 跳转到修改页面,根据用户id将用户信息显示到修改页面.
     * @return
     */
    @RequestMapping("/admin/toUpdate")
//    因为用的是转发(一次请求),所以用model就足够
//    这里的pageNum不要处理,后面的update页面要用
//    注意:返回只能是转发,否则pageNum就会丢失
    public String toUpdate(Integer id, Model model,Integer pageNum ){
       TAdmin admin = adminService.getAdminById(id);
        model.addAttribute("admin", admin);
        return "/admin/update";
    }

    @RequestMapping("/admin/update")
    public String update(TAdmin admin,Integer pageNum){
        adminService.updateAdmin(admin);

        return "redirect:/admin/index?pageNum=" + pageNum ;
    }

    @RequestMapping("/admin/delete")
    public String delete(Integer id){
        adminService.deleteAdminById(id);
        return "redirect:/admin/index";
    }

    @RequestMapping("/admin/deleteBatch")
    public String deleteBatch(String ids){
//        批量删除方式一:
////        拆分字符串
//        String[] idStr = ids.split(",");
//
//        for (String s : idStr) {
////            类型转换
//            int id = Integer.parseInt(s);
//            delete(id);
//        }

//        方式二:
        String[] idStr = ids.split(",");
//        用list集合保存id
        List<Integer> listId = new ArrayList<>();
        for (String s : idStr) {
            int id = Integer.parseInt(s);
            listId.add(id);
        }
       int row =  adminService.deleteBatchByIds(listId);
        System.out.println(row);
        return "redirect:/admin/index";
    }














}
