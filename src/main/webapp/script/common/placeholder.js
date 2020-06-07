function placeholder() {
    var placeholder = 'placeholder' in document.createElement('input');
    if (!placeholder) {
        $("textarea,input[placeholder]").each(function() {
            var $input = $(this);
            var position = $input.position();
            var $div;
            if (!$input.next("div").hasClass("placeholder")) {
                $div = $("<div/>").text($input.attr('placeholder'));
                $label = $("<label/>");
                $div.addClass('placeholder');
                if ($input[0].readOnly) {
                    $input.attr('unselectable', 'on');
                    $input.after($div);
                } else if ($input.parent().hasClass("layui-select-title")) {
                    $input.after($div);
                } else {
                    $input.after($label).appendTo($label).after($div);
                }
                $div.css("left", position.left);
                $div.css("top", position.top);
                $div.css("height", $input.css("height"));
                $div.css("line-height", $input.css("line-height"));
                $div.css("padding-left", $input.css("padding-left"));
                $div.css("padding-right", $input.css("padding-right"));
                $div.css("padding-top", $input.css("padding-top"));
                $div.css("padding-bottom", $input.css("padding-bottom"));
                $div.css("margin-left", $input.css("margin-left"));
                $div.css("margin-right", $input.css("margin-right"));
                $div.css("margin-top", $input.css("margin-top"));
                $div.css("margin-bottom", $input.css("margin-bottom"));
            } else {
                $div = $input.next("div.placeholder");
            }
            $input.on("input propertychange blur focus", function() {
                if ($(this).val().length > 0)
                    $div.hide();
                else
                    $div.show();
            }).trigger('blur');
            
        });
    }
}