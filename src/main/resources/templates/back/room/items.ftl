<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" rightIframe="text/html; charset=UTF-8">
    <title>游戏玩家列表</title>
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
            <td>玩家</td>
            <td>圈数</td>
            <td>钻石</td>
            <td>是否参加</td>
            <td>加入时间</td>
            <td>离开时间</td>
        </tr>
        </thead>
        <tbody>
        <#if pagination?? && pagination.list?? && pagination.list?size &gt; 0 >
        <#list pagination.list as bean >
        <tr>
            <td>${(bean_index + 1)!}</td>
            <td>${(bean.roomNo)!}</td>
            <td>${(bean.nickName)!}</td>
            <td>${(bean.playNum)!}</td>
            <td>${(bean.integral)!}</td>
            <td>${(Application['optionMap']['roomPlayer' + bean.isPlayer])!}</td>
            <td>${(bean.addTime?string('yyyy-MM-dd HH:mm'))!}</td>
            <td>${(bean.updateTime?string('yyyy-MM-dd HH:mm'))!}</td>
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
