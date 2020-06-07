function tipMenu(form){
     $(form+'.query-contextmenu').on('contextmenu', function(e) {
        if($(form+'input[type="text"]:focus').length!=0)
            return true;
        if ($(form+ '#tiptop').find('p').length == 0)
            return false;
        $(form+'#tiptop').show();
        var $width = $(form+'#tiptop').css('width');
        if (document.documentElement.clientWidth - e.clientX < parseInt($width) + 30) {
            $(form+'#tiptop').css({
                left : e.clientX + 8 - parseInt($width),
                top : e.clientY - 8
            });
        } else {
            $(form+'#tiptop').css({
                left : e.clientX - 8,
                top : e.clientY - 8
            });
        }
        return false;
    });
    $(form+'#tiptop').on('mouseleave', function() {
        $(form+'#tiptop').hide();
    });
    $(form+'#tiptop').on('contextmenu click mousedown', function() {
        return false;
    });
    $(form+'.table-contextmenu').on('contextmenu', 'tbody tr', function(e) {
        $('.tabletip').hide();
        if (hasSelect())
            return true;
        if ($(this).find('.tabletip').find('p').length == 0)
            return false;
        $(this).find('.tabletip').show();
        var $width = $(this).find('.tabletip').css('width');
        var $height = $(this).find('.tabletip').css('height');
        $(this).find('.tabletip').css({
            left : e.clientX - 8,
            top : e.clientY - 8
        });
        if (parseInt($width) > (document.documentElement.clientWidth - e.clientX - 30)) {
            $(this).find('.tabletip').css({
                left : e.clientX - parseInt($width) + 8
            });
        }
        if (parseInt($height) >= (document.documentElement.clientHeight - e.clientY - 10)) {
            $(this).find('.tabletip').css({
                top : e.clientY - parseInt($height) + 8
            });
        }
        return false;
    });
    //给tr绑定鼠标移出事件
    $(form+'.table-contextmenu').on('mouseleave', 'tbody tr', function() {
        $(".tabletip").hide();
    });
    $(form+".tabletip").on('contextmenu', function() {
        return false;
    });   
}
layui.use('form', function() {
    var form = layui.form;
    //全选
    form.on('checkbox(allChoose)', function(data) {
        var child = $(data.elem).parents('table').find('tbody input[type="checkbox"][lay-filter="singleChoose"]');
        child.each(function(index, item) {
            item.checked = data.elem.checked;
            if (item.checked)
                $(item).closest('tr').addClass('activetr');
            else
                $(item).closest('tr').removeClass('activetr');
        });
        form.render('checkbox');
    });
    form.on('checkbox(singleChoose)', function(data) {
        var parent = $(data.elem).parents('table').find('thead input[type="checkbox"][lay-filter="allChoose"]');
        if (!data.elem.checked) {
            parent[0].checked = false;
            $(data.elem).closest('tr').removeClass('activetr');
        } else {
            $(data.elem).closest('tr').addClass('activetr');
            var singleChooses = $(data.elem).parents('table').find('tbody input[type="checkbox"][lay-filter="singleChoose"]');
            parent[0].checked = true;
            singleChooses.each(function(index, item) {
                if (!item.checked) {
                    parent[0].checked = false;
                    return false;
                }
            });
        }
        form.render('checkbox');
    });
});
function hasSelect() {
    var text = "";
    if (window.getSelection) {
        text = window.getSelection();
    } else {
        text = document.selection.createRange().text;
    }
    return text != "";
}