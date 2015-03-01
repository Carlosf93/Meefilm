<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="../Fragments/header.jspf" %>
     
     <div class="CuadroHome">
     <div class="CuadroHomeTitulo"><h2>Usuarios recomendados</h2></div>
     <div class="CuadroHomeContenido">
     <c:if test="${empty usPar}">
     No hay usuarios recomendados que mostrar.
     </c:if>
     <c:forEach items="${usPar}" var="u" begin="0" end="4">
      	<div id="usurec" style="text-align: center; width: 100px;">
      	<a href="${prefix}usuario?u=${e:forHtmlContent(u.id)}" style="color: cadetblue">
      		<div id="cuadroUsu" style="width: 100px; height: 100px; border: 1px solid black;">
      		<img src="user/photo?id=${e:forHtmlContent(u.id)}" style="width: 100%; height: 100%;" /></div></a>
      		<span id="nombreusu">
      			<a href="${prefix}usuario?u=${e:forHtmlContent(u.id)}" style="color: cadetblue">${e:forHtmlContent(u.nombre)}</a>
      		</span>
      	</div>
     </c:forEach>
     </div>
	 </div>
	 <HR>
     
     <div class="CuadroHome">
     <div class="CuadroHomeTitulo"><h2>Peliculas recomendadas</h2></div>
     <div class="CuadroHomeContenido">
     <c:if test="${empty pePar}">
     No hay peliculas recomendados que mostrar.
     </c:if>
       <c:forEach items="${pePar}" var="p" begin="0" end="4">
      	<div id="pelirec" style="text-align: center; width: 100px;">
      	<a href="${prefix}pelicula?p=${e:forHtmlContent(p.id)}" style="color: cadetblue">
      		<div id="cuadroPeli" style="width: 100px; height: 150px; border: 1px solid black;">
      		<img src="pelicula/photo?id=${e:forHtmlContent(p.id)}" style="width: 100%; height: 100%;" /></div></a>
      		<span id="nombrepeli">
      			<a href="${prefix}pelicula?p=${e:forHtmlContent(p.id)}" style="color: cadetblue">${e:forHtmlContent(p.nombre)}</a>
      		</span>
      	</div>
     </c:forEach>
     </div>
     </div>
    <HR>
    
    <div class="CuadroHome">
     <div class="CuadroHomeTitulo"><h2>Series recomendadas</h2></div>
     <div class="CuadroHomeContenido">
     <c:if test="${empty sePar}">
     No hay series recomendados que mostrar.
     </c:if>
       <c:forEach items="${sePar}" var="s" begin="0" end="4">
      	<div id="serierec" style="text-align: center; width: 100px;">
      	<a href="${prefix}serie?s=${e:forHtmlContent(s.id)}" style="color: cadetblue">
      		<div id="cuadroSerie" style="width: 100px; height: 150px; border: 1px solid black;">
      		<img src="serie/photo?id=${e:forHtmlContent(s.id)}" style="width: 100%; height: 100%;" /></div></a>
      		<span id="nombrepeli">
      			<a href="${prefix}serie?s=${e:forHtmlContent(s.id)}" style="color: cadetblue">${e:forHtmlContent(s.nombre)}</a>
      		</span>
      	</div>
     </c:forEach>
     </div>
     </div>
	 
<%@ include file="../Fragments/footer.jspf" %>