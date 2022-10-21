layui.use(['table','layer'],function(){
    var layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        table = layui.table;

    //向原始表格容器填充数据
    var tableIns = table.render({
        // 为表格指定一个id属性
        id: 'rolesTable'
        // table 容器元素的id值
        ,elem: '#roleList'
        // 容器的高度 full-差值
        ,height: 'full-125'
        // 单元格的最小宽度
        ,cellHeight: 95
        //数据接口地址
        ,url: ctx + '/role/list'
        ,page: true //开启分页
        // 默认每页显示的数量
        ,limit: 5
        // 每页记录数的可选项
        ,limits: [5,10,20,30,50]
        // 开启同步工具栏区域, 表头工具栏ID
        ,toolbar: '#toolbarDemo'
        ,cols: [[ //表头
            // field:要求field属性值与返回的数据中对应的属性字段名一致
            // title：设置列标题
            // sort：是否允许排序，默认false
            // fixed：固定列
            {type: 'checkbox', fixed: 'center'}
            ,{field: 'id', title: '编号',  sort: true, fixed: 'left'}
            ,{field: 'roleName', title: '角色名',  sort: true, fixed: 'left'}
            ,{field: 'roleRemark', title: '用户备注', align: 'center'}
            ,{field: 'createDate', title: '创建时间', align: 'center'}
            ,{field: 'updateDate', title: '更新时间', align: 'center'}
            // 行工具栏  templet: '#行工具栏ID属性值'
            ,{title: '操作', templet: '#roleListBar', fixed: 'right', align: 'center', minWidth: 150}
        ]]
    });

    /**
     * 监听layui数据表格表头工具栏
     * 格式：
     * table.on('toolbar(原始表格容器元素的lay-filter属性值)', function(data){...});
     */
    table.on('toolbar(roles)', function (data) {
        // console.log(data);
        switch (data.event) {
            case 'add': // 添加
                // 打开添加/更新角色对花括
                openAddOrUpdateRoleDialog();
                break;
            case 'grant': // 授权
                // 获取数据表格选中行的数据 table.checkStatus('数据表格的id属性值')
                // 法一：通过配置项获取数据表格的id属性值
                var checkStatus = table.checkStatus(data.config.id);
                // 法二：直接写数据表格的id属性值
                //var checkStatus = table.checkStatus('rolesTable');
                // 打开授权的对话框
                openGrantDialog(checkStatus.data);
                break;
            default:
        }
    });

    /**
     * 打开添加/更新角色对话框
     * @param roleId
     */
    function openAddOrUpdateRoleDialog(roleId){
        var url = ctx + "/role/toAddOrUpdateRolePage";
        var title = '<h3>角色管理 - 添加角色<h3>';
        if (roleId != null && Number.parseInt(roleId) > 0) {
            // 更新操作
            title = '<h3>用户管理 - 更新用户<h3>';
            url += "?id=" + roleId;
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
     * 打开授权页面
     * @param data
     */
    function openGrantDialog(data) {
        // 判断是否选择了角色记录
        if (data.length < 1) {
            layer.msg("请选择要授权的角色!",{icon: 5});
            return false;
        }
        // 只支持单个角色授权
        if (data.length > 1) {
            layer.msg("暂不支持对角色进行批量授权!",{icon: 5});
            return false;
        }

        var url = ctx  + "/module/toGrantPage?roleId=" + data[0].id;
        var title = "<h3>角色管理 - 角色授权</h3>";

        layui.layer.open({
            title,
            content: url,
            type:2,
            area: ["600px", "600px"],
            maxmin: true
        });
    }

    /**
     * 监听layui数据表格行工具栏
     * 格式：
     * table.on('tool(原始表格容器元素的lay-filter属性值)',function(data){...});
     */
    table.on('tool(roles)',function (data) {

        switch (data.event) {
            case 'edit':
                // console.log(data.data.id);
                openAddOrUpdateRoleDialog(data.data.id);
                break;
            case 'del':
                // console.log('delete');
                deleteRole(data.data.id);
                break;
            default:
        }

    });

    function deleteRole(roleId) {
        // 询问用户是否确认删除
        layer.confirm("此操作不可逆，确定继续吗？",{icon:3, title: "角色管理"}, function (index) {
            // 点击确认
            // 关闭确认框
            layer.close(index);

            // 发送ajax请求，执行删除角色记录
            $.ajax({
                type: 'post',
                url: ctx + '/role/delete',
                data: {
                    id: roleId
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
        })
    }

    /**
     * 监听数据表格上方的搜索按钮事件
     */
    $('.search_btn').click(function () {

        // 获取搜索参数
        var roleName = $('[name=roleName]').val().trim(); // 角色名


        /**
         * 表格重载即多条件查询
         */
        tableIns.reload({
            // 设置需要给后端传递的参数
            where: { //设定异步数据接口的额外参数，任意设
                // 查询条件
                roleName,
            }
            ,page: {
                curr: 1 //重新从第 1 页开始
            }
        });

    });

});
