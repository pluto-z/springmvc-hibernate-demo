<div class="form-group">
[#if tag.label??]
    <label class="col-md-2 text-right">
        [#if (tag.required!"")=="true"]
            <em class="required">*</em>
        [/#if]${tag.label}:
    </label>
[/#if]
    <div class="col-md-10">
    ${tag.body}
    </div>
</div>
