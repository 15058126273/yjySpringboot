function rightBoxPosition () {
    $('.rightBox').css({'width': '100%'});
}

/*laydate({
    elem: '#startDate',
    event: 'focus',
});

laydate({
    elem: '#endDate',
    event: 'focus',
});*/

$(window).load(function () {
    rightBoxPosition();
    parent.setIframeSize();
});

/**** v = 0.1.1 *****/
