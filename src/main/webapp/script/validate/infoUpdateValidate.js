$().ready(function() {
    $("#infoUpdateForm").validate({
        ignore: "",
        rules : {
            "name" : {
                required : true,
                maxlength : 20
            },
            "sex" : {
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
                maxlength : "姓名不能大于20个字符"
            },
            "sex" : {
                required : "选择性别"
            },
            "phone" : {
                required : "电话号码不能为空",
                maxlength : "电话号码不大于12个字符"
            }
        },
        errorPlacement : function(error, element) {
            if(element.attr("name") == "sex")
                element = $("#sex");
            showError(error, element);
        }
    });
});
