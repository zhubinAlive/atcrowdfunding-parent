package com.atguigu.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class PathListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
//        servletContext 是在tomcat启动时创建
        ServletContext servletContext = sce.getServletContext();
//      获取工程路径,相当于前端的 ${pageContext.request.contextPath}
        String contextPath = servletContext.getContextPath();
        servletContext.setAttribute("appPath",contextPath);

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        if(servletContext  != null){
        }

    }
}
