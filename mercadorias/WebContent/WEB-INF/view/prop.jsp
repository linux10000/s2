<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/recursos/bootstrap/css/bootstrap.min.css">	
	<link rel="stylesheet" href="<%=request.getContextPath()%>/recursos/bootstrap/css/bootstrap-theme.min.css">	
	<link rel="stylesheet" href="<%=request.getContextPath()%>/recursos/jquery-ui-1.10.3.custom/css/smoothness/jquery-ui-1.10.3.custom.css" />
	

	<link href="<%=request.getContextPath()%>/recursos/css/style.css" type="text/css" rel="stylesheet"/>	
	
	
	<script type="text/javascript" src="<%=request.getContextPath()%>/recursos/jquery/jquery-1.10.2.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/recursos/jquery-ui-1.10.3.custom/jquery-ui-1.10.3.custom.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/recursos/bootstrap/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/recursos/jquery-mask-plugin/jquery-mask-plugin.js" charset="UTF-8"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/recursos/blockui/jquery.blockUI.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/recursos/js/helper.js" charset="UTF-8"></script>
	

<title>Mercadorias Properties</title>
	<script>
		function obtemContextPath(){
			return '<%=request.getContextPath()%>';
		}
		
		function logout(){
			$.ajax
			  ({
			    type: "GET",
			    url: obtemContextPath() + "/sistema/prop",
			    dataType: 'json',
			    async: false,
			    data: '{}',
			    beforeSend: function (xhr){ 
			    	xhr.setRequestHeader('Authorization', "Basic xxx");
			    },
			    error: function (xhr, ajaxOptions, thrownError) {
			        window.location = obtemContextPath() + '/sistema/home.html';
			      }
			    
			});
		}
		
		$(document).ready(function() {
			
			inicializaFormAjax('#div_form', 
								'#form_manut', 
								'<%=request.getContextPath()%>/sistema/prop/salvar',
								null,
								null
			);		
			
		});
	</script>
</head>
<body>
	<h1 align="center"><b>Mercadorias Properties</b></h1>
	<br/>

		<table align="center">
			<tr>
				<td>
					<form:form id="form_manut" modelAttribute="item_formulario" acceptCharset="ISO-8859-1">
						<div id="div_form">
							<form:label path="path" cssErrorClass="erros">Path MySql</form:label>
						    <form:input path="path" size="150"/>
						    
						    <br/>

						    <form:label path="usuario" cssErrorClass="erros">Usuario MySql</form:label>
						    <form:input path="usuario" size="150"/>
						    
						    <br/>
						    
						    <form:label path="enoc" cssErrorClass="erros">Senha MySql</form:label>
						    <form:input path="enoc" size="150"/>
						    
						    <br/>
						    
						    <input type="submit" value="Salvar">
						    
						    <br/>
						    
						    <form:errors path="*" element="div"  class="errorblock"/>	
					    </div>
					</form:form>
				</td>
			</tr>	
		</table>
	
	<h1 align="center"><a onclick="logout();">Sair</a></h1>
</body>
</html>