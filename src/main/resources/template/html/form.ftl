[#ftl]
<form role="form" id="${tag.id}" name="${tag.name}" action="${tag.action}" method="post"
      [#if tag.target??]target="${tag.target}"[/#if]${tag.parameterString}>
[#if RequestParameters['_params']??]<input name="_params" type="hidden"
                                           value="${RequestParameters['_params']?html}"/>[/#if]
${tag.body}
</form>
[#if tag.validate || tag.onsubmit??]
<script>
    _.load("validity");
    $(function () {
        $("#${tag.id}").on("validate", null, function () {
            var res = true;
            $.validity.setup({"outputMode": "${tag.validateOutputMode}"})
            [#if tag.validate]
            $.validity.start();
            ${tag.validity}
            res = $.validity.end().valid;
            [/#if]
            $("#${tag.id}").data("submit", res);
        });
    })
</script>
[/#if]
