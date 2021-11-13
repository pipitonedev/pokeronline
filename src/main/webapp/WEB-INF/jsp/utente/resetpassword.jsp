<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!doctype html>
<html lang="it" class="h-100" >
	 <head>
	 
	 	<!-- Common imports in pages -->
	 	<jsp:include page="../header.jsp" />
	 	 <style>
		    .error_field {
		        color: red; 
		    }
		</style>
	   
	   <title>Cambio Password</title>
	 </head>
	   <body class="d-flex flex-column h-100">
	   
	   		<!-- Fixed navbar -->
	   		<jsp:include page="../navbar.jsp"></jsp:include>
	    
			
			<!-- Begin page content -->
			<main class="flex-shrink-0">
			  <div class="container">
			  
			  		<div class="alert alert-danger alert-dismissible fade show ${errorMessage==null?'d-none':'' }" role="alert">
					  ${errorMessage}
					  <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close" ></button>
					</div>
			  
			  <div class='card'>
				    <div class='card-header'>
				        <h5>Cambio Password</h5> 
				    </div>
				    <div class='card-body'>
		
							<h6 class="card-title">I campi con <span class="text-danger">*</span> sono obbligatori</h6>
		
		
							<form:form method="post" action="saveCambioPassword" novalidate="novalidate" class="row g-3">
					
							
								<div class="col-md-6">
									<label for="nome" class="form-label">Vecchia Password <span class="text-danger">*</span></label>
									<input type="password" name="vecchiaPassword" id="vecchiaPassword" class="form-control" placeholder="Inserire vecchia password" required>
								</div>
								
								<div class="col-md-6">
									<label for="nuovaPassword" class="form-label">Nuova Password <span class="text-danger">*</span></label>
									<input type="password" name="nuovaPassword" id="nuovaPassword" class="form-control" placeholder="Inserire nuova password" required>
								</div>
								<div class="col-md-6">
									<label for="nuovaPasswordRep" class="form-label">Ripeti nuova password <span class="text-danger">*</span></label>
									<input type="password" class="form-control" name="nuovaPasswordRep" id="nuovaPasswordRep" placeholder="Ripetere nuova password" required>
								</div>
								
								<div class="col-12">
									<button type="submit" name="submit" value="submit" id="submit" class="btn btn-primary">Conferma</button>
									<a class="btn btn-outline-primary ml-2" href="${pageContext.request.contextPath}/home">Back</a>
								</div>
		
						</form:form>
  
				    
				    
					<!-- end card-body -->			   
				    </div>
				<!-- end card -->
				</div>		
					  
			    
			  <!-- end container -->  
			  </div>
			  
			</main>
			
			<!-- Footer -->
			<jsp:include page="../footer.jsp" />
	  </body>
</html>