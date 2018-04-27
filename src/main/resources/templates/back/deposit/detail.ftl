<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>查看钻石信息</title>
    <link rel="stylesheet" type="text/css" href="${base}/common/back/style/detail.css?v=1.0">
    <script src="${base}/common/back/js/jquery-1.7.2.min.js"></script>
</head>
<body>
<div class="rightBox">
    <div>
        <ul>
            <li><span>*</span>订单号：</li>
            <li>${(bean.orderNo)!}</li>
        </ul>
    </div>
    <div>
        <ul>
            <li><span>*</span>用户：</li>
            <li>${(bean.buyerName)!}</li>
        </ul>
    </div>
    <div>
        <ul>
            <li><span>*</span>钻石数：</li>
            <li>${(bean.num)!}</li>
        </ul>
    </div>
    <div>
        <ul>
            <li><span>*</span>充值类型：</li>
            <li>${(Application['optionMap']['depositType' + bean.type])!}</li>
        </ul>
    </div>
    <div>
        <ul>
            <li><span>*</span>编辑时间：</li>
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