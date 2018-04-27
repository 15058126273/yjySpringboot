<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" rightIframe="text/html; charset=UTF-8">
    <title>添加公告信息</title>
    <link rel="stylesheet" href="${base }/common/back/style/editor.css?v=1.2"/>
    <script src="${base }/common/back/js/jquery-1.7.2.min.js" type="text/javascript"></script>
    <script src="${base }/common/back/js/base.js"></script>
    <script src="${base}/common/back/js/jquery-validate.js" type="text/javascript"></script>
    <script src="${base}/common/back/js/jquery-form.js" type="text/javascript"></script>
    <script type="text/javascript">
        $(function () {
            $("#noticeFrom").validate({
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
<form id="noticeFrom" class="rightBox" action="${backBase}/notice/save.do" method="post">
    <div class="detailRemarks">
        <ul>
            <li><span>*</span>公告类型：</li>
            <li>
                <select name="type" class="required via">
                    <option value="">请选择类型</option>
		                <#list Application['broadcastType'> as item >
			     			<option value="${(item.fieldKey)!}"  <#if bean?? && bean.type?? && item.fieldKey == bean.type  >
                                    selected </#if> >${(item.fieldValue)!}</option>
                        </#list>
                </select>
            </li>
        </ul>
        <ul class="detailRemarks">
            <li><span>*</span>公告信息：</li>
            <li>
                <textarea id="content" rows="5" cols="55" name="content">${(bean.content)!} </textarea>
            </li>
        </ul>
    </div>
    <div class="btnBox">
        <input type="submit" value="提交"/>
        <a onclick="javascript:history.back(-1);">返回</a>
    </div>
</form>
<script src="${base }/common/back/js/editorFrame.js"></script>
<script type="text/javascript">
    $(function () {
        var message = "${message!}";
        if (null != message && "" != message)
            alert(message);
    });
</script>
</body>
</html>