<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>IESS-面经分享系统</title>
    <#include "common.ftl">
</head>
<body class="layui-layout-body layuimini-all">
<div class="layui-layout layui-layout-admin">
    <div class="layui-header header">
        <div class="layui-logo">
            <a href="">
                <img src="images/logo.png" alt="logo">
                <h1>IESS-面经系统</h1>
            </a>
        </div>
        <a>
            <div class="layuimini-tool"><i title="展开" class="fa fa-outdent" data-side-fold="1"></i></div>
        </a>
        <ul class="layui-nav layui-layout-right">
            <li class="layui-nav-item mobile layui-hide-xs" lay-unselect>
                <a href="javascript:;" data-check-screen="full"><i class="fa fa-arrows-alt"></i></a>
            </li>
            <li class="layui-nav-item layuimini-setting">
                <a href="javascript:;">${(loginUser.trueName)!""}</a>
                <dl class="layui-nav-child">
                    <dd>
                        <a href="javascript:;" data-iframe-tab="${ctx}/user/toSettingPage" data-title="基本资料" data-icon="fa fa-gears">基本资料</a>
                    </dd>
                    <dd>
                        <a href="javascript:;" data-iframe-tab="${ctx}/user/toPasswordPage" data-title="修改密码" data-icon="fa fa-gears">修改密码</a>
                    </dd>
                    <dd>
                        <a href="javascript:;" class="login-out">退出登录</a>
                    </dd>
                </dl>
            </li>
            <li class="layui-nav-item layuimini-select-bgcolor mobile layui-hide-xs" lay-unselect>
                <a href="javascript:;"></a>
            </li>
        </ul>
    </div>

    <div class="layui-side layui-bg-black">
        <div class="layui-side-scroll layui-left-menu">
            <#-- 判断当前登录用户是否应用权限 -->
            <#if permissions??> <#-- ??用于判断变量是否存在，存在返回true，不存在返回false -->
            <ul class="layui-nav layui-nav-tree layui-left-nav-tree layui-this" id="currency">
                <#-- 通过freemark的内建指令来判断菜单是否显示 -->
                <#-- seq_contains(val):用于判断集合或数组中是否包含指定值，包含返回true，否则返回false -->
                <li class="layui-nav-item">
                    <a href="javascript:;" class="layui-menu-tips"><i class="fa fa-street-view"></i><span class="layui-left-nav"> 系统公告</span> <span class="layui-nav-more"></span></a>
                    <dl class="layui-nav-child">
                        <dd>
                            <a href="javascript:;" class="layui-menu-tips" data-type="tabAdd" data-tab-mpi="m-p-i-1" data-tab="announcement/index" target="_self"><i class="fa fa-tty"></i><span class="layui-left-nav"> 通知</span></a>
                        </dd>
                    </dl>
                </li>

<#--                <#if permissions?seq_contains("10")>   &lt;#&ndash; 授权码10表示营销管理模块 &ndash;&gt;-->
                <li class="layui-nav-item">
                    <a href="javascript:;" class="layui-menu-tips"><i class="fa fa-street-view"></i><span class="layui-left-nav"> 个人中心</span> <span class="layui-nav-more"></span></a>
                    <dl class="layui-nav-child">
<#--                        <#if permissions?seq_contains("1010")> &lt;#&ndash; 授权码1010表示营销机会管理 &ndash;&gt;-->
                        <dd>
                            <a href="javascript:;" class="layui-menu-tips" data-type="tabAdd" data-tab-mpi="m-p-i-1" data-tab="user/indexx" target="_self"><i class="fa fa-tty"></i><span class="layui-left-nav"> 内容</span></a>
                        </dd>
<#--                        </#if>-->
<#--                        <#if permissions?seq_contains("1020")> &lt;#&ndash; 授权码1020表示客户开发计划 &ndash;&gt;-->
                        <dd>
                            <a href="javascript:;" class="layui-menu-tips" data-type="tabAdd" data-tab-mpi="m-p-i-2" data-tab="collection/index" target="_self"><i class="fa fa-ellipsis-h"></i><span class="layui-left-nav"> 收藏</span></a>
                        </dd>
