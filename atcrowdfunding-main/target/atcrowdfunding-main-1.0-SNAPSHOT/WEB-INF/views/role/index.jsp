
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

<%@include file="/WEB-INF/common/nav.jsp"%>
<div class="container-fluid">
    <div class="row">

        <%@include file="/WEB-INF/common/side.jsp"%>

        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
                </div>
                <div class="panel-body">
                    <form class="form-inline" role="form" style="float:left;">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <input class="form-control has-success" name="keyWord" type="text" placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button type="button" id="btnSearch" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询</button>
                    </form>
                    <button type="button" class="btn btn-danger" style="float:right;margin-left:10px;" id="deleteBatch" ><i class=" glyphicon glyphicon-remove"></i> 删除</button>
                    <button type="button" class="btn btn-primary" style="float:right;" id="btnAdd" ><i class="glyphicon glyphicon-plus"></i> 新增</button>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr >
                                <th width="30">#</th>
                                <th width="30"><input id="checkAll" type="checkbox"></th>
                                <th>名称</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody>
<%--                                角色信息--%>


                            </tbody>
                            <tfoot>
                            <tr >
                                <td colspan="6" align="center">
                                    <ul class="pagination">
<%--                                        分页数据(分页页码)--%>

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

<!-- Button trigger modal -->
<%--<button type="button" class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myModal">--%>
<%--    Launch demo modal--%>
<%--</button>--%>

<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">添加角色信息</h4>
            </div>
            <div class="modal-body">
                <form role="form"  method="post">

                    <div class="form-group">
                        <label for="exampleInputPassword1">角色名称</label>
                        <input type="text" class="form-control" name="name" id="exampleInputPassword1" placeholder="请输入用户名称">
                    </div>

                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" id="btnTJ">添加</button>
            </div>
        </div>
    </div>
</div>

