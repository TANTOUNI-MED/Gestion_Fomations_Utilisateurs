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
				<a class="btn btn-outline-light" href="${pageContext.request.contextPath}/typologies"><i class="bi bi-arrow-left"></i></a>
				<h3 class="form-label text-center"><c:out value="${title }" escapeXml="false"></c:out></h3>
				<br>
				<br>
				<form name="typologyForm" method="post" action="edit">
					<c:if test="${!empty message }">
                  		<div class="alert alert-danger" role="alert">
                  			<c:out value="${message }" />
						</div>
                  	</c:if>
					<input type="hidden" name="id" value="${editTypology.typologyId }">
					<div class="mb-3">
    					<label class="form-label" for="typologyCode" class="form-label">Code *</label>
    					<input type="text" class="form-control" id="typologyCode" name="typologyCode"
    					value="${editTypology.typologyCode }">
  					</div>
					<div class="mb-3">
    					<label class="form-label" for="typologyLabel" class="form-label">Libellé *</label>
    					<input type="text" class="form-control" id="typologyLabel" name="typologyLabel"
    					value="${editTypology.typologyLabel }">
  					</div>
  					<div class="text-center">
  						<button type="submit" class="btn btn-outline-light">Enregistrer</button>
  					</div>
				</form>
			</div>
		</div>
		<script type="text/javascript">
			function onLoad() {
				$("#typologies").removeClass("text-dark");
				$("#typologies").addClass("active-menu");
			}
		</script>
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous"></script>
	</body>
</html>