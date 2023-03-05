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
				<a class="btn btn-outline-light" href="${pageContext.request.contextPath}/customers"><i class="bi bi-arrow-left"></i></a>
				<h3 class="form-label text-center"><c:out value="${title }" escapeXml="false"></c:out></h3>
				<br>
				<br>
				<form name="customerForm" method="post" action="edit">
					<input type="hidden" name="id" value="${editCustomer.customerId }">
					<div class="mb-3">
    					<label class="form-label" for="externalCode" class="form-label">Code externe *</label>
    					<input type="text" class="form-control" id="externalCode" name="externalCode"
    					value="${editCustomer.customerExternalCode }">
  					</div>
					<div class="mb-3">
    					<label class="form-label" for="label" class="form-label">Libellé *</label>
    					<input type="text" class="form-control" id="label" name="label"
    					value="${editCustomer.customerLabel }">
  					</div>
  					<div class="mb-3">
    					<label class="form-label" for="email" class="form-label">E-mail *</label>
    					<input type="email" class="form-control" id="email" name="email"
    					value="${editCustomer.customerEmail }">
  					</div>
  					<div class="mb-3">
    					<label class="form-label" for="phoneNumber" class="form-label">Numéro de téléphone *</label>
    					<input type="tel" class="form-control" id="phoneNumber" name="phoneNumber"
    					value="${editCustomer.customerPhoneNumber }">
  					</div>
  					<div class="text-center">
  						<button type="submit" class="btn btn-outline-light">Enregistrer</button>
  					</div>
				</form>
			</div>
		</div>
		<script type="text/javascript">
			function onLoad() {
				$("#customers").removeClass("text-dark");
				$("#customers").addClass("active-menu");
			}
		</script>
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous"></script>
	</body>
</html>