layui.use(['form', 'layer'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery;


    /**
     * 关闭添加/更新角色窗口
     */
    $("#closeBtn").click(function () {
        var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
        parent.layer.close(index); //再执行关闭
    });

    /**
     * 提交表单事件
     *  格式：
     *  form.on('submit(提交按钮的lay-filter属性值)',function(data){...});
     */
    form.on("submit(addOrUpdateRole)",function (data) {

        var index = layer.msg("数据提交中，请稍候...",{
            icon: 16, //图标
            time: false, // 不关闭
            shade: 0.8 // 设置遮罩的透明度
        });

        var url = ctx + "/role/add";

        // 获取角色ID值，判断是添加函数修改操作
        var roleId = $('[name=id]').val().trim();
        if (roleId != null && Number.parseInt(roleId) > 0) {
            url = ctx + "/role/update";
        }

        // 获取表单元素中的值
        var formData = data.field;

        $.post(url, formData, function (result) {
            if (result.code == 200) {
                // 成功
                layer.msg("操作成功！",{icon:6});
                // 关闭加载层
                layer.close(index);
                // 关闭弹出层
                layer.closeAll("iframe");
                // 刷新父窗口，重新加载数据
                parent.location.reload();
            } else {
                // 添加失败
                layer.msg(result.msg, {icon: 5});
            }
        });

        return false;

    })

    

});