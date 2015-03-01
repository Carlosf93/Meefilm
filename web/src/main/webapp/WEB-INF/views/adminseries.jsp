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
	$.post("delSerie", {id: id, csrf: "${e:forJavaScript(csrf_token)}"}, 
			function(data) {
				$("#del_"+id).parent().parent().remove();
			});
}
</script>

<table class="users">
<thead>
	<tr><td>Título<td>Géneros<td>Eliminar</tr>
</thead>
<tbody>

	<c:forEach items="${series}" var="s">
		<tr>
		<td>${e:forHtmlContent(s.nombre)}</a>
		<td>${e:forHtmlContent(s.genero)}
		<td><button class="x" id="del_${s.id}">x</button></tr>
		</tr>
	</c:forEach>
</tbody>	
</table>

<%@ include file="../Fragments/footer.jspf" %>