[#ftl]
<button [#if tag.id??]id="${tag.id}"[/#if] type="submit"
        class="btn ${tag.cssClass!}" ${tag.parameterString}>${tag.value!'Submit'}</button>
<script>
    $(function () {
        $("#${tag.id}").off("click").on("click", null, function (event) {
            event.preventDefault();
            _.submit("${tag.formId}", [#if tag.action??]"${tag.action}"[#else]null[/#if],[#if tag.target??]"${tag.target}"[#else]null[/#if], [#if tag.onsubmit??]"${tag.onsubmit}"[#else]null[/#if]);
        })
    })
</script>
