<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title><c:out value="${title }" escapeXml="false"></c:out></title>
		<link href="<c:url value="/css/comun.css" />" rel="stylesheet">
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">
		<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.3/font/bootstrap-icons.css">
	</head>
	<body>
		<div class="">
			<%@ include file="menu.jsp"%>
			<div class="page-content p-5" id="content">
				<a class="btn btn-outline-light" href="${pageContext.request.contextPath}/requests"><i class="bi bi-arrow-left"></i></a>
				<h3 class="form-label text-center"><c:out value="${title }" escapeXml="false"></c:out></h3>
				<br>
				<br>
				<form name="requestForm" method="post" action="edit">
					<c:if test="${!empty message }">
                  		<div class="alert alert-danger" role="alert">
                  			<c:out value="${message }" />
						</div>
                  	</c:if>
					<input type="hidden" name="requestId" value="${editRequest.requestId }">
					<input type="hidden" name="number" value="${editRequest.requestNumber }">
					<input type="hidden" name="changeFormationType" value="false">
					<input type="hidden" name="changeFormation" value="false">
					<div class="mb-3">
    					<label class="form-label" for="number" class="form-label">Numéro de demande</label>
    					<input type="text" class="form-control" id="number" 
    					value="${editRequest.requestNumber }" disabled>
  					</div>
  					<div class="mb-3">
    					<label class="form-label" for="formationType" class="form-label">Type de formation *</label>
    					<select class="form-select" id="formationType" name="formationType" onchange="onChangeFormationType();">
  							<option value="0" selected>Selectionnez...</option>
  							<c:forEach items="${formationTypes }" var="formationType">
  								<c:choose>
  									<c:when test="${editRequest.formation.formationType.formationTypeId == formationType.formationTypeId }">
  										<option value="${formationType.formationTypeId }" selected><c:out value="${formationType.formationTypeLabel }" escapeXml="false"></c:out></option>
  										<c:set var="formations" scope="page" value="${formationType.formations }"></c:set>
  									</c:when>
  									<c:otherwise>
  										<option value="${formationType.formationTypeId }"><c:out value="${formationType.formationTypeLabel }" escapeXml="false"></c:out></option>
  									</c:otherwise>
  								</c:choose>
  							</c:forEach>
						</select>
  					</div>
  					<c:if test="${!empty formations }">
  					<div class="mb-3">
    					<label class="form-label" for="formation" class="form-label">Formation *</label>
    					<select class="form-select" id="formation" name="formation" onchange="onChangeFormation();">
  							<option value="0" selected>Selectionnez...</option>
  							<c:forEach items="${formations }" var="formation">
  								<c:choose>
  									<c:when test="${editRequest.formation.formationId == formation.formationId }">
  										<option value="${formation.formationId }" selected><c:out value="${formation.formationLabel }" escapeXml="false"></c:out></option>
  									</c:when>
  									<c:otherwise>
  										<option value="${formation.formationId }"><c:out value="${formation.formationLabel }" escapeXml="false"></c:out></option>
  									</c:otherwise>
  								</c:choose>
  							</c:forEach>
						</select>
  					</div>
  					</c:if>
  					<div class="mb-3">
  						<label class="form-label" for="employees" class="form-label">Employés concernés *</label>
  						<ul class="list-group" id="employees">
  							<c:choose>
  								<c:when test="${!empty employees }">
  									<c:forEach items="${employees }" var="employee">
  										<li class="list-group-item">
  											<c:choose>
  												<c:when test="${employee.selected }">
  													<input class="form-check-input me-1" type="checkbox" value="${employee.employeeId }" name="selectedEmployees" checked>
  												</c:when>
  												<c:otherwise>
  													<input class="form-check-input me-1" type="checkbox" value="${employee.employeeId }" name="selectedEmployees">
  												</c:otherwise>
  											</c:choose>
    										<c:out value="${employee.employeeLastName } ${employee.employeeFirstName }"></c:out>
  										</li>
  									</c:forEach>
  								</c:when>
  								<c:otherwise>
  									<li class="list-group-item">
  										Aucun employé.
  									</li>
  								</c:otherwise>
  							</c:choose>
  						</ul>
  					</div>
  					<c:if test="${!empty employees }">
  						<div class="text-center">
  							<button type="submit" class="btn btn-outline-light">Envoyer</button>
  						</div>
  					</c:if>
				</form>
			</div>
		</div>
		<script type="text/javascript">
			function onChangeFormationType() {
				document.requestForm.changeFormationType.value = true;
				if(document.requestForm.formation != null) {
					document.requestForm.formation.value = "0";
				}
				document.requestForm.submit();
			}
			
			function onChangeFormation() {
				document.requestForm.changeFormation.value = true;
				document.requestForm.submit();
			}
		</script>
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous"></script>
	</body>
</html>