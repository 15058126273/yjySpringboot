<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" rightIframe="text/html; charset=UTF-8">
    <title>玩家用户列表</title>
    <link rel="stylesheet" href="${base }/common/back/style/base.css?v=1.1"/>
    <script src="${base }/common/back/js/jquery-1.7.2.min.js" type="text/javascript"></script>
</head>
<body>
<div class="rightBox">
    <div class="orderFormListBox">
        <form action="${backBase }/user/list.do" method="post">
            <div class="downListBox">
                <span>筛选：</span>
                <input type="text" value="${name! }" name="name" placeholder="请输入用户昵称"/>
            </div>
            <input class="submitBtn" type="submit" value="确定"/>
        </form>
    </div>
    <table class="defaultTable">
        <thead>
        <tr>
            <td>序号</td>
            <td>代码</td>
            <td>昵称</td>
            <td>头像</td>
            <td>操作</td>
        </tr>
        </thead>
        <tbody>
        <#if pagination??&& pagination.list??&& pagination.list?size &gt; 0 >
        <#list pagination.list as bean >
        <tr>
            <td>${(bean_index + 1)!}</td>
            <td>${(bean.code)!}</td>
            <td>${(bean.nickName)!}</td>
            <td><img src="${(bean.headImg)!}" style="width:45px;"/></td>
            <td>
                <a href="${backBase }/room/list.do?userId=${bean.id}">房间</a> |
                <a href="${backBase }/consume/list.do?userId=${bean.id}">钻石消耗</a>
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
    <form id="paginationForm" action="${backBase}/user/list.do" method="post">
        <input type="hidden" name="name" value="${name! }"/>
        <#include "../include/page.ftl"/>
    </form>
</div>
<script src="${base }/common/back/js/listFrame.js"></script>
</body>
</html>
