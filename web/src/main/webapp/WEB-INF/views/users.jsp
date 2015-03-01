<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="../Fragments/header.jspf" %>
     
    <h1 style="font-family: fantasy; color: cadetblue">Usuarios</h1>
    
    <c:forEach items="${users}" var="u">
    <c:if test="${u.role != 'admin'}">
	<c:if test="${u.id != user.id}">
	<div class="bloquelista">
    	<div class="useravatar"><img src="user/photo?id=${e:forHtmlContent(u.id)}" style="width: 100%; height: 100%;" /></div>
    	<div class="textolista">
    		<span class="titulolista">
    			<a href="${prefix}usuario?u=${e:forHtmlContent(u.id)}" style="color: cadetblue">${e:forHtmlContent(u.login)}</a>
    		</span><br><br>
    		<span class="sinopsislista">
    			Ciudad: ${u.ciudad}
    		</span><br><br>
    		<span class="generolista">
    			Edad: ${u.edad}
    		</span>
    	</div>
	</div>
	</c:if>
	</c:if>
	</c:forEach>
	 
<%@ include file="../Fragments/footer.jspf" %>