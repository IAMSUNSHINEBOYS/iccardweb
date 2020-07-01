//商户组织列表
function merchant_shsMerchant_list(page) {
    makeList(page, "smartMerchanttList", "queryForm");
}

//商户删除
function merchant_smartMerchant_delete(id) {
    top.layer.confirm("确定删除选中的数据吗？", {
        icon : 3
    }, function(index) {
        loadClose(index);
        index = 0;
        $.ajax({
            url : "smartMerchantDelete?merchantId="+id+"&time=" + new Date().getTime(),
            dataType : "JSON",
            beforeSend : function() {
                index = load();
            },
            success : function(data) {
                if (data.status == 0) {
                    location.reload();
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

//商户添加输入(监听是否选中父ID)
function merchant_smartMerchant_addInput(merchantId) {
    var index = 0;
    $.ajax({
        url : "smartMerchantAdd?merchantId="+merchantId+"&time=" + new Date().getTime(),
        beforeSend : function() {
            index = load();
        },
        success : function(data) {
            top.layer.open({
                type : 1,
                area : ['565px', 'auto'],
                title : '添加商户',
                maxmin : true,
                content : data,
                success : function(layero, index) {
                    layero.find("#smartMerchantAddSubmit").click(function() {
                        var data = layero.find("#smartMerchantAddForm").serialize();
                        merchant_smartMerchant_addSave(data, index, $(this));
                    });
                    layero.find("#continueAddSubmit").click(function() {
                        var data = layero.find("#smartMerchantAddForm").serialize();
                        merchant_smartMerchant_continueAddSave(data, index, $(this));
                    });
                }
            });
        },
        error : function(XMLHttpRequest) {
            ajaxErrorTips(XMLHttpRequest, function() {
                msg("获取加载失败", 2);
            });
        },
        complete : function(XMLHttpRequest, textStatus) {
            loadClose(index);
            redirect(XMLHttpRequest, textStatus);
        }
    });
}

//商户添加保存
function merchant_smartMerchant_addSave(data, dialogIndex, submit) {
    if (!top.$("#smartMerchantAddForm").valid())
        return;
    var index = 0;
    $.ajax({
        url : "smartMerchantAddSave?time=" + new Date().getTime(),
        data : data,
        dataType : "JSON",
        beforeSend : function() {
            submit.attr("disabled", "disabled");
            index = load();
        },
        success : function(data) {
            if (data.status == 0) {
                loadClose(dialogIndex);
                location.reload();
                msg(data.msg, 1);
            } else {
                msg(data.msg, 2);
            }
        },
        error : function(XMLHttpRequest) {
            ajaxErrorTips(XMLHttpRequest, function() {
                msg('数据加载失败', 2);
            });
        },
        complete : function(XMLHttpRequest, textStatus) {
            submit.removeAttr("disabled");
            loadClose(index);
            redirect(XMLHttpRequest, textStatus);
        }
    });
}

//商户添加保存（继续添加）
function merchant_smartMerchant_continueAddSave(data, dialogIndex, submit) {
    if (!top.$("#smartMerchantAddForm").valid())
        return;
    var index = 0;
    $.ajax({
        url : "smartMerchantAddSave?time=" + new Date().getTime(),
        data : data,
        dataType : "JSON",
        beforeSend : function() {
            submit.attr("disabled", "disabled");
            index = load();
        },
        success : function(data) {
            if (data.status == 0) {
                msg(data.msg, 1);
            } else {
                msg(data.msg, 2);
            }
        },
        error : function(XMLHttpRequest) {
            ajaxErrorTips(XMLHttpRequest, function() {
                msg('数据加载失败', 2);
            });
        },
        complete : function(XMLHttpRequest, textStatus) {
            submit.removeAttr("disabled");
            loadClose(index);
            redirect(XMLHttpRequest, textStatus);
        }
    });
}

//商户修改输入
function merchant_smartMerchant_updateInput(merchantId) {
    var index = 0;
    $.ajax({
        url : "smartMerchantUpdate?merchantId="+merchantId+"&time=" + new Date().getTime(),
        beforeSend : function() {
            index = load();
        },
        success : function(data) {
            top.layer.open({
                type : 1,
                area : ['565px', 'auto'],
                title : '修改商户',
                maxmin : true,
                content : data,
                success : function(layero, index) {
                    layero.find("#smartMerchantUpdateSubmit").click(function() {
                        var data = layero.find("#smartMerchantUpdateForm").serialize();
                        merchant_smartMerchant_updateSave(data, index, $(this));
                    });
                }
            });
        },
        error : function(XMLHttpRequest) {
            ajaxErrorTips(XMLHttpRequest, function() {
                msg("获取加载失败", 2);
            });
        },
        complete : function(XMLHttpRequest, textStatus) {
            loadClose(index);
            redirect(XMLHttpRequest, textStatus);
        }
    });
}

//商户修改保存
function merchant_smartMerchant_updateSave(data, dialogIndex, submit) {
    if (!top.$("#smartMerchantUpdateForm").valid())
        return;
    var index = 0;
    $.ajax({
        url : "smartMerchantUpdateSave?time=" + new Date().getTime(),
        data : data,
        dataType : "JSON",
        beforeSend : function() {
            submit.attr("disabled", "disabled");
            index = load();
        },
        success : function(data) {
            if (data.status == 0) {
                loadClose(dialogIndex);
                location.reload();
                msg(data.msg, 1);
            } else {
                msg(data.msg, 2);
            }
        },
        error : function(XMLHttpRequest) {
            ajaxErrorTips(XMLHttpRequest, function() {
                msg('数据加载失败', 2);
            });
        },
        complete : function(XMLHttpRequest, textStatus) {
            submit.removeAttr("disabled");
            loadClose(index);
            redirect(XMLHttpRequest, textStatus);
        }
    });
}

//商户信息列表
function merchant_smartTerminal_list(page) {
    makeList(page, "smartTerminalList", "queryForm");
}

//设备信息输入(监听是否选中父ID)
function merchant_smartTerminal_addInput() {
    var index = 0;
    $.ajax({
        url : "smartTerminalAdd?time=" + new Date().getTime(),
        beforeSend : function() {
            index = load();
        },
        success : function(data) {
            layer.open({
                type : 1,
                area : ['100%', '100%'],
                title : '设备信息<i class="iconfont m">&#xe604;</i>添加设备',
                move : false,
                content : data,
                success : function(layero, index) {
                    $("#roleAddSubmit").click(function() {
                        merchant_smartTerminal_addSave(index, $(this));
                    });
                    full(index);
                    layer.tips('返回设备信息列表', '.layui-layer-setwin .layui-layer-close', {
                        tips : [3, '#3595CC'],
                    }, 6000);
                }
            });
        },
        error : function(XMLHttpRequest) {
            ajaxErrorTips(XMLHttpRequest, function() {
                msg("获取加载失败", 2);
            });
        },
        complete : function(XMLHttpRequest, textStatus) {
            loadClose(index);
            redirect(XMLHttpRequest, textStatus);
        }
    });
}

//设备信息保存
function merchant_smartTerminal_addSave(data, dialogIndex, submit) {
    if (!$("#roleAddForm").valid())
        return;
    var index = 0;
    var data = $("#smartTerminalAddForm").serialize();
    $.ajax({
        url : "smartTerminalAddSave?time=" + new Date().getTime(),
        data : data,
        dataType : "JSON",
        beforeSend : function() {
            submit.attr("disabled", "disabled");
            index = load();
        },
        success : function(data) {
            if (data.status == 0) {
                layer.close(dialogIndex);
                merchant_smartTerminal_list($("#currentPage").text());
                msg(data.msg, 1);
            } else {
                msg(data.msg, 2);
            }
        },
        error : function(XMLHttpRequest) {
            ajaxErrorTips(XMLHttpRequest, function() {
                msg('数据加载失败', 2);
            });
        },
        complete : function(XMLHttpRequest, textStatus) {
            submit.removeAttr("disabled");
            loadClose(index);
            redirect(XMLHttpRequest, textStatus);
        }
    });
}
