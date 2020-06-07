function showError(error, element) {
    showDefaultError(error, element);
}

function showDefaultError(error, element) {
    error.insertAfter(element);
    error.css("position", "absolute");
    var position = element.position();
    error.css("left", position.left + "px");
    error.css("top", position.top + element.outerHeight() + 1 + "px");
}

function showLayerError(error, element) {
    layer.tips(error[0].innerText, element, {
        tips : [2, 'red'],
        tipsMore : true,
        time : 2000000
    });
}

function remoteSyncCheck(url, data) {
    var result = false;
    $.ajax({
        url : url,
        data : data,
        async : false,
        success : function(data) {
            if (data == 'true')
                result = true;
        }
    });
    return result;
}

function remoteSyncCheckStatus(url, data) {
    var result = {
        "status" : 1
    };
    $.ajax({
        url : url,
        data : data,
        dataType : "JSON",
        async : false,
        success : function(data) {
                result = data;
        }
    });
    return result;
}
