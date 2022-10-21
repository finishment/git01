layui.use(['form', 'layer'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery;

    // 关闭iframe对话框即弹出层
    $("#closeBtn").click(function () {
        var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
        parent.layer.close(index); //再执行关闭
    });

    /**
     *  监听提交按钮的submit事件
     */
    form.on("submit(updateModule)", function (data) {
        //弹出loading
        var index = layer.msg('数据提交中，请稍候', {icon: 16, time: false, shade: 0.8});
        // 发送ajax请求
        $.post(ctx+"/module/update", data.field, function (res) {
            if (res.code == 200) {
                setTimeout(function () {
                    layer.close(index);
                    layer.msg("操作成功！");
                    layer.closeAll("iframe");
                    //刷新父页面
                    parent.location.reload();
                }, 500);
            } else {
                layer.msg(rs.msg, {icon:5});
            }
        });
        return false;
    });
});