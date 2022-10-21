<!DOCTYPE html>
<html>
<head>
    <title>最热最火</title>
    <#include "../common.ftl">
</head>
<style>
    .container {
        margin: 0 auto;
        width: 360px;
        border-radius: 20px;
        box-shadow: rgba(0,0,0,.6) 10px 10px 2px;
        padding-top: 10px;
        text-align: center;
        background: #fff
    }

    .container .item {
        padding-left: 100px;
        background: #504d58 url(https://img-mid.csdnimg.cn/release/static/image/mid/ask/409256676536179.png) no-repeat 70px center;
        line-height: inherit;
        border-bottom: 2px solid #fff;
        line-height: 50px;
        color: #fff;
        text-align: left
    }
    .container .item:last-of-type {
        border-bottom: 0;
        border-bottom-left-radius: 20px;
        border-bottom-right-radius: 20px;
    }
</style>
<div class="col-md-9">
    <div class="data_list">
        <div class="data_list_title"><span class="glyphicon glyphicon-signal"></span>&nbsp;最强同学 </div>
        <div class="container-fluid">
            <div class="row" style="padding-top: 20px;">
                <div class="col-md-12">
                    <#--                    <%-- 柱状图的容器 --%>-->
                    <div id="numChart" style="height: 500px" align="center"></div>

                </div>
            </div>
        </div>
    </div>
</div>
<div class="col-md-9">
    <div class="data_list">
        <div class="data_list_title"><span class="glyphicon glyphicon-signal"></span>&nbsp;排行榜 </div>
        <div>
            <div class="container" style="background-color: #f7941d">
                <img src="https://www.yishuzi.cn/image.png?fsize=30&font=zzgx.ttf&text=%E6%9C%80%E7%81%AB%E7%BB%8F%E9%AA%8C&mirror=no&color=ee1d24&vcolor=000&bgcolor=f7941d&alpha=no&output=png&spacing=4&shadow=yes&transparent=no&icon=no&iconic=&top_spacing=5&left_spacing=6&icon_size=48" /><br />
                <img src="https://www.yishuzi.cn/image.png?fsize=30&font=gxsx.ttf&text=%E6%8E%92%E8%A1%8C%E6%A6%9C&mirror=no&color=ee1d24&vcolor=ee1d24&bgcolor=f7941d&alpha=no&output=png&spacing=4&shadow=yes&transparent=no&icon=no&iconic=&top_spacing=5&left_spacing=6&icon_size=48" width="150px" height="50px"/>

                <#assign x = 1>
                <#if !resultInfo??>
                    <h2>暂未查询到记录！</h2>
                <#else>
                <#--            <%-- 遍历获取云记列表 --%>-->
                    <div class="item">
                        <ul>
                            <#list resultInfo.result as item>
                                <li>top${x}.
<#--                                    评论数：${item.num}&nbsp;&nbsp;&nbsp;&nbsp;-->
                                    文章名：<a href="${ctx}/note/detail?noteId=${item.noteId}">${item.title}</a>
<#--                                    『${item.pubTime?string('yyyy-MM-dd')}』&nbsp;&nbsp;-->
                                </li>
                                <#assign x = x + 1>
                            </#list>
                        </ul>
                    </div>
                </#if>

            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="${ctx}/js/echarts/echarts.min.js"></script>
<script type="text/javascript" src="${ctx}/js/report/leaderboard.js"></script>

