$(function () {
    // 加载树形结构
    loadModuleData();

});

// 定义树形结构对象
var zTreeObj;

/**
 * 加载资源树形列表
 */
function loadModuleData() {
    // 树形结构的配置信息 zTree js插件的参数配置
    var setting = {
        // 使用复选框
        check: {
            enable: true
        },
        // 使用简单的JSON数据
        data: {
            simpleData: {
                enable: true
            }
        },
        // 绑定回调行数
        callback: {
            // 当zTree树中的checkbox/radio被选中或取消选中时触发的函数
            onCheck: zTreeOnCheck
        }
    };

    // 数据 通过ajax查询资源列表
    $.ajax({
        type: "get",
        url: ctx + "/module/queryAllModules",
        dataType: "json",
        data:{
            // 查询所有的资源列表时，需要传递角色ID，用于查询当前角色已经具有的资源
            roleId:$("[name=roleId]").val()
        },
        success: function (result) {
            // result：ajax请求查询到的资源列表
            // 加载zTree数数据  $("#test1"): test1为zTree的容器，且是ul元素
            zTreeObj = $.fn.zTree.init($("#test1"),setting,result);
        }
    });
}

/**
 * 当zTree树中的checkbox/radio被选中或取消选中时触发的函数
 */
function zTreeOnCheck(event,treeId,treeNode) {
    // console.log(treeNode.tId + ", " + treeNode.name + ", " + treeNode.checked);
    /**
     * getCheckedNodes(checked):获取所有被勾选的节点集合
     * 如果checked为true，表示获取所有被勾选的节点
     * 如果checked为false，表示获取未所有被勾选的节点
     */
    var nodes = zTreeObj.getCheckedNodes(true);
    console.log(nodes);

    // 获取所有被选中资源的id值 mIds=1&mIds=2&mIds=3...
    // 判断并遍历选中的节点集合
    if (nodes.length > 0) {
        // 定义资源ID
        var mIds = "mIds=";
        // 遍历节点集合，获取被选中的资源的id
        for (var i = 0; i < nodes.length; i++) {
            mIds += nodes[i].id + "&mIds=";
        }
        mIds = mIds.substr(0, mIds.lastIndexOf("&"));
        console.log(mIds);
    }

    // 获取需要授权的角色的ID（从隐藏域中获取）
    var roleId = $("[name=roleId]").val();
    // 发送ajax请求，执行角色的授权操作
    $.ajax({
        type: 'post',
        url: ctx + '/role/addGrant',
        data: mIds + '&roleId='+roleId,
        dataType: 'json',
        success: function (result) {
            console.log(result);
        }
    })

}