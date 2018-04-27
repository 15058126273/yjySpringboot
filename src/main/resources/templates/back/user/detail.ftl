<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>查看用户信息</title>
    <link rel="stylesheet" type="text/css" href="${base}/common/back/style/detail.css?v=1.0">
    <script src="${base}/common/back/js/jquery-1.7.2.min.js"></script>
</head>
<body>
<div class="rightBox">
    <div>
        <ul>
            <li><span>*</span>用户昵称：</li>
            <li>${(userDetail.nickName)!}</li>
        </ul>
    </div>
    <div>
        <ul>
            <li><span>*</span>钻石数量：</li>
            <li>${(userDetail.money)!}</li>
        </ul>
    </div>
    <div class="imgBox">
        <ul>
            <li><span>*</span>头像：</li>
            <li>
                <#if userDetail?? && userDetail.headImg?? >
                <img alt="头像" src="${base}${(userDetail.headImg)!}" data-action="zoom">
            </#if>
            </li>
        </ul>
    </div>
    <div>
        <ul>
            <li><span>*</span>省份：</li>
            <li>${(userDetail.province)!}</li>
        </ul>
    </div>
    <div>
        <ul>
            <li><span>*</span>城市：</li>
            <li>${(userDetail.city)!}</li>
        </ul>
    </div>
    <div>
        <ul>
            <li><span>*</span>注册时间：</li>
            <li>${(bean.addTime?string('yyyy-MM-dd HH:mm'))!}</li>
        </ul>
    </div>
    <div>
        <ul>
            <li><span>*</span>最后更新：</li>
            <li>${(bean.updateTime?string('yyyy-MM-dd HH:mm'))!}</li>
        </ul>
    </div>
    <div class="detailBack">
        <a href="#" onclick="javascript:history.back(-1);"> 返回</a>
    </div>
</div>
<script src="${base}/common/back/js/detailFrame.js?v=0.01"></script>
</body>
</html>