<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <%@include file="/WEB-INF/common/css.jsp"%>
    <style>
        .tree li {
            list-style-type: none;
            cursor:pointer;
        }
        table tbody tr:nth-child(odd){background:#F4F4F4;}
        table tbody td:nth-child(even){color:#C00;}
    </style>
</head>

<body>

<jsp:include page="/WEB-INF/common/nav.jsp"></jsp:include>

<div class="container-fluid">
    <div class="row">
        <jsp:include page="/WEB-INF/common/side.jsp"></jsp:include>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
                </div>
                <div class="panel-body">

                    <form class="form-inline" role="form" style="float:left;" id="queryBtn" action="${appPath}/admin/index" method="post">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
<%--                                param可以获得当前请求的参数,可以获得后台 /admin/index 收到的数据keyWord--%>
                                <input class="form-control has-success" value="${param.keyWord}" type="text" name="keyWord" placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button type="button" class="btn btn-warning" onclick="$('#queryBtn').submit()"><i class="glyphicon glyphicon-search"></i> 查询</button>
                    </form>

                    <button type="button" class="btn btn-danger" id="deleteBatch" style="float:right;margin-left:10px;"><i class=" glyphicon glyphicon-remove"></i> 删除</button>

                    <sec:authorize access="hasRole('大师')">
                        <button type="button" class="btn btn-primary" style="float:right;" onclick="window.location.href='${appPath}/admin/toAdd'"><i class="glyphicon glyphicon-plus"></i> 新增</button>
                    </sec:authorize>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr >
                                <th width="30">#</th>
                                <th width="30"><input type="checkbox" id="checkAll"></th>
                                <th>账号</th>
                                <th>名称</th>
                                <th>邮箱地址</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${pageInfo.list}" var="admin">
                                <tr>
                                    <td>1</td>
                                    <td><input type="checkbox" id="${admin.id}" class="checkboxAdmin"></td>
                                    <td>${admin.loginacct}</td>
                                    <td>${admin.username}</td>
                                    <td>${admin.email}</td>
                                    <td>
                                        <button type="button" class="btn btn-success btn-xs"><i class=" glyphicon glyphicon-check"></i></button>
                                        <button type="button" class="btn btn-primary btn-xs" onclick="location.href='${appPath}/admin/toUpdate?id=${admin.id}&pageNum=${pageInfo.pageNum}'"><i class=" glyphicon glyphicon-pencil"></i></button>
                                        <button type="button" class="btn btn-danger btn-xs" onclick="location.href='${appPath}/admin/delete?id=${admin.id}'"><i class=" glyphicon glyphicon-remove"></i></button>
                                    </td>
                                </tr>
                            </c:forEach>

                            </tbody>
                            <tfoot>
                            <tr >
                                <td colspan="6" align="center">
                                    <ul class="pagination">

<%--                                        如果是第一页,禁用掉上一页的跳转--%>
                                        <c:if test="${pageInfo.isFirstPage}">
                                            <li class="disabled"><a href="#">上一页</a></li>
                                        </c:if>

<%--                                          如果不是第一页,正常跳转--%>
                                        <c:if test="${not pageInfo.isFirstPage}">
                                            <li><a href="${appPath}/admin/index?pageNum=${pageInfo.pageNum-1}&keyWord=${param.keyWord}">上一页</a></li>
                                        </c:if>
<%--                                           navigatepageNums位所有导航页号,i 为当前页号--%>
                                        <c:forEach items="${pageInfo.navigatepageNums}" var="i">
                                            <c:if test="${pageInfo.pageNum==i}">
<%--                                            active为当前页号的高亮显示--%>
                                                <li class="active"><a href="${appPath}/admin/index?pageNum=${i}&keyWord=${param.keyWord}">${i} <span class="sr-only">(current)</span></a></li>
                                            </c:if>
                                            <%--如果不是当前页号,取消高亮 --%>
                                            <c:if test="${pageInfo.pageNum!=i}">
                                                <li ><a href="${appPath}/admin/index?pageNum=${i}&keyWord=${param.keyWord}">${i} <span class="sr-only">(current)</span></a></li>
                                            </c:if>
                                        </c:forEach>


                                        <c:if test="${pageInfo.isLastPage}">
                                            <li class="disabled"><a href="#">下一页</a></li>
                                        </c:if>

                                        <c:if test="${not pageInfo.isLastPage}">
                                            <li><a href="${appPath}/admin/index?keyWord=${param.keyWord}&pageNum=${pageInfo.pageNum+1}">下一页</a></li>
                                        </c:if>
                                    </ul>
                                </td>
                            </tr>

                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<%@include file="/WEB-INF/common/js.jsp"%>>
<%--引入layer 的js--%>
<script src="${appPath}/static/layer/layer.js"></script>
<script type="text/javascript">
    $(function () {
        $(".list-group-item").click(function(){
            if ( $(this).find("ul") ) {
                $(this).toggleClass("tree-closed");
                if ( $(this).hasClass("tree-closed") ) {
                    $("ul", this).hide("fast");
                } else {
                    $("ul", this).show("fast");
                }
            }
        });

        //jquery对象
        var checkAll = $("#checkAll");
    //    获取行中的复选框  集合
        var checkBoxAdmins = $(".checkboxAdmin");
        checkAll.click(function () {

           //  $.each(checkBoxAdmins,function (i,checkBox) {
//             通用例遍方法，可用于例遍对象和数组。
//              不同于例遍 jQuery 对象的 $().each() 方法，此方法可用于例遍任何对象。
//              回调函数拥有两个参数：第一个为对象的成员或数组的索引，第二个为对应变量或内容。
//              如果需要退出 each 循环可使回调函数返回 false，其它返回值将被忽略。
// checkBox 为dom对象,checked为其属性(checkBox为复选框checkBoxAdmins中的每一个复选框)
           $.each(checkBoxAdmins,function (i,checkBox) {
               checkBox.checked=checkAll[0].checked;
           });
        });

    //    获取批量删除按钮
        var deleteBatch = $("#deleteBatch");
        deleteBatch.click(function () {
        //    获取所有选中的复选框
        //    过滤选择器
            var checkeds = $(".checkboxAdmin:checked");
            //声明一个数组,用于存储id
            var ids = new Array();

            if(checkeds.length == 0){
                layer.msg("还没有选中任何要删除的行.",{time:2000,icon:6});
            }else{
                layer.confirm("你确定要删除吗?",{btn:["确定","取消"]},
                        function () {
                            $.each(checkeds,function (i,check) {
                                var id = $(check).attr("id");
                                //把所有被选中的admin的id 存进ids中,便于批量删除
                                ids.push(id);
                            });
                            location.href="${appPath}/admin/deleteBatch?ids="+ ids;
                            layer.msg("删除成功",{time:3000,icon:6});
                        },
                        function () {
                            layer.msg("取消批量删除操作",{time:2000});
                        }
                    );
            }

        });

    });

</script>
</body>
</html>
