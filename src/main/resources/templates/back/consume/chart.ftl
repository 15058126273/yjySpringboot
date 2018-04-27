<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" rightIframe="text/html; charset=UTF-8">
    <title>钻石消费统计</title>
    <link rel="stylesheet" href="${base }/common/back/style/mapOneDay.css?v=1.1"/>
    <link rel="stylesheet" href="${base }/common/thirdparty/My97DatePicker/skin/WdatePicker.css"/>
    <script src="${base }/common/back/js/jquery-1.7.2.min.js" type="text/javascript"></script>
    <script src="${base }/common/back/js/highChart.js" type="text/javascript"></script>
    <script src="${base }/common/thirdparty/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
</head>
<body>
<form class="mapBox" method="post" action="#" onsubmit="return false">
    <div class="btnBox">
        <input type="text" id="start" class="inputTime" name="start" value="${(start?string('yyyy-MM-dd'))!}"
               onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" placeholder="请输入开始时间"/>
        <input type="text" id="end" name="end" class="inputTime" value="${(end?string('yyyy-MM-dd'))!}"
               onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" placeholder="请输入结束时间"/>
        <input type='submit' id="dayBtn" class="searchBtn" value="查询"/>
    </div>
    <div class="mapContainer"></div>
</form>
<script src="${base }/common/back/js/mapOneDay.js?v=1.2"></script>
<script type="text/javascript">
    loading = false;
    $(function () {
        document.querySelector('#dayBtn') && document.querySelector('#dayBtn').addEventListener('click', function () {
            if (!loading) {
                consumeDays();
            }
        }, false);
    });
    //初始化执行
    consumeDays();
</script>
</body>
</html>
