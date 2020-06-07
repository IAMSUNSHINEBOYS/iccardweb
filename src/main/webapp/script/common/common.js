$.ajaxSetup({
	type : "POST",
	complete : function(XMLHttpRequest, textStatus) {
		redirect(XMLHttpRequest, textStatus);
	}
});

function redirect(XMLHttpRequest, textStatus) {
	var status = XMLHttpRequest.getResponseHeader("status");
	var redirectUrl = XMLHttpRequest.getResponseHeader("redirectUrl");
	if (status == "timeout") {
		window.top.location.replace(redirectUrl);
	} else if (status == "disable") {
		window.top.location.replace(redirectUrl);
	}
}

function makeList(page, uri, form, mainList) {
	var argLen = arguments.length;
	page = page > 0 ? page : 1;
	if (page > 0) {
		layui.use('layer', function() {
			var data = $("#" + form).find(":not([data-ignore])").serialize();
			data += "&pager.currentPage=" + page;
			uri = uri + "?flag=1&time=" + new Date().getTime();
			var index = 0;
			$.ajax({
				url : uri,
				data : data,
				beforeSend : function() {
					index = load();
				},
				success : function(data) {
					if (argLen == 4) {
						$("#" + mainList).html(data);
					} else
						$("#mainList").html(data);
				},
				error : function(XMLHttpRequest) {
					ajaxErrorTips(XMLHttpRequest, function() {
						msg('获取数据失败', 2);
					});
				},
				complete : function(XMLHttpRequest, textStatus) {
					loadClose(index);
					redirect(XMLHttpRequest, textStatus);
				}
			});
		});
	}
}

function deleteAll(uri, fun, parent) {
	var p = parent ? ("#" + parent + " ") : "";
	var len = $(p + "input:checked[name='ids']").length;
	if (!len > 0) {
		msg('选择要删除的数据', 0);
	} else {
		var confirmMsg = "确定删除选中的<span>" + len + "</span>条数据吗？";
		top.layer.confirm(confirmMsg, {
			icon : 3
		}, function(index) {
			loadClose(index);
			index = 0;
			$.ajax({
				url : uri,
				data : $(p + "input:checked[name='ids']").serialize(),
				dataType : "JSON",
				beforeSend : function() {
					index = load();
				},
				success : function(data) {
					if (data.status == 0) {
						fun($(p + ".currentPage").text());
						msg(data.msg, 1);
					} else {
						msg(data.msg, 2);
					}
				},
				error : function(XMLHttpRequest) {
					ajaxErrorTips(XMLHttpRequest, function() {
						msg('删除失败', 2);
					});
				},
				complete : function(XMLHttpRequest, textStatus) {
					loadClose(index);
					redirect(XMLHttpRequest, textStatus);
				}
			});
		});
	}
}

function deleteSingle(uri, id, fun, parent) {
	var p = parent ? ("#" + parent + " ") : "";
	top.layer.confirm("确定删除选中的数据吗？", {
		icon : 3
	}, function(index) {
		loadClose(index);
		index = 0;
		$.ajax({
			url : uri,
			data : "ids=" + id,
			dataType : "JSON",
			beforeSend : function() {
				index = load();
			},
			success : function(data) {
				if (data.status == 0) {
					fun($(p + ".currentPage").text());
					msg(data.msg, 1);
				} else {
					msg(data.msg, 2);
				}
			},
			error : function(XMLHttpRequest) {
				ajaxErrorTips(XMLHttpRequest, function() {
					msg('删除失败', 2);
				});
			},
			complete : function(XMLHttpRequest, textStatus) {
				loadClose(index);
				redirect(XMLHttpRequest, textStatus);
			}
		});
	});
}

function ajaxErrorTips(XMLHttpRequest, fun) {
	if (XMLHttpRequest.status != '800')
		fun();
}

function msg(content, type, fun) {
	top.layer.msg(content, {
		icon : type,
		time : 1000, //1秒
		zIndex : 666666666
	}, function() {
		if ( typeof fun === "function")
			fun();
	});
}

