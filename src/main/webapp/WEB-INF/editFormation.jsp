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
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
	</head>
	<body onload="onLoad()">
		<div class="">
			<%@ include file="menu.jsp"%>
			<div class="page-content p-5" id="content">
				<a class="btn btn-outline-light" href="${pageContext.request.contextPath}/formations"><i class="bi bi-arrow-left"></i></a>
				<h3 class="form-label text-center"><c:out value="${title }" escapeXml="false"></c:out></h3>
				<br>
				<br>
				<form name="formationForm" method="post" action="edit">
					<input type="hidden" name="id" value="${editFormation.formationId }">
					<div class="mb-3">
    					<label class="form-label" for="code" class="form-label">Code *</label>
    					<input type="text" class="form-control" id="code" name="code"
    					value="${editFormation.formationCode }">
  					</div>
					<div class="mb-3">
    					<label class="form-label" for="label" class="form-label">Libellé *</label>
    					<input type="text" class="form-control" id="label" name="label"
    					value="${editFormation.formationLabel }">
  					</div>
					<div class="mb-3">
    					<label class="form-label" for="description" class="form-label">Déscription *</label>
    					<textarea rows="4" cols="5" class="form-control" id="description" name="description">
    						<c:out value="${editFormation.formationDescription }" escapeXml="false"></c:out>	
    					</textarea>
  					</div>
  					<div class="mb-3">
    					<label class="form-label" for="duration" class="form-label">Durée *</label>
    					<input type="number" class="form-control" id="duration" name="duration"
    					value="${editFormation.formationDuration }">
  					</div>
  					<div class="mb-3">
    					<label class="form-label" for="url" class="form-label">URL *</label>
    					<input type="text" class="form-control" id="url" name="url"
    					value="${editFormation.formationUrl }">
  					</div>
  					<div class="mb-3">
    					<label class="form-label" for="formationType" class="form-label">Type de formation *</label>
    					<select class="form-select" id="formationType" name="formationType">
  							<option value="0" selected>Selectionnez...</option>
  							<c:forEach items="${formationTypes }" var="formationType">
  								<c:choose>
  									<c:when test="${editFormation.formationType.formationTypeId == formationType.formationTypeId }">
  										<option value="${formationType.formationTypeId }" selected><c:out value="${formationType.formationTypeLabel }" escapeXml="false"></c:out></option>
  									</c:when>
  									<c:otherwise>
  										<option value="${formationType.formationTypeId }"><c:out value="${formationType.formationTypeLabel }" escapeXml="false"></c:out></option>
  									</c:otherwise>
  								</c:choose>
  							</c:forEach>
						</select>
  					</div>
  					<div class="text-center">
  						<button type="submit" class="btn btn-outline-light">Enregistrer</button>
  					</div>
				</form>
			</div>
		</div>
		<script type="text/javascript">
			function onLoad() {
				$("#formations").removeClass("text-dark");
				$("#formations").addClass("active-menu");
			}
		</script>
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous"></script>
	</body>
</html>