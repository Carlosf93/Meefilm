<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="../Fragments/header.jspf" %>

  <script>
  $(function() {
    $( "#tabs" ).tabs();
  });
  </script>
  
  <script>
 	$(function() {
 	$('.tempbutton').click(function() {
 	    $('#temporadiv' + $(this).attr('target')).toggle('slow');
 	});
 	});
 	</script>
<div>
<h2>${s.nombre}</h2>
<span>Temporada ${t.numero}</span><br>
<span>Capítulo </span>
<c:forEach items="${t.capitulos}" var="cap">
	<c:choose>
	<c:when test="${cap.numero != c.numero}">
	<a href="${prefix}capitulo?t=${e:forHtmlContent(t.id)}
	&c=${e:forHtmlContent(cap.id)}&s=${e:forHtmlContent(s.id)}">
		${e:forHtmlContent(cap.numero)}
	</a>
	</c:when>
	<c:otherwise>
		<b>${e:forHtmlContent(cap.numero)}</b>
	</c:otherwise>
	</c:choose>
</c:forEach>
</div>

<div id="tabs" class='container'>
  <ul>
    <li><a href="#tabs-1">Ficha</a></li>
    <li><a href="#tabs-2">Comentarios</a></li>
  </ul>
  <div id="tabs-1" class='panel'>
  	<span class="titlestyle">Director</span><br><span class="textstyle">${e:forHtmlContent(c.director)}</span><br><br>
    <span class="titlestyle">Reparto</span><br><span class="textstyle"> ${e:forHtmlContent(c.reparto)}</span><br><br>
    <span class="titlestyle">Fecha de emisión</span><br><span class="textstyle"> ${e:forHtmlContent(c.fechaemision)}</span><br><br>
    <span class="titlestyle">Sinopsis</span><br><span class="textstyle"> ${e:forHtmlContent(c.sinopsis)}</span><br>
    <ul>
    <a href="http://www.imdb.com/title/${e:forHtmlContent(c.IMDbId)}">Ficha en IMDb</a>
    </ul>
  </div>
  
   <div id="tabs-2" class='panel'>
   	
   	<c:choose>
   	<c:when test="${not empty user}">
   	<div class="commentdiv">
   	<form action="addcomentariocapitulo?t=${e:forHtmlContent(t.id)}&c=${e:forHtmlContent(c.id)}&s=${e:forHtmlContent(s.id)}&u=${e:forHtmlContent(user.id)}" method="post" id="commentserie-form" novalidate="novalidate">
    <textarea type="text" id="texto" name="texto" style="width: 100%; box-sizing: border-box; height: 60px;"></textarea><br/><br>
    <input type="submit" name="submit" value="Añadir comentario" />    
    </form>
    </div>
    </c:when>
    </c:choose>
   	
   	<c:forEach items="${c.comentarios}" var="co">
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
   
</div>

 <%@ include file="../Fragments/footer.jspf" %>