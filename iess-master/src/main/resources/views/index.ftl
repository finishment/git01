<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>后台管理-登陆</title>
    <#include "common.ftl">
    <link rel="stylesheet" href="${ctx}/css/index.css" media="all">
</head>
<body>
<div class="layui-container">
    <div class="admin-login-background">
        <div class="layui-form login-form">
            <form class="layui-form" action="">
                <div class="layui-form-item logo-title">
                    <h1>IESS面经分享系统</h1>
                </div>
                <div class="layui-form-item">
                    <label class="layui-icon layui-icon-username" for="username"></label>
                    <input type="text" name="username" lay-verify="required|account" placeholder="用户名或者邮箱" autocomplete="off" class="layui-input" >
                </div>
                <div class="layui-form-item">
                    <label class="layui-icon layui-icon-password" for="password"></label>
                    <input type="password" name="password" lay-verify="required|password" placeholder="密码" autocomplete="off" class="layui-input" >
                </div>
                <#--记住我-->
                <div class="layui-form-item">
                    <input type="checkbox" name="rememberMe" id="rememberMe" value="true" lay-skin="primary" title="记住密码">
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <a class="layui-btn" id="phoneLogin"  onclick="phoneLogins()">手机登录</a>
                </div>
                <div class="layui-form-item">
                    <button class="layui-btn layui-btn-fluid" lay-submit="" lay-filter="login">登 入</button>
                </div>
<#--                <div class="layui-form-item">-->
<#--                </div>-->
            </form>
            <button class="layui-btn layui-btn-fluid" id="registers" onclick="registers()">注 册</button>

        </div>
    </div>


</div>

<#--手机号码登录模板-->
<div class="layui-row" id="comboxss" style="display:none">
    <div class="layui-col-md6 layui-col-md-offset3 com" >
        <h2 align="center">手机号码登录</h2>
        <div class="layuimini-container">
            <div class="layuimini-main">

                <div class="layui-form layuimini-form">
                    <div class="layui-form-item">
                        <label class="layui-form-label">手机号</label>
                        <div class="layui-input-block">
                            <input type="text" class="layui-input userName"
                                   lay-verify="required" name="phoneNumber" id="phoneNumber"  value="" placeholder="请输入手机号">
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label">验证码</label>
                        <div class="layui-input-block">
                            <input type="text" class="layui-input userName"
                                   lay-verify="required" name="code" id="code"  value="" placeholder="请输入验证码">
                        </div>
                    </div>
                    </div>
                    <br/>
                </div>
                <div align="right">
                    <button class="layui-btn" id="send">发送验证码</button>
                </div>
        </div>
    </div>
</div>
<#--   注册模板 //-->
<div class="layui-row" id="combox" style="display:none">
    <div class="layui-col-md6 layui-col-md-offset3 com" >
        <h2 align="center">注册</h2>
        <div class="layuimini-container">
            <div class="layuimini-main">
                <div class="layui-form layuimini-form">
                        <div class="layui-form-item">
                            <label class="layui-form-label">用户名</label>
                            <div class="layui-input-block">
                                <input type="text" class="layui-input userName"
                                       lay-verify="required" name="userName" id="userName"  value="${(user.userName)!}" placeholder="请输入用户名">
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label required">密码</label>
                            <div class="layui-input-block">
                                <input type="password" name="password" id="password" lay-verify="required" lay-reqtext="密码不能为空"   value="" class="layui-input">
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">邮箱</label>
                            <div class="layui-input-block">
                                <input type="text" class="layui-input userEmail"
                                       lay-verify="email" name="email" value="${(user.email)!}"
                                       id="email"
                                       placeholder="请输入邮箱">
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">手机号</label>
                            <div class="layui-input-block">
                                <input type="text" class="layui-input userEmail"
                                       lay-verify="phone" name="phone" value="${(user.phone)!}" id="phone" placeholder="请输入手机号">
                            </div>
                        </div>
                        <br/>
                    </div>
                </div>
            </div>
    </div>
</div>
<script src="https://cdn.bootcdn.net/ajax/libs/jquery/3.6.0/jquery.min.js"></script>

<script src="${ctx}/js/index.js" charset="utf-8"></script>

