[#if !login??]
    [#if default_menus??]
        [@d.head/]
        [#include "navbar.ftl"/]
    <div class="wrapper ajaxcontainer" id="contentDiv">
        [@body/]
    </div>
        [@d.foot/]
    [#else]
        [@body/]
    [/#if]
[/#if]
