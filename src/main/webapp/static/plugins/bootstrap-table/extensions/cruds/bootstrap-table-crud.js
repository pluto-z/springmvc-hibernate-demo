!function ($) {

    'use strict';

    var BootstrapTable = $.fn.bootstrapTable.Constructor;

    BootstrapTable.prototype.addCreate = function (operation) {
        var button = $(["<button type='button' class='btn btn-default'>",
            "<span class='glyphicon glyphicon-plus-sign'></span>",
            "</button>"].join(""));
        this.addItem(button, operation);
    }

    BootstrapTable.prototype.addRetrieve = function (operation) {
        var button = $(["<button type='button' class='btn btn-default'>",
            "<span class='glyphicon glyphicon-info-sign'></span>",
            "</button>"].join(""));
        operation = operation || {};
        this.addItem(button, operation);
    }

    BootstrapTable.prototype.addUpdate = function (operation) {
        var button = $(["<button type='button' class='btn btn-default'>",
            "<span class='glyphicon glyphicon-pencil'></span>", "</button>"]
            .join(""));
        operation = operation || {};
        this.addItem(button, operation);
    }

    BootstrapTable.prototype.addDelete = function (operation) {
        var button = $(["<button type='button' class='btn btn-default'>",
            "<span class='glyphicon glyphicon-remove-sign'></span>",
            "</button>"].join(""));
        operation = operation || {};
        this.addItem(button, operation);
    }

    BootstrapTable.prototype.addItem = function (obj, operation) {

        var $crudbar = this.$toolbar.find("#crud-bar");
        if ($crudbar.length == 0) {
            var $bars = $('<div class="bars pull-left"></div>');
            $bars.prependTo(this.$toolbar);
            $crudbar = $(["<div id='crud-bar'>", "<div class='btn-toolbar'>", "<div class='btn-group'>", "</div>", "</div>",
                "</div>"].join(''));
            $crudbar.appendTo($bars);
        }
        $crudbar.find(".btn-group").append(obj);
        if (typeof operation != 'undefined') {
            obj.click(operation);
        }
    }
    $.fn.bootstrapTable.methods.push('addCreate', 'addRetrieve', 'addUpdate', 'addDelete', 'addItem');
}(jQuery);
