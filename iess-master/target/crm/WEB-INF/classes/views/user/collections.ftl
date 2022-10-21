<!DOCTYPE html>
<html>
<head>
    <title>用户管理</title>
    <#include "../common.ftl">
</head>
<body class="childrenBody">
<form class="layui-form" >
    <blockquote class="layui-elem-quote quoteBox">
        <form class="layui-form">
            <div class="layui-inline">
                <div class="layui-input-inline">
                    <input type="text" id="title" name="title" class="layui-input searchVal" placeholder="帖子名称" value=""/>
                </div>
                <a id="search_btn" class= "layui-btn search_btn" data-type="reload">
                    <i class="layui-icon">&#xe615;</i>
                    搜索
                </a>
            </div>
        </form>
    </blockquote>
    <div class="layui-btn-container" >
        <button class="layui-btn layui-btn-radius layui-btn-normal" id="btn1" value="讨论帖">
            <#--            <i class="layui-icon">&#xe608;</i>-->
            讨论帖
        </button>
        <button  class="layui-btn layui-btn-radius layui-btn-normal" id="btn2" value="动态">
            <#--            <i class="layui-icon">&#xe608;</i>-->
            动态
        </button>
        <button  class="layui-btn layui-btn-radius layui-btn-normal" id="btn3" value="收藏">
            <#--            <i class="layui-icon">&#xe608;</i>-->
            收藏
        </button>
    </div>
    <div class="col-md-9">
        <div class="data_list">
            <div class="data_list_title">
                <span class="glyphicon glyphicon glyphicon-th-list"></span>&nbsp;
                ${menu!"讨论帖"}
            </div>
            <#--        <%-- 判断云记列表是否存在 --%>-->
            <#if !page??>
                <h2>暂未查询到记录！</h2>
            <#else>
            <#--            <%-- 遍历获取云记列表 --%>-->
                <div class="note_datas">
                    <ul>
                        <#list page.dataList as item>
                            <li>
<#--                                『${item.createDate?string('yyyy-MM-dd')}』&nbsp;&nbsp;-->
                                <a href="${ctx}/note/detail?noteId=${item.noteId!""}">${item.title!""}</a>
                            </li>
                        </#list>
                    </ul>
                </div>
                <div align="right">
                    <a href="${ctx}/note/view" class="layui-btn layui-btn-radius layui-btn-normal layui-icon-edit" id="btn3">
                        <#--            <i class="layui-icon">&#xe608;</i>-->
                        编写文章
                    </a>
                </div>
            <#--            <%-- 设置分页导航 --%>-->
                <nav style="text-align: center">
                    <ul class="pagination  center">
                        <#--                    <%-- 如果当前不是第一页，则显示上一页的按钮 --%>-->
                        <#if page.pageNum!=1>
                            <li>
                                <a href="${ctx}/collection/index?pageNum=${page.prePage}&title=${title!""}&date=${date!""}&typeId=${typeId!""}"><span>«</span> </a>
                            </li>
                        </#if>
                        <#--                    <%-- 导航页数 --%>-->
                        <#list page.startNavPage..page.endNavPage as p>
                            <li <#if page.pageNum == p>class="active"</#if> >
                                <a href="${ctx}/collection/index?pageNum=${p}&title=${title!""}&date=${date!""}&typeId=${typeId!""}">${p}</a>
                            </li>
                        </#list>
                        <#--                    <%-- 如果当前不是最后一页，则显示下一页的按钮 --%>-->
                        <#if page.pageNum < page.totalPages>
                            <li>
                                <a href="${ctx}/collection/index?&pageNum=${page.nextPage}&title=${title!""}&date=${date!""}&typeId=${typeId!""}">
                                    <span>»</span>
                                </a>
                            </li>
                        </#if>
                    </ul>
                </nav>

            </#if>
        </div>
    </div>
    <div class="col-md-3">
        <div class="data_list">
            <div class="data_list_title">
                <span class="glyphicon glyphicon glyphicon-th-list"></span>&nbsp;
                近期热门
                <div class="note_datas">
                </div>
            </div>
        </div>
    </div>
</form>

<script type="text/javascript">
    $("#search_btn").click(function () {
        var title = $("[name=title]").val();
        window.location.href=ctx+"/collection/index?title="+title;
    });
    $("#btn1").click(function () {
        var btn1 = $("#btn1").val();
        window.location.href=ctx+"/user/indexx?menu="+btn1;
    });
    $("#btn2").click(function () {
        var btn2 = $("#btn2").val();
        window.location.href=ctx+"/user/comment?menu="+btn2;
    });
    $("#btn3").click(function () {
        var btn3 = $("#btn3").val();
        window.location.href=ctx+"/collection/index?menu="+btn3;
    });
</script>
</body>
</html>
