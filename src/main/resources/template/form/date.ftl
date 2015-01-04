<script type="text/javascript">beangle.ui.load("My97DatePicker");</script>
<div class="form-group">
[#if tag.label??]
    <label for="${tag.id}" class="col-md-2 control-label">
        [#if (tag.required!"")=="true"]
            <em class="required">*</em>
        [/#if]${tag.label}:
    </label>
[/#if]
    <div class="col-md-10">
        <input type="text" id="${tag.id}" [#if tag.title??]title="${tag.title}"[/#if] class="Wdate"
               onFocus="WdatePicker({dateFmt:'${tag.format}'[#if tag.maxDate??],maxDate:'${tag.maxDate}'[/#if][#if tag.minDate??],minDate:'${tag.minDate}'[/#if]})"
               name="${tag.name}" value="${tag.value}" ${tag.parameterString}/>
    [#if tag.comment??]<label class="comment">${tag.comment}</label>[/#if]
    </div>
</div>
