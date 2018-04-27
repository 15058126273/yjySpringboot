<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" rightIframe="text/html; charset=UTF-8">
    <title>管理员列表</title>
    <link rel="stylesheet" href="${base }/common/back/style/base.css?v=1.1"/>
    <script src="${base }/common/back/js/jquery-1.7.2.min.js" type="text/javascript"></script>
</head>
<body>
<div class="rightBox">
    <div class="orderFormListBox">
        <form action="${backBase }/admin/list.do" method="post">
            <input class="submitBtn" type="button" value="新增" onclick="location.href='${backBase}/admin/add.do'"/>
        </form>
    </div>
    <table class="defaultTable">
        <thead>
        <tr>
            <td>序号</td>
            <td>用户名</td>
            <td>真实姓名</td>
            <td>等级</td>
            <td>添加时间</td>
            <td>操作</td>
        </tr>
        </thead>
        <tbody>
        <#if pagination?? && pagination.list?? && pagination.list?size &gt; 0 >
        <#list pagination.list as bean >
        <tr>
            <td>${(bean_index + 1)!}</td>
            <td>${(bean.userName)!}</td>
            <td>${(bean.realName)!}</td>
            <td>
                <#if bean.level??>
                <#if bean.level==0>超级管理员
                </#if>
            <#if bean.level==1>普通管理员
            </#if>
                </#if>
            </td>
            <td>${(bean.updateTime?string('yyyy-MM-dd HH:mm'))!}</td>
            <td>
                <a href="${backBase }/admin/edit.do?id=${bean.id}">修改</a> |
                <a href="${backBase }/admin/delete.do?id=${bean.id}" onclick="return confirm('确定删除吗？')">删除</a>
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
    <form id="paginationForm" action="${backBase}/admin/list.do" method="post">
    <#include "../include/page.ftl"/>
    </form>
</div>
<script src="${base }/common/back/js/listFrame.js"></script>
</body>
</html>
