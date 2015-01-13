!function ($) {
    if (typeof window._ !== 'undefined') return;

    var d = {
        version: "1.0.0",
        styleCache: {},
        scriptPath: null,
        contextPath: null,
        container: null,
        addParam: function (url, param) {
            if (url.indexOf("?") > 0) {
                return url + "&" + param;
            }
            return url + "?" + param;
        },
        init: function (options) {
            var self = this;
            var options = options || {};
            self.contextPath = options.contextPath;
            if (!self.contextPath) {
                self.contextPath = document.location.pathname
                    .substring(0, document.location.pathname.substring(1)
                        .indexOf('/') + 1);
            }
            self.scriptPath = options.scriptPath || self.contextPath
            + "/static/";
            self.container = options.container || "#contentDiv";
            self.load("bootstrap");
            self.load("bootbox");
            self.load("history");
            self.load("menu");
            self.load("jqueryform");
            return self;
        },
        alert: function (msg) {
            bootbox.dialog({
                message: msg,
                title: "友情提示",
                closeButton: false,
                buttons: {
                    success: {
                        label: "关闭",
                        className: "btn-danger"
                    }
                }
            });
        },
        bar: {
            baseHref: null,
            bs_tableId: null,
            addMethod: 'add',
            updateMethod: 'edit',
            deleteMethod: 'delete',
            retrieveMethod: 'info',
            isMulti: function (ids) {
                return ids == "" || ids.indexOf(",") > -1;
            },
            getCheckedValue: function (name) {
                var ids = '';
                $("input[name='" + name + "']:checked").each(function () {
                    if (ids != '')ids += ',';
                    ids += this.value;
                })
                return ids;
            }, init: function (bs_tableId) {
                this.baseHref = History.getShortUrl(History.getBaseHref());
                this.bs_tableId = bs_tableId;
            },
            addCreate: function () {
                var self = this;
                $("#"+this.bs_tableId).bootstrapTable("addCreate", function () {
                    d.go(self.baseHref + self.addMethod, d.container);
                });
            },
            addRetrieve: function () {
                var self = this;
                $("#"+self.bs_tableId).bootstrapTable("addRetrieve", function () {
                    var id = self.getCheckedValue($("#"+self.bs_tableId).data("selectItemName"));
                    if (self.isMulti(id)) {
                        d.alert("请只选择一条操作");
                        return;
                    }
                    d.go(self.baseHref + self.retrieveMethod + "/" + id, d.container);
                });
            },
            addUpdate: function () {
                var self = this;
               $("#"+self.bs_tableId).bootstrapTable("addUpdate", function () {
                    var id = self.getCheckedValue($("#"+self.bs_tableId).data("selectItemName"));
                    if (self.isMulti(id)) {
                        bootbox.alert("请只选择一条操作");
                        return;
                    }
                    d.go(self.baseHref + self.updateMethod + "/" + id, d.container);
                });
            },
            addDelete: function () {
                var self = this;
               $("#"+self.bs_tableId).bootstrapTable("addDelete", function () {
                    var ids = self.getCheckedValue($("#"+self.bs_tableId).data("selectItemName"));
                    if (ids == '') {
                        bootbox.alert("请至少选择一条操作");
                        return;
                    }
                    d.go(self.baseHref + self.deleteMethod, d.container, {"entityIds": ids});
                });
            }
        },
        getBar: function (bs_tableId) {
            if (null == this.bs_tableId || this.bs_tableId !== bs_tableId) {
                this.bar.init(bs_tableId);
            }
            return this.bar;
        },
        history: {
            recursiveStateWithHolder: function (state, stack, _stack, count) {
                count = count || 0;
                _stack.unshift(state.id);
                if ($(state.data.holder).length > 0) {
                    return count == 0 ? [] : _stack;
                } else {
                    var idx = $.inArray(state.id, stack);
                    if (idx < 0) {
                        return [];
                    } else if (idx == 0) {
                        _stack.unshift(History.getRootStateId());
                        return _stack;
                    }
                    return d.history.recursiveStateWithHolder(History.getStateById(stack[idx - 1]), stack, _stack, count + 1);
                }
            },
            navigationHandler: function (event, state) {
                var $container = $(state.data.holder),
                    url = state.url,
                    params = state.data.params || {};
                if ($container.length > 0) {
                    $.ajax({
                        url: url,
                        cache: false,
                        async: false,
                        type: "POST",
                        dataType: "html",
                        data: params,
                        complete: function (jqXHR) {
                            $container.html(jqXHR.responseText);
                        }
                    }).complete(function () {
                        jQuery(window).trigger("daily.statechange");
                    })
                } else {
                    if (History.backwardStack.length == 0) {
                        window.location.reload();
                    } else {
                        _stack = d.history.recursiveStateWithHolder(state, History.backwardStack, []);
                        for (var i = 0; i < _stack.length; i++) {
                            var _state = History.getStateById(_stack[i]);
                            if (_state) {
                                $.ajax({
                                    url: _state.url,
                                    cache: false,
                                    async: false,
                                    type: _state.data.type,
                                    dataType: "html",
                                    data: _state.data.params,
                                    complete: function (jqXHR) {
                                        $(_state.data.holder).html(jqXHR.responseText);
                                    }
                                }).complete(function () {
                                    if (i == 0) jQuery(window).trigger("daily.statechange", _state.url);
                                })
                            }
                        }
                    }
                }
            },
            init: function () {
                if (document.location.protocol === 'file:'
                    || typeof History == "undefined"
                    || typeof History.Adapter == "undefined") {
                    bootbox.alert('Not Support Ajax History');
                    return;
                }
                if (History.backwardStack.length == 0 && History.forwardStack.length == 0) {
                    History.replaceState({
                            holder: "#contentDiv",
                            params: {},
                            type: "GET",
                            time: new Date().getTime()
                        }, "", History.getLocationHref()
                    );
                    History.setRootState();
                } else {
                    if (History.backwardStack.length == 0) {
                        History.setPrevState(History.getStateById(History.forwardStack.slice(-1)));
                    } else {
                        History.setPrevState(History.getStateById(History.backwardStack.slice(-1)));
                    }
                }
                History.Adapter.bind(window, "navigation-forward", d.history.navigationHandler);
                History.Adapter.bind(window, "navigation-backward", d.history.navigationHandler);
            }
        },
        isAjaxTarget: function (target) {
            if (!target) return false;
            if (target == "" || target == "_new" || target == "_blank" || target == "_self" || target == "_parent" || target == "_top") {
                return false;
            }
            targetEle = document.getElementById(target);
            if (!targetEle) return false;
            tagName = targetEle.tagName.toLowerCase();
            if (tagName == "iframe" || tagName == "object") {
                return false;
            }
            return true;
        },
        normalTarget: function (target) {
            if (target == "" || target == "new" || target == "_blank" || target == "_self" || target == "_parent") {
                return target;
            }
            var targetObj = document.getElementById(target);
            if (!targetObj || targetObj.tagName.toLowerCase() != "iframe") return "_self";
            else return target;
        },
        findTarget: function (ele) {
            var p = ele.parentNode, finalTarget = "_self";
            while (p) {
                if (p.id && p.className && (p.className.indexOf("contentDiv") > -1 )) {//||p.className.indexOf("ui-tabs-panel")>-1
                    finalTarget = p.id;
                    break;
                } else {
                    if (p == p.parentNode) p = null;
                    else p = p.parentNode;
                }
            }
            ele.target = finalTarget;
            return finalTarget;
        },
        submit: function (myForm, action, target, onsubmit, ajax) {
            var submitTarget, rs, origin_target, origin_action;
            if ((typeof myForm) == 'string') myForm = $("#" + myForm);
            myForm.trigger("validate");
            var res = myForm.data("submit");
            if (!res) {
                return;
            }
            if (onsubmit) {
                rs = null;
                if (typeof onsubmit == "function") {
                    try {
                        rs = onsubmit(myForm[0]);
                    } catch (e) {
                        console.error(e.stackTrace());
                        return;
                    }
                } else {
                    rs = eval(onsubmit);
                }
                if (!rs) {
                    return;
                }
            }
            submitTarget = (null != target) ? target : myForm.attr("target");
            if (!submitTarget) submitTarget = this.findTarget(myForm[0]);
            if (action == null) action = myForm.attr("action");
            if (action.indexOf("http://") == 0) action = action.substring(action.indexOf("/", 7));
            if (null == ajax || ajax) ajax = this.isAjaxTarget(submitTarget);
            if (ajax) {
                var formId = myForm.attr("id");
                if (null == formId || '' == formId) {
                    myForm.attr("id", myForm.attr("name"));
                }
                this.ajaxSubmit(myForm.attr("id"), action, submitTarget);
            } else {
                origin_target = myForm.attr("target");
                origin_action = myForm.attr("action");
                myForm.attr("action", action).attr("target", this.normalTarget(submitTarget));
                myForm[0].submit();
                myForm.attr("action", origin_action).attr("target", origin_target);
            }
        }

        ,
        ajaxSubmit: function (formId, action, target) {
            if (!action) action = document.getElementById(formId).action;
            jQuery('#' + formId).ajaxForm({
                success: function (result, statusCode, jqXHR) {
                    try {
                        jQuery('#' + target).html(result);
                        var redirect_url = jqXHR.getResponseHeader("daily_redirect");
                        if (redirect_url) {
                            url = redirect_url;
                            var redirect_param = jqXHR.getResponseHeader("params") || "{}";
                            params = $.parseJSON(redirect_param);
                        }
                        History.pushState({
                            holder: "#" + target,
                            params: params,
                            type: "POST",
                            time: new Date().getTime()
                        }, "", url);
                    } catch (e) {
                        console.error(e.stackTrace());
                    }
                },
                error: function (response) {
                    try {
                        jQuery('#' + target).html(response.responseText);
                    } catch (e) {
                        console.error(e.stackTrace());
                    }
                },
                url: action
            });
            jQuery('#' + formId).submit();
        }
        ,
        go: function (url, target, params, callback) {
            var self = this;
            params = params || {};
            switch (target) {
                case '_blank':
                    window.open(url);
                    break;
                case '_self':
                    self.go(url, 'body', params, callback);
                    break;
                case '_parent':
                    self.go(url, 'body', params, callback);
                    break;
                case '_top':
                    self.go(url, 'body', params, callback);
                    break;
                default:
                    $.ajax({
                        url: url,
                        cache: false,
                        async: false,
                        type: "GET",
                        dataType: "html",
                        data: params,
                        complete: function (jqXHR) {
                            $(target).html(jqXHR.responseText);
                            var redirect_url = jqXHR.getResponseHeader("daily_redirect");
                            if (redirect_url) {
                                url = redirect_url;
                                var redirect_param = jqXHR.getResponseHeader("params") || "{}";
                                params = $.parseJSON(redirect_param);
                            }
                            History.pushState({
                                holder: target,
                                params: params,
                                type: "GET",
                                time: new Date().getTime()
                            }, "", url);
                            if (typeof callback != 'undefined') callback.call();
                        }
                    });
            }
        }
        ,
        require: function (files, callBack, basePath) {
            var self = this, successFunction, path;
            successFunction = callBack || function () {
            };
            path = basePath || null;

            if (path === null && !self.scriptPath) {
                path = "";
            } else if (path === null && self.scriptPath) {
                path = self.scriptPath;
            }

            if (typeof files === "string") {
                files = files.split(",");
            }
            $.each(files, function (i, file) {
                file = self.addParam(file, "version=" + d.version);
                $.ajax({
                    type: "GET",
                    scriptCharset: "UTF-8",
                    url: path + file,
                    success: successFunction,
                    dataType: "script",
                    cache: true,
                    async: false
                });
            });
        }
        ,
        requireCss: function (cssFile, basePath) {
            var self = this
            if (!self.styleCache[cssFile]) {
                var path, cssref;
                path = basePath || null;
                if (path === null && !self.scriptPath) {
                    path = '';
                } else if (path === null && self.scriptPath) {
                    path = self.scriptPath;
                }
                $css = $("<link/>").attr("rel", "stylesheet").attr("type",
                    "text/css").attr("href", (path + cssFile));
                $css.appendTo($("head"));
                self.styleCache[cssFile] = true;
            }
        }
        ,
        load: function (module, callback) {
            var self = this;
            switch (module) {
                case "jcrop":
                    self
                        .requireCss("plugins/jcrop/css/jquery.Jcrop.min.css");
                    self.require("plugins/jcrop/js/jquery.Jcrop.min.js");
                    break;
                case "jqueryform":
                    self.require("plugins/jquery-form/jquery.form.min.js");
                    break;
                case "jsrender":
                    self.require("plugins/jsrender/jsrender.min.js");
                    break;
                case "menu":
                    self.require("plugins/jsrender/jsrender.min.js");
                    self.require("script/menu.js");
                    break;
                case "bootstrap":
                    self.requireCss("plugins/bootstrap/css/bootstrap.min.css");
                    self
                        .requireCss("plugins/bootstrap/css/bootstrap-theme.min.css");
                    self.require("plugins/bootstrap/js/bootstrap.min.js");
                    break;
                case "bootbox":
                    self.require("plugins/bootbox/bootbox.min.js");
                    break;
                case "history":
                    callback = callback || self.history.init;
                    self.require("plugins/history/jquery.history.js", callback);
                    break;
                case "validity":
                    self.requireCss("plugins/validity/css/jquery.validity.css");
                    self.require("plugins/validity/js/jquery.validity.min.js");
                    self.require(
                        "plugins/validity/lang/jquery.validity.lang.zh.js",
                        callback);
                    break;
                case "bootstrap-table":
                    self
                        .requireCss("plugins/bootstrap-table/bootstrap-table.min.css");
                    self.require("plugins/bootstrap-table/bootstrap-table.js");
                    self
                        .require("plugins/bootstrap-table/extensions/cruds/bootstrap-table-crud.js");
                    self
                        .require(
                        "plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js",
                        callback);
                    break;
                case "bootstrap-table-filter":
                    self
                        .requireCss("plugins/bootstrap-table/bootstrap-table.min.css");
                    self
                        .requireCss("plugins/bootstrap-table/extensions/filter/bootstrap-table-filter.css");
                    self.require("plugins/bootstrap-table/bootstrap-table.js");
                    self
                        .require("plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js");
                    self
                        .require("plugins/bootstrap-table/extensions/filter/bootstrap-table-filter.js");
                    self
                        .require("plugins/bootstrap-table/extensions/filter/bs-table.js");
                    self
                        .require("plugins/bootstrap-table/extensions/cruds/bootstrap-table-crud.js");
                    self
                        .require(
                        "plugins/bootstrap-table/extensions/filter/bootstrap-table-filter-ext.js",
                        callback);
                default:
                    break;
            }
        }
    }
    if (!d.contextPath) {
        window._ = d.init();
    }
    $(function () {
        if (History.backwardStack.length > 0) {
            var _stack = d.history.recursiveStateWithHolder(History.getStateById(History.backwardStack.slice(-1)), History.backwardStack, []);
            for (var i = 0; i < _stack.length; i++) {
                var _state = History.getStateById(_stack[i]);
                $.ajax({
                    url: _state.url,
                    cache: false,
                    async: false,
                    type: "POST",
                    dataType: "html",
                    data: _state.data.params,
                    complete: function (jqXHR) {
                        $(_state.data.holder).html(jqXHR.responseText);
                    }
                }).complete(function () {
                    if (i == 0) jQuery(window).trigger("daily.statechange", _state.url);
                })
            }
        }
    })
}
(jQuery, undefined);

!function () {
    var $ready = jQuery.prototype.ready;
    jQuery.prototype.ready = function (fn) {
        return $ready(function () {
            try {
                fn();
            } catch (e) {
                console.error(e.stackTrace());
            }
        });
    }
};