function load(tips, time) {
	var index = top.layer.msg( tips ? tips : '数据加载中', {
		icon : 16,
		maxWidth : 145,
		shade : 0.01,
		time : ( time ? time : 10) * 1000, // 默认10秒
		zIndex : 666666666
	});
	return index;
}

function loadClose(index) {
	top.layer.close(index);
}

function showImg(obj) {
	top.layer.photos({
		photos : {
			title : false,
			data : [{
				"src" : obj.src
			}]
		},
		imgsee : false,
		shade : 0.01,
		closeBtn : 1,
		anim : 5
	});
}

function postRequest(data, url) {
	var index = load();
	var form = $("<form style='display:none'></form>");
	form.attr("action", url).attr("method", "post").attr("target", "_blank");
	$.each(data, function(i, field) {
		form.append('<input type="hidden" name="' + field.name + '" value="' + field.value + '"/>');
	});
	form.appendTo("body").submit().remove();
	loadClose(index);
}

function full(index) {
	layer.rescollbar(index);
}

function order(tableList, form, page) {
	var thOrder = $("#" + tableList + " th[data-order]");
	thOrder.append('<span class="switch-top-bottom"><i class="row-top"></i><i class="row-bottom"></i></span>');
	$("#" + tableList + " th i").removeClass('active');
	var order = $("#" + form + " [name='order']");
	var field = $("#" + form + " [name='field']");
	var bottom = $("#" + tableList + " th[data-order='" + field.val() + "'] .row-bottom");
	var top = $("#" + tableList + " th[data-order='" + field.val() + "'] .row-top");
	if (order.val() == '0') {//降序
		bottom.addClass('active');
	} else if (order.val() == '1') {//升序
		top.addClass('active');
	}
	thOrder.click(function() {
		var that = $(this);
		if (that.find('.row-top').hasClass('active')) {
			order.val(2);//取消排序
		} else if (that.find('.row-bottom').hasClass('active')) {
			order.val(1);//变为升序
		} else {
			order.val(0);//变为降序
		}
		field.val(that.data('order'));
		page();
	});
}

function getJsonData(form) {
	var data = {};
	$.each($("#" + form).serializeArray(), function(i, field) {
		data[field.name] = field.value;
	});
	return data;
}

function readIDCard() {
	var result = {
		"status" : 1,
		"msg" : "读取身份证信息失败"
	};
	var com = IDCard.FindReader();
	if (com > 0) {
		//设置出生日期格式：yyyy-MM-dd
		IDCard.SetBornType(3);
		var cardMsg = IDCard.ReadCardMsg();
		if (cardMsg == 0) {
			result["status"] = 0;
			result["name"] = trim(IDCard.NameA);
			result["idNum"] = IDCard.CardNo;
			result["sex"] = IDCard.Sex;
			result["born"] = IDCard.Born;
			result["address"] = trim(IDCard.Address);
			result["photo"] = encodeURIComponent(IDCard.Base64Photo);
		}
	} else {
		result["msg"] = "没有找到身份证读卡器";
	}
	return result;
}

function trim(str) {
	return $.trim(str);
};

function getName(name, form) {
	var obj;
	if (form)
		obj = $("#" + form + " [name='" + name + "']");
	else
		obj = $("[name='" + name + "']");
	return obj;
};

function subs(str, begin, len) {
	return str.substr(begin, len);
}

function getData(info, index) {
	if (info == null)
		return null;
	var ar = info.split(",");
	if (index >= ar.length || index == -1) {
		return ar[ar.length - 1];
	}
	return ar[index];
}