<%@include file="/WEB-INF/common/js.jsp"%>
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

        //加载角色信息和分页信息, 1 表示查看第一页的数据.
        loadData(1);
    });

    var keyWord;//查询条件

    //用于异步和后台交互
    function loadData(pageNum) {
        // 可用的异步方法 : $.ajax({})  $.get()      $.post()
        // $.getJSON("url","参数",function (res) {
        // 可以通过使用JSONP形式的回调函数来加载其他网域的JSON数据,如 "myurl?callback=?"。
        // jQuery 将自动替换 ? 为正确的函数名，以执行回调函数。
        // 注意：此行以后的代码将在这个回调函数执行前执行。
        //url 为请求的url , 参数为请求的url传递的参数,function 为回调函数  ,res 为回传数据
        //res 包含角色的信息,和分页信息 pageInfo
        $.getJSON("${appPath}/role/loadData",{"pageNum":pageNum,"pageSize":2,"keyWord":keyWord},function (res) {
            if(res=="403"){
                layer.msg("访问受限",{time:2000,icon:5,shift:6});
                return ;
            }
            showRole(res.list);//展示角色信息  res.list 后台传过来的数据为pageInfo,则res.list代表pageInfo.list
            showPage(res);//展示分页数据
        });

        //获取并展示角色的信息  roleList 为pageInfo.list
        function showRole(roleList) {
            var content="";
            for(var i = 0 ; i < roleList.length;i++){
                content+='<tr>';
                content+='    <td>'+(i+1)+'</td>';
                content+='    <td><input type="checkbox" id="'+(roleList[i].id)+'" class="roleCheck"></td>';
                content+='   <td>'+(roleList[i].name)+'</td>';
                content+='    <td>';
                content+='	<button type="button" class="btn btn-success btn-xs"><i class=" glyphicon glyphicon-check"></i></button>';
                content+='	<button type="button" class="btn btn-primary btn-xs"><i class=" glyphicon glyphicon-pencil"></i></button>';
                content+='	<button type="button" class="btn btn-danger btn-xs"><i class=" glyphicon glyphicon-remove"></i></button>';
                content+='    </td>';
                content+='</tr>';
            }
            //给网页添加查询到的信息
            $("tbody").html(content);
        }

        //展示分页信息
        function showPage(pageInfo) {
            var content="";

            //如果是第一页,则禁止点上一页
            if(pageInfo.isFirstPage){
                content+='<li class="disabled"><a href="#">上一页</a></li>';
            }else{//不是第一页,可以正常点
                //由于是异步请求,不能直接调用控制器,得调用异步方法 loadData
                content+='<li ><a onclick="loadData('+(pageInfo.pageNum - 1)+')">上一页</a></li>';
            }

            for(var i = 0 ; i < pageInfo.navigatepageNums.length; i++){
                //当前页 , 高亮显示
                if(pageInfo.pageNum==pageInfo.navigatepageNums[i]){
                    //异步请求当前页数据
                    content+='<li class="active"><a onclick="loadData('+(pageInfo.navigatepageNums[i])+')">'+pageInfo.navigatepageNums[i]+' <span class="sr-only">(current)</span></a></li>';
                }else{
                    content+='<li><a onclick="loadData('+(pageInfo.navigatepageNums[i])+')">'+pageInfo.navigatepageNums[i]+' <span class="sr-only">(current)</span></a></li>';

                }
            }

            //如果是最后一页,则禁止点下一页
            if(pageInfo.isLastPage){
                content+='<li class="disabled"><a href="#">下一页</a></li>';
            }else{//不是第一页,可以正常点
                //由于是异步请求,不能直接调用控制器,得调用异步方法 loadData
                content+='<li ><a onclick="loadData('+(pageInfo.pageNum + 1)+')">下一页</a></li>';
            }
            //添加到网页中
            $(".pagination").html(content);

        }
    }

    $("#btnSearch").click(function () {
       // 标签选择器
       keyWord =  $("input[name='keyWord']").val();
       loadData(1);
    });

    $("#btnAdd").click(function () {
        $('#myModal').modal({
           show:true,
            backdrop:false

        });
    });

    $("#btnTJ").click(function () {
       var roleName=$("input[name='name']").val();
       $.post("${appPath}/role/save",{"name":roleName},function (res) {
           if(res=="yes"){
                layer.msg("添加角色成功!",{time:2000,icon:6},function (res) {
                    //添加成功后隐藏信息
                    $("#myModal").modal("hide");
                    loadData(1);
                });
           }else {
                layer.msg("添加角色失败!");
           }
       });
    });

    var checkAll = $("#checkAll");
    checkAll.click(function () {
       var roleChecks = $(".roleCheck");//集合
        $.each(roleChecks,function (i, check) {//check 为dom对象
            //赋值时要先把jquery对象转化为dom对象
            check.checked = checkAll.get(0).checked;
        });
    });

    $("#deleteBatch").click(function () {
        var checks = $(".roleCheck:checked");
        if(checks.length == 0){
            layer.msg("还没有选中要删除的角色信息!");
        }else{
            layer.confirm("确定要删除吗?",{btn:["确定","取消"]},
                function () {
                    //用数组保存要删除的id
                    var ids = new Array();
                    $.each(checks,function (i, check) {//check 为dom对象
                        //将dom对象转化为jquery对象,便于这里取id属性
                        var id = $(check).attr("id");
                        ids.push(id);
                    });
                    //异步加载
                    $.get("${appPath}/role/deleteBatch?ids="+ids,function (res) {
                        if(res=="yes"){
                            layer.msg("删除成功");
                            //删除成功后,重新加载数据,展示在第一页
                            loadData(1);
                        }else {
                            layer.msg("删除不成功!",{time:3000,icon:5});
                        }
                    });
                },
                function () {
                    layer.msg("取消操作!",{time:3000,icon:5});
                }
            );
        }
    });

</script>
</body>
</html>

