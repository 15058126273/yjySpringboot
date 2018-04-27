$(function(){
		$(".index_system_menu").click(function(){
			$(".index_system_ul").removeClass("slide");
			$(".index_system_menu").removeClass("Bg9CB9AF");
			$(".index_system_menu").removeClass("Bg86AEA0");
			$(this).next().addClass("slide");
			if($(this).next().hasClass("down")){
				$(this).addClass("Bg9CB9AF");
				$(".slide").slideUp(300);
				$(this).next().removeClass("down");
			}else{
				$(this).addClass("Bg86AEA0");
				$(".slide").slideDown(300);
				$(this).next().addClass("down");
			}
			$(".index_system_ul").not(".slide").removeClass("down");
			$(".index_system_ul").not(".slide").slideUp(300);
		});
		
		
		$(".select_menu").click(function(){
			$(".select_menu").addClass("index_system_li_bg");
			$(this).removeClass("index_system_li_bg");
		})
		
		$(".index_system_menu>span").eq(0).hide();
		
		  $('.update>p').eq(0).find('span').click(function () {
		      hideUpdate();
		  });
	});

$(function(){
		setInterval(timeGoing,1000);
	})
	
	function timeGoing(){
		var year = new Date().getFullYear();		
		var month = new Date().getMonth()+1;		
		var day = new Date().getDate();		
		var hour = new Date().getHours()>=10?(new Date().getHours()):"0"+(new Date().getHours());		
		var min = new Date().getMinutes() >=10?(new Date().getMinutes()):"0"+(new Date().getMinutes());		
		var second = new Date().getSeconds() >= 10?new Date().getSeconds() :"0"+new Date().getSeconds();
		//var mmm = new Date().getMilliseconds();
		$("#current_time").html(year+"年"+ month+"月"+day+"日"+ hour+":"+min+":"+second);
		
	}


/******3.21*******/
//显示弹出框
  function showUpdate( num ) {
	  if(num <= 0)
		  return;
	  $('.update>p>m').eq(0).html(num);
      $('.update').fadeIn();
      setTimeout(function () {
          $('.update').fadeOut();
      },5000);
  }

  function hideUpdate() {
      $('.update').fadeOut();
  }

  showUpdate(8);


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
	showUpdate( allNum);
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