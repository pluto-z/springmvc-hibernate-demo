<div class="form-group">
[#if tag.label??]
    <label for="${tag.id}" class="col-md-2 text-right">
        <em class="required">*</em>${tag.label}:
    </label>
[/#if]
    <div class="col-md-10" style="">
    [#list tag.radios as radio]
        <input type="radio" style="width:10px;vertical-align:middle;" id="${radio.id}" name="${tag.name}"
               value="${radio.value}" ${tag.parameterString} [#if (tag.value!"")==radio.value]checked="checked"[/#if]/>
        <label for="${radio.id}">&nbsp;${radio.title!}</label>&nbsp;&nbsp;
    [/#list]
        <input type="hidden" id="${tag.id}"/>
    </div>
</div>
