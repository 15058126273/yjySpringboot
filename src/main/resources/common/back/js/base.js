
var hide = false;

$(window).load(function () {
    listClick();
    bindClick();
    $('.leftBox').css({'height': $(window).height() - 100 + 'px'});
    setIframeSize();
});

function hideLeftBox() {
    hide = true;
    $('.listBox').hide();
    $('.leftBox').css({'overflow': 'hidden'});
    $('.borderBoxRight,.toRightImg').show();
    $('#rightBoxFrame').css({'width': '100%'});
    rightIframe.window && rightIframe.window.rightBoxPosition && rightIframe.window.rightBoxPosition();
}

function showLeftBox() {
    hide = false;
    $('.listBox').show();
    $('.leftBox').css({'overflowY': 'scroll','overflowX': 'hidden'});
    $('.borderBoxRight,.toRightImg').hide();
    rightIframe.window && rightIframe.window.rightBoxPosition && rightIframe.window.rightBoxPosition();
    setIframeSize();
}

//定义左侧列表点击事件
function listClick() {
    for (var i = 0; i < $('.listBox>div').length; i++) {
        (function (i) {
            $('.listBox>div').eq(i).click(function () {
                childList(i);
            });
        })(i)
    }
}

function childList(i) {
    var slideOrDown = $('.listBox>div').eq(i).find('ul').css('display');
    if (slideOrDown == 'block') {
        $('.listBox>div').eq(i).find('ul').hide();
        $('.listBox>div').eq(i).find('span').html('+');

    } else {
        $('.listBox>div').eq(i).find('ul').show();
        $('.listBox>div').eq(i).find('span').html('-');
    }
}

//点击左侧列表子菜单触发请求数据

//1.阻止冒泡时间
function getData(e) {

    if (e&&e.stopPropagation) {//非IE

        e.stopPropagation();

    } else {//IE

        window.event.cancelBubble=true;

    }
}

//点击变化背景色
//为菜单栏绑定点击事件
function bindClick() {
    for (var i = 0; i < $('.listBox>div').length; i++) {
        (function (i) {
            for (var m = 0; m < $('.listBox>div').eq(i).find('li').length; m++) {
                (function (m) {
                    $('.listBox>div').eq(i).find('li').eq(m).click(function () {
                        defaultBack();
                        var e=arguments.callee.caller.arguments[0]||event; //若省略此句，下面的e改为event，IE运行可以，但是其他浏览器就不兼容
                        if (e && e.stopPropagation) {
                            // this code is for Mozilla and Opera
                            e.stopPropagation();
                        } else if (window.event) {
                            // this code is for IE
                            window.event.cancelBubble = true;
                        }
                        $('.listBox>div').eq(i).find('li').eq(m).css({'backgroundColor': '#288ce2'});
                    });
                })(m);
            }
        })(i);
    }
}

function defaultBack() {
    for (var i = 0; i < $('.listBox>div').length; i++) {
        for (var m = 0; m < $('.listBox>div').eq(i).find('li').length; m++) {
            $('.listBox>div').eq(i).find('li').eq(m).css({'backgroundColor': '#1f7ed0'});
        }
    }
}

/***********************/

/*$(window).resize(function () {
    rightBoxPosition();
});*/

//上传图片点击事件
function fileClicked (id) {
    $('#' + id).click();
}

function newImg (id) {
    var imgSrc = $('#'+id).val();
    if (!imgSrc) return;
    imgSrc = imgSrc.slice(12);
    switch (id) {
        case 'spreadImg':
            $(".uploadImg").eq(0).find('textarea').val(imgSrc);
            break;
        case 'shoppeImg':
            $(".uploadImg").eq(1).find('textarea').val(imgSrc);
            break;
        case 'fodder':
            $(".uploadImg").eq(2).find('textarea').val(imgSrc);
            break;
        default: break;
    }
}

function clearUrl(id) {
    switch (id) {
        case 'spreadImg':
            $(".uploadImg").eq(0).find('textarea').val(null);
            $('#' + id).val(null);
            break;
        case 'shoppeImg':
            $(".uploadImg").eq(1).find('textarea').val(null);
            $('#' + id).val(null);
            break;
        case 'fodder':
            $(".uploadImg").eq(2).find('textarea').val(null);
            $('#' + id).val(null);
            break;
        default: break;
    }
}

//定义iframe大小
function setIframeSize() {
    $('#rightBoxFrame').height(window.innerHeight - $('.header').outerHeight());
    hide == false ? $('#rightBoxFrame').width(window.innerWidth - $('.listBox').outerWidth()) : $('#rightBoxFrame').width(window.innerWidth);
}

/**** v = 0.1.1 *****/
