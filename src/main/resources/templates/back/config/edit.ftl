<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" rightIframe="text/html; charset=UTF-8">
	<title>修改系统参数</title>
	<link rel="stylesheet" href="${base }/common/back/style/editor.css?v=1.2"/>
	<script src="${base }/common/back/js/jquery-1.7.2.min.js" type="text/javascript"></script>
    <script src="${base }/common/back/js/base.js"></script>
    <script src="${base}/common/back/js/jquery-validate.js" type="text/javascript"></script>
    <script src="${base}/common/back/js/jquery-form.js" type="text/javascript"></script>
    <script type="text/javascript">    
	    $(function() {
	    	$("#configFrom").validate({
	            errorPlacement: function(error, element) {
	                // 是否设置了错误显示位置
	                var errorContainer = $( element )
	                        .closest( "form" )
	                        .find( "label[for='" + element.prop( "name" ) + "']" );
	                if (errorContainer.length) {
	                    errorContainer.append( error );
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
	<form  class="rightBox"  action="${backBase}/config/update.do" method="post"  id="configFrom" >
		<input type="hidden" name="id" value="${(bean.id)!}" />
	    <div class="detailSection">
	        <ul>
	            <li><span>*</span>参数名称：</li>
	            <li>
	                ${(bean.fieldName)!}
	            </li>
	        </ul>
	    </div>
	    <div class="detailSection">
	        <ul>
	            <li><span>*</span>参数标示：</li>
	            <li>
	               <input type="text" name="fieldKey" class="required" size="45" class=""  value="${(bean.fieldKey)!}" placeholder="输入参数标示"/>
	            </li>
	        </ul>
	    </div>
	    <div class="detailSection">
	        <ul>
	            <li><span>*</span>参数值：</li>
	            <li>
	               <input type="text" name="fieldValue" class="required" size="45" class=""  value="${(bean.fieldValue)!}" placeholder="输入字段显示值"/>
	            </li>
	        </ul>
	    </div>
	    <div class="detailRemarks" >
	        <ul>
	            <li><span>*</span>填写说明：</li>
	            <li>
	               <textarea id="description" rows="3" cols="55"  name="description" >${(bean.description)!} </textarea>
	            </li>
	        </ul>
	    </div>
	    <div class="detailSection">
	        <ul>
	            <li><span>*</span>状态：</li>
	            <li>
			        <select name="status"  class="required via" >
                 		<#list Application['commonStatus'> as item >
                 			<option value="${(item.fieldKey)!}" <#if bean?? && bean.status?? && item.fieldKey == bean.status > selected </#if> >${(item.fieldValue)!}</option>
                 		</#list>
                 	</select>
	            </li>
	        </ul>
	    </div>
	    <div class="btnBox">
	        <input type="submit" value="提交" />
	        <a onclick="javascript:history.back(-1);" >返回</a>
	    </div>
	</form>
<script src="${base }/common/back/js/editorFrame.js"></script>
</body>
</html>