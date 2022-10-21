layui.use(['table','layer'],function(){
    var layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        table = layui.table;

    //向原始表格容器填充数据
    var tableIns = table.render({
        // 为表格指定一个id属性
        id: 'usersTable'
        // table 容器元素的id值
        ,elem: '#userList'
        // 容器的高度 full-差值
        ,height: 'full-125'
        // 单元格的最小宽度
        ,cellHeight: 95
        //数据接口地址
        ,url: ctx + '/user/classList'
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
            ,{field: 'userName', title: '用户名', align: 'center'}
            ,{field: 'trueName', title: '真实姓名', align: 'center'}
            ,{field: 'phone', title: '联系电话', align: 'center'}
            ,{field: 'email', title: '用户邮箱', align: 'center'}
            ,{field: 'createDate', title: '创建时间', align: 'center'}
            ,{field: 'updateDate', title: '更新时间', align: 'center'}
            // 行工具栏  templet: '#行工具栏ID属性值'
            ,{title: '操作', templet: '#userListBar', fixed: 'right', align: 'center', minWidth: 150}
        ]]
    });

    /**
     * 点击搜索按钮触发事件
     */
    $('.search_btn').click(function () {
        var userName = $("[name=userName]").val(); // 客户名
        var email = $("[name=email]").val(); // 创建人
        var phone = $("[name=phone]").val(); // 状态

        /**
         * 表格重载即多条件查询
         */
        tableIns.reload({
            // 设置需要给后端传递的参数
            where: { //设定异步数据接口的额外参数，任意设
                // 查询条件，
                userName
                ,email
                ,phone
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
    table.on('toolbar(users)', function(data){
        switch(data.event){
            case 'add': // 添加用户
                // layer.msg('添加');
                openAddOrUpdateUserDialog();
                break;
            case 'del': // 删除用户
                // layer.msg('删除');
                deleteUserByIds(data);
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
    function openAddOrUpdateUserDialog(userId) {
        var url = ctx + "/user/toAddOrUpdateUserPage";
        var title = '<h4>用户管理 - 添加用户<h4>';
        if (userId != null && Number.parseInt(userId) > 0) {
            // 更新操作
            title = '<h4>用户管理 - 更新用户<h4>';
            url += "?id=" + userId;
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


    function openAddOrUpdateClassGroup(userId) {
        var url = ctx + "/user/toAddOrUpdateClassGroup";
        var title = '<h4>用户管理 - 添加班别<h4>';
        if (userId != null && Number.parseInt(userId) > 0) {
            // 更新操作
            title = '<h4>用户管理 - 更新班别<h4>';
            url += "?id=" + userId;
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
    table.on('tool(users)', function(data){
        switch(data.event){
            case 'group':
                openAddOrUpdateClassGroup(data.data.id)
                break;
            case 'edit': // 更新用户
                // console.log(data.data.id);
                openAddOrUpdateUserDialog(data.data.id);
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
                        url: ctx + "/user/delete",
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
    function deleteUserByIds(data) {
        // 获取数据表格选中行的状态  参数为 tableIns 变量中表格的id属性值
        var checkStatus = table.checkStatus("usersTable");
        // 获取选中行的数据
        var userData = checkStatus.data;

        if (userData == null || userData.length < 1) {
            layer.msg("待删除记录不存在!",{icon:5});
            return false;
        }

        layer.confirm("此操作不可逆，确定继续吗？",{icon:3, title: "营销机会管理"}, function (index) {
            // 点击确认
            // 关闭确认框
            layer.close(index);

            var ids = "ids=";
            // 遍历 userData，拼接id数组
            for (var i = 0; i < userData.length; i++) {
                ids += userData[i].id + "&ids=";
            }

            ids = ids.substr(0, ids.lastIndexOf("&"));
            // console.log(ids);  // ids=3&ids=5&ids=4

            // 发送ajax请求
            $.ajax({
                type: 'post',
                url: ctx + '/user/delete',
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