[#macro body]
<h1>this is index page</h1>
<a href="javascript:void(0)" id="indexA">aaa</a>
<br/>
<div id="indexDiv" class="ajaxcontainer" style="width:300px;height:300px;border: 2px solid rgba(20, 20, 20, 0.3);">div
</div>
<script>
    $(function () {
        $("#indexA").click(function () {
            _.go("${base}/homeList", "#indexDiv", {"id": "1"})
        })
    })
</script>
[/#macro]
