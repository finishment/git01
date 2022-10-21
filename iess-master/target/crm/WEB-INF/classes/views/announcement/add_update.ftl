<!DOCTYPE html>
<html>
    <head>
        <#include "../common.ftl">
    </head>
    <body class="childrenBody">
        <form class="layui-form" style="width:80%;">

            <#-- 隐藏域，保存用户ID -->
            <input name="id" type="hidden" value="${(announcementInfo.id)!}"/>
            <div class="layui-form-item layui-row layui-col-xs12">
                <label class="layui-form-label" style="width: 100px;float: none">通知内容</label>
                <div class="layui-input-block">
                    <input type="text" class="layui-textarea"
                           lay-verify="required" name="announcement" id="announcement" value="${(announcementInfo.announcement)!}" placeholder="请输入通知内容">
<#--                        <textarea name="announcement" id="announcement" lay-verify="required" value="${(announcementInfo.announcement)!}" placeholder="请输入通知内容" class="layui-textarea"></textarea>-->
                </div>


            <br/>
            <div class="layui-form-item layui-row layui-col-xs12">
                <div class="layui-input-block">
                    <button class="layui-btn layui-btn-lg" lay-submit=""
                            lay-filter="addOrUpdateAnnouncement">确认
                    </button>
                    <button class="layui-btn layui-btn-lg layui-btn-normal" id="closeBtn">取消</button>
                </div>
            </div>
        </form>

    <script type="text/javascript" src="${ctx}/js/announcement/add.update.js"></script>
    </body>
</html>