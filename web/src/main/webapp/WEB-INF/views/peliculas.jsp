<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="../Fragments/header.jspf" %>
 	
 	<script>
 	$(function() {
 	$('#addPeliculaB').click(function() {
 	    $('#registerPelicula-form').toggle('slow');
 	});
 	});
 	</script>	
	
	<h1 style="font-family: fantasy; color: cadetblue">Películas</h1> 	
	
 	<input type='button' id='addPeliculaB' value='Añadir pelicula' class="button">
	<div id="registerPelicula-form" style="display:none">
 	
 	<form action="addpelicula" method="post" id="addPeliIMDB" novalidate="novalidate"><br/><br/>
    <div class="label">ID IMDB: </div> <input type="text" id="imdbid" name="imdbid" /><br/><br/>
    <input type="submit" name="submit" value="Añadir" /><br/><br/>
    </form>
 
 	</div> 
 	     
     <c:forEach items="${pelicula}" var="p">
     	<div class="bloquelista">
     		<div class="peliculaC"><img src="pelicula/photo?id=${e:forHtmlContent(p.id)}" style="width: 100%; height: 100%;" /></div>
     		<div class="textolista">
     			<span class="titulolista">
     				<a href="${prefix}pelicula?p=${e:forHtmlContent(p.id)}" style="color: cadetblue">${e:forHtmlContent(p.nombre)}</a>
     			</span><br><br>
     			<span class="sinopsislista">
     				${e:forHtmlContent(p.sinopsis)}
     			</span><br><br>
     			<span class="generospelicula">${e:forHtmlContent(p.genero)}</span>
     		</div>
		</div>
		
	</c:forEach>
	 
<%@ include file="../Fragments/footer.jspf" %>