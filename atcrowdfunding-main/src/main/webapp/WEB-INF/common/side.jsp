<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="col-sm-3 col-md-2 sidebar">
    <div class="tree">
        <ul style="padding-left:0px;" class="list-group">
<%--            jstl遍历数据库中查到的所有父菜单(子菜单与其做了关联)--%>
            <c:forEach items="${parentMenus}" var="parent">
<%--                该节点没有子节点直接显示--%>
                <c:if test="${parent.childMenus.size()==0}">
                    <li class="list-group-item tree-closed" >
                        <a href="${appPath}/${parent.url}"><i class="${parent.icon}"></i> ${parent.name}</a>
                    </li>
                </c:if>
<%--                该节点有子节点--%>
                <c:if test="${parent.childMenus.size()!=0}">
                    <li class="list-group-item tree-closed">
                        <span><i class="${parent.icon}"></i> ${parent.name} <span class="badge" style="float:right">${parent.childMenus.size()}</span></span>
                        <ul style="margin-top:10px;display:none;">

                            <c:forEach items="${parent.childMenus}" var="child">
                                <li style="height:30px;">
                                    <a href="${appPath}/${child.url}"><i class="${child.icon}"></i> ${child.name}</a>
                                </li>
                            </c:forEach>

                        </ul>
                    </li>
                </c:if>

            </c:forEach>
        </ul>
    </div>
</div>
