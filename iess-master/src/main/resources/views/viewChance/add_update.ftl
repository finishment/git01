<!DOCTYPE html>
<html>
<head>
    <#include "../common.ftl">
</head>
<body class="childrenBody">
<form class="layui-form" style="width:80%;">
    <#--设置面经数据的id隐藏域-->
    <input type="hidden" name="id"  value="${(viewChance.id)!}">
    <#--设置面经人的id-->
    <input type="hidden" name="assignId"  value="${(viewChance.assignId)!}">
    <div class="layui-form-item layui-row layui-col-xs12">
        <label class="layui-form-label">面试者姓名</label>
        <div class="layui-input-block">
            <input type="text" class="layui-input"
                   lay-verify="required" name="interviewerName" id="interviewerName"  value="${(viewChance.interviewerName)!}"  placeholder="请输入面试者姓名">
        </div>
    </div>
    <div class="layui-form-item layui-row layui-col-xs12">
        <label class="layui-form-label">面试官</label>
        <div class="layui-input-block">
            <input type="text" class="layui-input"
                   name="interviewer" id="interviewer" placeholder="请输入面试官" value="${(viewChance.interviewer)!}">
        </div>
    </div>
    <div class="layui-form-item layui-row layui-col-xs12">
        <label class="layui-form-label">创建人</label>
        <div class="layui-input-block">
            <input type="text" class="layui-input"
                   name="createMan"  lay-verify="required"
                   placeholder="请输入面经创建人" value="${(viewChance.createMan)!}">
        </div>
    </div>
    <div class="layui-form-item layui-row layui-col-xs12">
        <label class="layui-form-label">面试经验</label>
        <div class="layui-input-block">
            <textarea placeholder="请输入面试经验" name="experience" class="layui-textarea">${(viewChance.experience)!}</textarea>
        </div>
    </div>
    <#--<div class="layui-form-item layui-row layui-col-xs12">
        <label class="layui-form-label">指派给</label>
        <div class="layui-input-block">
            <select name="assignId" id="assignId">
                <option value="" >请选择</option>
            </select>
        </div>
    </div>-->
    <br/>
    <div class="layui-form-item layui-row layui-col-xs12">
        <div class="layui-input-block">
            <button class="layui-btn layui-btn-lg" lay-submit=""
                    lay-filter="addOrUpdateViewChance">确认
            </button>
            <button class="layui-btn layui-btn-lg layui-btn-normal" id="closeBtn">取消</button>
        </div>
    </div>
</form>
<script type="text/javascript" src="${ctx}/js/viewChance/add.update.js"></script>
</body>
</html>