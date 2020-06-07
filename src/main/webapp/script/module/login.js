$(function() {
    placeholder();
    if ($("#tip").text() != '')
        $("#tip").show();
    setTimeout(function() {
        $("#tip").hide();
        $("#tip").empty();
    }, 2000);
    $(document).keydown(function(e) {
        if (e.keyCode == 13) {
            $("#loginBtn").click();
        }
    });
});
function login() {
    var $tip = $('#tip');
    if ($('#username').val().length <= 0) {
        $.tip('用户名不能为空', $tip);
    } else if ($('#password').val().length <= 0) {
        $.tip('密码不能为空', $tip);
    } else if ($('#checkCode').val().length <= 0) {
        $.tip('验证码不能为空', $tip);
    } else {
    	var publicKey=$('#publicKey').val();
        $.ajax({
            url : "adminLogin?time=" + new Date().getTime(),
            data : {
                'username' : $('#username').val(),
                'password' : getEntryptPwd($('#password').val(),publicKey),
                'checkCode' : $('#checkCode').val(),
                'publicKey' : publicKey,
                'token' : $('#token').val()
            },
            type : "post",
            dataType : "json",
            beforeSend : function() {
                disableLoginBtn();
            },
            success : function(data) {
                if (data.status == 0) {// 登录成功
                    flag = true;
                    $tip.empty();
                    window.top.location.href = "admin/index";
                } else {
                    $.tip(data.msg, $tip);
                    if (data.status == 1){// 验证码错误
                        $("#checkCode").focus();
                        $("#code").click();
                    }
                    enableLoginBtn();
                }
            },
            error : function(XMLHttpRequest, textStatus, errorThrown) {
                $.tip("系统错误", $tip);
                enableLoginBtn();
            },
        });
    }
}

$.tip = function(msg, tip) {
    var t;
    tip.show().text(msg);
    clearTimeout(t);
    t = setTimeout(function() {
        tip.empty();
    }, 2000);
};
function reload(obj) {
    obj.src = "randomCode?time=" + new Date().getTime();
}

function disableLoginBtn() {
    $("#loginBtn").attr("disabled", "disabled").val("正在登录");
}

function enableLoginBtn() {
    $("#loginBtn").removeAttr("disabled").val("登录");
}


function getEntryptPwd(pwd, publicKey) {
	if (!pwd || !publicKey || publicKey == '') {
		return pwd;
	}
	var encrypt = new JSEncrypt();
	encrypt.setPublicKey(publicKey);
	return encrypt.encrypt(pwd);
}
