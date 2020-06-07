$().ready(function() {
    $("#roleAddForm").validate({
        rules : {
            "name" : {
                required : true,
                maxlength : 20,
                remote : {
                    url : "checkRoleName?flag=0",
                    type : "post",
                    cache : false,
                    async : false
                }
            },
            "remark" : {
                maxlength : 100
            }
        },
        messages : {
            "name" : {
                required : "角色名称不能为空",
                maxlength : "角色名称长度不能大于20字符",
                remote : "角色名称已存在"
            },
            "remark" : {
                maxlength : "备注长度不能大于100个字符"
            }
        },
        errorPlacement : function(error, element) {
            showError(error, element);
        }
    });
});
