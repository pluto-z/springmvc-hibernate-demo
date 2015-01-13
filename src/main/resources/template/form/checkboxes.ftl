<div class="form-group">
[#if tag.label??]
    <label for="${tag.id}" class="col-md-2 text-right">
        [#if (tag.required!"")=="true"]
            <em class="required">*</em>
        [/#if]${tag.label}:
    </label>
[/#if]
    <div class="col-md-10" style="">
    [#list tag.checkboxes as checkbox]
        <input type="checkbox" id="${checkbox.id}" style="width:10px;vertical-align:middle;" name="${tag.name}"
               value="${checkbox.value}"${tag.parameterString} [#if checkbox.checked]checked="checked"[/#if] />
        <label style="vertical-align:middle;margin:0;font-weight:normal;"
               for="${checkbox.id}">${checkbox.title!}</label>
    [/#list]
    <input type="hidden" id="${tag.id}"/>
    [#if tag.comment??]<label class="comment">${tag.comment}</label>[/#if]
    </div>
</div>
