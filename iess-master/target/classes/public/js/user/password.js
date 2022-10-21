layui.use(['form','jquery','jquery_cookie'], () => {
   var form = layui.form,
       layer = layui.layer,
       $ = layui.jquery,
       $ = layui.jquery_cookie($);

    /**
     * form表单的监听
     * form.on('submit(提交按钮的ay-filter属性值)', function(data){
     *
     * });
     */
   form.on('submit(saveBtn)', data => {
       // 所有表单元素的键值对
        console.log(data.field);

        // 发送ajax请求
       $.ajax({
           type: "post",
           url: ctx + "/user/updatePwd",
           data: {
               oldPwd: data.field.oldPwd,
               newPwd: data.field.newPwd,
               repeatPwd: data.field.repeatPwd,

           },
           success: result => {
               // 判断是否修改成功
               if (result.code == 200) {
                    // 修改成功,且成功后会清空cookie，然后跳转到登录页面
                   layer.msg("用户密码修改成功，系统将在3秒钟后退出...", function () {
                      // 清空当前地址（网站）的cookie
                      $.removeCookie("userIdStr",{domain:"localhost",path:"/crm"});
                      $.removeCookie("userName",{domain:"localhost",path:"/crm"});
                      $.removeCookie("trueName",{domain:"localhost",path:"/crm"});

                      // 跳转到登陆页面(父窗口跳转)
                       window.parent.location.href = ctx + "/index";
                   });

               } else {
                   // 修改失败
                   layer.msg(result.msg, {icon: 5});
               }
           }
       })
   });
});