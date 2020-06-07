$().ready(function() {
    $("#pswUpdateForm").validate({
        rules : {
            "oldPassword" : {
                required : true,
                maxlength : 20,
                checkOldPassword : true
            },
            "newPassword" : {
                required : true,
                maxlength : 20,
                minlength : 5,
            },
            "confirmPsw" : {
                required : true,
                maxlength : 20,
                equalTo : "#newPassword"
            }
        },
        messages : {
            "oldPassword" : {
                required : "当前密码不能为空",
                maxlength : "当前密码长度不能大于20字符"
            },
            "newPassword" : {
                required : "新密码不能为空",
                maxlength : "新密码长度不能大于20字符",
                minlength : "新密码长度不能小于5个字符"
            },
            "confirmPsw" : {
                required : "确认密码不能为空",
                maxlength : "确认密码长度不能大于20字符",
                equalTo : "两次输入的新密码不一样"
            }
        },
        errorPlacement : function(error, element) {
            showError(error, element);
        }
    });
});
jQuery.validator.addMethod("checkOldPassword", function(value, element) {
    var errorMsg = '';
    var result = false;
    var publicKey=$('#publicKey').val();
    $.ajax({
        url : "person/checkCurrentPsw?time=" + new Date().getTime(),
        data : {
            "oldPassword" : getEntryptPwd($('#oldPassword').val(),publicKey),
            "publicKey" : publicKey,
            "token" : $("#token").val()
        },
        dataType : "JSON",
        async : false,
        success : function(data) {
            if (data.status == 0)
                result = true;
            else
                errorMsg = data.msg;
        },
        error : function(XMLHttpRequest) {
            ajaxErrorTips(XMLHttpRequest, function() {
                errorMsg = '数据加载失败';
            });
        }
    });
    jQuery.validator.messages.checkOldPassword = errorMsg;
    return result;
}); 