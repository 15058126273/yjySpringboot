$(window).load(function () {
    rightBoxPosition();
    alertMsg();
});

//list页面
function rightBoxPosition () {
    $('.defaultTable').css({'width': '90%','marginLeft': '5%'});
    $('.rightBox').css({'width': '100%'});
}

function alertMsg() {
	var msg = $("#alertMsg").val();
	if(null != msg && "" != msg) {
		alert(msg);
	}
}

/**** v = 0.1.1 *****/