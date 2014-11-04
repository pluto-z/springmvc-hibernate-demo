[#ftl]
[@b.head]
<table class="table table-bordered table-striped">
	<thead>
    	<tr>
      	<th>用户名</th>
      	<th>姓名</th>
      	<th>邮箱</th>
  			<th>角色</th>
      	<th>状态</th>
    	</tr>
  	</thead>
  	<tbody>
  		[#list users as user]
		<tr>
      	<td>${user.username!?js_string}</td>
      	<td>${user.fullName!?js_string}</td>
      	<td>${user.email!?js_string}</td>
      	<td>[#list user.roles as role]<span>${role.description!?js_string}</span>[/#list]</td>
      	<td>${user.enabled?string('','')}</td>
    	</tr>
    	[/#list]
  	</tbody>
</table>
[/@]
