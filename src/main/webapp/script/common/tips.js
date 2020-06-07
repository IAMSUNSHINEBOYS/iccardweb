function tips(content, elem, height, width) {
    layer.tips(content, elem, {
        tips : [3, '#f9f9f9'],
        area : [width, height],
        time : 0,
        isOutAnim : false,
        fixed : false,
        success : function(layero, index) {
            $(elem).one('mouseleave', function() {
                layer.close(index);
            });
            $("body").one('mouseout mouseover click', function(e) {
                var result = true;
                if (e.target == elem) {
                    result = false;
                } else {
                    result = !$.contains(elem, e.target);
                }
                if (result) {
                    layer.close(index);
                }
            });
        }
    });
}


$('[data-user-tips]').mouseenter(function() {
    userTips(this);
});

function userTips(obj){
    var that = obj;
    var data=$(that).attr('data-user-tips');
    if(trim(data)=='')
       return;
    var url = contextPath + "/admin/tipsData/getUserData.do?time=" + new Date().getTime();
    var data = "data=" + data;
    if ($(that).attr('data-type'))//卡账号
        data += "&type=1";
    $.get(url, data, function(data) {
        tips(data, that, '215px', '230px');
    });
}

$('[data-mbox-tips]').mouseenter(function() {
    var that = this;
    var url = contextPath + "/admin/tipsData/getMboxData.do?time=" + new Date().getTime();
    var data = "mboxId=" + $(that).attr('data-mbox-tips');
    $.get(url, data, function(data) {
        tips(data, that, '186px', '250px');
    });
});

$('[data-pc-tips]').mouseenter(function() {
    var that = this;
    var url = contextPath + "/admin/tipsData/getPcData.do?time=" + new Date().getTime();
    var data = "pillarId=" + $(that).attr('data-pc-tips');
    $.get(url, data, function(data) {
        tips(data, that, '186px', '250px');
    });
});

$('[data-com-tips]').mouseenter(function() {
    var that = this;
    var url = contextPath + "/admin/tipsData/getComData.do?time=" + new Date().getTime();
    var data = "machineId=" + $(that).attr('data-com-tips');
    $.get(url, data, function(data) {
        tips(data, that, '186px', '250px');
    });
});

$('[data-bikeTrack-tips]').click(function() {
    var that = this;
    var url = contextPath + "/admin/tipsData/bikeTrack.do?time=" + new Date().getTime();
    var carNumber = $(that).attr('data-bikeTrack-tips');
    var data = "carNumber=" + carNumber;
    $.get(url, data, function(data) {
        top.layer.open({
            type : 1,
            id :'trackId',
            shadeClose : true,
            closeBtn : 0,
            title : '车辆轨迹<i class="iconfont m">&#xe604;</i>' + carNumber,
            offset : 'r',
            shade : .1,
            area : ["565px", '650px'],
            content : data,
        });
    });
});

$('[data-pcTrack-tips]').click(function() {
    var that = this;
    var url = contextPath + "/admin/tipsData/pcTrack.do?time=" + new Date().getTime();
    var pillarId = $(that).attr('data-pcTrack-tips');
    var data = "pillarId=" + pillarId;
    $.get(url, data, function(data) {
        top.layer.open({
            type : 1,
            id :'trackId',
            shadeClose : true,
            closeBtn : 0,
            title : '锁柱轨迹<i class="iconfont m">&#xe604;</i>' + pillarId,
            offset : 'r',
            shade : .1,
            area : ["565px", '650px'],
            content : data,
        });
    });
});

$('[data-rentReturn-tips]').click(function() {
    var that = this;
    var url = contextPath + "/admin/tipsData/rentReturnRecord.do?time=" + new Date().getTime();
    var cardUser = $(that).attr('data-rentReturn-tips');
    var data = "cardUser=" + cardUser;
    $.get(url, data, function(data) {
        top.layer.open({
            type : 1,
            id :'trackId',
            shadeClose : true,
            closeBtn : 0,
            title : '借还记录<i class="iconfont m">&#xe604;</i>' + cardUser,
            offset : 'r',
            shade : .1,
            area : ["520px", '650px'],
            content : data,
        });
    });
});

