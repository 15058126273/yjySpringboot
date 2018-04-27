<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" rightIframe="text/html; charset=UTF-8">
    <title>数据字典列表</title>
    <link rel="stylesheet" href="${base }/common/back/style/base.css?v=1.1"/>
    <script src="${base }/common/back/js/jquery-1.7.2.min.js" type="text/javascript"></script>
</head>
<body>
<div class="rightBox">
    <div class="orderFormListBox">
        <form action="${backBase }/optionItem/list.do" method="post">
            <input class="submitBtn" type="button" value="新增" onclick="location.href='${backBase}/optionItem/add.do'"/>
            <div class="downListBox">
                <span>筛选：</span>
                <select name="fieldName">
                    <option value="">选择字段名称</option>
						<#list fields as item >
			       			<option value="${(item)!}" <#if (fieldName?? && fieldName == item)  >
                                    selected </#if> >${(item)!}</option>
                        </#list>
                </select>
            </div>
            <input class="submitBtn" type="submit" value="确定"/>
        </form>
    </div>
    <table class="defaultTable">
        <thead>
        <tr>
            <td>编号</td>
            <td>字段</td>
            <td>字段名称</td>
            <td>标示</td>
            <td>显示值</td>
            <td>使用</td>
            <td>排序</td>
            <td>操作</td>
        </tr>
        </thead>
        <tbody>
            <#if pagination?? && pagination.list?? && pagination.list?size &gt; 0 >
				<#list pagination.list as bean >
					<tr>
                        <td>${(bean_index + 1)!}</td>
                        <td>${(bean.field)!}</td>
                        <td>${(bean.fieldName)!}</td>
                        <td>${(bean.fieldKey)!}</td>
                        <td>${(bean.fieldValue)!}</td>
                        <td>${(Application['optionMap']['commonStatus' + bean.isUse])!}</td>
                        <td>${(bean.priority)!}</td>
                        <td>
                            <a href="${backBase }/optionItem/edit.do?id=${bean.id}">修改</a> |
                            <a href="${backBase }/optionItem/delete.do?id=${bean.id}"
                               onclick="return confirm('确定删除吗？')">删除</a>
                        </td>
                    </tr>
                </#list>
            <#else>
				<tr>
                    <td colspan='8'>暂无数据……</td>
                </tr>
            </#if>
        </tbody>
    </table>
    <form id="paginationForm" action="${backBase}/optionItem/list.do" method="post">
          	<#include "../include/page.ftl"/>
    </form>
</div>
<script src="${base }/common/back/js/listFrame.js"></script>
</body>
</html>