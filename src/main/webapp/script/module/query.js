//在线用户
function query_online_list(page) {
    makeList(page, "onlineList", "queryOnlineForm", "mainOnlineList");
}

//在线删除
function query_online_delete(id) {
    if (id)
        deleteSingle("onlineDelete", id, query_online_list, "queryOnlineForm");
    else
        deleteAll("onlineDelete", query_online_list, "queryOnlineForm");
}

//登录记录
function query_loginRecord_list(page) {
    makeList(page, "loginRecordList", "queryLoginRecordForm", "mainLoginRecordList");
}

//登录记录删除
function query_loginRecord_delete(id) {
    if (id)
        deleteSingle("loginRecordDelete", id, query_loginRecord_list, "queryLoginRecordForm");
    else
        deleteAll("loginRecordDelete", query_loginRecord_list, "queryLoginRecordForm");
}

//用户车辆列表
function query_userCar_list(page) {
    makeList(page, "userCarList", "queryForm");
}
