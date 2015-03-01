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
  <form action="report?id=${e:forHtmlContent(p.id)}&e=pelicula" method="post" id="report-form" novalidate="novalidate">
    <div class="label">Motivo: </div><textarea type="text" id="motivo" name="motivo" style="width:100%; height: 60px;"></textarea><br/>
    <input type="submit" name="submit" value="Reportar" />    
    </form>
    <a href = "javascript:void(0)" onclick = "document.getElementById('reportar').style.display='none';document.getElementById('fade').style.display='none'">Cerrar</a>
    </div>

<span class="tituloperfil">${e:forHtmlContent(p.nombre)}</span>
<div id="filmzone">
<div class="fspanel">
<div id="imagenP"><a href=""><img src="pelicula/photo?id=${e:forHtmlContent(p.id)}" style="width: 100%; height: 100%;"></a></div>
<br><br>
<a href="http://www.imdb.com/title/${e:forHtmlContent(p.IMDbId)}"><button class="menubutton"> Ficha en IMDb </button></a><br><br>
<c:choose>
<c:when test="${not empty user}">
<c:choose>
    <c:when test="${fp == '0' }">
    <a href="${prefix}addpeliculalista?p=${e:forHtmlContent(p.id)}"><button class="menubutton"> Añadir a favoritos </button></a><br><br>
    </c:when>
    <c:when test="${fp == '1' }">
    <a href="${prefix}deletepeliculalista?p=${e:forHtmlContent(p.id)}&ud=${e:forHtmlContent(user.id)}"><button class="menubutton"> Borrar de favoritos </button></a><br><br>
    </c:when>
    </c:choose>
<a href = "javascript:void(0)" onclick = "document.getElementById('reportar').style.display='block';document.getElementById('fade').style.display='block'">
		<button class="menubutton"> Reportar Pelicula</button></a>
</c:when>
</c:choose>
</div>

<div id="tabs" class='container'>
  <ul>
    <li><a href="#tabs-1">Ficha</a></li>
    <li><a href="#tabs-2">Comentarios</a></li>
    <li><a href="#tabs-3">Favoritos</a></li>
  </ul>
  <div id="tabs-1" class='panel'>
  	<span class="titlestyle">Director</span><br><span class="textstyle">${p.director}</span><br><br>
    <span class="titlestyle">Reparto</span><br><span class="textstyle"> ${p.reparto}</span><br><br>
    <span class="titlestyle">Género</span><br><span class="textstyle"> ${p.genero}</span><br><br>
    <span class="titlestyle">Año</span><br><span class="textstyle"> ${p.year}</span><br><br>
    <span class="titlestyle">Sinopsis</span><br><span class="textstyle"> ${p.sinopsis}</span><br>
  </div>
 <div id="tabs-2" class='panel'>
 	<c:choose>
   	<c:when test="${not empty user}">
 	<div class="commentdiv">
    <form action="addcomentariopelicula?p=${e:forHtmlContent(p.id)}&u=${e:forHtmlContent(user.id)}" method="post" id="commentpeli-form" novalidate="novalidate">
    <textarea id="texto" name="texto" style="width: 100%; box-sizing: border-box; height: 60px;"></textarea><br/><br>
    <input type="submit" name="submit" value="Añadir comentario" />    
    </form>
    </div>
    </c:when>
    </c:choose>
   	
   	<c:forEach items="${p.comentarios}" var="co">
	<div class="comentario">
   		<div style="border: 1px solid black; padding: 1px; background: white;">
   		<div style="border: 1px solid black; width: 50px; height: 50px;">
		<img src="user/photo?id=${co.usuario.id}" style="width: 100%; height: 100%;">
		</div>
		<a href="${prefix}usuario?u=${e:forHtmlContent(co.usuario.id)}">${e:forHtmlContent(co.usuario.login)}</a></div>
		<div style="width: 89%; margin-left: 5px; border: 1px solid black; padding: 5px; background: white;">
			<span>${co.texto}</span>
		</div>
		</div>
	</c:forEach>
  </div>
  <div id="tabs-3" class='panel'>
 	 <c:forEach items="${p.listaUsuarios}" var="lu">
		<div style="border: 1px solid black; width: 50px; height: 50px;">
			<img src="user/photo?id=${lu.id}" style="width: 100%; height: 100%;">
		</div>
		<a href="${prefix}usuario?u=${e:forHtmlContent(lu.id)}">${e:forHtmlContent(lu.login)}</a>
	</c:forEach>
  </div>
</div>
</div>

 <%@ include file="../Fragments/footer.jspf" %>