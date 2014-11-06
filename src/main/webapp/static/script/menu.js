!function ($, window) {
    var addMenuClass = function (obj) {
        if (obj.hasClass("dropdown-submenu")) {
            obj.parent("ul").parent("li").addClass("active");
        } else {
            obj.addClass("active");
        }
    }


    $(function () {
        _.requireCss("css/style.css");
        addMenuClass($(
            "[data-href='"
            + History.getShortUrl(History.getLocationHref())
            + "']").parent());
        $("[data-func='nav']").delegate(
            "a",
            "click",
            function () {
                var $src = $(this);
                _.go($src.attr("data-href"), $src.attr(
                    "data-target"), null, function () {
                    $("ul.nav").find(".active").removeClass("active");
                    addMenuClass($src.parent());
                })
                ;
            })
    })
}(jQuery, window)
