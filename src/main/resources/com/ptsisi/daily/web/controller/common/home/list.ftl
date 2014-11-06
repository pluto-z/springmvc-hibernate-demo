[#macro body]
<h1>this is content page</h1>
<a href="javascript:void(0)" id="subindexA${id!}">aaa</a>
<br/>
<div id="subindexDiv${id!}" class="ajaxcontainer"
     style="width:300px;height:300px;border: 2px solid rgba(20, 20, 20, 0.3);">div
</div>
<script>
    $(function () {
        $("#subindexA${id!}").click(function () {
            _.go("${base}/homeList", "#subindexDiv${id!}", {'id': "${id!}${id!}"})
        })
    })
</script>
[/#macro]
