<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="../Fragments/header.jspf" %>

  <script>
  $(function() {
    $( "#tabs" ).tabs();
  });
  </script>
  
 	<div id="reportar" class="white_content" style="height: auto;">
    <form action="report?id=${e:forHtmlContent(p.id)}&e=quedada" method="post" id="report-form" novalidate="novalidate">
    <div class="label">Motivo: </div><textarea type="text" id="motivo" name="motivo" style="width:100%; height: 60px;"></textarea><br/>
    <input type="submit" name="submit" value="Reportar" />    
    </form>
    <a href = "javascript:void(0)" onclick = "document.getElementById('reportar').style.display='none';document.getElementById('fade').style.display='none'">Cerrar</a>
    </div>

<h2>Quedada en ${q.ciudad} para ver ${q.pelicula}</h2>
<div id="filmzone">
<div class="fspanel">
<c:choose>
   	<c:when test="${not empty user}">
   	<c:choose>
    <c:when test="${fq == '0' }">
    <a href="${prefix}addquedadalista?q=${e:forHtmlContent(q.id)}"><button class="menubutton" style="width: 140px"> Asistir a la quedada </button></a><br><br>
    </c:when>
    <c:when test="${fq == '1' }">
    <a href="${prefix}deletequedadalista?q=${e:forHtmlContent(q.id)}&ud=${e:forHtmlContent(user.id)}"><button class="menubutton" style="width: 140px"> No asistir </button></a><br><br>
    </c:when>
    </c:choose>
<a href = "javascript:void(0)" onclick = "document.getElementById('reportar').style.display='block';document.getElementById('fade').style.display='block'">
		<button class="menubutton" style="width: 140px"> Reportar Quedada</button></a>
</c:when>
</c:choose>
</div>

<div id="tabs" class='container'>
  <ul>
    <li><a href="#tabs-1">Ficha</a></li>
    <li><a href="#tabs-2">Comentarios</a></li>
    <li><a href="#tabs-3">Usuarios</a></li>
  </ul>
  <div id="tabs-1" class='panel'>
  	<span class="titlestyle">C.Autonoma</span><br><span class="textstyle">${e:forHtmlContent(q.comunidadAut)}</span><br><br>
    <span class="titlestyle">Ciudad</span><br><span class="textstyle">${e:forHtmlContent(q.ciudad)}</span><br><br>
    <span class="titlestyle">Cine</span><br><span class="textstyle">${e:forHtmlContent(q.cine)}</span><br><br>
    <span class="titlestyle">Pelicula</span><br><span class="textstyle">${e:forHtmlContent(q.pelicula)}</span><br><br>
    <span class="titlestyle">Descripcion</span><br><span class="textstyle">${e:forHtmlContent(q.descripcion)}</span><br>
  </div>
  <div id="tabs-2" class='panel'>
  	<c:choose>
   	<c:when test="${not empty user}">
  	<div class="commentdiv">
    <form action="addcomentarioquedada?q=${e:forHtmlContent(q.id)}&u=${e:forHtmlContent(user.id)}" method="post" id="commentpeli-form" novalidate="novalidate">
    <textarea type="text" id="texto" name="texto" style="width: 100%; box-sizing: border-box; height: 60px;"></textarea><br/><br>
    <input type="submit" name="submit" value="Añadir comentario" />    
    </form>
    </div>
    </c:when>
    </c:choose>
    
    <c:forEach items="${q.comentarios}" var="co">
	<div class="comentario">
   		<div style="border: 1px solid black; padding: 1px; background: white;">
   		<div style="border: 1px solid black; width: 50px; height: 50px;">
		<img src="user/photo?id=${e:forHtmlContent(co.usuario.id)}" style="width: 100%; height: 100%;">
		</div>
		<a href="${prefix}usuario?u=${e:forHtmlContent(co.usuario.id)}">${e:forHtmlContent(co.usuario.login)}</a></div>
		<div style="width: 89%; margin-left: 5px; border: 1px solid black; padding: 5px; background: white;">
			<span>${e:forHtmlContent(co.texto)}</span>
		</div>
		</div>
	</c:forEach>
  </div>
  <div id="tabs-3" class='panel'>
  	<c:forEach items="${q.listaUsuarios}" var="lu">
		<div style="border: 1px solid black; width: 50px; height: 50px;">
			<img src="user/photo?id=${e:forHtmlContent(lu.id)}" style="width: 100%; height: 100%;">
		</div>
		<a href="${prefix}usuario?u=${e:forHtmlContent(lu.id)}">${e:forHtmlContent(lu.login)}</a>
	</c:forEach>
  </div>
</div>
</div>

 <%@ include file="../Fragments/footer.jspf" %>