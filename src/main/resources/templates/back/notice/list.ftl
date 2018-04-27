<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" rightIframe="text/html; charset=UTF-8">
    <title>套餐列表</title>
    <link rel="stylesheet" href="${base }/common/back/style/base.css?v=1.1"/>
    <script src="${base }/common/back/js/jquery-1.7.2.min.js" type="text/javascript"></script>
</head>
<body>
<div class="rightBox">
    <div class="orderFormListBox">
        <form action="${backBase }/notice/list.do" method="post">
            <input class="submitBtn" type="button" value="新增" onclick="location.href='${backBase}/notice/add.do'"/>
            <div class="downListBox">
                <span>筛选：</span>
                <input type="text" value="${name! }" name="name" placeholder="请输入关键字"/>
            </div>
            <input class="submitBtn" type="submit" value="确定"/>
        </form>
    </div>
    <table class="defaultTable">
        <thead>
        <tr>
            <td>序号</td>
            <td>类型</td>
            <td>内容</td>
            <td>编辑时间</td>
            <td>操作</td>
        </tr>
        </thead>
        <tbody>
            <#if pagination?? && pagination.list?? && pagination.list?size &gt; 0 >
				<#list pagination.list as bean >
					<tr>
                        <td>${(bean_index + 1)!}</td>
                        <td>${(Application['optionMap']['broadcastType' + bean.type])!}</td>
                        <td><#if (bean.content)?length gt 30 >
                            ${(bean.content)?substring(0,30)}
                        <#else>
                            ${(bean.content)!}
                        </#if>
                        </td>
                        <td>${(bean.updateTime?string('yyyy-MM-dd HH:mm'))!}</td>
                        <td>
                            <a href="${backBase }/notice/detail.do?id=${bean.id}">详情</a> |
                            <a href="${backBase }/notice/edit.do?id=${bean.id}">编辑</a> |
                            <a href="${backBase }/notice/delete.do?id=${bean.id}"
                               onclick="return confirm('确定删除此系统公告吗？')">删除</a>
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
    <form id="paginationForm" action="${backBase}/notice/list.do" method="post">
          	<#include "../include/page.ftl"/>
    </form>
</div>
<script src="${base }/common/back/js/listFrame.js"></script>
</body>
</html>
