<!DOCTYPE html>
<html>
<head>
    <#include "../common.ftl">
</head>
<body class="childrenBody">
<blockquote class="layui-elem-quote quoteBox">
    <form class="layui-form">
        <div class="layui-inline">
            <div class="layui-input-inline">
                <input type="text" id="noteName" name="noteName" class="layui-input searchVal" placeholder="帖子名称" value=""/>
            </div>
            <a id="search_btn" class= "layui-btn search_btn" data-type="reload">
                <i class="layui-icon">&#xe615;</i>
                搜索
            </a>
        </div>
    </form>
</blockquote>
<div class="col-md-9">
    <div class="data_list">
        <div class="data_list_title">
            <span class="glyphicon glyphicon glyphicon-th-list"></span>&nbsp;
            吐槽
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
                            『${item.pubTime?string('yy-MM-dd')}』&nbsp;&nbsp;
                            <a href="${ctx}/note/detail?noteId=${item.noteId}">${item.title}</a>
                        </li>
                    </#list>
                </ul>
            </div>
            <div align="right">
                <a class="layui-btn layui-btn-radius layui-btn-normal layui-icon-edit"
                   href="${ctx}/note/view" id="btn3">
                    <#--            <i class="layui-icon">&#xe608;</i>-->
                    我要吐槽
                </a>
            </div>
<#--            <%-- 设置分页导航 --%>-->
            <nav style="text-align: center">
                <ul class="pagination  center">
<#--                    <%-- 如果当前不是第一页，则显示上一页的按钮 --%>-->
                    <#if page.pageNum!=1>
                        <li>
                            <a href="${ctx}/discuss/index?pageNum=${page.prePage}&title=${title!""}&date=${date!""}&typeId=${typeId!""}"><span>«</span> </a>
                        </li>
                    </#if>
<#--                    <%-- 导航页数 --%>-->
                    <#list page.startNavPage..page.endNavPage as p>
                        <li <#if page.pageNum == p>class="active"</#if> >
                        <a href="${ctx}/discuss/index?pageNum=${p}&title=${title!""}&date=${date!""}&typeId=${typeId!""}">${p}</a>
                        </li>
                    </#list>
<#--                    <%-- 如果当前不是最后一页，则显示下一页的按钮 --%>-->
                    <#if page.pageNum < page.totalPages>
                        <li>
                            <a href="${ctx}/discuss/index?&pageNum=${page.nextPage}&title=${title!""}&date=${date!""}&typeId=${typeId!""}">
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
            热门话题
            <div class="note_datas">
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $("#search_btn").click(function () {
        var noteName = $("[name=noteName]").val();
        window.location.href=ctx+"/tease/index?title="+noteName;
    });
</script>
</body>
</html>
