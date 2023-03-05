<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>Accueil</title>
		<link href="<c:url value="/css/comun.css" />" rel="stylesheet">
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">
		<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.3/font/bootstrap-icons.css">
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
	</head>
	<body onload="onLoad()">
		<div class="">
			<%@ include file="menu.jsp"%>
			<div class="page-content p-5" id="content">
      			<form class="form-inline" action="typologies" method="post">
					<div class="row">
						<div class="col-md-4">
    						<input onkeyup="onChangeInputValue(this);" placeholder="Libellé" type="text" class="form-control" id="keyWord" name="keyWord">
  						</div>
  						<div class="col-md-1 clearfix">
  							<button disabled id="search" type="submit" class="btn btn-outline-light mb-2 float-left"><i class="bi bi-search"></i></button>
  						</div>
  						<div class="col-md-6"></div>
  						<div class="col-md-1 clearfix">
  							<a class="btn btn-outline-light mb-2 float-end" role="button" href="${pageContext.request.contextPath}/typologies/create">
  								<i class="bi bi-plus"></i>
  							</a>
  						</div>
					</div>
				</form>
				<br>
				<table class="table align-middle mb-0 bg-white">
  <thead class="bg-light">
    <tr>
      <th>Code</th>
      <th>Libellé</th>
      <th>Actions</th>
    </tr>
  </thead>
  <tbody>
  <c:if test="${!empty message }">
  	<div class="alert alert-danger" role="alert">
  		<c:out value="${message }" />
	</div>
  </c:if>
  <c:choose>
  	<c:when test="${!empty typologies }">
  		<c:forEach items="${typologies }" var="typology">
  	<tr>
      <td>
      	<p class="fw-normal mb-1"><c:out value="${typology.typologyCode }" escapeXml="false"></c:out></p>
      </td>
      <td>
      	<p class="fw-normal mb-1"><c:out value="${typology.typologyLabel }" escapeXml="false"></c:out></p>
      </td>
      <td>
      	<a class="btn btn-outline-warning mb-2" role="button" href="${pageContext.request.contextPath}/typologies/edit?typologyId=${typology.typologyId}" >
  			<i class="bi bi-pen"></i>
  		</a>
  		<button type="button" class="btn btn-outline-danger mb-2" onclick="deleteTypology(${typology.typologyId})">
  			<i class="bi bi-trash3"></i>
  		</button>
      </td>
    </tr>
  </c:forEach>
  	</c:when>
  	<c:otherwise>
  		<tr>
  			<td>Aucune typologie à afficher.</td>
  		</tr>
  	</c:otherwise>
  </c:choose>
  </tbody>
</table>
			</div>
		</div>
		<script type="text/javascript">
			function onLoad() {
				$("#typologies").removeClass("text-dark");
				$("#typologies").addClass("active-menu");
			}
			
			function onChangeInputValue(el) {
				if($(el).val() != "") {
					$("#search").removeAttr('disabled');
				} else {
					$("#search").attr('disabled', true);
				}
			}
			
			function deleteTypology(typologyId) {
				Swal.fire({
			  		title: 'Êtes vous sûre?',
			  		text: "Voulez vous vraiment supprimer cette typologie?",
			  		icon: 'warning',
			  		showCancelButton: true,
			  		confirmButtonColor: '#3085d6',
			  		cancelButtonColor: '#d33',
			  		confirmButtonText: 'Oui',
			  		cancelButtonText: 'Non'
				}).then((result) => {
					if (result.isConfirmed) {
						window.location.href = "/stage2/typologies?action=delete&typologyId="+typologyId;
					}
				})
			}
		</script>
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous"></script>
		<script src="//cdn.jsdelivr.net/npm/sweetalert2@11"></script>
		<script src="sweetalert2.all.min.js"></script>
	</body>
</html>