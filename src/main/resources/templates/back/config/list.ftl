<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" rightIframe="text/html; charset=UTF-8">
    <title>系统配置参数列表</title>
    <link rel="stylesheet" href="${base }/common/back/style/base.css?v=1.1"/>
    <script src="${base }/common/back/js/jquery-1.7.2.min.js" type="text/javascript"></script>
</head>
<body>
<div class="rightBox">
    <div class="orderFormListBox">
        <form action="${backBase }/config/list.do" method="post">
            <input class="submitBtn" type="button" value="新增" onclick="location.href='${backBase}/config/add.do'"/>
        </form>
    </div>
    <table class="defaultTable">
        <thead>
        <tr>
            <td>序号</td>
            <td>参数名称</td>
            <td>参数值</td>
            <td>状态</td>
            <td>编辑时间</td>
            <td>操作</td>
        </tr>
        </thead>
        <tbody>
            <#if pagination?? && pagination.list?? && pagination.list?size &gt; 0 >
				<#list pagination.list as bean >
					<tr>
                        <td>${(bean_index + 1)!}</td>
                        <td>${(bean.fieldName)!}</td>
                        <td>
							<#if bean.fieldValue?length gt 45>
                                ${(bean.fieldValue?substring(0,45) )!}
                            <#else>
                                ${(bean.fieldValue)!}
                            </#if>
                        </td>
                        <td>${(Application['optionMap']['commonStatus' + bean.status])!}</td>
                        <td>${(bean.updateTime?string('yyyy-MM-dd HH:mm'))!}</td>
                        <td>
                            <a href="${backBase }/config/edit.do?id=${bean.id}">修改</a>
                        </td>
                    </tr>
                </#list>
            <#else>
				<tr>
                    <td colspan='9'>暂无数据……</td>
                </tr>
            </#if>
        </tbody>
    </table>
    <form id="paginationForm" action="${backBase}/config/list.do" method="post">
          	<#include "../include/page.ftl"/>
    </form>
</div>
<script src="${base }/common/back/js/listFrame.js"></script>
</body>
</html>