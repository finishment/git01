layui.use(['table','layer'],function(){
    var layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        table = layui.table;


    //面经数据列表展示
    var  tableIns = table.render({
        elem: '#viewChanceList',
        url : ctx+'/view_chance/list',
        cellMinWidth : 95,
        page : true,
        height : "full-125",
        limits : [10,15,20,25],
        limit : 10,
        toolbar: "#toolbarDemo",
        id : "viewChanceListTable",
        cols : [[
            {type: "checkbox", fixed:"center"},
            {field: "id", title:'编号',fixed:'left' ,sort: true},
            {field: 'interviewerName', title: '面试者姓名',align:'center'},
            {field: 'interviewer', title: '面试官',  align:'center'},
            {field: 'experience', title: '面试经验',  align:'center'},
            {field: 'createMan', title: '管理者', align:'center'},
            {field: 'createDate', title: '创建时间', align:'center'},
            {field: 'updateDate', title: '更新时间', align:'center'},
            {title: '操作', templet: '#viewChanceListBar',fixed: 'right',align: 'center', minWidth:150}
        ]]
    });

    /**
     * 多条件搜索,搜索按钮的点击绑定事件
     */
    $(".search_btn").on("click",function () {
        table.reload("viewChanceListTable",{
            page:{
                curr:1
            },
            where:{
                interviewerName:$("input[name='interviewerName']").val(),// 面试者姓名
                createMan:$("input[name='createMan']").val(),// 创建人
            }
        })
    });


    // 头工具栏事件
    table.on('toolbar(viewChances)',function (obj) {
        switch (obj.event) {
            case "add":
                openAddOrUpdateViewChanceDialog();
                break;
            case "del":
                delViewChance(table.checkStatus(obj.config.id).data);
                break;
        }
    });


    function delViewChance(datas){
        /**
         * 批量删除
         *   datas:选择的待删除记录数组
         */
        if(datas.length==0){
            layer.msg("请选择待删除记录!");
            return;
        }

        layer.confirm("确定删除选中的记录",{
            btn:['确定','取消']
        },function (index) {
            layer.close(index);
            var ids="ids=";
            for(var i=0;i<datas.length;i++){
                if(i<datas.length-1){
                    ids=ids+datas[i].id+"&ids=";
                }else{
                    ids=ids+datas[i].id;
                }
            }

            $.ajax({
                type:"post",
                url:ctx+"/view_chance/delete",
                data:ids,
                dataType:"json",
                success:function (data) {
                    if(data.code==200){
                        tableIns.reload();
                    }else{
                        layer.msg(data.msg);
                    }
                }
            })
        })
    }

    table.on('tool(viewChances)',function (obj) {
          var layEvent =obj.event;
          if(layEvent === "edit"){
              openAddOrUpdateViewChanceDialog(obj.data.id);
          }else if(layEvent === "del"){
              layer.confirm("确认删除当前记录?",{icon: 3, title: "面试信息管理"},function (index) {
                  $.post(ctx+"/view_chance/delete",{ids:obj.data.id},function (data) {
                      if(data.code==200){
                          layer.msg("删除成功");
                          tableIns.reload();
                      }else{
                          layer.msg(data.msg);
                      }
                  })
              })
          }
    });

    /**
     * 打开添加或更新对话框
     */
    function openAddOrUpdateViewChanceDialog(sid) {
        var title="面试信息管理-数据添加";
        var url=ctx+"/view_chance/addOrUpdateViewChancePage";
        if(sid){
            title="面试信息管理-数据更新";
            url=url+"?id="+sid;
        }
        layui.layer.open({
            title:title,
            type:2,
            area:["600px","500px"],
            maxmin:true,
            content:url
        })
    }

});
