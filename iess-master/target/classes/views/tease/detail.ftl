<!DOCTYPE html>
<head>
    <#include "../common.ftl">
</head>
<div class="col-md-9 layui-col-md-offset1" align="center">
    <div class="data_list">
        <div class="data_list_title">
            <span class="glyphicon glyphicon-eye-open"></span>&nbsp;内容浏览
        </div>
        <div>
            <div class="note_title"><h2>${note.title!""}</h2></div>
            <div class="note_info">
                发布时间：『${note.pubTime?string('yyyy-MM-dd')}』
<#--                &nbsp;&nbsp;讨论类别：${note.typeName!""}-->
            </div>
            <div class="note_content">
                <p>${note.content!""}</p>
            </div>
            <div >
                <#if collected??>
                    <button class="btn layui-btn-primary" id="collected" type="button" onclick="unCollectNote(${note.noteId!""})">已收藏</button>
                <#else>
                    <button class="btn layui-btn-warm" id="collect" type="button" onclick="collectNote(${note.noteId!""})">收藏文章</button>
                </#if>
                <button class="btn btn-primary" type="button" onclick="updateNote(${note.noteId!""})">修改</button>
                <button class="btn btn-danger" type="button" onclick="deleteNote(${note.noteId!""})">删除</button>
            </div>
        </div>
    </div>
</div>
<!--评论表单模态框-->
<div class="layui-row" id="combox">
    <div class="layui-col-md6 layui-col-md-offset3 com" >
        <h2>发表评论</h2>
        <form class="layui-form layui-form-pane" action="">
            <div class="layui-form-item">
                <div class="layui-form-item">
                    <div class="layui-input-inline">
                        <textarea id="content" name="content" placeholder="" class="layui-textarea" required
                                  lay-verify="required" maxlength="1000" style="resize:none;" cols="50"
                                  rows="5"></textarea>
                    </div>
                </div>
            </div>
            <input type="text" id="parentId" hidden="hidden" value="${note.userId!""}">
            <input type="text" id="noteId" hidden="hidden" value="${note.noteId!""}">
            <input type="text" id="userId" hidden="hidden" value="${loginUser.id!""}">
            <div class="layui-form-item">
                <button class="layui-btn" lay-submit lay-filter="comform">提交</button>
            </div>
        </form>
    </div>
</div>
<!--评论表单模态框-->
<div class="layui-row" id="combox2" style="display:none">
    <div class="layui-col-md6 layui-col-md-offset3 com" >
        <h2>回复评论</h2>

        <form class="layui-form layui-form-pane" action="">
            <div class="layui-form-item">
                <div class="layui-form-item">
                    <div class="layui-input-inline">
                        <textarea id="content1" name="content1" placeholder="" class="layui-textarea" required
                                  lay-verify="required" maxlength="1000" style="resize:none;" cols="50"
                                  rows="5"></textarea>
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>
<!--评论展示区-->
<div class="layui-row">
    <div class="layui-col-md6 layui-col-md-offset3 com-box">
        <div class="comment-list-box">
            <ul class="comment-list">
                <#list clist as comment>
                    &nbsp;
                <li class="comment-line-box d-flex" data-commentid="15937123" data-username="weixin_46274168">
                    <a href="#">
                        <span class="nickname">${comment.userName}</span>
                    </a>
                    <span class="colon">:</span>
                    <span class="comment">${comment.content}</span>
                    <span class="opt">
                        &nbsp;&nbsp;
                        <a onclick=reply("${comment.id}","${comment.id}")>回复</a>
                        <a onclick=remove("${comment.id}")>删除</a>
                    </span>
                </li>
                    <#if comment.commentList??>
                        <#list comment.getCommentList() as subComment>
                        <li class="replay-box" style="display:block" >
                            <ul class="comment-list">
                                <li class="comment-line-box" data-commentid="15938081" data-replyname="IndexMan">
                                    <a href="#">
                                        <span>&nbsp;&nbsp;&nbsp;</span>
                                        <span class="nickname">${subComment.userName}</span>
                                    </a>
                                    <span class="text">回复</span>
                                    <a href="#">
                                        <span class="nickname">${(subComment.parentName)}</span>
                                    </a>
                                    <span class="colon">:</span>
                                    <span class="comment">${subComment.content}</span>
                                    <span class="opt">
                                        &nbsp;&nbsp;
                                        <a onclick=reply("${subComment.commId}","${subComment.id}")>回复</a>
                                        <a onclick=remove("${subComment.id}")>删除</a>
                                    </span>
                                </li>
                            </ul>
                        </li>
                        </#list>
                    </#if>
                </#list>
            </ul>
        </div>
    </div>
</div>

