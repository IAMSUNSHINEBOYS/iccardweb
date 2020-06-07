var layerIndex = 0;
function addTab(selector) {
	layui.use('element', function() {
		var element = layui.element;
		//左侧导航新增标签或者切换到当前标签
		selector.on('click', function() {
			var othis = $(this);
			var layId = othis.attr('href');
			var newTab = true;
			$.each($('.layui-tab-title li'), function() {
				if (layId === $(this).attr('lay-id')) {
					element.tabChange('main-tab', layId);
					return newTab = false;
				}
			});
			if (newTab) { //新增一个标签
				var title = othis.html();
				if (othis.attr('tab-title'))
					title = othis.attr('tab-title');
				element.tabAdd('main-tab', {
					title : title,
					content : '<iframe src=' + layId + ' class="iframe-tab"></iframe>',
					id : layId
				}).tabChange('main-tab', layId);
			}
			return false;
		});
	});
}

layui.use('element', function() {
	addTab($('.addTab'));
	var element = layui.element;
	//标签添加右击弹窗
	$('.layui-tab-title').on('contextmenu', 'li', function(e) {
		var othis = $(this);
		var layId = othis.attr('lay-id');
		$('.mainTabTips').remove();
		var mainTabTips = $('<div class="mainTabTips"><div>');
		$('<p><i class="iconfont">&#xe69b;</i>关闭其它</p>').on('click', function() {
			$.each($('.layui-tab-title li'), function() {
				if (layId !== $(this).attr('lay-id')) {
					element.tabDelete('main-tab', $(this).attr('lay-id'));
				}
			});
		}).appendTo(mainTabTips);
		$('<p><i class="iconfont">&#xe612;</i>关闭所有</p>').on('click', function() {
			$.each($('.layui-tab-title li'), function() {
				element.tabDelete('main-tab', $(this).attr('lay-id'));
			});
		}).appendTo(mainTabTips);
		$('<p><i class="iconfont">&#xe6c9;</i>新窗口打开</p>').on('click', function() {
			window.open(layId);
		}).appendTo(mainTabTips);
		$('<p><i class="iconfont">&#xe69c;</i>刷新</p>').on('click', function() {
			$(".iframe-tab").get(othis.index()).contentWindow.location.reload(true);
		}).appendTo(mainTabTips);
		mainTabTips.appendTo($(this)).css({
			left : e.clientX - 8,
			top : e.clientY - 8
		});
		$(this).on('mouseleave', function() { //绑定鼠标移除事件
			$('.mainTabTips').remove();
		});
		return false;
	});
});

layui.use('element', function() {
	var element = layui.element;
	element.on('nav(menu)', function(elem) {
		var meun = $(elem).parent();
		if (!meun.is("li"))
			return;
		if (meun.hasClass('layui-nav-itemed')) {
			meun.siblings('li').find('dl').slideUp(120);
			meun.find('dl').hide().slideDown(120);
		} else {
			meun.find('dl').slideUp(120);
		}
	});
	$(document).on('click', function() {
		$('.layui-tab-title').find('li div').remove();
	});
	$('.flexible').on('click', function() {
		if ($('.site-mobile').is(':visible')) {
			$('.site-mobile-shade').show();
			$(".left_menu .addTab").one("click", function() {
				if ($('.site-mobile').is(':visible')) {
					$('.flexible i').removeClass('layui-icon-spread-left').addClass('layui-icon-shrink-right');
					$('.site-mobile-shade').hide();
					flexible();
				}
			});
		}
		flexible();
	});
});

function flexible(spead) {
	var tspead = spead ? spead : 250;
	if ($('.flexible i').hasClass('layui-icon-shrink-right')) { //隐藏菜单
		$('.left_menu').animate({
			width : 0
		}, tspead);
		$('.right_body').animate({
			left : 0
		}, tspead);
		$('.flexible i').removeClass('layui-icon-shrink-right').addClass('layui-icon-spread-left');
	} else if ($('.flexible i').hasClass('layui-icon-spread-left')) { //展开菜单
		$('.left_menu').animate({
			width : 200
		}, tspead);
		$('.right_body').animate({
			left : 200
		}, tspead);
		$('.flexible i').removeClass('layui-icon-spread-left').addClass('layui-icon-shrink-right');
	}
}
function app_flexible(spead) {
	if ($('.site-mobile').is(':visible')) {
		$('.flexible i').removeClass('layui-icon-spread-left').addClass('layui-icon-shrink-right');
	} else {
		$('.flexible i').removeClass('layui-icon-shrink-right').addClass('layui-icon-spread-left');
	}
	flexible(spead);
	$('.site-mobile-shade').hide();
}
$(function() {
	app_flexible();
	windowResize(function(){
		app_flexible(150);
	});
	$(".site-mobile-shade").click(function() {
		app_flexible();
	});
	weather();
})

//获取加密密码
function getEntryptPwd(pwd, publicKey) {
	if (!pwd || !publicKey || publicKey == '') {
		return pwd;
	}
	var encrypt = new JSEncrypt();
	encrypt.setPublicKey(publicKey);
	return encrypt.encrypt(pwd);
}

//获取天气
function weather() {
    var city='广州市';
    $('.weather').attr('title',city);
    $(".w1").text('');
    $(".w3").text('');
    $(".w4").text('');
	$.ajax({
		url : "getWeather?time=" + new Date().getTime(),
		data : "city="+city,
		type : "POST",
		dataType : "JSON",
		success : function(data) {
			if (data.status == '0') {
				$(".w1").text(data.temperature);
				$(".w3").text('℃');
				$(".w4").text(data.weather);
			}
		}
	});
}