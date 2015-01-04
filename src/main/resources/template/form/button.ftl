[#ftl]
<button [#if tag.id??]id="${tag.id}"[/#if] type="button" class="btn ${tag.cssClass!}" ${tag.parameterString}>${tag.value!'Button'}</button>
