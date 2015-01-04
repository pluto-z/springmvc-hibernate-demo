[#ftl]
<form role="form" class="form-horizontal" id="${tag.id}" name="${tag.name}" action="${tag.action}" method="post"
      [#if tag.target??]target="${tag.target}"[/#if]${tag.parameterString}>
[#if RequestParameters['_params']??]<input name="_params" type="hidden"
                                           value="${RequestParameters['_params']?html}"/>[/#if]
${tag.body}
</form>
[#if tag.validate || tag.onsubmit??]
<script>
    _.load("validity");
    $(function () {
        $("#"${tag.id}).submit(function () {
            var res = null;
            [#if tag.validate]
                $.validity.start();
            ${tag.validity}
                res = $.validity.end().valid;
            [/#if]
            if (false == res) return false;
            [#if tag.onsubmit??]
                var nativeOnsubmit${tag.id} = function () {${tag.onsubmit}}
                try {
                    res = nativeOnsubmit${tag.id}();
                } catch (e) {
                    alert(e);
                    return false;
                }
            [/#if]
            return res;
        });
    })
</script>
[/#if]
