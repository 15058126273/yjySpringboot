<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" rightIframe="text/html; charset=UTF-8">
    <title>编辑用户信息</title>
    <link rel="stylesheet" href="${base }/common/back/style/editor.css?v=1.2"/>
    <script src="${base }/common/back/js/jquery-1.7.2.min.js" type="text/javascript"></script>
    <script src="${base }/common/back/js/base.js"></script>
    <script src="${base}/common/back/js/jquery-validate.js" type="text/javascript"></script>
    <script src="${base}/common/back/js/jquery-form.js" type="text/javascript"></script>
    <script type="text/javascript">
        $(function () {
            $("#submitForm").validate({
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
        });
    </script>
</head>
<body>
<form class="rightBox" action="${backBase}/admin/updateInfo.do" method="post" id="submitForm">
    <input type="hidden" name="id" value="${adminUser.id!}"/>
    <div class="detailSection">
        <ul>
            <li><span>*</span>用户名：</li>
            <li>
            ${adminUser.userName!}
                <input type="hidden" name="userName" value="${adminUser.userName!}"/>
            </li>
        </ul>
    </div>
    <div class="detailSection">
        <ul>
            <li><span>*</span>真实姓名：</li>
            <li>
                <input id="realName" class="text required" type="text" name="realName" value="${adminUser.realName!}"/>
            </li>
        </ul>
    </div>
    <div class="detailSection">
        <ul>
            <li><span>*</span>原始密码：</li>
            <li>
                <input id="password" class="text required" type="password" autocomplete='off' name="password"/>
            </li>
        </ul>
    </div>
    <div class="detailSection">
        <ul>
            <li><span>*</span>密码：</li>
            <li>
                <input type="password" class="text" id="newPassword" name="newPassword" autocomplete='off'/>
            </li>
        </ul>
    </div>
    <div class="detailSection">
        <ul>
            <li><span>*</span>重复密码：</li>
            <li>
                <input type="password" class="text" id="rePassword" name="rePassword" autocomplete='off'/>
            </li>
        </ul>
    </div>
    <div class="contact">
        <ul>
            <li><span>*</span>联系电话：</li>
            <li>
                <input id="mobile" class="text" type="text" name="mobile" value="${adminUser.mobile!}"/>
            </li>
            <li><span>*</span>QQ：</li>
            <li>
                <input id="qq" class="text" type="text" name="qq" value="${adminUser.qq!}"/>
            </li>
        </ul>
    </div>
    <div class="btnBox">
        <input type="button" value="提交" onclick="doSubmit()"/>
        <a onclick="javascript:history.back(-1);">返回</a>
    </div>
</form>
<script src="${base }/common/back/js/editorFrame.js"></script>
<script type="text/javascript">
    var message = "${message!}";
    $(function () {
        if (null != message && "" != message) {
            alert(message);
        }
    });

    function doSubmit() {
        var password = $("#password").val().trim();

        var newPassword = $("#newPassword").val().trim();
        var rePassword = $("#rePassword").val().trim();
        if ("" != newPassword) {
            if (newPassword.indexOf(" ") != -1) {
                alert("密码中不能有空格");
                return false;
            }
            if (newPassword.length < 6 || newPassword.length > 18) {
                alert("密码长度必须在6-18位");
                return false;
            }

            if ("" != rePassword) {
                if (rePassword != newPassword) {
                    alert("两次输入的密码不一样");
                    return false;
                }
            } else {
                alert("重复新密码必须填写");
                return false;
            }
        }

        if ("" == password || password.length <= 0) {
            alert("原始密码必须填写");
            return false;
        }
        $("#submitForm").submit();

    }
</script>
</body>
</html>