<!DOCTYPE html>
<html>
<head>
    <title>每月分享数目统计</title>
    <#include "../common.ftl">
</head>
<div class="col-md-9">
    <div class="data_list">
        <div class="data_list_title"><span class="glyphicon glyphicon-signal"></span>&nbsp;数据报表 </div>
        <div class="container-fluid">
            <div class="row" style="padding-top: 20px;">
                <div class="col-md-12">
<#--                    <%-- 柱状图的容器 --%>-->
                    <div id="monthChart" style="height: 500px" align="center"></div>

<#--                    <%-- 百度地图的加载 --%>-->
                    <h3 align="center">用户地区分布图</h3>
<#--                    <%-- 百度地图的容器 --%>-->
                    <div id="baiduMap" style="height: 600px;width: 80%;"></div>

                </div>
            </div>
        </div>
    </div>
</div>

<#--<%--
    关于Echarts报表的使用
        1. 下载Echarts的依赖 （JS文件）
        2. 在需要使用的页面引入Echarts的JS文件
        3. 为 ECharts 准备一个具备高宽的 DOM 容器
        4. 通过 echarts.init 方法初始化一个 echarts 实例并通过 setOption 方法生成一个报表
 --%>-->

<script type="text/javascript" src="${ctx}/js/echarts/echarts.min.js"></script>
<script type="text/javascript" src="${ctx}/js/report/info.js"></script>
<#--<%-- 引用百度地图API文件，需要申请百度地图对应ak密钥--%>-->
<script type="text/javascript" src="https://api.map.baidu.com/api?v=1.0&&type=webgl&ak=yrxymYTyuefnxNtXbZcMU8phABXtu6TG"></script>
<script type="text/javascript">

</script>
<style>
    #baiduMap{ margin:0 auto; width:320px; height:100px;}
</style>
