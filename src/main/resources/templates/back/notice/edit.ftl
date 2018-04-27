<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" rightIframe="text/html; charset=UTF-8">
    <title>编辑管理员信息</title>
    <link rel="stylesheet" href="${base }/common/back/style/editor.css?v=1.2"/>
    <link href="${base}/common/thirdparty/uploadify/css/uploadify.css" rel="stylesheet" type="text/css"/>
    <link href="${base}/common/thirdparty/lightbox/css/lightbox.css" rel="stylesheet" type="text/css"/>
    <script src="${base }/common/back/js/jquery-1.7.2.min.js" type="text/javascript"></script>
    <script src="${base }/common/back/js/base.js"></script>
    <script src="${base}/common/back/js/jquery-validate.js" type="text/javascript"></script>
    <script src="${base}/common/back/js/jquery-form.js" type="text/javascript"></script>
    <script type="text/javascript">
        document.write('<script type="text/javascript" src="${base}/common/thirdparty/uploadify/jquery.uploadify.js?v=' + (1000 + Math.ceil(Math.random() * 10000)) + '"><\/script>')
    </script>
    <script type="text/javascript" src="${base}/common/thirdparty/lightbox/lightbox.js"></script>
    <script type="text/javascript">
        $(function () {
            $("#noticeForm").validate({
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
<form id="noticeForm" class="rightBox" action="${backBase}/notice/update.do" method="post">
    <input type="hidden" name="id" value="${bean.id}"/>
    <div class="detailRemarks">
        <ul>
            <li><span>*</span>公告类型：</li>
            <li>
                <select name="type" class="required via">
                    <option value="">请选择类型</option>
		                <#list Application['broadcastType'> as item >
			     			<option value="${(item.fieldKey)!}"  <#if bean.type?? && item.fieldKey == bean.type  >
                                    selected </#if> >${(item.fieldValue)!}</option>
                        </#list>
                </select>
            </li>
        </ul>
        <ul>
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