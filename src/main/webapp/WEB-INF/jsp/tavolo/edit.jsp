<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!doctype html>
<html lang="it" class="h-100">
	<head>
		<jsp:include page="../header.jsp" />
		
	    <link rel="stylesheet" href="${pageContext.request.contextPath }/assets/css/jqueryUI/jquery-ui.min.css" />
		<style>
			.ui-autocomplete-loading {
				background: white url("../assets/img/jqueryUI/anim_16x16.gif") right center no-repeat;
			}
			.error_field {
		        color: red; 
		    }
		</style>
		<title>Modifica tavolo</title>
	    
	</head>
	<body class="d-flex flex-column h-100">
		<jsp:include page="../navbar.jsp" />
		
		<!-- Begin page content -->
		<main class="flex-shrink-0">
			<div class="container">
		
					<%-- se l'attributo in request ha errori --%>
					<spring:hasBindErrors  name="film_creatore_attr">
						<%-- alert errori --%>
						<div class="alert alert-danger " role="alert">
							Attenzione!! Sono presenti errori di validazione
						</div>
					</spring:hasBindErrors>
				
					<div class="alert alert-danger alert-dismissible fade show ${errorMessage==null?'d-none':'' }" role="alert">
					  ${errorMessage}
					  <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close" ></button>
					</div>
					
					<div class='card'>
					    <div class='card-header'>
					        <h5>Modifica tavolo</h5> 
					    </div>
					    <div class='card-body'>
			
								<form:form method="post" modelAttribute="update_tavolo_attr" action="${pageContext.request.contextPath}/tavolo/saveUpdate" novalidate="novalidate" class="row g-3">
								
									<input type="hidden" name="id" value="${update_tavolo_attr.id}">
								
									<div class="col-md-6">
										<label for="denominazione" class="form-label">Denominazione</label>
										<spring:bind path="denominazione">
											<input type="text" name="denominazione" id="denominazione" class="form-control ${status.error ? 'is-invalid' : ''}" placeholder="Inserire denominazione" value="${update_tavolo_attr.denominazione }">
										</spring:bind>
										<form:errors  path="denominazione" cssClass="error_field" />
									</div>
									
									<div class="col-md-6">	
										<fmt:formatDate pattern='yyyy-MM-dd' var="parsedDate" type='date' value='${update_tavolo_attr.dateCreated}' />
										<div class="form-group col-md-6">
											<label for="dateCreated" class="form-label">Data di creazione</label>
			                        		<spring:bind path="dateCreated">
				                        		<input class="form-control ${status.error ? 'is-invalid' : ''}" id="dateCreated" type="date" placeholder="dd/MM/yy"
				                            		title="formato : gg/mm/aaaa"  name="dateCreated" value="${parsedDate}" >
				                            </spring:bind>
			                            	<form:errors  path="dateCreated" cssClass="error_field" />
										</div>
									</div>
										
									<div class="col-md-6">
										<label for="esperienzaMin" class="form-label">Esperienza minima</label>
										<spring:bind path="esperienzaMin">
											<input type="number" class="form-control ${status.error ? 'is-invalid' : ''}" name="esperienzaMin" id="esperienzaMin" placeholder="Inserire esperienza minima" value="${update_tavolo_attr.esperienzaMin }">
										</spring:bind>
										<form:errors  path="esperienzaMin" cssClass="error_field" />
									</div>
									
									<div class="col-md-6">
										<label for="cifraMinima" class="form-label">Cifra minima</label>
										<spring:bind path="cifraMin">
											<input type="number" class="form-control ${status.error ? 'is-invalid' : ''}" name="cifraMin" id="cifraMin" placeholder="Inserire la cifra minima" value="${update_tavolo_attr.cifraMin }">
										</spring:bind>
										<form:errors  path="cifraMin" cssClass="error_field" />
									</div>
									
									<div class="col-12">	
										<button type="submit" name="submit" value="submit" id="submit" class="btn btn-primary">Conferma</button>
									</div>
									
								</form:form>
								
					    
						<!-- end card-body -->			   
					    </div>
					<!-- end card -->
					</div>
				<!-- end container -->
				</div>	
		
		<!-- end main -->	
		</main>
		<jsp:include page="../footer.jsp" />
		
	</body>
</html>