<#--注册js-->
<script>
    function registers() {
        $("#combox").show();
        layer.open({
            type: 1 ,
            title: '注册',   //标题
            area: ["700px", "400px"],   //宽高
            shade: 0.4,   //遮罩透明度
            // btn: ['确定', '取消'], //按钮组
            btn: ['注册'],
            scrollbar: false ,//屏蔽浏览器滚动条
            content: $("#combox"),
            cancel : function() {
                // setTimeout('window.location.reload()', 0);
                $("#combox").hide();
                // $("#combox2").css("display","none");
            },
            yes: function(){//layer.msg('yes');    //点击确定回调
                var data={
                    "userName":$("#userName").val(),
                    "userPwd": $('#password').val(),
                    "email": $('#email').val(),
                    "phone": $('#phone').val(),
                };
                // console.log(data)
                registerss(data)
            },

        });
    }

    function registerss(data) {
        $.ajax({
            url: ctx+'/user/registerss',
            type: 'POST',
            contentType: "application/json",
            dataType: "json",
            data: JSON.stringify(data),
            // data:data.field,
            success: function (result) {
                if (result.code == 200) {
                    // 刷新页面
                    layer.alert(result.msg);
                    setTimeout('window.location.reload()', 3000);
                } else {
                    layer.alert(result.msg);
                }
            }
        });
        console.log(data)
    }
</script>

<#--手机号登录js-->
<script>
    // 按钮点击之后，会禁用 disabled 为true
    // 同时按钮里面的内容会变化， 注意 button 里面的内容通过 innerHTML修改
    // 里面秒数是有变化的，因此需要用到定时器
    // 定义一个变量，在定时器里面，不断递减
    // 如果变量为0 说明到了时间，我们需要停止定时器，并且复原按钮初始状态
    // var btn = document.querySelector('send');
    var btn= document.getElementById('send');
    var time = 60; // 定义剩下的秒数
    btn.addEventListener('click', function () {
        $('#send').addClass("layui-btn-disabled").attr("disabled",true);

        sendCode();
        var timer = setInterval(function () {
            if (time == 0) {
                // 清除定时器和复原按钮
                clearInterval(timer);
                $('#send').removeClass("layui-btn-disabled").attr("disabled",false);
                btn.innerHTML = '获取验证码';
                time = 60;
            } else {
                btn.innerHTML = time + '秒后重新获取';
                time--;
            }
        }, 1000)
    })
    function sendCode() {
        // var data={
        //     "phoneNumber": $('#phoneNumber').val(),
        // };
        $.ajax({
            url: ctx+'/api/msm/send/'+$('#phoneNumber').val(),
            type: 'GET',
            contentType: "application/json",
            dataType: "json",
            // data: JSON.stringify(data),
            success: function (result) {
                if (result.code == 200) {
                    // 刷新页面
                    layer.alert(result.msg);
                } else {
                    layer.alert(result.msg);
                }
            }
        });
    }
    function phoneLogins() {
        $("#comboxss").show();
        layer.open({
            type: 1 ,
            title: '手机号码登录',   //标题
            area: ["700px", "400px"],   //宽高
            shade: 0.4,   //遮罩透明度
            // btn: ['确定', '取消'], //按钮组
            btn: ['手机登录'],
            scrollbar: false ,//屏蔽浏览器滚动条
            content: $("#comboxss"),
            cancel : function() {
                // setTimeout('window.location.reload()', 0);
                $("#comboxss").hide();
                // $("#combox2").css("display","none");
            },
            yes: function(){//layer.msg('yes');    //点击确定回调
                // console.log(data)
                phoneLogin()
            },

        });
    }

    function phoneLogin() {
        // var data={
        //     "phoneNumber": $('#phoneNumber').val(),
        //     "code": $('#code').val(),
        // };
        $.ajax({
            url: ctx+'/api/msm/send/'+$('#phoneNumber').val()+'/'+$('#code').val(),
            type: 'GET',
            contentType: "application/json",
            dataType: "json",
            // data: JSON.stringify(data),
            // data:data.field,
            success: function (result) {
                if (result.code == 200) {
                    // 刷新页面
                    layer.alert(result.msg);
                    $.cookie("userIdStr", result.data.userIdStr);
                    $.cookie("userName", result.data.userName);
                    // setTimeout(, 3000);
                    window.location.href=ctx+"/main"
                } else {
                    layer.alert("操作失败");
                }
            }
        });
    }

</script>



</body>
</html>
