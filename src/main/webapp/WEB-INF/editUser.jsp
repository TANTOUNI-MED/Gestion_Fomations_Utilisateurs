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
				<a class="btn btn-outline-light" href="${pageContext.request.contextPath}/users"><i class="bi bi-arrow-left"></i></a>
				<h3 class="form-label text-center"><c:out value="${title }" escapeXml="false"></c:out></h3>
				<br>
				<br>
				<form name="userForm" method="post" action="edit">
					<c:if test="${!empty message }">
                  		<div class="alert alert-danger" role="alert">
                  			<c:out value="${message }" />
						</div>
                  	</c:if>
					<input type="hidden" name="userId" value="${editUser.userId }">
					<input type="hidden" name="changeTypology">
					<div class="mb-3">
    					<label class="form-label" for="lastName" class="form-label">Nom *</label>
    					<input type="text" class="form-control" id="lastName" name="lastName"
    					value="${editUser.userLastName }">
  					</div>
					<div class="mb-3">
    					<label class="form-label" for="firstName" class="form-label">Prénom *</label>
    					<input type="text" class="form-control" id="firstName" name="firstName"
    					value="${editUser.userFirstName }">
  					</div>
  					<div class="mb-3">
    					<label class="form-label" for="login" class="form-label">E-mail *</label>
    					<input type="email" class="form-control" id="login" name="login"
    					value="${editUser.userLogin }">
  					</div>
  					<div class="mb-3">
    					<label class="form-label" for="password" class="form-label">Mot de passe *</label>
    					<input type="text" class="form-control" id="password" name="password"
    					value="${editUser.userPassword }">
  					</div>
  					<div class="mb-3">
    					<label class="form-label" for="typology" class="form-label">Typologie *</label>
    					<select class="form-select" id="typology" name="typology" onchange="onChangeTypology();">
  							<option value="0" selected>Selectionnez...</option>
  							<c:forEach items="${typologies }" var="typology">
  								<c:choose>
  									<c:when test="${editUser.profile.typology.typologyId == typology.typologyId }">
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
  									<c:when test="${editUser.profile.profileId == profile.profileId }">
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
  					<c:if test="${!empty customers }">
  					<div class="mb-3">
    					<label class="form-label" for="customer" class="form-label">Client *</label>
    					<select class="form-select" id="customer" name="customer">
  							<option value="0" selected>Selectionnez...</option>
  							<c:forEach items="${customers }" var="customer">
  								<c:choose>
  									<c:when test="${editUser.customer.customerId == customer.customerId }">
  										<option value="${customer.customerId }" selected><c:out value="${customer.customerExternalCode } - ${customer.customerLabel }" escapeXml="false"></c:out></option>
  									</c:when>
  									<c:otherwise>
  										<option value="${customer.customerId }"><c:out value="${customer.customerExternalCode } - ${customer.customerLabel }" escapeXml="false"></c:out></option>
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
				$("#users").removeClass("text-dark");
				$("#users").addClass("active-menu");
			}
			
			function onChangeTypology() {
				document.userForm.changeTypology.value = true;
				document.userForm.submit();
			}
		</script>
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous"></script>
	</body>
</html>