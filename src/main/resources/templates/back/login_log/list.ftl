<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" rightIframe="text/html; charset=UTF-8">
    <title>用户登录列表</title>
    <link rel="stylesheet" href="${base }/common/back/style/base.css?v=1.1"/>
    <script src="${base }/common/back/js/jquery-1.7.2.min.js" type="text/javascript"></script>
</head>
<body>
<div class="rightBox">
    <div class="orderFormListBox">
        <form action="${backBase }/loginLog/list.do" method="post">
        </form>
    </div>
    <table class="defaultTable">
        <thead>
        <tr>
            <td>序号</td>
            <td>用户</td>
            <td>手机型号</td>
            <td>操作系统</td>
            <td>登录时间</td>
        </tr>
        </thead>
        <tbody>
            <#if pagination?? && pagination.list?? && pagination.list?size &gt; 0 >
				<#list pagination.list as bean >
					<tr>
                        <td>${(bean_index + 1)!}</td>
                        <td>${(bean.nickName)!}</td>
                        <td>${(bean.customerModel)!}</td>
                        <td>${(bean.operatingSystem)!}</td>
                        <td>${(bean.addTime?string('yyyy-MM-dd HH:mm'))!}</td>
                    </tr>
                </#list>
            <#else>
				<tr>
                    <td colspan='9'>暂无数据……</td>
                </tr>
            </#if>
        </tbody>
    </table>
    <form id="paginationForm" action="${backBase}/loginLog/list.do" method="post">
          	<#include "../include/page.ftl"/>
    </form>
</div>
<script src="${base }/common/back/js/listFrame.js"></script>
</body>
</html>
