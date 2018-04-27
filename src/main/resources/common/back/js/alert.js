
	/******4.7*******/
//显示弹出框
function showUpdate(allNum) {
	if (allNum > 0) {
    	var alertDom = "<div class='update'><p>淘客提示：<span>×</span></p><p>您有<span>" + allNum + "</span>条更新消息!</p></div>";
	    $("body").append(alertDom);
	    $('div.update>p').eq(0).find('span').click(function () {
	        hideUpdate();
		});
	    $('.update').fadeIn();
	     setTimeout(function () {
	        $('.update').fadeOut();
	    },10000);
    }    	
}

function hideUpdate() {
    $('.update').fadeOut();
}

var allNum = 0;
//如果为0，隐藏
function updateNum(id, num) {
	if(num == 0) {
		$("#"+ id).hide();
		return false;
	}
	$("#"+ id).html("+" + num);
	allNum = num;
	$("#"+ id).show();
	showUpdate(allNum);
}

//检查是否有新的待处理数据
function checkNum(url, id) {
	if(null == url || null == id) {
		return false;
	}
	$.ajax({
		url:url,
		type:"post",
		dataType:"json",
		data:{},
		success:function(data){
			 if (data.status == 1) {
				 var numjson = data.data;
				 updateNum(id, numjson.num );
           } else {
               alert(data.info);
           }
		}
	});
}