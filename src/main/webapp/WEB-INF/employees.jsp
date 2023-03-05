<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>Mes employés</title>
		<link href="<c:url value="/css/comun.css" />" rel="stylesheet">
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">
		<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.3/font/bootstrap-icons.css">
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
	</head>
	<body onload="onLoad()">
		<div class="">
			<%@ include file="menu.jsp"%>
			<div class="page-content p-5" id="content">
				<form action="employees" method="post" class="form-inline" >
					<div class="row">
						<div class="col-md-4">
    						<input onkeyup="onChangeInputValue(this);" placeholder="E-mail" type="email" class="form-control" id="keyWord" name="keyWord">
  						</div>
  						<div class="col-md-1 clearfix">
  							<button disabled id="search" type="submit" class="btn btn-outline-light mb-2 float-left"><i class="bi bi-search"></i></button>
  						</div>
					</div>
				</form>
				<br>
				<div class="row">
					<c:if test="${!empty message }">
                  		<div class="alert alert-danger" role="alert">
                  			<c:out value="${message }" />
						</div>
                  	</c:if>
					<c:forEach items="${employees }" var="employee" varStatus="status">
					<div class="col-md-4 mb-4">
						<div class="card" style="width: 18rem;">
  						<img src="${pageContext.request.contextPath}/images/avatar.jpg" class="card-img-top" alt="...">
  						<div class="card-body">
    						<h5 class="card-title"><c:out value="${employee.employeeLastName} ${employee.employeeFirstName}"></c:out></h5>
    						<p class="card-text"><c:out value="${employee.employeeEmail}"></c:out></p>
  						</div>
						</div>
					</div>
  					</c:forEach>
				</div>
			</div>
		<script type="text/javascript">
			function onLoad() {
				$("#employees").removeClass("text-dark");
				$("#employees").addClass("active-menu");
			}
			
			function onChangeInputValue(el) {
				if($(el).val() != "") {
					$("#search").removeAttr('disabled');
				} else {
					$("#search").attr('disabled', true);
				}
			}
		</script>
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous"></script>
	</body>
</html>