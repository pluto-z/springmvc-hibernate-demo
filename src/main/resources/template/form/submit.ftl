[#ftl]
<button [#if tag.id??]id="${tag.id}"[/#if] type="submit" class="btn ${tag.cssClass!}" ${tag.parameterString}>${tag.value!'Submit'}</button>
[#--
<input type="submit"  value="" onclick="bg.form.submit('${tag.formId}',[#if tag.action??]'${tag.action}'[#else]null[/#if],[#if tag.target??]'${tag.target}'[#else]null[/#if],[#if tag.onsubmit??]${tag.onsubmit}[#else]null[/#if]);return false;"/>
--]