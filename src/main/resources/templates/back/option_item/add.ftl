<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" rightIframe="text/html; charset=UTF-8">
    <title>添加数据字典信息</title>
    <link rel="stylesheet" href="${base }/common/back/style/editor.css?v=1.2"/>
    <script src="${base }/common/back/js/jquery-1.7.2.min.js" type="text/javascript"></script>
    <script src="${base }/common/back/js/base.js"></script>
    <script src="${base}/common/back/js/jquery-validate.js" type="text/javascript"></script>
    <script src="${base}/common/back/js/jquery-form.js" type="text/javascript"></script>
    <script type="text/javascript">
        $(function () {
            $("#optionItemFrom").validate({
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
<form class="rightBox" action="${backBase}/optionItem/save.do" method="post" id="optionItemFrom">
    <div class="detailSection">
        <ul>
            <li><span>*</span>字段：</li>
            <li>
                <input type="text" name="field" class="required" size="45" class="" value="${(bean.field)!}"
                       placeholder="输入字段"/>
            </li>
        </ul>
    </div>
    <div class="detailSection">
        <ul>
            <li><span>*</span>字段标示：</li>
            <li>
                <input type="text" name="fieldKey" class="required" size="45" class="" value="${(bean.fieldKey)!}"
                       placeholder="输入字段标示"/>
            </li>
        </ul>
    </div>
    <div class="detailSection">
        <ul>
            <li><span>*</span>字段显示值：</li>
            <li>
                <input type="text" name="fieldValue" class="required" size="45" class="" value="${(bean.fieldValue)!}"
                       placeholder="输入字段显示值"/>
            </li>
        </ul>
    </div>
    <div class="detailSection">
        <ul>
            <li><span>*</span>是否显示：</li>
            <li>
                <select name="isUse" class="required via">
                    <#list Application['commonStatus'> as item >
                    <option value="${(item.fieldKey)!}"
                    <#if bean?? && bean.isUse?? && item.fieldKey == bean.isUse?string > selected </#if>
                >${(item.fieldValue)!}</option>
            </#list>
            </select>
            </li>
        </ul>
    </div>
    <div class="detailSection">
        <ul>
            <li><span>*</span>排序：</li>
            <li>
                <input type="text" name="priority" class="required " size="45" class="" value="${(bean.priority)!}"
                       placeholder="输入字段排序"/>
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