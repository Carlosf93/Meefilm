<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="../Fragments/header.jspf" %>

  <script>
 $(function() {
 	$( "#tabs" ).tabs().addClass( "ui-tabs-vertical ui-helper-clearfix" );
 	$( "#tabs li" ).removeClass( "ui-corner-top" ).addClass( "ui-corner-left" );
 });
  
 $(function() {
 	$('.msjbutton').click(function() {
    	$('#msjdiv' + $(this).attr('target')).toggle('slow');
 	});
 });
 </script>
 	 	
 	  <style>
  .ui-tabs-vertical { width: 55em; }
  .ui-tabs-vertical .ui-tabs-nav { padding: .2em .1em .2em .2em; float: left; width: 12em; }
  .ui-tabs-vertical .ui-tabs-nav li { clear: left; width: 100%; border-bottom-width: 1px !important; border-right-width: 0 !important; margin: 0 -1px .2em 0; }
  .ui-tabs-vertical .ui-tabs-nav li a { display:block; }
  .ui-tabs-vertical .ui-tabs-nav li.ui-tabs-active { padding-bottom: 0; padding-right: .1em; border-right-width: 1px; border-right-width: 1px; }
  .ui-tabs-vertical .ui-tabs-panel { padding: 1em; float: right; width: 40em;}
  </style>
  
<h2>Bandeja de entrada</h2>
<div id="tabs">
  <ul>
    <li><a href="#tabs-1">Mensajes Privados</a></li>
    <li><a href="#tabs-2">Peticiones de amistad</a></li>
  </ul>
  <div id="tabs-1" style="height: 500px; border: 1px solid black;">
	<c:forEach items="${u.bandejaEntrada}" var="be">
	<c:if test="${be.tipo == 'MP'}">
	
	<div id="light" class="white_content" style="height: auto;">
    <form action="enviarmensaje?ue=${e:forHtmlContent(user.id)}&ud=${e:forHtmlContent(be.usuario.id)}" method="post" id="enviamensaje-form" novalidate="novalidate">
    <div class="label">Asunto: </div><input type="text" id="asunto" name="asunto" style="width:100%"/><br/>
    <div class="label">Texto: </div><textarea type="textarea" id="texto" name="texto" style="width:100%; height: 60px;"></textarea><br/><br>
    <div><input type="submit" name="submit" value="Enviar" /></div>  <br><br>  
    </form>
    <a href = "javascript:void(0)" onclick = "document.getElementById('light').style.display='none';document.getElementById('fade').style.display='none'">Cerrar</a>
    </div>
    			
		<button class="msjbutton" target = "${be.asunto}"> ${e:forHtmlContent(be.usuario.login)} - ${e:forHtmlContent(be.asunto)}</button><br>
    	
    	<div id="msjdiv${be.asunto}" class="msjbox">
    		<span class="msjtitulo">Asunto: </span> ${e:forHtmlContent(be.asunto)}<br><br>
    		<span class="msjtitulo">Enviado por: </span>
    			<a href="${prefix}usuario?u=${e:forHtmlContent(be.usuario.id)}">${e:forHtmlContent(be.usuario.login)}</a><br><br>
    		<span class="msjtitulo">Mensaje: </span><br>
			${be.texto}<br><br>
			<a href = "javascript:void(0)" onclick = "document.getElementById('light').style.display='block';document.getElementById('fade').style.display='block'">
				<button>Responder</button></a>
			
			<a href = "eliminarmensaje?u=${e:forHtmlContent(user.id)}&m=${e:forHtmlContent(be.id)}">
				<button>Eliminar</button>
			</a>
		</div>
		<br>
		
		
		</c:if>
	</c:forEach>
  </div>
  
  <div id="tabs-2" style="height: 500px; border: 1px solid black;">

	<c:forEach items="${u.bandejaEntrada}" var="be">
		
		<c:if test="${be.tipo == 'FR'}">
			<div class="frbutton">
			Petición de amistad del usuario  
			<a href="${prefix}usuario?u=${e:forHtmlContent(be.usuario.id)}">${e:forHtmlContent(be.usuario.login)}</a>
			<span> - </span>
			<a href="acceptfr?ue=${e:forHtmlContent(be.usuario.id)}&ud=${e:forHtmlContent(user.id)}&fr=${e:forHtmlContent(be.id)}"><button>Aceptar</button></a>
			<span> - </span>
			<a href="rejectfr?u=${e:forHtmlContent(user.id)}&fr=${e:forHtmlContent(be.id)}"><button>Rechazar</button></a>
			</div>
			<br>
		</c:if>
		</c:forEach>
  </div>
</div>
 
<%@ include file="../Fragments/footer.jspf" %>