function findCard() {
	var result = {
		"status" : 1
	};
	try {
		var cardInfo = top.cardReader.findCard();
		if (getData(cardInfo, 0) == '0') {
			var type = getData(cardInfo, -1);
			result["type"] = type;
			if (type == '0' || type == '8') {//未初始化的IC卡
				result["status"] = 2;
				result["phyNum"] = getData(cardInfo, 1);
				result["cardInfo"] = cardInfo;
				result["msg"] = "卡未初始化";
			} else if (type == '1' || type == '4' || type == '9') {//已初始化的IC卡
				result["status"] = 0;
				result["cardNum"] = getData(cardInfo, 3);
				result["phyNum"] = getData(cardInfo, 1);
				result["hashCode"] = getData(cardInfo, 2);
				result["cardInfo"] = cardInfo;
				checkKeys(result.phyNum + "," + result.hashCode);
			} else if (type == '7') {//株洲银联卡
				result["status"] = 0;
			} else {
				result["msg"] = "不支持的卡类型";
			}
		} else {
			result["msg"] = "读卡失败";
		}
	} catch (e) {
		result["msg"] = "读卡异常";
	}
	return result;
}

function activeBikeApp(appInfo, appData) {
	var result = {
		"status" : 1
	};
	try {
		var active = top.cardReader.ActiveBikeApp(appInfo, appData);
		if (getData(active, 0) == '0') {
			result["status"] = 0;
		} else {
			result["msg"] = "激活失败";
		}
	} catch (e) {
		result["msg"] = "激活异常";
	}
	return result;
}

function findAppData() {
	var result = {
		"status" : 1
	};
	try {
		var appData = top.cardReader.ReadBikeAppData();
		if (getData(appData, 0) == '0') {
			var cardType = getData(appData, 1);
			result["cardType"] = cardType.length > 1 ? cardType : '0' + cardType;
			result["status"] = 0;
		} else
			result["msg"] = "读应用失败";
	} catch (e) {
		result["msg"] = "读应用异常";
	}
	return result;
}

function findCardInfo(type) {
	var result = {
		"status" : 1
	};
	try {
		var cardData = top.cardReader.ReadCardData();
		if (getData(cardData, 0) == '0') {
			if (type == '7') {//株洲银联卡
				result["status"] = 0;
				result["cardNum"] = getData(cardData, 2);
			} else {
				result["msg"] = "读卡信息不支持";
			}
		} else {
			result["msg"] = "读卡信息失败";
		}
	} catch (e) {
		result["msg"] = "读卡信息异常";
	}
	return result;
}

function checkKeys(cardInfo) {
	try {
		$.ajax({
			url : contextPath + "/admin/cardManage/getKeys.do?time=" + new Date().getTime(),
			data : "cardInfo=" + cardInfo,
			dataType : "JSON",
			async : false,
			success : function(data) {
				if (data.status == '0') {
					var keys = top.cardReader.GetKeys(data.keya);
					if (getData(keys, 0) != '0')
						msg("获取密钥失败", 2);
				}
			},
			error : function(XMLHttpRequest) {
				ajaxErrorTips(XMLHttpRequest, function() {
					msg("获取密钥数据失败", 2);
				});
			},
			complete : function(XMLHttpRequest, textStatus) {
				redirect(XMLHttpRequest, textStatus);
			}
		});
	} catch (e) {
		msg("获取密钥异常", 2);
	}
	return result;

	var keys = top.cardReader.GetKeys();
	if (getData(appData, 0) == '0') {
		appData = getData(appData, 1);
		result["cardType"] = subs(appData, 10, 2);
		result["status"] = 0;
	} else
		result["msg"] = "读应用失败:" + getData(appData, 1);

}

function eraseCard(cardInfo) {
	var result = {
		"status" : 1
	};
	try {
		var erase = top.cardReader.EraseCard(cardInfo);
		if (getData(erase, 0) == '0') {
			result["status"] = 0;
			result["msg"] = "还原成功";
		} else
			result["msg"] = "还原失败:" + getData(erase, 1);
	} catch (e) {
		result["msg"] = "还原异常";
	}
	return result;
}

