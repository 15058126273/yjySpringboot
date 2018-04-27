<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="renderer" content="webkit"/>
    <title>花牌游戏管理系统</title>
    <link rel="stylesheet" href="${base }/common/back/style/base.css?v=1.0"/>
    <script src="${base }/common/back/js/jquery-1.7.2.min.js" type="text/javascript"></script>
</head>
<body>
<div class="DL_outBox">
    <div class="DL_inputBox">
        <div>
            <h3>花牌游戏管理系统</h3>
            <h4>FlowerGame Team Game System</h4>
        </div>
        <form id="loginForm" action="${backBase }/login.do" method="post" onsubmit="return doSubmit();">
            <ul>
                <li class="DL_input">
                    <span>账号</span> <input type="text" id="userName" value="" name="userName"/>
                </li>
                <li class="DL_input">
                    <span>密码</span> <input type="password" id="password" value="" name="password"/>
                </li>

                <li>
                    <input type="submit" class="submitBtn" value="登录"/>
                </li>
            </ul>
        </form>
    </div>
</div>

<script type="text/javascript">

    $(document).ready(function () {
        //盒子居中
        function setCenterForBox() {
            var widthOfWindow = $(window).width();
            var widthOfBox = $(".DL_inputBox").width();
            var heightOfWindow = $(window).height();

            $(".DL_inputBox").css("left", (widthOfWindow - widthOfBox) / 2 + "px");
            $(".DL_outBox").css("height", heightOfWindow + "px");
        }

        setCenterForBox();

        $(window).resize(function () {
            setCenterForBox();
        });

        $("#userName").focus();

        if (top != self) {
            window.parent.location.href = "${backBase}/index.do";
        }

        var message = "${message!}";
        if ("" != message) {
            alert(message);
        }
    });

    function doSubmit() {
        var userName = $("#userName").val();
        var password = $("#password").val();
        if ("" == userName) {
            alert("用户名必须填写");
            return false;
        }
        if ("" == password) {
            alert("密码必须填写");
            return false;
        }
    }
</script>
</body>
</html>