<!DOCTYPE html>
<html>
<head>
    <title>系统公告</title>
    <#include "../common.ftl">
</head>
<style>
    @font-face {
        font-family: "iconfont"; /* Project id 2516453 */
        src: url("//at.alicdn.com/t/font_2516453_g6qjhhqblt9.woff2?t=1620545333370")
        format("woff2"),
        url("//at.alicdn.com/t/font_2516453_g6qjhhqblt9.woff?t=1620545333370")
        format("woff"),
        url("//at.alicdn.com/t/font_2516453_g6qjhhqblt9.ttf?t=1620545333370")
        format("truetype");
    }
    .iconfont {
        font-family: "iconfont" !important;
        font-size: 16px;
        font-style: normal;
        -webkit-font-smoothing: antialiased;
        -webkit-text-stroke-width: 0.2px;
        -moz-osx-font-smoothing: grayscale;
        margin: 0;
    }
    i {
        color: #ff6146;
        font-size: 20px;
        margin-right: 10px;
    }
    #barck{
        background-image: url("https://pic.616pic.com/bg_w1180/00/00/75/tVWcO1MZq7.jpg");
        background-size: cover;
    }
    div img{
        width: 100%;
        height: 100%;
        object-fit:cover;
    }
</style>
<body class="childrenBody">

    <form class="layui-form" >
        <#if permissions?seq_contains("601002")>   <#-- 授权码601002表示用户查询 -->
            <blockquote class="layui-elem-quote quoteBox">
                <form class="layui-form">
                    <div class="layui-inline">
                        <div class="layui-input-inline">
                            <input type="text" name="announcement" class="layui-input searchVal" placeholder="通知" />
                        </div>
                        <a class="layui-btn search_btn" data-type="reload">
                            <i class="layui-icon">&#xe615;</i>
                            搜索
                        </a>
                    </div>
                </form>
            </blockquote>
        </#if>

        <table id="announcementList" class="layui-table"  lay-filter="announcement"></table>

        <#-- 表头工具栏 -->
        <script type="text/html" id="toolbarDemo">
            <div class="layui-btn-container">
                <#if permissions?seq_contains("601001")>   <#-- 授权码601001表示用户添加 -->
                    <a class="layui-btn layui-btn-normal addNews_btn" lay-event="add">
                        <i class="layui-icon">&#xe608;</i>
                        添加通知
                    </a>
                </#if>

                <#if permissions?seq_contains("601004")>   <#-- 授权码601004表示用户删除 -->
                    <a class="layui-btn layui-btn-normal delNews_btn" lay-event="del">
                        <i class="layui-icon">&#xe608;</i>
                        删除通知
                    </a>
                </#if>
            </div>
        </script>

        <!--行工具栏-->
        <script id="announcementListBar" type="text/html">
            <#if permissions?seq_contains("601003")>   <#-- 授权码601003表示用户修改 -->
                <a class="layui-btn layui-btn-xs" id="edit" lay-event="edit">编辑</a>
            </#if>

            <#if permissions?seq_contains("601004")>   <#-- 授权码601004表示用户删除 -->
                <a class="layui-btn layui-btn-xs layui-btn-danger" lay-event="del">删除</a>
            </#if>
        </script>
    </form>
<script type="text/javascript" src="${ctx}/js/announcement/announcement.js"></script>
</body>
</html>