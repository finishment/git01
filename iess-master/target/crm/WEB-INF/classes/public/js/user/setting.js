layui.use(['form', 'layer', 'formSelects'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery;
    /**
     * 提交表单事件
     *  格式：
     *  form.on('submit(提交按钮的lay-filter属性值)',function(data){...});
     */
    form.on('submit(saveBtn)', function(data){

        // 获取用户主键id，如果有值表示要执行添加操作，如果有值表示执行更新
        var userId = $("[name=id]").val();

        var index = layer.msg("数据提交中，请稍候...",{
            icon: 16, //图标
            time: false, // 不关闭
            shade: 0.8 // 设置遮罩的透明度
        });

        var url = ctx + "/user/add";

        if (userId != null && Number.parseInt(userId) > 0) {
            // 更新操作
            url = ctx + "/user/update";
        }

        // 获取所有表单元素的值
        var formData = data.field;
        // console.log(formData);

        $.post(url, formData, function (result) {
            // console.log(result);
            if (result.code == 200) {
                // 成功
                layer.msg("操作成功！",{icon:6});
                // 关闭加载层
                layer.close(index);
                // 关闭弹出层
                layer.closeAll("iframe");
                // 刷新父窗口，重新加载数据
                //TODO 可尝试其他改法，提交数据后页面无法及时更新
                parent.location.reload();
            } else {
                // 添加失败
                layer.msg(result.msg, {icon: 5});
            }
        });

        return false;

    });
});
