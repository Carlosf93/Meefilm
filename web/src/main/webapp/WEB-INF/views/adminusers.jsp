<%@ include file="../Fragments/adminheader.jspf" %>

<script type="text/javascript">
$(function() {
	$(".x").click(function(){
		var id=$(this).attr("id").substring("del_".length);
		console.log("deleting", id);
		delUser(id);
	});
})

function delUser(id) {
	$.post("delUser", {id: id, csrf: "${e:forJavaScript(csrf_token)}"}, 
			function(data) {
				$("#del_"+id).parent().parent().remove();
			});
}
</script>

<form action="">
<table class="users">
<thead>
	<tr><td>Foto de perfil<td>Nombre de Usuario<td>Ciudad<td>Edad<td>Eliminar</tr>
</thead>
<tbody>

	<c:forEach items="${users}" var="u">
		<tr>
		<td><div id="imagenU"><img src="user/photo?id=${u.id}" style="width: 100%; height: 100%;" /></div>
		<td><a href="${prefix}usuario?u=${e:forHtmlContent(u.id)}">${e:forHtmlContent(u.login)}</a>
		<td>${e:forHtmlContent(u.ciudad)}
		<td>${e:forHtmlContent(u.edad)}
		<td><button class="x" id="del_${u.id}">x</button></tr>
		</tr>
	</c:forEach>
</tbody>	
</table>
</form>
<%@ include file="../Fragments/footer.jspf" %>