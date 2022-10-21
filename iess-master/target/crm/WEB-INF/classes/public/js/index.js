layui.use(['form','jquery','jquery_cookie'], function () {
    var form = layui.form,
        layer = layui.layer,
        $ = layui.jquery,
        $ = layui.jquery_cookie($);

    /**
     * 表单 submit 提交
     * form.on(submit(按钮的lay-filter属性值), function(data){
     *      return false; //阻止表单跳转。
     * })
     */
    form.on('submit(login)', function(data){
        console.log(data.field) //当前容器的全部表单字段，名值对形式：{name: value}
        /* 表单元素校验 */

        /* 发送ajax请求，传递用户名和密码给后台，执行用户登录操作 */
        $.ajax({
            type:'post',
            url: ctx + "/user/login",
            data: {
                userName: data.field.username,
                userPwd: data.field.password
            },
            success: function (result) { // 请求成功的回调函数
                console.log("result", result);
                // 判断是否登录成功，如果 code=200则表示成功，否则失败
                if (result.code == 200) {
                    // 登陆成功
                    /**
                     * 设置用户是登录状态
                     * 方法1：利用session会话
                     *      保存用户信息，如果会话存在，则用户是登录状态；否则是非登录状态
                     *      缺点：服务器关闭或浏览器关闭，session就失效了
                     *      解决方案：使用redis实现session持久化
                     * 方法2：利用cookie存储
                     *      保存用户信息，cookie未失效，则用户是登录状态
                     *      缺点：默认浏览器关闭cookie将会失效
                     */
                    layer.msg("登陆成功！", {icon:1,time:50, shade:0.4},function () {

                        // 判断用户是否选择记住密码（判断复选框是否被选中，如果被选中，则设置cookie7天内有效）
                        if ($('#rememberMe').prop("checked")) {
                            // 表示选中，设置cookie7天内有效
                            $.cookie("userIdStr", result.result.userIdStr, {expires:7});
                            $.cookie("userName", result.result.userName, {expires:7});
                            $.cookie("trueName", result.result.trueName, {expires:7});
                        } else {
                            // 1.将用户信息设置到cookie中
                            $.cookie("userIdStr", result.result.userIdStr);
                            $.cookie("userName", result.result.userName);
                            $.cookie("trueName", result.result.trueName);
                        }


                        // 2.登录成功后，跳转到首页
                        window.location = ctx + "/main";
                    });
                } else {// 登陆失败
                    // 弹出一个对话框提示用户 参数1：提示信息 参数2：图标
                    layer.msg(result.msg, {icon:5});
                }
            }
        });
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可
    });
});
