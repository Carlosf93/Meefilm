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
	$.post("delReporte", {id: id, csrf: "${e:forJavaScript(csrf_token)}"}, 
			function(data) {
				$("#del_"+id).parent().parent().remove();
			});
}
</script>

<span>Actualizar Películas, series y usuarios recomendados:</span><br>
<a href="parecidos"><input type='button' value='Actualizar' class="button"></a><br><br>

<span>REPORTES:</span>

<table class="users">
<thead>
	<tr><td>ID<td>Tipo de elemento<td>Nombre elemento<td>Motivo del reporte<td>Eliminar reporte</tr>
</thead>
<tbody>

	<c:forEach items="${reportes}" var="r">
		<tr>
		<td>${e:forHtmlContent(r.id)}
		<td>${e:forHtmlContent(r.tipo)}
		<td>${e:forHtmlContent(r.nombre)}
		<td>${e:forHtmlContent(r.motivo)}
		<td><button class="x" id="del_${r.id}">x</button></tr>
		</tr>
	</c:forEach>
</tbody>	
</table>

<%@ include file="../Fragments/footer.jspf" %>