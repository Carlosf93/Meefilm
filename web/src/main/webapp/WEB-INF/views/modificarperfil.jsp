<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="../Fragments/header.jspf" %>
 	
 	 	<script>
 	$(function() {
 		$("#register-form").validate({
 		    
 	        // Specify the validation rules
 	        rules: {
 	            nombre: "required",
 	            apellidos: "required",
 	            ciudad: "required",
 	            fdn: "required",
 	            email: {
 	                required: true,
 	                email: true
 	            },
 	            password: {
 	                minlength: 5
 	            },
 	            nombreusu: {
 	                required: true,
 	                minlength: 5
 	            }
 	        },
 	        
 	        // Specify the validation error messages
 	        messages: {
 	            nombre: "Por favor introduzca su nombre",
 	            apellidos: "Por favor introduzca sus apellidos",
 	            ciudad: "Por favor introduzca su ciudad",
 	            fdn: {required: "Por favor introduzca su fecha de nacimiento",
 	            	max: "fecha incorrecta"},       
 	            password: {
 	                minlength: "Su contraseña debe tener al menos 5 caracteres"
 	            },
 	            nombreusu: {
 	                required: "Por favor introduzca su nombre de usuario",
 	                minlength: "Su nombre de usuario debe tener al menos 5 caracteres"
 	            },
 	            email: {
 	            	email: "Por favor introduzca un email valido", 
 	            	required: "Por favor introduzca su email"
 	            }
 	        },
 	        
 	        submitHandler: function(form) {
 	            form.submit();
 	        }
 	    });
  });
 	</script>
 	
 	<h2>Modificar perfil</h2>
 	<div id="areaReg">
 	
 	<form action="updateprofile" method="post" id="register-form" novalidate="novalidate" enctype="multipart/form-data">
 	<div class="label">Nombre de Usuario: </div><input type="text" id="nombreusu" name="nombreusu" value="${e:forHtmlContent(user.login)}" readonly style="background: lightgray;"/><br/>
    <div class="label">Nombre: </div><input type="text" id="nombre" name="nombre" value="${e:forHtmlContent(user.nombre)}"/><br />
    <div class="label">Email</div><input type="text" id="email" name="email" value="${e:forHtmlContent(user.correo)}"/><br />
    <div class="label">Profesión</div><input type="text" id="profesion" name="profesion" value="${e:forHtmlContent(user.profesion)}"/><br />
    <div class="label">Aficiones</div><input type="text" id="aficiones" name="aficiones" value="${e:forHtmlContent(user.aficiones)}"/><br />
    <div class="label">Ciudad</div><input type="text" id="ciudad" name="ciudad" value="${e:forHtmlContent(user.ciudad)}"/><br />
    <div class="label">Fecha de nacimiento</div><input type="date" id="fdn" name="fdn" value="${e:forHtmlContent(user.fecha_nacimiento)}"/><br />
    <div class="label">Cambiar contraseña</div><input type="password" id="password" name="password" /><br />
    <br>
	Cambiar foto de perfil: <br>
	<input type="file" name="photo" id="photo"><br />
	<input hidden="submit" name="id" value="${user.id}" />
    <br>
    <input type="submit" name="submit" value="Aceptar cambios" />
    </form>
 
 </div> 
 <%@ include file="../Fragments/footer.jspf" %>