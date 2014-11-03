[#ftl]
[@b.head]
[#assign base= rc.contextPath/]
[#include "navbar.ftl"/]
<div class="main-container border-black" id="contentDiv">
aa
</div>
<script>
$(function(){
 	setInterval(function(){
    	var date = new Date();
    	$("#date").text(date.toLocaleString());
	},100);
	
	$(".menu-entry").click(function(){
		var url = $(this).attr("data-href");
		var target = $(this).attr("target");
		$.ajax({
        	url: url,
        	async: false,
        	cache:false,
	      type: "POST",
	      dataType: "html",
	      complete: function(jqXHR) {
      		target="#"+target;
          	if(jQuery(target).html().length>0){
            	//bg.history.snapshot();
            	//History.pushState({content:jqXHR.responseText,container:target},"",url);
          	}else{
            	//var state=History.getState();
	            //History.replaceState({content:jqXHR.responseText,container:target,updatedAt:(new Date()).getTime()},state.title,state.url);
	            try {
	              jQuery(target).html(jqXHR.responseText);
	            } catch(e){
	            	alert(e);
	            	}
	          	}
	        }
  		});
	})
});
</script>
[/@]
