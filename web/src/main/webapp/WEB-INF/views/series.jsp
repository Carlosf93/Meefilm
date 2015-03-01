<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="../Fragments/header.jspf" %>
     
     <script>
 	$(function() {
 		
 		$("addSerieIMDB").validate({
 	        
 	        // Specify the validation rules
 	        rules: {
 	        	imdbid: "required",
 	          
 	        },
 	        
 	        // Specify the validation error messages
 	        messages: {
 	        	imdbid: "Campo requerido."
 	         
 	        },
 	        
 	        submitHandler: function(form) {
 	            form.submit();
 	        }
 	    });
	 
  });
 	</script>
 	
 	<script>
 	$(function() {
 	$('#addSerieB').click(function() {
 	    $('#registerSerie-form').toggle('slow');
 	});
 	});
 	</script>	
     
    <h1 style="font-family: fantasy; color: cadetblue">Series</h1>
    
    <input type='button' id='addSerieB' value='Añadir serie' class="button">

	<div id="registerSerie-form" style="display:none">
 	
 	<form action="addserie" method="post" id="addSerieIMDB" novalidate="novalidate"><br/><br/>
    <div class="label">ID IMDB: </div> <input type="text" id="imdbid" name="imdbid" /><br/><br/>
    <input type="submit" name="submit" value="Añadir" /><br/><br/>
    </form>
 
 	</div> 
	
	<c:forEach items="${serie}" var="s">
	<div class="bloquelista">
    	<div class="peliculaC"><img src="serie/photo?id=${e:forHtmlContent(s.id)}" style="width: 100%; height: 100%;" /></div>
    	<div class="textolista">
    		<span class="titulolista">
    			<a href="${prefix}serie?s=${e:forHtmlContent(s.id)}" style="color: cadetblue">${e:forHtmlContent(s.nombre)}</a>
    		</span><br><br>
    		<span class="sinopsislista">
    			${s.sinopsis}
    		</span><br><br>
    		<span class="generolista">
    			${s.genero}
    		</span>
    	</div>
	</div>
	</c:forEach>
	 
<%@ include file="../Fragments/footer.jspf" %>