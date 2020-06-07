//个人资料修改输入
function person_infoUpdateInput() {
    var index = 0;
    $.ajax({
        url : "person/infoUpdate?time=" + new Date().getTime(),
        beforeSend : function() {
            index = load();
        },
        success : function(data) {
            layer.open({
                type : 1,
                area : ['540px', 'auto'],
                title : '个人资料',
                maxmin : true,
                content : data,
                success : function(layero, index) {
                    $("#personInfoSubmit").click(function() {
                        person_infoUpdateSave(index, $(this));
                    });
                }
            });
        },
        error : function(XMLHttpRequest) {
            ajaxErrorTips(XMLHttpRequest, function() {
                msg("数据加载失败", 2);
            });
        },
        complete : function(XMLHttpRequest, textStatus) {
            loadClose(index);
            redirect(XMLHttpRequest, textStatus);
        }
    });
}

//个人资料修改保存
function person_infoUpdateSave(dialogIndex, submit) {
    if (!$("#infoUpdateForm").valid())
        return;
    var index = 0;
    $.ajax({
        url : "person/infoUpdateSave?time=" + new Date().getTime(),
        data : $("#infoUpdateForm").serialize(),
        type : "POST",
        dataType : "JSON",
        beforeSend : function() {
            submit.attr("disabled", "disabled");
            index = load();
        },
        success : function(data) {
            if (data.status == 0) {
                layer.close(dialogIndex);
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

//密码修改输入
function person_pswUpdateInput() {
    var index = 0;
    $.ajax({
        url : "person/pswUpdate?time=" + new Date().getTime(),
        beforeSend : function() {
            index = load();
        },
        success : function(data) {
            layer.open({
                type : 1,
                area : ['540px', 'auto'],
                title : '修改密码',
                maxmin : true,
                content : data,
                success : function(layero, index) {
                    $("#pswUpdateSubmit").click(function() {
                        person_pswUpdateSave(index, $(this));
                    });
                }
            });
        },
        error : function(XMLHttpRequest) {
            ajaxErrorTips(XMLHttpRequest, function() {
                msg("数据加载失败", 2);
            });
        },
        complete : function(XMLHttpRequest, textStatus) {
            loadClose(index);
            redirect(XMLHttpRequest, textStatus);
        }
    });
}

//密码修改保存
function person_pswUpdateSave(dialogIndex, submit) {
    if (!$("#pswUpdateForm").valid())
        return;
    var index = 0;
    var publicKey = $('#publicKey').val();
    $.ajax({
        url : "person/pswUpdateSave?time=" + new Date().getTime(),
        data : {
            'oldPassword' : getEntryptPwd($('#oldPassword').val(), publicKey),
            'newPassword' : getEntryptPwd($('#newPassword').val(), publicKey),
            'publicKey' : publicKey,
            'token' : $('#token').val()
        },
        type : "POST",
        dataType : "JSON",
        beforeSend : function() {
            submit.attr("disabled", "disabled");
            index = load();
        },
        success : function(data) {
            if (data.status == 0) {
                layer.close(dialogIndex);
                msg(data.msg, 1);
                if (type == '2')
                    window.location.reload();
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

//修改头像
function person_headImgUpdate() {
    var index = 0;
    $.ajax({
        url : "person/headImgUpdate?time=" + new Date().getTime(),
        beforeSend : function() {
            index = load();
        },
        success : function(data) {
            layer.open({
                type : 1,
                area : ['400px', 'auto'],
                title : '修改头像',
                content : data,
                btn : ['提交', '取消'],
                yes : function(index) {
                    person_headImgUpdateSave(index);
                }
            });
        },
        error : function(XMLHttpRequest) {
            ajaxErrorTips(XMLHttpRequest, function() {
                msg("数据加载失败", 2);
            });
        },
        complete : function(XMLHttpRequest, textStatus) {
            loadClose(index);
            redirect(XMLHttpRequest, textStatus);
        }
    });
}

//修改头像保存
function person_headImgUpdateSave(dialogIndex) {
    if (!$("#headImaUpdateForm").valid())
        return;
    var index = 0;
    $.ajax({
        url : "person/headImgUpdateSave?time=" + new Date().getTime(),
        data : "fileFileName=" + $("#headImgName").val(),
        dataType : "JSON",
        beforeSend : function() {
            index = load();
        },
        success : function(data) {
            if (data.status == 0) {
                $("#heasImg").attr("src", $("#headImgPreview").attr("src"));
                layer.close(dialogIndex);
                msg(data.msg, 1);
            } else {
                msg(data.msg, 2);
            }
        },
        error : function(XMLHttpRequest) {
            ajaxErrorTips(XMLHttpRequest, function() {
                msg("数据加载失败", 2);
            });
        },
        complete : function(XMLHttpRequest, textStatus) {
            loadClose(index);
            redirect(XMLHttpRequest, textStatus);
        }
    });
}