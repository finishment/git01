<!DOCTYPE html>
<html>
<head>
	<title>面试信息管理</title>
	<#include "../common.ftl">
</head>
<body class="childrenBody">

<form class="layui-form" >
	<blockquote class="layui-elem-quote quoteBox">
		<form class="layui-form">
			<div class="layui-inline">
				<div class="layui-input-inline">
					<input type="text" name="interviewerName"
						   class="layui-input
					searchVal" placeholder="面试者姓名" />
				</div>
				<div class="layui-input-inline">
					<input type="text" name="createMan" class="layui-input
					searchVal" placeholder="管理者" />
				</div>
				<a class="layui-btn search_btn" data-type="reload"><i
							class="layui-icon">&#xe615;</i> 搜索</a>
			</div>
		</form>
	</blockquote>
	<table id="viewChanceList" class="layui-table"  lay-filter="viewChances"></table>

	<script type="text/html" id="toolbarDemo">
		<#--             TODO 待完善 相关用户才能使用-->
		<#if permissions?seq_contains("10")>
		<div class="layui-btn-container">
			<a class="layui-btn layui-btn-normal addNews_btn" lay-event="add">
				<i class="layui-icon">&#xe608;</i>
				添加
			</a>
			<a class="layui-btn layui-btn-normal delNews_btn" lay-event="del">
				<i class="layui-icon">&#xe608;</i>
				删除
			</a>
		</div>
		</#if>
	</script>

	<!--操作-->
	<script id="viewChanceListBar" type="text/html">
		<#--             TODO 待完善 相关用户才能使用-->
		<#if permissions?seq_contains("10")>
		<a class="layui-btn layui-btn-xs" id="edit" lay-event="edit">编辑</a>
		<a class="layui-btn layui-btn-xs layui-btn-danger" lay-event="del">删除</a>
		</#if>
	</script>

</form>
<script type="text/javascript" src="${ctx}/js/viewChance/view.chance.js"></script>

</body>
</html>
