$().ready(function() {
    $("#headImaUpdateForm").validate({
        ignore : "",
        rules : {
            "headImgName" : {
                required : true
            }
        },
        messages : {
            "headImgName" : {
                required : "请选择头像"
            }
        },
        errorPlacement : function(error, element) {
            if (element.attr("name") == 'headImgName')
                element = $("#selectFile")
            showError(error, element);
        }
    });
});
