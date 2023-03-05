<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>Authentification</title>
		<link href="${pageContext.request.contextPath}/css/authentication.css" rel="stylesheet" type="text/css">
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">
	</head>
	<body>
		<div class="container py-5 h-100">
    <div class="row d-flex justify-content-center align-items-center h-100">
      <div class="col-xl-10">
        <div class="card rounded-3 text-black">
          <div class="row g-0">
            <div class="col-lg-6">
              <div class="card-body p-md-5 mx-md-4">

                <div class="text-center">
                  <img src="${pageContext.request.contextPath}/images/logo.PNG"
                    style="width: 185px;" alt="logo" width="100" height="120">
                  <h4 id="us" class="mt-1 mb-5 pb-1">Ste Ben Bazide</h4>
                </div>

                <form method="post" action="authentication">
                  <c:if test="${!empty message }">
                  	<div class="alert alert-danger" role="alert">
                  		<c:out value="${message }" />
					</div>
                  </c:if>

                  <div class="form-outline mb-4">
                    <input type="email" class="form-control"
                      placeholder="E-mail" name="username"
                      value="${!empty username ? username : '' }" />
                  </div>

                  <div class="form-outline mb-4">
                    <input type="password" class="form-control"
                      placeholder="Mot de passe" name="password"
                      value="${!empty password ? password : '' }" />
                  </div>
                  
                  <div class="form-outline mb-4">
                  	<div class="text-center pt-1 mb-5 pb-1">
                  		<button class="btn btn-primary btn-lg gradient-custom-2" type="submit">S'authentifier</button>
                  	</div>
                  </div>

                </form>

              </div>
            </div>
            <div class="col-lg-6 d-flex align-items-center gradient-custom-2">
              <div class="text-white px-3 py-4 p-md-5 mx-md-4">
                <h4 class="mb-4">Nous sommes plus qu'une simple entreprise</h4>
                <p class="small mb-0">Formation et sensibilisation du personnel sur la prevention contre les incendies et les accidents de travail, 
                la surveillance et le gardiennage et l’utilisation du materiel de securité.</p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
	</body>
</html>