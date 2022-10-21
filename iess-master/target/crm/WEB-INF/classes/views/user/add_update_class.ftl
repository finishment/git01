<!DOCTYPE html>
<html>
<head>
    <#include "../common.ftl">
</head>
<body class="childrenBody">
<form class="layui-form" style="width:80%;">

    <#-- 隐藏域，保存用户ID -->
    <input name="userId" type="hidden" value="${(student.userId)!}"/>
    <div class="layui-form-item layui-row layui-col-xs12">
        <label class="layui-form-label">班别</label>
        <div class="layui-input-block">
<#--            <input type="text" class="layui-input userName"
                   lay-verify="required" name="classId" id="userName"  value="${(student.classId)!}" placeholder="请输入用户班别">-->
            <select name="classId" id="trueName" >
                <option value="${(student.classId)!}" selected>大数据${(student.classId)!}期</option>
                <option value="22">大数据22期</option>
                <option value="23">大数据23期</option>
                <option value="24">大数据24期</option>
                <option value="25">大数据25期</option>
                <option value="26">大数据26期</option>
                <option value="27">大数据27期</option>
                <option value="28">大数据28期</option>
                <option value="29">大数据29期</option>
                <option value="30">大数据30期</option>
                <option value="31">大数据31期</option>
                <option value="32">大数据32期</option>
                <option value="33">大数据33期</option>
                <option value="34">大数据34期</option>
                <option value="35">大数据35期</option>
            </select>
        </div>
    </div>
    <#--<div class="layui-form-item layui-row layui-col-xs12">
        <label class="layui-form-label">组别</label>
        <div class="layui-input-block">
            <input type="text" class="layui-input userName"
                   lay-verify="required" name="groupId" id="trueName" value="${(student.groupId)!}" placeholder="请输入用户组别">
        </div>
    </div>-->
    <div class="layui-form-item layui-row layui-col-xs12">
        <label class="layui-form-label">组别</label>
        <div class="layui-input-block">
            <select name="groupId" id="trueName" >
                <option value="${(student.groupId)!}" selected>${(student.groupId)!}</option>
                <option value="1">1</option>
                <option value="2">2</option>
                <option value="3">3</option>
                <option value="4">4</option>
                <option value="5">5</option>
                <option value="6">6</option>
                <option value="7">7</option>
                <option value="8">8</option>
            </select>
        </div>
    </div>

    <div class="layui-form-item layui-row layui-col-xs12">
        <label class="layui-form-label">年薪(w)</label>
        <div class="layui-input-block">
            <input type="text" class="layui-input userName"
                   name="annualSal" id="trueName" value="${(student.annualSal)!}">
        </div>
    </div>

    <div class="layui-form-item layui-row layui-col-xs12">
        <label class="layui-form-label">职位</label>
        <div class="layui-input-block">
            <input type="text" class="layui-input userName"
                   name="job" id="trueName" value="${(student.job)!}">
        </div>
    </div>



    <br/>
    <#if permissions?seq_contains("6090")>
    <div class="layui-form-item layui-row layui-col-xs12">
        <div class="layui-input-block">
            <button class="layui-btn layui-btn-lg" lay-submit=""
                    lay-filter="addOrUpdateUser">确认
            </button>
            <button class="layui-btn layui-btn-lg layui-btn-normal" id="closeBtn">取消</button>
        </div>
    </div>
    </#if>
</form>

<script type="text/javascript" src="${ctx}/js/user/add.update.class.js"></script>
</body>
</html>