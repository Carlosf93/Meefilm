<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="../Fragments/header.jspf" %>

  <script>
  $(function() {
    $( "#tabs" ).tabs();
  });
  </script>

    <div id="light" class="white_content" style="height: auto;">
    <form action="enviarmensaje?ue=${e:forHtmlContent(user.id)}&ud=${e:forHtmlContent(u.id)}" method="post" id="enviamensaje-form" novalidate="novalidate">
    <div class="label">Asunto: </div><input type="text" id="asunto" name="asunto" style="width:100%"/><br/>
    <div class="label">Texto: </div><textarea type="textarea" id="texto" name="texto" style="width:100%; height: 60px;"></textarea><br/><br>
    <div><input type="submit" name="submit" value="Enviar" /></div>  <br><br>  
    </form>
    <a href = "javascript:void(0)" onclick = "document.getElementById('light').style.display='none';document.getElementById('fade').style.display='none'">Cerrar</a>
    </div>
    
    <div id="reportar" class="white_content" style="height: auto;">
    <form action="report?id=${e:forHtmlContent(u.id)}&e=usuario" method="post" id="report-form" novalidate="novalidate">
    <div class="label">Motivo: </div><textarea type="text" id="motivo" name="motivo" style="width:100%; height: 60px;"></textarea><br/>
    <input type="submit" name="submit" value="Reportar" />    
    </form>
    <a href = "javascript:void(0)" onclick = "document.getElementById('reportar').style.display='none';document.getElementById('fade').style.display='none'">Cerrar</a>
    </div>
    
    
<span class="tituloperfil">${e:forHtmlContent(u.login)}</span><br>

<div class="userzone">
<div class="userarea">
	<div class="useravatar"><img src="user/photo?id=${e:forHtmlContent(u.id)}" style="width: 100%; height: 100%;"></div>
	<br><br>
	
	<c:choose>
    <c:when test="${fr == '1' }">
    <a href="friendborrar?ue=${e:forHtmlContent(user.id)}&ud=${e:forHtmlContent(u.id)}"><button class="menubutton"> Borrar amigo </button></a><br>
    </c:when>
    <c:when test="${fr == '0' }">
    <a href="friendrequest?ue=${e:forHtmlContent(user.id)}&ud=${e:forHtmlContent(u.id)}"><button class="menubutton"> Agregar amigo </button></a><br>
    </c:when>
    </c:choose>
    
	<br>
	
	<c:choose>
   	<c:when test="${not empty user}">
	<a href = "javascript:void(0)" onclick = "document.getElementById('light').style.display='block';document.getElementById('fade').style.display='block'">
		<button class="menubutton"> Enviar mensaje </button></a><br>
		<br>
	<a href = "javascript:void(0)" onclick = "document.getElementById('reportar').style.display='block';document.getElementById('fade').style.display='block'">
		<button class="menubutton"> Reportar Usuario </button></a><br>
	
	</c:when>
	</c:choose>
</div>


<div id="tabs" class='container'>
  <ul>
    <li><a href="#tabs-1">Datos Personales</a></li>
    <li><a href="#tabs-2">Favoritos</a></li>
    <li><a href="#tabs-3">Amigos</a></li>
  </ul>
  <div id="tabs-1" class='panel'>
   	<span class="titlestyle">Nombre</span><br><span class="textstyle">${u.nombre}</span><br><br>
    <span class="titlestyle">Edad</span><br><span class="textstyle">${u.edad}</span><br><br>
    <span class="titlestyle">Ciudad</span><br><span class="textstyle">${u.ciudad}</span><br><br>
    <span class="titlestyle">Aficiones</span><br><span class="textstyle">${u.aficiones}</span><br><br>
    <span class="titlestyle">Profesion</span><br><span class="textstyle">${u.profesion}</span><br>
  </div>
  <div id="tabs-2" class='panel'>
	  	<span class="titlestyle">Series</span><br><br>
  	<div>
	<c:forEach items="${u.listaSeries}" var="ls">
		<div style="border: 1px solid black; width: 50px; height: 70px; display: inline-block;">
			<img src="serie/photo?id=${e:forHtmlContent(ls.id)}" style="width: 100%; height: 100%;">
		</div>
		<a href="${prefix}serie?s=${e:forHtmlContent(ls.id)}">${e:forHtmlContent(ls.nombre)}</a><br>
	</c:forEach>
	</div><br>
	<br><span class="titlestyle">Peliculas</span><br><br>
	<div>
	<c:forEach items="${u.listaPeliculas}" var="lp">
		<div style="border: 1px solid black; width: 50px; height: 70px; display: inline-block;">
			<img src="pelicula/photo?id=${e:forHtmlContent(lp.id)}" style="width: 100%; height: 100%;">
		</div>
		<a href="${prefix}pelicula?p=${e:forHtmlContent(lp.id)}">${e:forHtmlContent(lp.nombre)}</a><br>
	</c:forEach>
	</div>
  </div>
  
  <div id="tabs-3" class='panel'>
	<c:forEach items="${u.listaAmigos}" var="la">
		<div class="friendiv">
		<div style="border: 1px solid black; width: 50px; height: 50px;">
			<img src="user/photo?id=${e:forHtmlContent(la.id)}" style="width: 100%; height: 100%;">
		</div>
		<a href="${prefix}usuario?u=${e:forHtmlContent(la.id)}">${e:forHtmlContent(la.login)}</a>
		</div>
	</c:forEach>
  </div>
</div>
</div>
 
<%@ include file="../Fragments/footer.jspf" %>