<#--                        </#if>-->
                    </dl>
                </li>
<#--                </#if>-->

<#--                <#if permissions?seq_contains("20")>   &lt;#&ndash; 授权码20表示客户管理模块 &ndash;&gt;-->
                <li class="layui-nav-item">
                    <a href="javascript:;" class="layui-menu-tips"><i class="fa fa-flag"></i><span class="layui-left-nav"> 面经交流</span> <span class="layui-nav-more"></span></a><dl class="layui-nav-child">
<#--                        <#if permissions?seq_contains("1010")> &lt;#&ndash; 授权码1010表示营销机会管理 &ndash;&gt;-->
                            <dd>
                                <a href="javascript:;" class="layui-menu-tips" data-type="tabAdd" data-tab-mpi="m-p-i-1" data-tab="view_chance/index" target="_self"><i class="fa fa-tty"></i><span class="layui-left-nav"> 面经分享</span></a>
                            </dd>
<#--                        </#if>-->
<#--                        <#if permissions?seq_contains("2010")>   &lt;#&ndash; 授权码2010表示客户信息管理 &ndash;&gt;-->
                        <dd>
                            <a href="javascript:;" class="layui-menu-tips" data-type="tabAdd" data-tab-mpi="m-p-i-3" data-tab="discuss/index" target="_self"><i class="fa fa-exchange"></i><span class="layui-left-nav"> 讨论区</span></a>
                        </dd>
<#--                        </#if>-->

<#--                        <#if permissions?seq_contains("2020")>   &lt;#&ndash; 授权码2020表示客户流失管理 &ndash;&gt;-->
                        <dd>
                            <a href="javascript:;" class="layui-menu-tips" data-type="tabAdd" data-tab-mpi="m-p-i-4" data-tab="customer_loss/index" target="_self"><i class="fa fa-user-times"></i><span class="layui-left-nav"> 公司优选</span></a>
                        </dd>
<#--                        </#if>-->
                    </dl>
                </li>
<#--                </#if>-->

                <#if permissions?seq_contains("30")>   <#-- 授权码30表示服务管理模块 -->
                <li class="layui-nav-item">
                    <a href="javascript:;" class="layui-menu-tips"><i class="fa fa-desktop"></i><span class="layui-left-nav"> 圈子</span> <span class="layui-nav-more"></span></a>
                    <dl class="layui-nav-child">
                        <#if permissions?seq_contains("3010")>   <#-- 授权码3010表示创建服务 -->
                        <dd>
                            <a href="javascript:;" class="layui-menu-tips" data-type="tabAdd" data-tab-mpi="m-p-i-5" data-tab="customer_serve/index/1" target="_self"><i class="fa fa-tachometer"></i><span class="layui-left-nav"> 跑步打卡</span></a>
                        </dd>
                        </#if>

                        <#if permissions?seq_contains("3020")>   <#-- 授权码3020表示服务分配 -->
                        <dd>
                            <a href="javascript:;" class="layui-menu-tips" data-type="tabAdd" data-tab-mpi="m-p-i-6" data-tab="circle/xuYuan" target="_self"><i class="fa fa-tachometer"></i><span class="layui-left-nav"> 许愿池</span></a>
                        </dd>
                        </#if>

                        <#if permissions?seq_contains("3030")>   <#-- 授权码3030表示服务处理 -->
                        <dd>
                            <a href="javascript:;" class="layui-menu-tips" data-type="tabAdd" data-tab-mpi="m-p-i-7" data-tab="circle/index" target="_self"><i class="fa fa-tachometer"></i><span class="layui-left-nav"> 吐槽</span></a>
                        </dd>
                        </#if>

                        <#if permissions?seq_contains("3040")>   <#-- 授权码3040表示服务反馈 -->
                        <dd>
                            <a href="javascript:;" class="layui-menu-tips" data-type="tabAdd" data-tab-mpi="m-p-i-8" data-tab="circle/zuFang" target="_self"><i class="fa fa-tachometer"></i><span class="layui-left-nav"> 租房圈</span></a>
                        </dd>
                        </#if>

                        <#if permissions?seq_contains("3050")>   <#-- 授权码3050表示服务归档 -->
                        <dd>
                            <a href="javascript:;" class="layui-menu-tips" data-type="tabAdd" data-tab-mpi="m-p-i-9" data-tab="circle/moYu" target="_self"><i class="fa fa-tachometer"></i><span class="layui-left-nav"> 摸鱼圈</span></a>
                        </dd>
                        </#if>
                    </dl>
                </li>
                </#if>

