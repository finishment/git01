layui.use(['table', 'treetable'], function () {
    var $ = layui.jquery;
    var table = layui.table;
    var treeTable = layui.treetable;

    // 渲染表格
    var tableIns = treeTable.render({
        treeColIndex: 1,
        treeSpid: -1,
        // 节点名（设置为Module对象中的id属性即可）
        treeIdName: 'id',
        // 父节点名（设置为Module对象中的parentId属性即可）
        treePidName: 'parentId',
        // 原始表格容器的id
        elem: '#menu-table',
        // 请求地址
        url: ctx+'/module/list',
        // 绑定数据表格头部工具栏，值为工具栏id
        toolbar: "#toolbarDemo",
        treeDefaultClose:true,
        page: true,
        cols: [[
            {type: 'numbers'},
            {field: 'moduleName', minWidth: 100, title: '菜单名称'},
            {field: 'optValue', title: '权限码'},
            {field: 'url', title: '菜单url'},
            {field: 'createDate', title: '创建时间'},
            {field: 'updateDate', title: '更新时间'},
            {
                field: 'grade', width: 80, align: 'center', templet: function (d) {
                    if (d.grade == 0) {
                        return '<span class="layui-badge class="layui-bg-orange"">目录</span>';
                    }
                    if(d.grade==1){
                        return '<span class="layui-badge-rim layui-bg-blue ">菜单</span>';
                    }
                    if (d.grade == 2) {
                        return '<span class="layui-badge  layui-bg-gray">按钮</span>';
                    }
                }, title: '类型'
            },
            {templet: '#auth-state', minWidth: 200, align: 'center', title: '操作', fixed: 'right'}
        ]],
        done: function () {
            layer.closeAll('loading');
        }
    });


    /**
     * 监听头部工具栏
     * 格式：
     *  table.on('toolbar(原始表格容器的lay-filter属性值)',function(data){...});
     */
    table.on('toolbar(menu-table)',function (data) {
        // 判断 lay-event 属性
        switch (data.event) {
            case 'expand': // 全部展开
                // expandAll('原始表格容器的id属性值')
                treeTable.expandAll('#menu-table');
                break;
            case 'fold': // 全部折叠
                // foldAll('原始表格容器的id属性值')
                treeTable.foldAll('#menu-table');
                break;
            case 'add': // 添加目录 层级0 父菜单parentId=-1

                openAddModuleDialog(0,-1);
                break;
            default:
        }
    });


    /**
     * 监听数据表格中的行工具栏
     * 格式:
     *  table.on('toolbar(原始表格容器的lay-filter属性值)',function(data){...});
     */
    table.on('tool(menu-table)', function (data) {
        // 判断 lay-event 属性
        switch (data.event) {
            case 'add': //添加子项
                // console.log(data.data); // 获取当前行的数据
                // 判断当前的层级，如果是三级菜单（grade=2）就不能添加
                if ( data.data.grade > 1) {
                    layer.msg("暂不支持添加四级菜单!",{icon: 5});
                    return false;
                }
                // 一级菜单|二级菜单 grade=当前层级+1,parentId=当前资源的id
                openAddModuleDialog(data.data.grade + 1, data.data.id);
                break;
            case 'edit': // 修改
                openUpdateModuleDialog(data.data.id); //参数为当前行资源的ID
                break;
            case 'del': // 删除
                openDeleteModule(data.data.id); //参数为当前行资源的ID
                break;
            default:
        }
    });

    /**
     * 打开添加资源的对话框
     * @param grade 层级
     * @param parentId 父菜单id
     */
    function openAddModuleDialog(grade, parentId) {
        var title = "<h3>资源管理 - 添加资源</h3>";
        var url = ctx + "/module/toAddModulePage?grade=" + grade + "&parentId=" + parentId;

        layui.layer.open({
            // 对话框类型 iframe
            type: 2,
            // 标题
            title,
            // 请求地址
            content: url,
            // 大小
            area: ['700px','450px'],
            // 最大/小化
            maxmin: true
        });
    }

    /**
     * 打开修改资源的对话框
     * @param id 主键ID
     */
    function openUpdateModuleDialog(id) {
        var title = "<h3>资源管理 - 修改资源</h3>";

        var url = ctx + "/module/toUpdateModulePage?id=" + id;

        layui.layer.open({
            // 对话框类型 iframe
            type: 2,
            // 标题
            title,
            // 请求地址
            content: url,
            // 大小
            area: ['700px','450px'],
            // 最大/小化
            maxmin: true
        });
    }

    /**
     * 删除资源
     * @param id
     */
    function openDeleteModule(id){
        // 弹出确认询问框询问用户是否删除
        layer.confirm("此操作不可逆，点击确定继续!", {icon: 3, title: "资源管理"},function (index) {
            // 关闭确认框
            layer.close(index);
            // 则发送ajax请求
            $.post(ctx + "/module/delete", {id:id}, function (result) {
                // 判断是否删除成功
                if (result.code == 200) {
                    layer.msg("操作成功!",{icon: 6});
                    // 刷新页面
                    parent.location.reload();
                } else {
                    // 删除失败
                    layer.msg(result.msg, {icon: 5});
                }
            })
        })
    }

    
});