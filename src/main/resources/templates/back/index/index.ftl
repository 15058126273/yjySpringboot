<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" rightIframe="text/html; charset=UTF-8">
    <title>花牌游戏管理系统</title>
    <link rel="stylesheet" href="${base }/common/back/style/base.css?v=1.1"/>
    <link rel="stylesheet" href="${base }/common/back/style/style.css?v=1.1"/>
    <script src="${base }/common/back/js/jquery-1.7.2.min.js" type="text/javascript"></script>
    <script src="${base }/common/back/js/alert.js?v=0.05"></script>
</head>
<body>
<div id="container">
    <div class="header">
        <a href="${backBase }/welcome.do" target="rightIframe">
            <div class="logo">
                <h1>
                    花牌游戏管理系统
                </h1>
                <h3>
                    FlowerGame Team Game System
                </h3>
            </div>
        </a>
        <div class="state">
            <ul>
                <li>
                    <a href="#">
                        <img src="${base }/common/back/img/header.png" class="headerImg"/>
                    </a>
                </li>
                <li> 您好，<#if user.realName?? > ${(user.realName)! } <#else> ${(user.userName)! } </#if>欢迎您登陆</li>
                <li>
                    <a href="${backBase}/admin/changePassword.do" title="修改密码" target="rightIframe">
                        <img src="${base }/common/back/img/password.png" class="items"/>
                    </a>
                </li>
                <li>
                    <a href="${backBase}/loginOut.do" title="退出" target="rightIframe">
                        <img src="${base }/common/back/img/exit.png" class="items"/>
                    </a>
                </li>
            </ul>
        </div>
    </div>
    <div class="contentBox">
        <div class="borderBoxRight"></div>
        <div class="toRightImg" onClick="showLeftBox()">
            <img src="${base }/common/back/img/arrowToRight.png"/>
        </div>
        <div class="leftBox">
            <div class="listBox">
                <i class="borderBox"></i>
                <i class="toLeftImg" onClick="hideLeftBox()">
                    <img src="${base }/common/back/img/arrowToLeft.png"/>
                </i>
                <div class="items1">
                    玩家管理
                    <span>+ </span>
                    <ul>
                        <li><a href="${backBase }/user/list.do" target="rightIframe">用户列表</a></li>
                        <li><a href="${backBase }/feedback/list.do" target="rightIframe">反馈列表</a></li>
                        <li><a href="${backBase }/loginLog/list.do" target="rightIframe">登录记录</a></li>
                    </ul>
                </div>
                <div class="items3">
                    游戏管理
                    <span>+ </span>
                    <ul>
                        <li><a href="${backBase }/room/list.do" target="rightIframe">房间列表</a></li>
                        <li><a href="${backBase }/room/scores.do" target="rightIframe">战绩列表</a></li>
                        <li><a href="${backBase }/consume/list.do" target="rightIframe">钻石消费</a></li>
                    </ul>
                </div>
                <#if user?? && user.level == 0>
                <div class="items3">
                    管理员
                    <span>+ </span>
                    <ul>
                        <li><a href="${backBase }/admin/list.do" target="rightIframe">管理员列表</a></li>
                    </ul>
                </div>
                <div class="items4">
                    系统设置
                    <span>+ </span>
                    <ul>
                        <li><a href="${backBase }/notice/list.do" target="rightIframe">公告管理</a></li>
                        <li><a href="${backBase }/optionItem/list.do" target="rightIframe">参数设置</a></li>
                        <li><a href="${backBase }/config/list.do" target="rightIframe">系统配置</a></li>
                    </ul>
                </div>
                </#if>
            </div>
        </div>
        <iframe src="${backBase }/welcome.do" scrolling="yes" frameborder="1"
                style="border:none; width:450px; height:80px"
                id="rightBoxFrame" name="rightIframe"></iframe>
    </div>
</div>
<script src="${base }/common/back/js/base.js?v=0.05"></script>
</body>
</html>