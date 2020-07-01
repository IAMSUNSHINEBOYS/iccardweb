$().ready(function() {
    $("#smartMerchantUpdateForm").validate({
        ignore : "",
        rules : {
            "smtMerchantName" : {
                required : true,
                maxlength : 50
            }
        },
        messages : {
            "smtMerchantName" : {
                required : "组织名称不能为空",
                maxlength : "长度不能大于50字符"
            }
        },
        errorPlacement : function(error, element) {
            showError(error, element);
        }
    });
});