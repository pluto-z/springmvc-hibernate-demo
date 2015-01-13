!function ($, window) {
    var addMenuClass = function (obj) {
        if (obj.length == 0) return;
        $("ul.nav").find(".active").removeClass("active");
        if (obj.hasClass("dropdown-submenu")) {
            obj.parent("ul").parent("li").addClass("active");
        } else {
            obj.addClass("active");
        }
    }

    $(function () {
        _.requireCss("css/style.css");
        $(window).unbind("daily.statechange").bind("daily.statechange", function (event, url) {
                url = url || History.getLocationHref();
                if (url.indexOf('?') > -1) url = url.substr(0, url.indexOf('?'));
                addMenuClass($("[data-href='" + History.getShortUrl(url) + "']").parent());
            }
        );
        $(window).trigger("daily.statechange");
    })
}(jQuery, window)
