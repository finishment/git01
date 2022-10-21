<!DOCTYPE html>
<head>
    <#include "../common.ftl">
    <link href="https://unpkg.com/@wangeditor/editor@latest/dist/css/style.css" rel="stylesheet">
    <style>
        #editor—wrapper {
            border: 1px solid #ccc;
            z-index: 100; /* 按需定义 */
        }
        #toolbar-container { border-bottom: 1px solid #ccc; }
        #editor-container { height: 500px; }
    </style>
</head>
<div class="col-md-9">
    <div class="data_list">
        <div class="data_list_title">
            <span class="glyphicon glyphicon-cloud-upload"></span>&nbsp;
            <#if !noteInfo??>
                发布帖子
            </#if>
            <#if noteInfo??>
                修改帖子
            </#if>

        </div>
        <div class="container-fluid">
            <div class="container-fluid">
                <div class="row" style="padding-top: 20px;">
                    <div class="col-md-12">
<#--                        <%-- 判断类型列表是否为空，如果为空，提示用户先添加类型 --%>-->
<#--                        <#if !typeList??>-->
<#--                            <h2>暂未查询到云记类型！</h2>-->
<#--                            <h4><a href="type/addOrUpdate?actionName=list">添加类型</a> </h4>-->
<#--                        </#if>-->
                            <form class="form-horizontal" method="post" action="${ctx}/note/addOrUpdate">
<#--                                <%-- 设置隐藏域：用来存放用户行为actionName --%>-->
                                <input type="hidden" name="actionName" value="addOrUpdate">
<#--                                <%-- 设置隐藏域：用来存放noteId --%>-->
                                <#if noteInfo??>
                                <input type="hidden" name="noteId" value="${noteInfo.noteId!}">
                                </#if>
<#--                                <%-- 设置隐藏域：用来存放用户发布云记时所在地区的经纬度 --%>-->
<#--                                <%-- 经度 --%>-->
                                <input type="hidden" name="lon" id="lon">
<#--                                <%-- 纬度 --%>-->
                                <input type="hidden" name="lat" id="lat">
                                <input type="hidden" name="content" id="content">

                                <div class="form-group">
                                    <label for="title" class="col-sm-2 control-label">标题:</label>
                                    <div class="col-sm-8">
                                        <#if noteInfo??>
                                                <input class="form-control" name="title" id="title" placeholder="请输入标题" value="${noteInfo.title}">
                                            <#else>
                                                <input class="form-control" name="title" id="title" placeholder="请输入标题" value="">
                                        </#if>

                                    </div>
                                </div>


                                <div class="form-group">
                                    <label for="title" class="col-sm-2 control-label">内容:</label>
                                    <div class="col-sm-8">
                                        <div id="editor—wrapper">
                                            <div id="toolbar-container"><!-- 工具栏 --></div>

                                            <div id="editor-container"><!-- 编辑器 --></div>
                                        </div>

                                    </div>
                                </div>

                                <div class="form-group">
                                    <div class="col-sm-offset-4 col-sm-4">
                                        <input type="submit" class="btn btn-primary" onclick="return checkForm()"  value="保存">
                                        <#if resultInfo??>
                                        &nbsp;<span id="msg" style="font-size: 12px;color: red">${resultInfo.msg!""}</span>
                                        </#if>
                                    </div>
                                </div>
                            </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

    <script type="text/javascript">
        function isEmpty(str) {
            if (str == null || str.trim() == "") {
                return true;
            }
            return false;
        }
        function checkForm() {
            /*  1. 获取表单元素的值 */
            // 获取下拉框选中的选项  .val()
            var typeId = $("#typeId").val();
            // 获取文本框的值       .val()
            var title = $("#title").val();
            //  获取富文本编辑器的内容 ue.getContent()
            var content = editor.getText();

            // /* 2. 参数的非空判断 */
            // if (isEmpty(typeId)) {
            //     $("#msg").html("请选择云记类型！");
            //     return false;
            // }
            if (isEmpty(title)) {
                swal("","<h3>标题不能为空！</h3>", "error");
                return false;
            }
            if (isEmpty(content)) {
                swal("","<h3>内容不能为空！</h3>", "error");
                return false;
            }
            return true;
        }

    </script>

<#--    <%-- 引用百度地图API文件，需要申请百度地图对应ak密钥--%>-->
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=yrxymYTyuefnxNtXbZcMU8phABXtu6TG"></script>
    <script type="text/javascript">
        /* 百度地图获取当前地址位置的经纬度 */
        var geolocation = new BMap.Geolocation();
        geolocation.getCurrentPosition(function (r){
            // 判断是否获取到
            if (this.getStatus() == BMAP_STATUS_SUCCESS) {
                console.log("您的位置：" + r.point.lng + "，" + r.point.lat);
                // 将获取到的坐标设置给隐藏域
                $("#lon").val(r.point.lng);
                $("#lat").val(r.point.lat);
            } else {
                console.log("failed:" + thi.getStatus());
            }
        });
    </script>
<script src="https://unpkg.com/@wangeditor/editor@latest/dist/index.js"></script>
<script>
    const { createEditor, createToolbar } = window.wangEditor
    const editorConfig = {
        MENU_CONF: {},
        placeholder: 'Type here...',
        onChange(editor) {
            const html = editor.getHtml()
            $("#content").val(html);
            // console.log('editor content', html)
            // 也可以同步到 <textarea>
        }
    }

    editorConfig.MENU_CONF['uploadImage'] = {
        // 其他配置...

        // 小于该值就插入 base64 格式（而不上传），默认为 0
        base64LimitSize: 10 * 1024*1024 // 10Mb
    }
    const editor = createEditor({
        selector: '#editor-container',
        <#if noteInfo??>
        html:'${noteInfo.content!""}',
        </#if>
        config: editorConfig,
        mode: 'default', // or 'simple'
    })

    const toolbarConfig = {}

    const toolbar = createToolbar({
        editor,
        selector: '#toolbar-container',
        config: toolbarConfig,
        mode: 'default', // or 'simple'
    })
</script>