<#--                <#if permissions?seq_contains("40")>   &lt;#&ndash; 授权码40表示统计报表模块 &ndash;&gt;-->
                    <li class="layui-nav-item">
                        <a href="javascript:;" class="layui-menu-tips"><i class="fa fa-home"></i><span class="layui-left-nav"> 统计报表</span> <span class="layui-nav-more"></span></a><dl class="layui-nav-child">
                            <#if permissions?seq_contains("4010")>   <#-- 授权码4010表示客户贡献分析 -->
                                <dd>
                                    <a href="javascript:;" class="layui-menu-tips" data-type="tabAdd" data-tab-mpi="m-p-i-10" data-tab="report/info" target="_self"><i class="fa fa-tachometer"></i><span class="layui-left-nav">总览</span></a>
                                </dd>
                            </#if>

                            <#--                    <#if permissions?seq_contains("4020")>   &lt;#&ndash; 授权码4020表示客户构成分析 &ndash;&gt;-->
                            <#--                    <dd>-->
                            <#--                        <a href="javascript:;" class="layui-menu-tips" data-type="tabAdd" data-tab-mpi="m-p-i-10" data-tab="report/company/info" target="_self"><i class="fa fa-tachometer"></i><span class="layui-left-nav"> 公司总览</span></a>-->
                            <#--                    </dd>-->
                            <#--                    </#if>-->

                            <#--                    <#if permissions?seq_contains("4030")>   &lt;#&ndash; 授权码4030客户服务分析 &ndash;&gt;-->
                            <#--                    <dd>-->
                            <#--                        <a href="javascript:;" class="layui-menu-tips" data-type="tabAdd" data-tab-mpi="m-p-i-10" data-tab="sale_chance/index" target="_self"><i class="fa fa-tachometer"></i><span class="layui-left-nav"> 班级总览</span></a>-->
                            <#--                    </dd>-->
                            <#--                    </#if>-->

<#--                            <#if permissions?seq_contains("4060")>   &lt;#&ndash; 授权码4060表示客户类别分析 &ndash;&gt;-->
                                <dd>
                                    <a href="javascript:;" class="layui-menu-tips" data-type="tabAdd" data-tab-mpi="m-p-i-10" data-tab="report/leaderboard" target="_self"><i class="fa fa-tachometer" id="charts"></i><span class="layui-left-nav"> 最热最火</span></a>
                                </dd>
<#--                            </#if>-->
                        </dl>
                    </li>
