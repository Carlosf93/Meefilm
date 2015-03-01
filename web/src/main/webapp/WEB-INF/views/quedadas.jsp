<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="../Fragments/header.jspf" %>

<script>
 	$(function() {
 		$("#registerquedada-form").validate({
 		    
 	        // Specify the validation rules
 	        rules: {
 	            caut: "required",
 	            ciudad: "required",
 	            cine: "required",
 	           	peli: "required",
 	          	descripcion: "required"
 	          
 	        },
 	        
 	        // Specify the validation error messages
 	        messages: {
 	            caut: "Por favor introduzca la comunidad autonoma",
 	            ciudad: "Por favor introduzca la ciudad de la quedada",
 	            cine: "Por favor introduzca cine de la quedada",
 	            peli: "Por favor introduzca la pelicula",
 	            descripcion: "Por favor introduzca la descripcion"
 	        },
 	        
 	        submitHandler: function(form) {
 	            form.submit();
 	        }
 	    });
  });
 	</script>
 	
 	<script>
 	$(function() {
 	$('#addQuedadaB').click(function() {
 	    $('#registerquedada-form').toggle('slow');
 	});
 	});
 	</script>
 	 
     <h1 style="font-family: fantasy; color: cadetblue">Quedadas</h1>
     
     <c:choose>
     <c:when test="${not empty user}">
     
      <input type='button' id='addQuedadaB' value='Añadir quedada' class="button"><br><br>
      	
      	<div id="addQuedada"   >
 		<form action="addquedada" style="display:none" method="post" id="registerquedada-form" novalidate="novalidate">
 		<div class="label">Comunidad autónoma: </div>
 		<select name="caut" id="caut">
		<option value="Andalucia">Andalucía</option>
		<option value="Aragon">Aragón</option>
		<option value="Principado de Asturias">Principado de Asturias</option>
		<option value="Islas Baleares">Islas Baleares</option>
		<option value="Canarias">Canarias</option>
		<option value="Cantabria">Cantabria</option>
		<option value="Castilla-La Mancha">Castilla-La Mancha</option>
		<option value="Castilla y Leon">Castilla y León</option>
		<option value="Cataluña">Cataluña</option>
		<option value="Comunidad Valenciana">Comunidad Valenciana</option>
		<option value="Extremadura">Extremadura</option>
		<option value="Galicia">Galicia</option>
		<option value="La Rioja">La Rioja</option>
		<option value="Comunidad de Madrid">Comunidad de Madrid</option>
		<option value="Comunidad Foral de Navarra">Comunidad Foral de Navarra</option>
		<option value="Pais Vasco">País Vasco</option>
		<option value="Región de Murcia">Región de Murcia</option>
		<option value="Ceuta">Ceuta</option>
		<option value="Melilla"> Melilla</option>
		
		</select>
    	<div class="label">Ciudad</div><input type="text" id="ciudad" name="ciudad" /><br />
    	<div class="label">Cine</div><input type="text" id="cine" name="cine" /><br />
    	<div class="label">Pelicula</div><input type="text" id="peli" name="peli" /><br />
    	<div class="label">Descripcion</div><textarea type="text" id="descripcion" name="descripcion" style="width: 166px; height: 100px;"></textarea><br />
    	<div style="margin-left:140px;"><input type="submit" name="submit" value="Registrar" /></div>
    	</form>
 		</div>
 		<br><br>
 		Seleccione comunidad autonoma
 		<form action="quedadas"  method="get"  novalidate="novalidate">
 		
 		<select name="autonoma" id="autonoma">
 		<option value="Todas">Todas</option>
		<option value="Andalucia">Andalucía</option>
		<option value="Aragon">Aragón</option>
		<option value="Principado de Asturias">Principado de Asturias</option>
		<option value="Islas Baleares">Islas Baleares</option>
		<option value="Canarias">Canarias</option>
		<option value="Cantabria">Cantabria</option>
		<option value="Castilla-La Mancha">Castilla-La Mancha</option>
		<option value="Castilla y Leon">Castilla y León</option>
		<option value="Cataluña">Cataluña</option>
		<option value="Comunidad Valenciana">Comunidad Valenciana</option>
		<option value="Extremadura">Extremadura</option>
		<option value="Galicia">Galicia</option>
		<option value="La Rioja">La Rioja</option>
		<option value="Comunidad de Madrid">Comunidad de Madrid</option>
		<option value="Comunidad Foral de Navarra">Comunidad Foral de Navarra</option>
		<option value="Pais Vasco">País Vasco</option>
		<option value="Región de Murcia">Región de Murcia</option>
		<option value="Ceuta">Ceuta</option>
		<option value="Melilla"> Melilla</option>
		</select>		
		<input type="submit" name="submit" id='mostrarQuedada' value='Seleccionar Comunidad' class="button">		
		</form>
		<br><br>
		<table class="users">
		<thead>
		<tr><td style="background: lightgray;">Comunidad autónoma
			<td style="background: lightgray;">Ciudad
			<td style="background: lightgray;">Cine
			<td style="background: lightgray;">Pelicula
			<td style="background: lightgray;">Más información</tr>
		</thead>
		<tbody>
			<c:forEach items="${quedada}" var="q">
				<tr>
				<td>${e:forHtmlContent(q.comunidadAut)}
				<td>${e:forHtmlContent(q.ciudad)}
				<td>${e:forHtmlContent(q.cine)}
				<td>${e:forHtmlContent(q.pelicula)}
				<td><a href="${prefix}quedada?q=${e:forHtmlContent(q.id)}">Ir</a>
				</tr>
			</c:forEach>
		</tbody>	
		</table>
		</c:when>
		<c:otherwise>
		Necesitas estar registrado para acceder a las quedadas.
		</c:otherwise>
		</c:choose> 

<%@ include file="../Fragments/footer.jspf" %>