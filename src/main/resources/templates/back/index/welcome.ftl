<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>welcome</title>
    <link rel="stylesheet" href="${base }/common/back/style/error.css">
    <link rel="stylesheet" href="${base }/common/back/style/base.css?v=1.1"/>
    <script src="${base }/common/back/js/jquery-1.7.2.min.js" type="text/javascript"></script>
</head>
<body>
<div class="welcomeBox">
    <span>欢迎登陆花牌管理系统！</span>
</div>
<div>
    <input type="button" value="刷新" id="loadData" class="submitBtn">
    <span>当前房间数：</span><span id="roomNum">0</span>
    <span>当前玩家数：</span><span id="playerNum">0</span>
</div>
<script type="text/javascript">
    var loading = false;
    $(function () {
        $("#loadData").click(function () {
            console.log("sadadsasdasd")
            if (!loading) {
                gameDate();
            } else {
                alter("慢点提交，悠着点");
            }
        })
    });

    //获取当时数据
    function gameDate() {
        loading = true;
        $.ajax({
            type: 'post',
            url: '${backBase}/gameInfo.do',
            data: {},
            dataType: 'json',
            success: function (data) {
                if (data.status == 1) {
                    data = data.data;
                    $("#roomNum").html(data.room);
                    $("#playerNum").html(data.user);
                } else {
                    alert(data.info);
                }
                loading = false;
            },
            error: function () {
                alert('数据获取失败，请稍后重试！');
                loading = false;
            }
        });
    }

    //初始化执行
    gameDate();
</script>
</body>
</html>