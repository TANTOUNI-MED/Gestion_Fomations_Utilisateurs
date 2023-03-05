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
				<a class="btn btn-outline-light" href="${pageContext.request.contextPath}/roles"><i class="bi bi-arrow-left"></i></a>
				<h3 class="form-label text-center"><c:out value="${title }" escapeXml="false"></c:out></h3>
				<br>
				<br>
				<form name="roleForm" method="post" action="edit">
					<c:if test="${!empty message }">
                  		<div class="alert alert-danger" role="alert">
                  			<c:out value="${message }" />
						</div>
                  	</c:if>
					<input type="hidden" name="roleId" value="${editRole.roleId }">
					<input type="hidden" name="changeTypology">
					<div class="mb-3">
    					<label class="form-label" for="roleLabel" class="form-label">libellé *</label>
    					<input type="text" class="form-control" id="roleLabel" name="roleLabel"
    					value="${editRole.roleLabel }">
  					</div>
  					<div class="mb-3">
    					<label class="form-label" for="typology" class="form-label">Typologie *</label>
    					<select class="form-select" id="typology" name="typology" onchange="onChangeTypology();">
  							<option value="0" selected>Selectionnez...</option>
  							<c:forEach items="${typologies }" var="typology">
  								<c:choose>
  									<c:when test="${editRole.profile.typology.typologyId == typology.typologyId }">
  										<option value="${typology.typologyId }" selected><c:out value="${typology.typologyLabel }" escapeXml="false"></c:out></option>
  										<c:set var="profiles" scope="page" value="${typology.profiles }"></c:set>
  									</c:when>
  									<c:otherwise>
  										<option value="${typology.typologyId }"><c:out value="${typology.typologyLabel }" escapeXml="false"></c:out></option>
  									</c:otherwise>
  								</c:choose>
  							</c:forEach>
						</select>
  					</div>
  					<c:if test="${!empty profiles }">
  					<div class="mb-3">
    					<label class="form-label" for="profile" class="form-label">Profil *</label>
    					<select class="form-select" id="profile" name="profile">
  							<option value="0" selected>Selectionnez...</option>
  							<c:forEach items="${profiles }" var="profile">
  								<c:choose>
  									<c:when test="${editRole.profile.profileId == profile.profileId }">
  										<option value="${profile.profileId }" selected><c:out value="${profile.profileLabel }" escapeXml="false"></c:out></option>
  									</c:when>
  									<c:otherwise>
  										<option value="${profile.profileId }"><c:out value="${profile.profileLabel }" escapeXml="false"></c:out></option>
  									</c:otherwise>
  								</c:choose>
  							</c:forEach>
						</select>
  					</div>
  					</c:if>
  					<c:if test="${!empty profiles }">
  						<div class="text-center">
  							<button type="submit" class="btn btn-outline-light">Enregistrer</button>
  						</div>
  					</c:if>
				</form>
			</div>
		</div>
		<script type="text/javascript">
			function onLoad() {
				$("#roles").removeClass("text-dark");
				$("#roles").addClass("active-menu");
			}
			
			function onChangeTypology() {
				document.roleForm.changeTypology.value = true;
				document.roleForm.submit();
			}
		</script>
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous"></script>
	</body>
</html>