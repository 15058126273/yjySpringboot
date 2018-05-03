<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" rightIframe="text/html; charset=UTF-8">
    <title>玩家战绩列表</title>
    <link rel="stylesheet" href="${base }/common/back/style/base.css?v=1.1"/>
    <script src="${base }/common/back/js/jquery-1.7.2.min.js" type="text/javascript"></script>
    <script src="${base }/common/thirdparty/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
</head>
<body>
<div class="rightBox">
    <div class="orderFormListBox">
        <form action="${backBase }/room/scores.do" method="post">
            <#if userId?? >
            <input class="submitBtn" type="button" onclick="javascript:window.location.href='../user/list.do' ;"
                   value="返回"/>
            </#if>
            <div class="downListBox">
                <span>筛选：</span>
                <input type="text" value="${roomNo! }" name="roomNo" placeholder="请输入房间编号"/>
                <input type="text" value="${code! }" name="code" placeholder="请输入玩家编号"/>
                <input type="text" id="start" name="start" value="${(start?string('yyyy-MM-dd'))!}"
                       onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" placeholder="请输入游戏开始时间"/>
                <input type="text" id="end" name="end" value="${(end?string('yyyy-MM-dd'))!}"
                       onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" placeholder="请输入游戏结束时间"/>
            </div>
            <input class="submitBtn" type="submit" value="确定"/>
        </form>
    </div>
    <table class="defaultTable">
        <thead>
        <tr>
            <td>序号</td>
            <td>房间号</td>
            <td>编号</td>
            <td>昵称</td>
            <td>战绩</td>
            <td>参与时间</td>
            <td>操作</td>
        </tr>
        </thead>
        <tbody>
        <#if pagination?? && pagination.list?? && pagination.list?size &gt; 0 >
        <#list pagination.list as bean >
        <tr>
            <td>${(bean_index + 1)!}</td>
            <td>${(bean.roomNo)!}</td>
            <td>${(bean.code)!}</td>
            <td>${(bean.nickName)!}</td>
            <td>
                ${(bean.integral)!}
            </td>
            <td>${(bean.addTime?string('yyyy-MM-dd HH:mm'))!}</td>
            <td>
                <a href="${backBase }/room/dayScore.do?userId=${bean.userId}&start=${(bean.addTime?string('yyyy-MM-dd'))!}"
                   title="前面一周的数据">统计</a>
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
    <form id="paginationForm" action="${backBase}/room/scores.do" method="post">
        <input type="hidden" name="code" value="${code! }"/>
        <input type="hidden" name="roomNo" value="${roomNo! }"/>
        <input type="hidden" name="start" value="${(start?string('yyyy-MM-dd'))!}"/>
        <input type="hidden" name="end" value="${(end?string('yyyy-MM-dd'))!}"/>
        <#include "../include/page.ftl"/>
    </form>
</div>
<script src="${base }/common/back/js/listFrame.js"></script>
</body>
</html>