function initCard(formatInfo, cardData) {
	var result = {
		"status" : 1
	};
	try {
		var format = top.cardReader.FormatCard(formatInfo, cardData);
		if (getData(format, 0) == '0') {
			result["status"] = 0;
			result["msg"] = "初始化成功";
		} else {
			result["msg"] = "初始化失败:" + getData(format, 1);
		}
	} catch (e) {
		result["msg"] = "初始化异常";
	}
	return result;
}

function timeTypeChange(type) {
	var startTime = $("#startTime");
	var endTime = $("#endTime");
	startTime.val("");
	endTime.val("");
	var timeType = $("#timeType").val();
	var cday;
	var cmonth;
	if (type == 1) {
		cday = function() {
			WdatePicker({
				dateFmt : 'yyyy-MM-dd',
				maxDate : '%y-%M-%d'
			});
		};
		cmonth = function() {
			WdatePicker({
				dateFmt : 'yyyy-MM',
				maxDate : '%y-%M'
			});
		};
	} else {
		cday = function() {
			WdatePicker({
				dateFmt : 'yyyy-MM-dd',
				maxDate : '%y-%M-{%d-1}'
			});
		};
		cmonth = function() {
			WdatePicker({
				dateFmt : 'yyyy-MM',
				maxDate : '%y-{%M-1}'
			});
		};
	}
	startTime.off("click focus");
	endTime.off("click focus");
	if (timeType == 2) {
		startTime.on("click focus",function(){
		    cday();
		});
		endTime.on("click focus",function(){
		    cday();
		});
	} else if (timeType == 1) {
	    startTime.on("click focus",function(){
            cmonth();
        });
        endTime.on("click focus",function(){
            cmonth();
        });
	}
}

function tabAddBadge(data, fun) {
	var tab = top.$("li.layui-this span");
	if (data == '' || data == 0) {
		tab.find(".layui-badge").remove();
	} else {
		if (tab.find(".layui-badge").length > 0) {
			tab.find(".layui-badge").text(data);
		} else {
			tab.append('<span class="layui-badge">' + data + '</span>');
		}
		tab.find(".layui-badge").unbind('click').click(function() {
			fun();
		});
	}
}

function tabInit(filter) {
	layui.use(['element', 'layer'], function() {
		$(".layui-tab-title>li").first().addClass("layui-this");
		$(".layui-tab-item").first().addClass("layui-show").addClass("inited");
		var showElem = $(".layui-tab-item.layui-show");
		if (showElem.hasClass("inited") && showElem.attr("data-url")) {
			tabInitData(showElem);
		}
		var element = layui.element;
		element.on('tab(' + filter + ')', function(data) {
			var changeElem = $(".layui-tab-item").eq(data.index);
			if (!changeElem.hasClass("inited") && changeElem.attr("data-url")) {
				tabInitData(changeElem);
			}
			changeElem.addClass("inited");
		});
	});
}

function tabInitData(elem) {
	var index = 0;
	$.ajax({
		url : elem.attr("data-url") + "?time=" + new Date().getTime(),
		beforeSend : function() {
			if (elem.hasClass("load"))
				index = load();
		},
		success : function(data) {
			elem.html(data);
		},
		error : function(XMLHttpRequest) {
			ajaxErrorTips(XMLHttpRequest, function() {
				msg('获取数据失败', 2);
			});
		},
		complete : function(XMLHttpRequest, textStatus) {
			loadClose(index);
			redirect(XMLHttpRequest, textStatus);
		}
	});
}

function tabRefresh() {
	var showElem = $(".layui-tab-item.layui-show");
	if (showElem.hasClass("inited") && showElem.attr("data-url")) {
		tabInitData(showElem);
	}
}

function getThisIframe() {
	var thisIndex = $('.layui-tab-title li.layui-this').index();
	return $(".iframe-tab").eq(thisIndex);
}

function windowResize(callback) {
    window.onresize = function() {
        var target = this;
        if (target.resizeFlag) {
            clearTimeout(target.resizeFlag);
        }
        target.resizeFlag = setTimeout(function() {
            callback();
            target.resizeFlag = null;
        }, 100);
    }
}