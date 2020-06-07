$().ready(function() {
    $("#adminAddForm").validate({
        ignore : "",
        rules : {
            "name" : {
                required : true,
                maxlength : 20
            },
            "username" : {
                required : true,
                maxlength : 20,
                checkUserName : true
            },
            "role" : {
                required : true
            },
            "sex" : {
                required : true
            },
            "valid" : {
                required : true
            },
            "phone" : {
                required : true,
                maxlength : 12
            }
        },
        messages : {
            "name" : {
                required : "姓名不能为空",
                maxlength : "姓名长度不能大于20字符"
            },
            "username" : {
                required : "登录名不能为空",
                maxlength : "登录名长度不能大于20字符",
                checkUserName : "登陆名已存在"
            },
            "role" : {
                required : "选择用户角色"
            },
            "sex" : {
                required : "选择性别"
            },
            "valid" : {
                required : "选择是否启用"
            },
            "phone" : {
                required : "电话不能为空",
                maxlength : "电话的长度不能大于12个字符"
            }
        },
        errorPlacement : function(error, element) {
            if (element.attr("name") == 'role')
                element = element.parents("fieldset");
            if (element.attr("name") == "sex")
                element = $("#sex");
            if (element.attr("name") == "valid")
                element = $("#valid");
            showError(error, element);
        }
    });
});
jQuery.validator.addMethod("checkUserName", function(value, element) {
    var data = "username=" + $("#adminAddForm input[name='username']").val();
    return remoteSyncCheck("admin/checkUserName?flag=0", data);
}); 