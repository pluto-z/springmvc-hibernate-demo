[#ftl]
[@b.head]
	<div class="server-exception"> 
		
   		<center><h1><span class="glyphicon glyphicon-info-sign"></span></h1></center>

   		<h3>处理失败</h3> 
   		<hr> 
   		<p>原因:${exception.message}</p> 
   		<hr> 
   		<p>
   			<button type="button" class="btn btn-default" onclick="history.back();">
   				<span class="glyphicon glyphicon-backward"></span> 返回
   			</button>
   		</p>
   	</div>
[/@]