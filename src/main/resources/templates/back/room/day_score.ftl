<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" rightIframe="text/html; charset=UTF-8">
    <title>统计玩家战绩</title>
    <link rel="stylesheet" href="${base }/common/back/style/base.css?v=1.1"/>
    <script src="${base }/common/back/js/jquery-1.7.2.min.js" type="text/javascript"></script>
</head>
<body>
<div class="rightBox">
    <div class="orderFormListBox">
        <input class="submitBtn" type="button" onclick="javascript:history.go(-1);" value="返回"/>
    </div>
    <table class="defaultTable">
        <thead>
        <tr>
            <td>序号</td>
            <td>编号</td>
            <td>昵称</td>
            <td>战绩</td>
            <td>参与时间</td>
        </tr>
        </thead>
        <tbody>
        <#if list?? && list?size &gt; 0 >
        <#list list as bean >
        <tr>
            <td>${(bean_index + 1)!}</td>
            <td>${(player.code)!}</td>
            <td>${(player.nickName)!}</td>
            <td>${(bean.integral)!}</td>
            <td>${(bean.comeTime?string('yyyy-MM-dd'))!}</td>
        </tr>
        </#list>
        <#else>
        <tr>
            <td colspan='9'>暂无数据……</td>
        </tr>
    </#if>
    </tbody>
    </table>
</div>
<script src="${base }/common/back/js/listFrame.js"></script>
</body>
</html>
