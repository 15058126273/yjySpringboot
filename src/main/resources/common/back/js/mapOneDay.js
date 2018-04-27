var time = ["12点", "13点", "14点", "15点", "16点", "17点"];
var newNum = [120, 190, 30, 50, 2, 30];
var loading = false;

function draw(time,num, room) {

    $('.mapContainer').highcharts({
        chart: {
            type: 'line',
        },
        title: {
            text: '统计图表',
        },
        xAxis: {
            categories: time,
        },
        yAxis: {
            title: {
                text: '数量'
            }
        },
        plotOptions: {
            line: {
                dataLabels: {
                    enabled: true          // 开启数据标签
                },
                enableMouseTracking: false // 关闭鼠标跟踪，对应的提示框、点击事件会失效
            }
        },
        series: [ {
            name: '钻石',
            data: num,
        },
        {
            name: '房间',
            data: room,
        }]
    });
}

function drawMap(time,num,room) {
    setTimeout(function () {
        draw(time,num, room);
    },1);
};

/**
 * 每天的消费钻石数量统计
 */
function consumeDays() {
    var startTime = document.querySelector('#start').value;
    var endTime = document.querySelector('#end').value;
    loading = true;
    $.ajax({
        type: 'post',
        url: 'chartData.do',
        data: {start:startTime,end:endTime},
        dataType: 'json',
        success: function (data) {
            if (data.status == 1) {
            	data = data.data;
            	var time=new Array(), num = new Array(), room = new Array();
            	for(var i=0; i<data.length; i++)  {
            		var o = data[i];
            		time[i] = o.addTime;
            		num[i] = o.num;
            		room[i] = o.balance;
                }  
            	drawMap(time, num, room);
            } else {
                alert(data.info);
            }
            loading = false;
        },
        error: function () {
            alert('数据获取失败，请稍后重试！');
            loading = false;
        }
    });
}