<script src="https://cdn.bootcdn.net/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script>
    layui.use(['layer', 'form'], function () {
        var form = layui.form;
        var layer = layui.layer;
        $ = layui.jquery;


        // 加载评论列表
        // 提交评论
        form.on('submit(comform)', function (data) {
            var data={
                "userId":$("#userId").val(),
                "content": $('#content').val(),
                "noteId": $('#noteId').val(),
                "parentId": $('#parentId').val()
            };

            //layer.alert(JSON.stringify(data));
            // 添加评论
            saveComment(data);

            return false;
        });


    });

    function remove(id) {
        layer.confirm('确定要删除吗？', function (index) {

            //layer.alert(JSON.stringify(ids));
            $.ajax({
                url: ctx+'/comment/remove/'+id,
                type: 'POST',
                success: function (result) {
                    if (result.code == 200) {
                        // 刷新页面
                        location.reload();
                    }else {
                        layer.msg("删除失败！", {icon: 5});
                    }
                }
            });
        });
    }

    function reply(commId,parentId) {
        $("#combox2").show();
        layer.open({
            type: 1 ,
            title: '回复评论',   //标题
            skin: "myclass",
            area: ["700px", "400px"],   //宽高
            shade: 0.4,   //遮罩透明度
            // btn: ['确定', '取消'], //按钮组
            btn: ['提交'],
            scrollbar: false ,//屏蔽浏览器滚动条
            content: $("#combox2"),
            commId:commId,
            parentId:parentId,
            cancel : function() {
                setTimeout('window.location.reload()', 0);

                // $("#combox2").css("display","none");
            },
            yes: function(){//layer.msg('yes');    //点击确定回调
                var data={
                    "userId":$("#userId").val(),
                    "content": $('#content1').val(),
                    "noteId": $('#noteId').val(),
                    "parentId": this.parentId,
                    "commId":this.commId,
                };
                // console.log(data)
                saveComment(data)
            },

        });
    }

    function saveComment(data) {
        $.ajax({
            url: ctx+'/comment/add',
            type: 'POST',
            contentType: "application/json",
            dataType: "json",
            data: JSON.stringify(data),
            // data:data.field,
            success: function (result) {
                if (result.code == 200) {
                    // 刷新页面
                    location.reload();
                } else {
                    layer.alert("操作失败");
                }
            }
        });
    }

    function collectNote(noteId) {
        var data={
            "user_id":$("#userId").val(),
            "note_id": $('#noteId').val(),
        };
        $.ajax({
            type:"post",
            url:"/crm/collection/add",
            data:JSON.stringify(data),
            contentType: "application/json",
            dataType: "json",
            success:function (result) {
                // 判断是否收藏成功
                if (result.code == 200) {
                    // 跳转到首页
                    swal("","<h3>收藏成功！</h3>", "success");
                    setTimeout('window.location.reload()', 1000);
                } else {
                    // 提示失败
                    swal("","<h3>收藏失败！</h3>", "error");
                }
            }
        });
    }
    function unCollectNote(noteId) {
        $.ajax({
            type:"post",
            url:"/crm/collection/delete/"+noteId,
            // data:{
            //     noteId:noteId
            // },
            success:function (result) {
                // 判断是否取消收藏成功
                if (result.code == 200) {
                    // 跳转到首页
                    swal("","<h3>取消收藏成功！</h3>", "success");
                    setTimeout('window.location.reload()', 1000);
                } else {
                    // 提示失败
                    swal("","<h3>取消收藏失败！</h3>", "error");
                }
            }
        });
    }

    function deleteNote(noteId) {
        // 弹出提示框询问用户是否确认删除
        swal({
            title: "",  // 标题
            text: "<h3>您确认要删除该记录吗？</h3>", // 内容
            type: "warning", // 图标  error	 success	info  warning
            showCancelButton: true,  // 是否显示取消按钮
            confirmButtonColor: "orange", // 确认按钮的颜色
            confirmButtonText: "确定", // 确认按钮的文本
            cancelButtonText: "取消" // 取消按钮的文本
        }).then(function(){
            // 如果用户确认删除，则发送ajax请求
            $.ajax({
                type:"post",
                url:"/crm/note/delete",
                data:{
                    actionName:"delete",
                    noteId:noteId
                },
                success:function (code) {
                    // 判断是否删除成功
                    if (code == 1) {
                        // 跳转到首页
                        // swal("","<h3>删除成功！</h3>", "success");
                        window.location.href = ctx+"/discuss/index";
                    } else {
                        // 提示失败
                        swal("","<h3>删除失败！</h3>", "error");
                    }
                }
            });

        });
    }

    /**
     * 进入发布云记的页面
     * @param noteId
     */
    function updateNote(noteId) {
        window.location.href = ctx+"/note/view?noteId="+noteId;
    }

</script>
