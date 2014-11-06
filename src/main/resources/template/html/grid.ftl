[#ftl]
[#if tag.filterable]
<div id="filter-bar"></div>
[/#if]
<table id="${tag.id}" data-select-item-name="${tag.selectItemName!('id')}"
       data-classes="table table-hover ${tag.cssClass!}" data-height="${tag.height}" data-url="${tag.url}"
       data-id-field="${tag.idField!('id')}" [#if !tag.filterable && (tag.showAll || tag.search)]data-search="true"
       [#if tag.searchAlign??]data-search-align="${tag.searchAlign}"[/#if][/#if]
       [#if tag.toolbarAlign??]data-toolbar-align="${tag.toolbarAlign}"[/#if]
       [#if tag.pageList??]data-page-list="${tag.pageList}"[/#if]
       [#if tag.pageSize??]data-page-size="${tag.pageSize}"[/#if] [#if tag.method??]data-method="${tag.method}"[/#if]
       [#if !tag.filterable && tag.toolbar??]data-toolbar="${tag.toolbar}"[/#if]
       [#if tag.rowStyle??]data-row-style="${tag.rowStyle}"[/#if]
       [#if tag.clickToSelect]data-click-to-select="true"[/#if] [#if tag.singleSelect]data-single-select="true"[/#if]
       [#if tag.pagination]data-pagination="true"[/#if]
       [#if tag.serverPaginationSide]data-side-pagination="server"[/#if] [#if tag.striped]data-striped="true"[/#if]
       [#if !tag.cache]data-cache="false"[/#if] [#if tag.showAll || tag.showColumns]data-show-columns="true"[/#if]
       [#if tag.showAll || tag.showToggle]data-show-toggle="true"[/#if]
       [#if tag.showAll || tag.showRefresh]data-show-refresh="true"[/#if]
       [#if tag.maintainSelected]data-maintain-selected="true"[/#if] [#if !tag.sortable]data-sortable="false"[/#if]
       [#if tag.exportable]data-show-export="true"
       [#if tag.exportType??]data-export-types="${tag.exportType}"[/#if][/#if]
       [#if tag.editable]data-editable="true"[/#if] [#if tag.filterable]data-show-filter="true"
       data-toolbar="#filter-bar"[/#if] ${tag.parameterString}>
[#if tag.cols?size>0]
    <thead>
    <tr>
        [#list tag.cols as cln]
            [#if cln.type??]
                <th data-field="${cln.field}" [#if cln.type == 'radio']data-radio="true"
                    [#else]data-checkbox="true"[/#if]></th>
            [#else]
                <th data-align="${cln.align!}" data-halign="${cln.halign!}" data-valign="${cln.valign!}"
                    [#if cln.formatter??]data-formatter="${cln.formatter}"[/#if]
                    [#if cln.field??]data-field="${cln.field}"[/#if]
                    [#if cln.cssClass??]data-class="${cln.cssClass}"[/#if]
                    [#if cln.width??]data-width="${cln.width}"[/#if] [#if cln.events??]data-events="${cln.events}"[/#if]
                    [#if cln.cellStyle??]data-cellStyle="${cln.cellStyle}"[/#if]
                    data-sortable="${cln.sortable?string('true','false')}"
                    [#if cln.sorter??]data-sorter="${cln.sorter}"[/#if]
                    [#if !cln.switchable]data-switchable="false"[/#if]
                    [#if !cln.visible]data-visible="false"[/#if] >${cln.title}</th>
            [/#if]
        [/#list]
    </tr>
    </thead>
[/#if]
</table>
<script>
    _.load("bootstrap-table${tag.filterable?string('-filter','')}", function () {
        $("#${tag.id}").bootstrapTable();
    });
    [#if tag.hasScript]
    var bar = _.getBar($('#${tag.id}'));
    ${tag.toolbarScript}
    [/#if]
</script>