<#--                </#if>-->

                <#if permissions?seq_contains("60")>   <#-- 授权码60表示系统管理模块 -->
                    <li class="layui-nav-item">
                        <a href="javascript:;" class="layui-menu-tips"><i class="fa fa-gears"></i><span class="layui-left-nav"> 系统管理</span> <span class="layui-nav-more"></span></a>
                        <dl class="layui-nav-child">

                            <#if permissions?seq_contains("6010")>   <#-- 授权码6010表示用户管理 -->
                                <dd>
                                    <a href="javascript:;" class="layui-menu-tips" data-type="tabAdd" data-tab-mpi="m-p-i-11" data-tab="user/index" target="_self"><i class="fa fa-user"></i><span class="layui-left-nav"> 用户管理</span></a>
                                </dd>
                            </#if>

                            <#if permissions?seq_contains("6050")>   <#-- 授权码6010表示用户管理 -->
                                <dd>
                                    <a href="javascript:;" class="layui-menu-tips" data-type="tabAdd" data-tab-mpi="m-p-i-11" data-tab="user/monitor" target="_self"><i class="fa fa-user"></i><span class="layui-left-nav"> 班长管理</span></a>
                                </dd>
                            </#if>

                            <#if permissions?seq_contains("6080")>   <#-- 授权码6010表示用户管理 -->
                                <dd>
                                    <a href="javascript:;" class="layui-menu-tips" data-type="tabAdd" data-tab-mpi="m-p-i-11" data-tab="user/class" target="_self"><i class="fa fa-user"></i><span class="layui-left-nav"> 我的班级</span></a>
                                </dd>
                            </#if>

                            <#if permissions?seq_contains("6060")>   <#-- 授权码6010表示用户管理 -->
                                <dd>
                                    <a href="javascript:;" class="layui-menu-tips" data-type="tabAdd" data-tab-mpi="m-p-i-11" data-tab="user/leader" target="_self"><i class="fa fa-user"></i><span class="layui-left-nav"> 组长管理</span></a>
                                </dd>
                            </#if>

                            <#if permissions?seq_contains("6070")>   <#-- 授权码6010表示用户管理 -->
                                <dd>
                                    <a href="javascript:;" class="layui-menu-tips" data-type="tabAdd" data-tab-mpi="m-p-i-11" data-tab="user/student" target="_self"><i class="fa fa-user"></i><span class="layui-left-nav"> 组员管理</span></a>
                                </dd>
                            </#if>

                            <#if permissions?seq_contains("6020")>   <#-- 授权码6020表示角色管理 -->
                                <dd class="">
                                    <a href="javascript:;" class="layui-menu-tips" data-type="tabAdd" data-tab-mpi="m-p-i-12" data-tab="role/index" target="_self"><i class="fa fa-tachometer"></i><span class="layui-left-nav"> 授权管理</span></a>
                                </dd>
                            </#if>

                            <#if permissions?seq_contains("6030")>   <#-- 授权码6030表示资源管理 -->
                                <dd class="">
                                    <a href="javascript:;" class="layui-menu-tips" data-type="tabAdd" data-tab-mpi="m-p-i-13" data-tab="module/index" target="_self"><i class="fa fa-tachometer"></i><span class="layui-left-nav"> 资源管理</span></a>
                                </dd>
                            </#if>

                            <#if permissions?seq_contains("6040")>   <#-- 授权码6040表示字典管理 -->
                                <dd>
                                    <a href="javascript:;" class="layui-menu-tips" data-type="tabAdd" data-tab-mpi="m-p-i-10" data-tab="data_dic/index" target="_self"><i class="fa fa-tachometer"></i><span class="layui-left-nav"> 字典管理</span></a>
                                </dd>
                            </#if>
                        </dl>
                    </li>
                </#if>
                <span class="layui-nav-bar" style="top: 201px; height: 0px; opacity: 0;"></span>
            </ul>
            </#if>
        </div>
    </div>

    <div class="layui-body">
        <div class="layui-tab" lay-filter="layuiminiTab" id="top_tabs_box">
            <ul class="layui-tab-title" id="top_tabs">
                <li class="layui-this" id="layuiminiHomeTabId" lay-id="welcome"><i class="fa fa-home"></i> <span>首页</span></li>
            </ul>

            <ul class="layui-nav closeBox">
                <li class="layui-nav-item">
                    <a href="javascript:;"> <i class="fa fa-dot-circle-o"></i> 页面操作</a>
                    <dl class="layui-nav-child">
                        <dd><a href="javascript:;" data-page-close="other"><i class="fa fa-window-close"></i> 关闭其他</a></dd>
                        <dd><a href="javascript:;" data-page-close="all"><i class="fa fa-window-close-o"></i> 关闭全部</a></dd>
                    </dl>
                </li>
            </ul>
            <div class="layui-tab-content clildFrame">
                <div id="layuiminiHomeTabIframe" class="layui-tab-item layui-show">
                </div>
            </div>
        </div>
    </div>

</div>

<script type="text/javascript" src="${ctx}/js/main.js"></script>
</body>
</html>
