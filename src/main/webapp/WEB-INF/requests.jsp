<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>Demandes</title>
		<link href="<c:url value="/css/comun.css" />" rel="stylesheet">
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">
		<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.3/font/bootstrap-icons.css">
		<link rel="stylesheet" href="sweetalert2.min.css">
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
	</head>
	<body onload="onLoad()">
		<div class="">
			<%@ include file="menu.jsp"%>
			<div class="page-content p-5" id="content">
				<c:if test="${!empty sessionScope.user.profile.typology.typologyCode }">
      				<c:set var="typologyCode" value="${sessionScope.user.profile.typology.typologyCode }" scope="page"></c:set>
      			</c:if>
				<form class="form-inline" action="requests" method="post">
					<div class="row">
						<div class="col-md-4">
    						<input onkeyup="onChangeInputValue(this);" placeholder="N° de Demande" type="text" class="form-control" id="keyWord" name="keyWord">
  						</div>
  						<div class="col-md-1 clearfix">
  							<button disabled id="search" type="submit" class="btn btn-outline-light mb-2 float-left"><i class="bi bi-search"></i></button>
  						</div>
  						<div class="col-md-6"></div>
  						<c:if test="${typologyCode == 'C' }">
  							<div class="col-md-1 clearfix">
  								<a class="btn btn-outline-light mb-2 float-end" role="button" href="${pageContext.request.contextPath}/requests/create">
  									<i class="bi bi-cart-plus"></i>
  								</a>
  							</div>
  						</c:if>
					</div>
				</form>
				<br>
				<table class="table align-middle mb-0 bg-white">
  <thead class="bg-light">
    <tr>
      <th>Numéro de demande</th>
      <th>Date d'envoi</th>
      <th>Statut</th>
      <th>Formation</th>
      <th>Type de formation</th>
      <th>Employés</th>
      <th></th>
    </tr>
  </thead>
  <tbody>
  <c:choose>
  	<c:when test="${!empty requests }">
  	  <c:forEach items="${requests }" var="request">
  	<tr>
      <td>
        <p class="fw-normal mb-1"><c:out value="${request.requestNumber }" escapeXml="false"></c:out></p>
      </td>
      <td>
        <p class="fw-normal mb-1"><c:out value="${request.requestDate }" escapeXml="false"></c:out></p>
      </td>
      <td>
      	<span class="badge text-bg-${request.requestStatusColor}">
      		<c:out value="${request.requestStatusLabel }" escapeXml="false"></c:out>
      	</span>
      </td>
      <td>
      	<a href="${pageContext.request.contextPath}/formations?requestId=${request.requestId}">
      		<c:out value="${request.formation.formationLabel}" escapeXml="false"></c:out>
      	</a>
      </td>
      <td>
      	<p class="fw-normal mb-1"><c:out value="${request.formation.formationType.formationTypeLabel}" escapeXml="false"></c:out></p>
      </td>
      <td>
      	<a href="${pageContext.request.contextPath}/employees?requestId=${request.requestId}">
      		Concernés
      	</a>
      </td>
      <td>
      	<c:choose>
      		<c:when test="${typologyCode == 'C' && request.showActionsButtons}">
      			<a class="btn btn-outline-warning mb-2" role="button" href="${pageContext.request.contextPath}/requests/edit?requestId=${request.requestId}">
  					<i class="bi bi-pen"></i>
  				</a>
  				<button type="button" class="btn btn-outline-danger mb-2" onclick="deleteRequest(${request.requestId})">
  					<i class="bi bi-trash3"></i>
  				</button>
      		</c:when>
      		<c:otherwise>
      			<c:if test="${request.showAcceptButton }">
      				<a class="btn btn-outline-success mb-2" role="button" href="${pageContext.request.contextPath}/requests?action=accept&requestId=${request.requestId}">
  						Accepter
  					</a>
      			</c:if>
      		</c:otherwise>
      	</c:choose>
      </td>
    </tr>
  </c:forEach>
  	</c:when>
  	<c:otherwise>
  		<tr>
  			<td>Vous n'avez aucune demande.</td>
  		</tr>
  	</c:otherwise>
  </c:choose>
  </tbody>
</table>
			</div>
		</div>
		<script type="text/javascript">
			function onLoad() {
				$("#requests").removeClass("text-dark");
				$("#requests").addClass("active-menu");
			}
			
			function onChangeInputValue(el) {
				if($(el).val() != "") {
					$("#search").removeAttr('disabled');
				} else {
					$("#search").attr('disabled', true);
				}
			}
			
			function deleteRequest(requestId) {
				Swal.fire({
				  title: 'Êtes vous sûre?',
				  text: "Voulez vous vraiment supprimer cette demande?",
				  icon: 'warning',
				  showCancelButton: true,
				  confirmButtonColor: '#3085d6',
				  cancelButtonColor: '#d33',
				  confirmButtonText: 'Oui',
				  cancelButtonText: 'Non'
				}).then((result) => {
					if (result.isConfirmed) {
						window.location.href = "/stage2/requests?action=delete&requestId="+requestId;
					}
				})
			}
		</script>
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous"></script>
		<script src="//cdn.jsdelivr.net/npm/sweetalert2@11"></script>
		<script src="sweetalert2.all.min.js"></script>
	</body>
</html>