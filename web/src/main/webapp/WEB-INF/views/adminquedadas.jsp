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
	$.post("delQuedada", {id: id, csrf: "${e:forJavaScript(csrf_token)}"}, 
			function(data) {
				$("#del_"+id).parent().parent().remove();
			});
}
</script>

<table class="users">
<thead>
	<tr><td>Comunidad autónoma<td>Ciudad<td>Película<td>Eliminar</tr>
</thead>
<tbody>

	<c:forEach items="${quedadas}" var="q">
		<tr>
		<td>${e:forHtmlContent(q.comunidadAut)}</a>
		<td>${e:forHtmlContent(q.ciudad)}
		<td>${e:forHtmlContent(q.pelicula)}
		<td><button class="x" id="del_${q.id}">x</button></tr>
		</tr>
	</c:forEach>
</tbody>	
</table>
<%@ include file="../Fragments/footer.jspf" %>