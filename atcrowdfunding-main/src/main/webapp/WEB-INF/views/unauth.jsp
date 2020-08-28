
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

<%--    静态包含,把两个类合并成一个类,编译生成一个类--%>
    <%@include file="/WEB-INF/common/css.jsp"%>

<%--    动态包含,两个类分开编译,生成两个类(servlet),当页面内容较多时使用--%>
<%--    <jsp:include page="/WEB-INF/common/css.jsp"></jsp:include>--%>

    <style>
        .tree li {
            list-style-type: none;
            cursor:pointer;
        }
        .tree-closed {
            height : 40px;
        }
        .tree-expanded {
            height : auto;
        }
    </style>
</head>

<body>

<%--动态包含nav 导航条--%>
<%--如果斜杠被服务器解析. 得到的路径是 : http://ip:port/工程路径/ , 映射到IDEA 代码的web目录--%>
    <jsp:include page="/WEB-INF/common/nav.jsp"></jsp:include>

<div class="container-fluid">
    <div class="row">
<%--       动态包含 左侧导航栏--%>
        <jsp:include page="/WEB-INF/common/side.jsp"></jsp:include>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <h1 class="page-header">控制面板</h1>

            <div class="row placeholders">
                <h2>没有权限访问该内容!</h2>
            </div>
        </div>
    </div>
</div>
<%@include file="/WEB-INF/common/js.jsp"%>
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
    });
</script>
</body>
</html>
