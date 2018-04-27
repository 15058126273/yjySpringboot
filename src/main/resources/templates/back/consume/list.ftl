<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" rightIframe="text/html; charset=UTF-8">
    <title>钻石消费列表</title>
    <link rel="stylesheet" href="${base }/common/back/style/base.css?v=1.1"/>
    <link rel="stylesheet" href="${base}/common/thirdparty/My97DatePicker/skin/WdatePicker.css"/>
    <script src="${base }/common/back/js/jquery-1.7.2.min.js" type="text/javascript"></script>
    <script src="${base}/common/thirdparty/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
</head>
<body>
<div class="rightBox">
    <div class="orderFormListBox">
        <form action="${backBase }/consume/list.do" method="post">
            <input type="hidden" name="userId" value="${userId! }"/>
           		<#if userId?? >
           			<input type="button" class="submitBtn" value="返回"
                           onclick="javascript:window.location.href='../user/list.do';"/>
                </#if>
            <div class="downListBox">
                <span>筛选：</span>
                <select name="type">
                    <option value="">选择消费类型</option>
						<#list Application['consumeType'] as item >
			       			<option value="${(item.fieldKey)!}" <#if (type?? && type?string == item.fieldKey)  >
                                    selected </#if> >${(item.fieldValue)!}</option>
                        </#list>
                </select>
                <input type="text" id="start" name="start" value="${(start?string('yyyy-MM-dd'))!}"
                       onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" placeholder="请输入开始时间"/>
                <input type="text" id="end" name="end" value="${(end?string('yyyy-MM-dd'))!}"
                       onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" placeholder="请输入结束时间"/>
            </div>
            <input class="submitBtn" type="submit" value="确定"/>
            <input type="button" class="submitBtn" value="统计" onclick="javascript:window.location.href='chart.do';"/>
        </form>
    </div>
    <table class="defaultTable">
        <thead>
        <tr>
            <td>序号</td>
            <td>钻石数</td>
            <td>玩家</td>
            <td>消费类型</td>
            <td>消费时间</td>
        </tr>
        </thead>
        <tbody>
            <#if pagination?? && pagination.list?? && pagination.list?size &gt; 0 >
				<#list pagination.list as bean >
					<tr>
                        <td>${(bean_index + 1)!}</td>
                        <td>${(bean.num)!}</td>
                        <td>${(bean.userId)!}</td>
                        <td>${(Application['optionMap']['consumeType' + bean.type])!}</td>
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
    <form id="paginationForm" action="${backBase}/consume/list.do" method="post">
        <input type="hidden" name="userId" value="${userId! }"/>
        <input type="hidden" name="type" value="${type!}"/>
        <input type="hidden" name="start" value="${(start?string('yyyy-MM-dd'))!}"/>
        <input type="hidden" name="end" value="${(end?string('yyyy-MM-dd'))!}"/>
          	<#include "../include/page.ftl" />
    </form>
</div>
<script src="${base }/common/back/js/listFrame.js"></script>
</body>
</html>
