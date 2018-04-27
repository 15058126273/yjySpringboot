<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" rightIframe="text/html; charset=UTF-8">
    <title>编辑管理员信息</title>
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
        });

    </script>
</head>
<body>
<form id="adminFrom" class="rightBox" action="${backBase}/admin/update.do" method="post">
    <input type="hidden" name="id" value="${adminUser.id}"/>
    <div class="detailSection">
        <ul>
            <li><span>*</span>用户名：</li>
            <li>
                <input type="text" value="${adminUser.userName}" disabled="disabled"/>
            </li>
        </ul>
    </div>
    <div class="detailSection">
        <ul>
            <li><span>*</span>密码：</li>
            <li>
                <input id="password" class="text" type="password" autocomplete='off' name="password" value=""/>
            </li>
        </ul>
    </div>
    <div class="detailSection">
        <ul>
            <li><span>*</span>真实姓名：</li>
            <li>
                <input id="realName" class="text" type="text required" name="realName" value="${adminUser.realName!}"/>
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
    <div class="detailSection">
        <ul>
            <li><span>*</span>等级：</li>
            <li>
                <select id="level" name="level" class="required via">
                    <option value="1" <#if adminUser?? && adminUser.level==1>selected</#if>>普通用户</option>
                    <option value="0" <#if adminUser?? && adminUser.level==0>selected</#if>>超级管理员</option>
                </select>
            </li>
        </ul>
    </div>
    <div class="detailSection">
        <ul>
            <li><span>*</span>是否可用：</li>
            <li>
                <select id="status" name="status" class="required via">
                    <option value="1" <#if adminUser?? && adminUser.status?? && adminUser.status==1> selected </#if> >
                        可用
                    </option>
                    <option value="0" <#if adminUser?? && adminUser.status?? && adminUser.status==0> selected </#if> >
                        禁用
                    </option>
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