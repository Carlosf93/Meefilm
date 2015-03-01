<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="../Fragments/header.jspf" %>

  <script>
  $(function() {
    $( "#tabs" ).tabs();
  });
  </script>

<span class="tituloperfil">Mi perfil</span><br>

<div class="userzone">
<div class="userarea">
	<div class="useravatar"><img src="user/photo?id=${e:forHtmlContent(user.id)}" style="width: 100%; height: 100%;"></div>
	<br><br>
	<a href="modificarperfil"><button class="menubutton"> Modificar perfil </button></a>
</div>


<div id="tabs" class='container'>
  <ul>
    <li><a href="#tabs-1">Datos Personales</a></li>
    <li><a href="#tabs-2">Favoritos</a></li>
    <li><a href="#tabs-3">Amigos</a></li>
    <li><a href="#tabs-4">Quedadas</a></li>
  </ul>
  <div id="tabs-1" class='panel'>
    <span class="titlestyle">Nombre</span><br><span class="textstyle">${e:forHtmlContent(user.nombre)}</span><br><br>
    <span class="titlestyle">Edad</span><br><span class="textstyle">${e:forHtmlContent(user.edad)}</span><br><br>
    <span class="titlestyle">Ciudad</span><br><span class="textstyle">${e:forHtmlContent(user.ciudad)}</span><br><br>
    <span class="titlestyle">Aficiones</span><br><span class="textstyle">${e:forHtmlContent(user.aficiones)}</span><br><br>
    <span class="titlestyle">Profesion</span><br><span class="textstyle">${e:forHtmlContent(user.profesion)}</span><br>
  </div>
  <div id="tabs-2" class='panel'>
  	<span class="titlestyle">Series</span><br><br>
  	<div>
	<c:forEach items="${user.listaSeries}" var="ls">
		<div style="border: 1px solid black; width: 50px; height: 70px; display: inline-block;">
			<img src="serie/photo?id=${e:forHtmlContent(ls.id)}" style="width: 100%; height: 100%;">
		</div>
		<a href="${prefix}serie?s=${e:forHtmlContent(ls.id)}">${e:forHtmlContent(ls.nombre)}</a><br>
	</c:forEach>
	</div><br>
	<br><span class="titlestyle">Peliculas</span><br><br>
	<div>
	<c:forEach items="${user.listaPeliculas}" var="lp">
		<div style="border: 1px solid black; width: 50px; height: 70px; display: inline-block;">
			<img src="pelicula/photo?id=${e:forHtmlContent(lp.id)}" style="width: 100%; height: 100%;">
		</div>
		<a href="${prefix}pelicula?p=${e:forHtmlContent(lp.id)}">${e:forHtmlContent(lp.nombre)}</a><br>
	</c:forEach>
	</div>
  </div>
  
  <div id="tabs-3" class='panel'>
	<c:forEach items="${user.listaAmigos}" var="la">
		<div style="border: 1px solid black; width: 50px; height: 50px;">
			<img src="user/photo?id=${e:forHtmlContent(la.id)}" style="width: 100%; height: 100%;">
		</div>
		<a href="${prefix}usuario?u=${e:forHtmlContent(la.id)}">${e:forHtmlContent(la.login)}</a>
	</c:forEach>
  </div>
  
   <div id="tabs-4" class='panel'>
   	<c:forEach items="${user.listaQuedadas}" var="lq">
		<a href="${prefix}quedada?q=${e:forHtmlContent(lq.id)}">${lq.ciudad} - ${lq.pelicula}</a>
	</c:forEach>
   </div>
</div>
</div>
 
<%@ include file="../Fragments/footer.jspf" %>