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
				<c:if test="${!empty sessionScope.user.profile.typology.typologyCode }">
      				<c:set var="typologyCode" value="${sessionScope.user.profile.typology.typologyCode }" scope="page"></c:set>
      			</c:if>
      			<form class="form-inline">
					<div class="row">
						<div class="col-md-4">
    						<input type="text" class="form-control" id="keyWord">
  						</div>
  						<div class="col-md-1 clearfix">
  							<button type="submit" class="btn btn-outline-light mb-2 float-left"><i class="bi bi-search"></i></button>
  						</div>
  						<div class="col-md-6"></div>
  						<c:if test="${typologyCode == 'A' }">
  							<div class="col-md-1 clearfix">
  								<a class="btn btn-outline-light mb-2 float-end" role="button" href="${pageContext.request.contextPath}/formations/create">
  									<i class="bi bi-file-earmark-plus"></i>
  								</a>
  							</div>
  						</c:if>
					</div>
				</form>
				<br>
				<div class="row">
					<c:forEach items="${formations }" var="formation">
						<div class="col-md-4 mb-4">
						<div class="card" style="width: 18rem;">
  							<div class="card-body">
    							<h5 class="card-title"><c:out value="${formation.formationLabel }" escapeXml="false"></c:out></h5>
    							<h6 class="card-subtitle mb-2 text-muted"><c:out value="${formation.formationType.formationTypeLabel }" escapeXml="false"></c:out> / <c:out value="${formation.formationDuration } mois" escapeXml="false"></c:out><c:if test="${typologyCode == 'E' }"> <span class="badge text-bg-${formation.statusColor}"><c:out value="${formation.statusLabel }" escapeXml="false"></c:out></span></c:if></h6>
    							<p class="card-text"><c:out value="${formation.formationDescription }" escapeXml="false"></c:out></p>
    							<c:if test="${typologyCode == 'A' }">
    								<a class="btn btn-outline-warning mb-2" role="button" href="${pageContext.request.contextPath}/formations/edit?formationId=${formation.formationId}">
  										<i class="bi bi-pen"></i>
  									</a>
  									<button type="button" class="btn btn-outline-danger mb-2" onclick="deleteFormation(${formation.formationId})">
  										<i class="bi bi-trash3"></i>
  									</button>
    							</c:if>
    							<c:if test="${typologyCode == 'E' && formation.showButton }">
  									<button type="button" class="btn btn-outline-primary mb-2" onclick="startOrEndFormation(${formation.formationId }, '${formation.statusLabel}', '${formation.formationUrl}');">
  										<c:out value="${formation.buttonLabel }" escapeXml="false"></c:out>
  									</button>
    							</c:if>
  							</div>
						</div>
						</div>
					</c:forEach>
				</div>
			</div>
		</div>
		<script type="text/javascript">
			function onLoad() {
				$("#formations").removeClass("text-dark");
				$("#formations").addClass("active-menu");
			}
			
			function deleteFormation(formationId) {
				Swal.fire({
			  		title: 'Êtes vous sûre?',
			  		text: "Voulez vous vraiment supprimer cette formation?",
			  		icon: 'warning',
			  		showCancelButton: true,
			  		confirmButtonColor: '#3085d6',
			  		cancelButtonColor: '#d33',
			  		confirmButtonText: 'Oui',
			  		cancelButtonText: 'Non'
				}).then((result) => {
					if (result.isConfirmed) {
						window.location.href = "/stage2/formations?action=delete&formationId="+formationId;
					}
				})
			}
			
			function startOrEndFormation(formationId, status, url) {
				if("A passer" == status) {
					window.location.href = "/stage2/formations?action=start&formationId="+formationId;
					window.open(url, '_blank');
				} else {
					window.location.href = "/stage2/formations?action=end&formationId="+formationId;
				}
			}
		</script>
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous"></script>
		<script src="//cdn.jsdelivr.net/npm/sweetalert2@11"></script>
		<script src="sweetalert2.all.min.js"></script>
	</body>
</html>