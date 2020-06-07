$('.flex-div').parent().on('click', function() {
    var ishidden = $(this).find('.flex-content').is(':hidden');
    $('.flex-div .flex-content').hide();
    $('.flex-div .flex-top').find('i').html('&#xe604;');
    if (ishidden) {
        $(this).find('.flex-content').show();
        $(this).find('i').html('&#xe624;');
    } else {
        $(this).find('i').html('&#xe604;');
    }
});
$('.flex-div').parents("tr").on('mouseleave', function() {
    $('.flex-div .flex-content').hide();
    $('.flex-div').find('i').html('&#xe604;');
});
$('.show-more').on('click', function() {
    showMore(this);
    return false;
});
function showMore(obj){
    $('.layui-table-tips').remove();
    var tips = $('<div class="layui-table-tips"><div class="layui-layer-content"><div class="layui-table-tips-main"></div></div><i class="iconfont layui-times layui-table-tips-close">&#xe612;</i></div>');
    if (trim($(obj).text()) == '')
        return;
    $(obj).parents('.layui-table').before(tips);
    tips.find('.layui-table-tips-close').on('click', function() {
        tips.remove();
    });
    tips.show().css({
        left : $(obj).position().left + 1,
        top : $(obj).position().top + 43,
    });
    tips.find('.layui-layer-content').css({
        'width' : $(obj).width() + 16,
        'line-height' : '20px',
        'overflow' : 'visible'
    });
    tips.find('.layui-table-tips-main').text($(obj).text());
    return false;
}
