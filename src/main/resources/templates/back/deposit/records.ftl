<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" rightIframe="text/html; charset=UTF-8">
    <title>购买钻石列表</title>
    <link rel="stylesheet" href="${base }/common/back/style/base.css?v=1.1"/>
    <script src="${base }/common/back/js/jquery-1.7.2.min.js" type="text/javascript"></script>
</head>
<body>
<div class="rightBox">
    <div class="orderFormListBox">
        <form action="${backBase }/deposit/records.do" method="post">
            <div class="downListBox">
                <span>筛选：</span>
                <select name="status">
                    <option value="">选择充值类型</option>
						<#list Application['depositType'] as item >
			       			<option value="${(item.fieldKey)!}" <#if (type?? && type?string == item.fieldKey)  >
                                    selected </#if> >${(item.fieldValue)!}</option>
                        </#list>
                </select>
            </div>
            <input type="hidden" name="id" value="${id!}"/>
            <input class="submitBtn" type="submit" value="确定"/>
        </form>
    </div>
    <table class="defaultTable">
        <thead>
        <tr>
            <td>序号</td>
            <td>订单号</td>
            <td>钻石数</td>
            <td>充值方式</td>
            <td>充值时间</td>
            <td>操作</td>
        </tr>
        </thead>
        <tbody>
            <#if pagination?? && pagination.list?? && pagination.list?size &gt; 0 >
				<#list pagination.list as bean >
					<tr>
                        <td>${(bean_index + 1)!}</td>
                        <td>${(bean.orderNo)!}</td>
                        <td>${(bean.num)!}</td>
                        <td>${(Application['optionMap']['depositType' + bean.type])!}</td>
                        <td>${(bean.updateTime?string('yyyy-MM-dd HH:mm'))!}</td>
                        <td>
                            <a href="${backBase }/deposit/detail.do?id=${bean.id}">详情</a>
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
    <form id="paginationForm" action="${backBase}/deposit/records.do" method="post">
          	<#include "../include/page.ftl"/>
    </form>
</div>
<script src="${base }/common/back/js/listFrame.js"></script>
</body>
</html>
