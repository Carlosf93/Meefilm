<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
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
 	                required: true,
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
 	                required: "Por favor introduzca su contraseña",
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
 	
 	<h2>Registro de nuevo usuario</h2>
 	<div id="areaReg">
 	
 	<form action="register" method="post" id="register-form" novalidate="novalidate">
    <div class="label">Nombre: </div><input type="text" id="nombre" name="nombre" /><br />
    <div class="label">Apellidos: </div><input type="text" id="apellidos" name="apellidos" /><br />
    <div class="label">Nombre de Usuario: </div><input type="text" id="nombreusu" name="nombreusu" /><br />
    <div class="label">Email</div><input type="text" id="email" name="email" /><br />
    <div class="label">Password</div><input type="password" id="password" name="password" /><br />
    <div class="label">Profesión</div><input type="text" id="profesion" name="profesion" /><br />
    <div class="label">Aficiones</div><input type="text" id="aficiones" name="aficiones" /><br />
    <div class="label">Ciudad</div><input type="text" id="ciudad" name="ciudad" /><br />
    <div class="label">Fecha de nacimiento</div><input type="date" id="fdn" name="fdn" /><br />
    <div style="margin-left:140px;"><input type="submit" name="submit" value="Registrar" /></div>
    </form>
 
 </div> 
 <%@ include file="../Fragments/footer.jspf" %>