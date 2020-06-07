//系统用户列表
function sysManage_admin_list(page) {
    makeList(page, "adminList", "queryForm");
}

//系统用户删除
function sysManage_admin_delete(id) {
    if (id)
        deleteSingle("adminDelete", id, sysManage_admin_list);
    else
        deleteAll("adminDelete", sysManage_admin_list);
}

//系统用户添加输入
function sysManage_admin_addInput() {
    var index = 0;
    $.ajax({
        url : "adminAdd?time=" + new Date().getTime(),
        beforeSend : function() {
            index = load();
        },
        success : function(data) {
            top.layer.open({
                type : 1,
                area : ['630px', 'auto'],
                title : '添加系统用户',
                maxmin : true,
                content : data,
                success : function(layero, index) {
                    layero.find("#adminAddSubmit").click(function() {
                        var data = layero.find("#adminAddForm").serialize();
                        sysManage_admin_addSave(data, index, $(this));
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

//系统用户添加保存
function sysManage_admin_addSave(data, dialogIndex, submit) {
    if (!top.$("#adminAddForm").valid())
        return;
    var index = 0;
    $.ajax({
        url : "adminAddSave?time=" + new Date().getTime(),
        data : data,
        dataType : "JSON",
        beforeSend : function() {
            submit.attr("disabled", "disabled");
            index = load();
        },
        success : function(data) {
            if (data.status == 0) {
                loadClose(dialogIndex);
                sysManage_admin_list($("#currentPage").text());
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

//系统用户修改输入
function sysManage_admin_updateInput(id) {
    var index = 0;
    $.ajax({
        url : "adminUpdate?id=" + id + "&time=" + new Date().getTime(),
        beforeSend : function() {
            index = load();
        },
        success : function(data) {
            top.layer.open({
                type : 1,
                area : ['630px', '390px'],
                title : '修改系统用户',
                maxmin : true,
                content : data,
                success : function(layero, index) {
                    layero.find("#adminUpdateSubmit").click(function() {
                        var data = layero.find("#adminUpdateForm").serialize();
                        sysManage_admin_updateSave(data, index, $(this));
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

//系统用户修改保存
function sysManage_admin_updateSave(data, dialogIndex, submit) {
    if (!top.$("#adminUpdateForm").valid())
        return;
    var index = 0;
    $.ajax({
        url : "adminUpdateSave?time=" + new Date().getTime(),
        data : data,
        dataType : "JSON",
        beforeSend : function() {
            submit.attr("disabled", "disabled");
            index = load();
        },
        success : function(data) {
            if (data.status == 0) {
                loadClose(dialogIndex);
                sysManage_admin_list($("#currentPage").text());
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

//系统用户详情
function sysManage_admin_detail(id) {
    var index = 0;
    $.ajax({
        url : "adminDetail?id=" + id + "&time=" + new Date().getTime(),
        beforeSend : function() {
            index = load();
        },
        success : function(data) {
            top.layer.open({
                type : 1,
                area : ['500px', 'auto'],
                title : '系统用户详情',
                maxmin : true,
                content : data
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

//系统用户导出EXCEL
function sysManage_admin_toExcel() {
    postRequest($("#queryForm").serializeArray(), "toExcel?time=" + new Date().getTime());
}

//系统用户密码重置
function sysManage_admin_pswReset(id) {
    top.layer.confirm("确定要密码重置(123456)吗?", {
        icon : 3
    }, function(index) {
        loadClose(index);
        index = 0;
        $.ajax({
            url : "pswReset?id=" + id + "&time=" + new Date().getTime(),
            dataType : "JSON",
            beforeSend : function() {
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
                loadClose(index);
                redirect(XMLHttpRequest, textStatus);
            }
        });
    });
}

//系统用户记录弹出
function sysManage_admin_loginRecord(id, name) {
    var index = 0;
    $.ajax({
        url : "loginRecordList?adminId=" + id + "&time=" + new Date().getTime(),
        beforeSend : function() {
            index = load();
        },
        success : function(data) {
            layer.open({
                type : 1,
                area : ['100%', '100%'],
                title : '系统用户<i class="iconfont m">&#xe656;</i>登录记录<i class="iconfont m">&#xe656;</i>' + name,
                move : false,
                content : data,
                success : function(layero, index) {
                    full(index);
                    layer.tips('返回系统用户列表', '.layui-layer-setwin .layui-layer-close', {
                        tips : [3, '#3595CC'],
                    }, 6000);
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

//系统用户记录列表
function sysManage_admin_loginRecordList(page) {
    makeList(page, "loginRecordList", "dialogQueryForm", "dialogMainList");
}

//用户权限详情
function sysManage_admin_perms(id) {
    var index = 0;
    $.ajax({
        url : "adminPerms?id=" + id + "&time=" + new Date().getTime(),
        beforeSend : function() {
            index = load();
        },
        success : function(data) {
            top.layer.open({
                type : 1,
                area : ['350px', '500px'],
                title : '权限详情',
                maxmin : true,
                content : data
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

//角色权限
function sysManage_role_list(page) {
    makeList(page, "roleList", "queryForm");
}

//角色删除
function sysManage_role_delete(id) {
    if (id)
        deleteSingle("roleDelete", id, sysManage_role_list);
    else
        deleteAll("roleDelete", sysManage_role_list);
}

//角色添加输入
function sysManage_role_addInput() {
    var index = 0;
    $.ajax({
        url : "roleAdd?time=" + new Date().getTime(),
        beforeSend : function() {
            index = load();
        },
        success : function(data) {
            layer.open({
                type : 1,
                area : ['100%', '100%'],
                title : '角色权限<i class="iconfont m">&#xe604;</i>添加角色',
                move : false,
                content : data,
                success : function(layero, index) {
                    $("#roleAddSubmit").click(function() {
                        sysManage_role_addSave(index, $(this));
                    });
                    full(index);
                    layer.tips('返回角色权限列表', '.layui-layer-setwin .layui-layer-close', {
                        tips : [3, '#3595CC'],
                    }, 6000);
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

//角色添加保存
function sysManage_role_addSave(dialogIndex, submit) {
    if (!$("#roleAddForm").valid())
        return;
    var index = 0;
    var nodes = $.fn.zTree.getZTreeObj("ztree").getCheckedNodes(true);
    var data = $("#roleAddForm").serialize();
    for (var i = 0; i < nodes.length; i++) {
        if (nodes[i].id != 0)
            data += "&menu=" + nodes[i].id;
    }
    $.ajax({
        url : "roleAddSave?time=" + new Date().getTime(),
        data : data,
        dataType : "JSON",
        beforeSend : function() {
            submit.attr("disabled", "disabled");
            index = load();
        },
        success : function(data) {
            if (data.status == 0) {
                layer.close(dialogIndex);
                sysManage_role_list($("#currentPage").text());
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

//角色修改输入
function sysManage_role_updateInput(id) {
    var index = 0;
    $.ajax({
        url : "roleUpdate?id=" + id + "&time=" + new Date().getTime(),
        beforeSend : function() {
            index = load();
        },
        success : function(data) {
            layer.open({
                type : 1,
                area : ['100%', '100%'],
                title : '角色权限<i class="iconfont m">&#xe604;</i>修改角色',
                move : false,
                content : data,
                success : function(layero, index) {
                    $("#roleUpdateSubmit").click(function() {
                        sysManage_role_updateSave(index, $(this));
                    });
                    full(index);
                    layer.tips('返回角色权限列表', '.layui-layer-setwin  .layui-layer-close', {
                        tips : [3, '#3595CC'],
                    }, 6000);
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

//角色修改保存
function sysManage_role_updateSave(dialogIndex, submit) {
    if (!$("#roleUpdateForm").valid())
        return;
    var index = 0;
    var nodes = $.fn.zTree.getZTreeObj("ztree").getCheckedNodes(true);
    var data = $("#roleUpdateForm").serialize();
    for (var i = 0; i < nodes.length; i++) {
        if (nodes[i].id != 0)
            data += "&menu=" + nodes[i].id;
    }
    $.ajax({
        url : "roleUpdateSave?time=" + new Date().getTime(),
        data : data,
        dataType : "JSON",
        beforeSend : function() {
            submit.attr("disabled", "disabled");
            index = load();
        },
        success : function(data) {
            if (data.status == 0) {
                layer.close(dialogIndex);
                sysManage_role_list($("#currentPage").text());
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

//功能菜单
function sysManage_menu_check() {
    if ($("[name='name']").val() == "") {
        msg('菜单名称不能为空', 0);
        $("[name='name']")[0].focus();
        return false;
    }
    return true;
}

//功能菜单输入
function sysManage_menu_put(treeNode) {
    layui.use('form', function() {
        var form = layui.form;
        if (treeNode.menuType == '2') {
            $("#menuUrl").show();
            $("#perms").show();
            $("#icon").show();
        } else if (treeNode.menuType == '0') {
            $("#menuUrl").hide();
            $("#perms").show();
            $("#icon").hide();
        } else if (treeNode.menuType == '1') {
            $("#menuUrl").hide();
            $("#perms").hide();
            $("#icon").show();
        }
        $("[name='id']").val(treeNode.id);
        $("[name='parent']").val(treeNode.pId);
        $("[name='name']").val(treeNode.name).trigger('blur');
        $("[name='perms']").val(treeNode.perms).trigger('blur');
        $("[name='orderNo']").val(treeNode.orderNo).trigger('blur');
        $("[name='icon']").val(treeNode.micon).trigger('blur');
        $("[name='menuUrl']").val(treeNode.menuUrl).trigger('blur');
        $("[name='menuType'][value=" + treeNode.menuType + "]").prop("checked", "checked");
        $("[name='enable'][value=" + treeNode.enable + "]").prop("checked", "checked");
        form.render();
    });
}

//菜单改变
function sysManage_menu_menuChange(value) {
    if (value == '2') {
        $("#menuUrl").show();
        $("#perms").show();
        $("#icon").show();
    } else if (value == '0') {
        $("#menuUrl").hide();
        $("#perms").show();
        $("#icon").hide();
    } else if (value == '1') {
        $("#menuUrl").hide();
        $("#perms").hide();
        $("#icon").show();
    }
}

//获取数据
function sysManage_menu_getData() {
    var form = $("#menuForm");
    var value = $("[name='menuType']:checked").val();
    if (value == '2') {
        $("#menuUrl").show();
        $("#perms").show();
        $("#icon").show();
    } else if (value == '0') {
        $("#menuUrl").hide();
        $("#perms").show();
        $("#icon").hide();
        form = $("#menuForm :not([name='icon'],[name='menuUrl'])");
    } else if (value == '1') {
        $("#menuUrl").hide();
        $("#perms").hide();
        $("#icon").show();
        form = $("#menuForm :not([name='perms'],[name='menuUrl'])");
    }
    return form.serialize();
}

//功能菜单添加
function sysManage_menu_add() {
    if (sysManage_menu_check()) {
        var index = 0;
        var data = "";
        $.ajax({
            url : "menuAdd?time=" + new Date().getTime(),
            data : sysManage_menu_getData(),
            dataType : "JSON",
            beforeSend : function() {
                $("#menuAdd").attr("disabled", "disabled");
                index = load();
            },
            success : function(data) {
                if (data.status == 0) {
                    msg(data.msg, 1);
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
                $("#menuAdd").removeAttr("disabled");
                loadClose(index);
                redirect(XMLHttpRequest, textStatus);
            }
        });
    }
}

//功能菜单删除
function sysManage_menu_delete() {
    if ($("[name='id']").val() == "") {
        msg('选择要删除的菜单', 0);
        return false;
    }
    top.layer.confirm("确定要删除吗？", {
        icon : 3
    }, function(index) {
        loadClose(index);
        if (!sysManage_menu_check())
            return false;
        var index = 0;
        $.ajax({
            url : "menuDelete?time=" + new Date().getTime(),
            data : $("#menuForm").serialize(),
            dataType : "JSON",
            beforeSend : function() {
                $("#menuDelete").attr("disabled", "disabled");
                index = load();
            },
            success : function(data) {
                if (data.status == 0) {
                    msg(data.msg, 1);
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
                $("#menuDelete").removeAttr("disabled");
                loadClose(index);
                redirect(XMLHttpRequest, textStatus);
            }
        });
    });
}

//功能菜单修改
function sysManage_menu_update() {
    if ($("[name='id']").val() == "") {
        msg('选择要修改的菜单', 0);
        return false;
    }
    if (!sysManage_menu_check())
        return false;
    var index = 0;
    $.ajax({
        url : "menuUpdate?time=" + new Date().getTime(),
        data : sysManage_menu_getData(),
        dataType : "JSON",
        beforeSend : function() {
            $("#menuUpdate").attr("disabled", "disabled");
            index = load();
        },
        success : function(data) {
            if (data.status == 0) {
                msg(data.msg, 1);
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
            $("#menuUpdate").removeAttr("disabled");
            loadClose(index);
            redirect(XMLHttpRequest, textStatus);
        }
    });
}

//功能用户列表
function sysManage_menu_menuAdminInfo(id, path) {
    var index = 0;
    var data = "id=" + id + "&pager.currentPage=1";
    $.ajax({
        url : "menuAdminInfo?time=" + new Date().getTime(),
        data : data,
        beforeSend : function() {
            index = load();
        },
        success : function(data) {
            top.layer.open({
                type : 1,
                area : '500px',
                title : '用户' + path,
                maxmin : true,
                content : data
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

//功能用户列表分页
function sysManage_menu_menuAdminInfoPage(page, id) {
    var index = 0;
    var data = "id=" + id + "&flag=1&pager.currentPage=" + page;
    $.ajax({
        url : contextPath + "/admin/menu/menuAdminInfo?time=" + new Date().getTime(),
        data : data,
        beforeSend : function() {
            index = load();
        },
        success : function(data) {
            $("#menuAdminInfo").html(data);
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

//功能角色列表
function sysManage_menu_menuRoleInfo(id, path) {
    var index = 0;
    var data = "id=" + id + "&pager.currentPage=1";
    $.ajax({
        url : "menuRoleInfo?time=" + new Date().getTime(),
        data : data,
        beforeSend : function() {
            index = load();
        },
        success : function(data) {
            top.layer.open({
                type : 1,
                area : '500px',
                title : '角色' + path,
                maxmin : true,
                content : data
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

//功能角色列表分页
function sysManage_menu_menuRolePage(page, id) {
    var index = 0;
    var data = "id=" + id + "&flag=1&pager.currentPage=" + page;
    $.ajax({
        url : contextPath + "/admin/menu/menuRoleInfo?time=" + new Date().getTime(),
        data : data,
        beforeSend : function() {
            index = load();
        },
        success : function(data) {
            $("#menuRoleInfo").html(data);
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