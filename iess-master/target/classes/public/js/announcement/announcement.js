


layui.use(['table','layer'],function(){
    var layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        table = layui.table;

    //向原始表格容器填充数据
    var tableIns = table.render({
        // 为表格指定一个id属性
        id: 'announcementTable'
        // table 容器元素的id值
        ,elem: '#announcementList'
        // 容器的高度 full-差值
        ,height: 'full-125'
        // 单元格的最小宽度
        ,cellHeight: 95
        //数据接口地址
        ,url: ctx + '/announcement/list'
        ,page: true //开启分页
        // 默认每页显示的数量
        ,limit: 10
        // 每页记录数的可选项
        ,limits: [5,10,20,30,50]
        // 开启同步工具栏区域, 工具栏ID
        ,toolbar: '#toolbarDemo'
        ,cols: [[ //表头
            // field:要求field属性值与返回的数据中对应的属性字段名一致
            // title：设置列标题
            // sort：是否允许排序，默认false
            // fixed：固定列
            {type: 'checkbox', fixed: 'center'}
            ,{field: 'id', title: '编号',  sort: true, fixed: 'left'}
            ,{field: 'announcement', title: '通知内容', align: 'center'}
            ,{field: 'createDate', title: '发布时间', align: 'center'}
            // 行工具栏  templet: '#行工具栏ID属性值'
            ,{title: '操作', templet: '#announcementListBar', fixed: 'right', align: 'center', minWidth: 150}
        ]]
    });

    /**
     * 点击搜索按钮触发事件
     */
    $('.search_btn').click(function () {
        var announcement = $("[name=announcement]").val();
        /**
         * 表格重载即多条件查询
         */
        tableIns.reload({
            // 设置需要给后端传递的参数
            where: { //设定异步数据接口的额外参数，任意设
                // 查询条件，
                announcement
            }
            ,page: {
                curr: 1 //重新从第 1 页开始
            }
        });
    });


    /**
     * 监听头部工具栏
     * 格式：
     *  table.on('toolbar(原始表格容器的lay-filter属性值)',function(data){...});
     */
    table.on('toolbar(announcement)', function(data){
        switch(data.event){
            case 'add': // 添加用户
                // layer.msg('添加');
                openAddOrUpdateAnnouncementDialog();
                break;
            case 'del': // 删除用户
                // layer.msg('删除');
                var checkStatus=table.checkStatus(data.config.id)
                deleteAnnouncementByIds(checkStatus.data);
                break;
            default:
        };
    });

    /**
     * 打开添加或修改用户窗口
     * @param userId 用户主键ID
     *              如果值存在且大于0，则表示要进行更新操作
     *              如果只为空，则表示要进行添加操作
     */
    function openAddOrUpdateAnnouncementDialog(id) {
        var url =ctx+"/announcement/toAddOrUpdateAnnouncementPage";
        var title = '<h4>通知管理 - 添加通知</h4>';
        if (id != null && Number.parseInt(id) > 0) {
            // 更新操作
            title = '<h4>通知管理 - 更新通知</h4>';
            url+= "?id=" + id;
        }
        //iframe层
        layui.layer.open({
            type: 2,
            title,
            shade: 0.8,
            area: ['650px', '400px'],
            content: [url,'no'],
            maxmin: true
        });
    }

    /**
     * 绑定行工具栏
     * 格式：
     *  table.on('tool(原始表格容器的lay-filter属性值)',function(data){...});
     */
    table.on('tool(announcement)', function(data){
        switch(data.event){
            case 'edit': // 更新用户
                // console.log(data.data.id);
                openAddOrUpdateAnnouncementDialog(data.data.id);
                break;
            case 'del': // 删除用户
                // layer.msg('删除');
                // 弹出确认框询问用户是否确认删除
                layer.confirm("此操作不可逆，确定继续吗？",{icon:3, title: "营销机会管理"}, function (index) {
                    // 关闭确认框
                    layer.close(index);
                    // f发送ajax请求删除记录
                    $.ajax({
                        type: "post",
                        url: ctx + "/announcement/delete",
                        // 我们这里只是删除一条记录
                        data: {
                            ids:data.data.id
                        },
                        success: function (result) {
                            // 判断删除结果
                            if (result.code == 200) {
                                // 提示删除成功
                                layer.msg("删除成功!",{icon: 6});
                                // 刷新数据表格
                                tableIns.reload();
                                // 刷新页面
                                location.reload();
                            } else {
                                // 提示删除失败
                                layer.msg(result.msg,{icon: 5});
                            }
                        }
                    });
                });
                break;
            default:
        }
    });

    /**
     * 批量逻辑删除用户
     * @param userId
     */
    function deleteAnnouncementByIds(data) {


        if (data == null || data.length < 1) {
            layer.msg("请选择要删除的通知!",{icon:5});
            return false;
        }

        layer.confirm("此操作不可逆，确定继续吗？",{icon:3, title: "通知管理"}, function (index) {
            // 点击确认
            // 关闭确认框
            layer.close(index);

            var ids = "ids=";
            // 遍历 userData，拼接id数组
            for (var i = 0; i < data.length; i++) {
                ids += data[i].id + "&ids=";
            }

            ids = ids.substr(0, ids.lastIndexOf("&"));
            // console.log(ids);  // ids=3&ids=5&ids=4

            // 发送ajax请求
            $.ajax({
                type: 'post',
                url: ctx + '/announcement/delete',
                data: ids,
                success: function (result) {
                    // 判断删除结果
                    if (result.code == 200) {
                        // 提示删除成功
                        layer.msg("删除成功!",{icon: 6});
                        // 刷新数据表格
                        tableIns.reload();
                        // 刷新页面
                        location.reload();
                    } else {
                        // 提示删除失败
                        layer.msg(result.msg,{icon: 5});
                    }
                }
            })
        });


    }
});