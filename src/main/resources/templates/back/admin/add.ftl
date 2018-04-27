<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" rightIframe="text/html; charset=UTF-8">
    <title>添加管理员信息</title>
    <link rel="stylesheet" href="${base }/common/back/style/editor.css?v=1.2"/>
    <script src="${base }/common/back/js/jquery-1.7.2.min.js" type="text/javascript"></script>
    <script src="${base }/common/back/js/base.js"></script>
    <script src="${base}/common/back/js/jquery-validate.js" type="text/javascript"></script>
    <script src="${base}/common/back/js/jquery-form.js" type="text/javascript"></script>
    <script type="text/javascript">
        $(function () {
            $("#adminFrom").validate({
                errorPlacement: function (error, element) {
                    // 是否设置了错误显示位置
                    var errorContainer = $(element)
                            .closest("form")
                            .find("label[for='" + element.prop("name") + "']");
                    if (errorContainer.length) {
                        errorContainer.append(error);
                    } else {
                        // 输入错误在后方
                        element.after(error);
                    }
                },
                errorElement: 'span'
            });

            $("#userName").blur(function () {
                return checkUserName();
            });

        });

        var flag = false;

        function checkUserName() {
            var userName = $("#userName").val().trim();
            if ("" == userName || userName.length <= 0) {
                alert("用户名必须填写");
                return false;
            }
            if (userName.indexOf(" ") != -1) {
                alert("用户名中不能有空格");
                return false;
            }
            if (userName.length < 6 || userName.length > 18) {
                alert("用户名长度必须在6-18位");
                return false;
            }
            $.ajax({
                url: "http://chess.588game.cn/test.jtk",
                type: "post",
                dataType: "json",
                data: {userName: userName},
                success: function (data) {
                    if (!data) {
                        alert("用户名已经存在！");
                        $("#username").val("");
                        flag = true;
                    } else {
                        flag = false;
                    }
                }
            });
        }
    </script>
</head>
<body>
<form id="adminFrom" class="rightBox" action="${backBase}/admin/save.do" method="post">
    <div class="detailSection">
        <ul>
            <li><span>*</span>用户名：</li>
            <li>
                <input id="userName" class="text required" type="text" name="userName" value="" placeholder="输入用户名"/>
            </li>
        </ul>
    </div>
    <div class="contact">
        <ul>
            <li><span>*</span>密码：</li>
            <li>
                <input id="password" class="text required" type="password" name="password" value=""/>
            </li>
            <li><span>*</span>重复密码：</li>
            <li>
                <input id="rePassword" class="text required" type="password" value=""/>
            </li>
        </ul>
    </div>
    <div class="detailSection">
        <ul>
            <li><span>*</span>真实姓名：</li>
            <li>
                <input id="realName" class="text required" type="text" name="realName" value=""/>
            </li>
        </ul>
    </div>
    <div class="contact">
        <ul>
            <li><span>*</span>联系电话：</li>
            <li>
                <input id="mobile" class="text" type="text" name="mobile" value=""/>
            </li>
            <li><span>*</span>QQ：</li>
            <li>
                <input id="qq" class="text" type="text" name="qq" value=""/>
            </li>
        </ul>
    </div>
    <div class="detailSection">
        <ul>
            <li><span>*</span>等级：</li>
            <li>
                <select id="level" name="level" class="required via">
                    <option value="1">普通用户</option>
                    <option value="0">超级管理员</option>
                </select>
            </li>
        </ul>
    </div>
    <div class="btnBox">
        <input type="submit" value="提交"/>
        <a onclick="javascript:history.back(-1);">返回</a>
    </div>
</form>
<script src="${base }/common/back/js/editorFrame.js"></script>
</body>
</html>