<div class="form-group">
[#if tag.label??]
    <label for="${tag.id}" class="col-md-2 text-right">
		[#if (tag.required!"")=="true"]
            <em class="required">*</em>
		[/#if]${tag.label}:
    </label>
[/#if]
    <div class="col-md-10">
        <input type="password" id="${tag.id}" class=" ${tag.cssClass!}" [#if tag.title??]title="${tag.title}"[/#if]
               name="${tag.name}" value="${tag.value?html}" maxlength="${tag.maxlength}" ${tag.parameterString}/>
	[#if tag.comment??]<label class="comment">${tag.comment}</label>[/#if]
    </div>
</div>
