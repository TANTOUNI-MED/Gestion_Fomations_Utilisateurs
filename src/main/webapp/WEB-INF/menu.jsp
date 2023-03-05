<div class="vertical-nav bg-white" id="sidebar">
  <div class="py-4 px-3 mb-4 bg-light">
    <div class="media d-flex align-items-center"><img src="${pageContext.request.contextPath}/images/avatar.jpg" alt="..." width="65" class="mr-3 rounded-circle img-thumbnail shadow-sm">
      <div class="media-body ms-2">
      <c:if test="${!empty sessionScope.user }">
      	<c:if test="${!empty sessionScope.user.userLastName && !empty sessionScope.user.userFirstName }">
      		<h5 class="m-0">
      			<c:out value="${sessionScope.user.userLastName }" escapeXml="false"></c:out> <c:out value="${sessionScope.user.userFirstName }" escapeXml="false"></c:out>
      		</h5>
      	</c:if>
      	<c:if test="${!empty sessionScope.user.profile && !empty sessionScope.user.profile.typology && !empty sessionScope.user.profile.typology.typologyLabel }">
      		<c:if test="${!empty sessionScope.user.profile.typology.typologyCode }">
      			<c:set var="typologyCode" value="${sessionScope.user.profile.typology.typologyCode }" scope="page"></c:set>
      		</c:if>
      		<c:if test="${!empty sessionScope.user.profile.typology.typologyLabel }">
      			<p class="font-weight-light text-muted mb-0">
      				<c:out value="${sessionScope.user.profile.typology.typologyLabel }" escapeXml="false" ></c:out>
      			</p>
      		</c:if>
      	</c:if>
      </c:if>
      </div>
    </div>
  </div>

  <ul class="nav flex-column bg-white mb-0">
  	<li class="nav-item">
  		<a id="home" href="${pageContext.request.contextPath}/" class="nav-link text-dark font-italic">
  			<i class="bi bi-house"></i>
  			Accueil
  		</a>
  	</li>
  	<c:choose>
  		<c:when test="${typologyCode == 'A' }">
    		<li class="nav-item">
      			<a id="users" href="${pageContext.request.contextPath}/users" class="nav-link text-dark font-italic">
                	<i class="bi bi-people"></i>
                	Utilisateurs
            	</a>
    		</li>
    		<li class="nav-item">
      			<a id="typologies" href="${pageContext.request.contextPath}/typologies" class="nav-link text-dark font-italic">
      				<i class="bi bi-list-task"></i>
                	Typologies d'utilisateurs
            	</a>
    		</li>
    		<li class="nav-item">
      			<a id="profiles" href="${pageContext.request.contextPath}/profiles" class="nav-link text-dark font-italic">
      				<i class="bi bi-list-task"></i>
                	Profils
            	</a>
    		</li>
    		<li class="nav-item">
      			<a id="roles" href="${pageContext.request.contextPath}/roles" class="nav-link text-dark font-italic">
      				<i class="bi bi-list-task"></i>
                	Rôles
            	</a>
    		</li>
    		<li class="nav-item">
      			<a id="formations" href="${pageContext.request.contextPath}/formations" class="nav-link text-dark font-italic">
      				<i class="bi bi-file-earmark"></i>
                	Formations
            	</a>
    		</li>
    		<li class="nav-item">
      			<a id="formationTypes" href="${pageContext.request.contextPath}/formationTypes" class="nav-link text-dark font-italic">
      				<i class="bi bi-list-task"></i>
                	Types de formations
            	</a>
    		</li>
    		<li class="nav-item">
      			<a id="customers" href="${pageContext.request.contextPath}/customers" class="nav-link text-dark font-italic">
      				<i class="bi bi-building"></i>
                	Clients
            	</a>
    		</li>
  		</c:when>
  		<c:when test="${typologyCode == 'F' }">
  			<li class="nav-item">
      			<a id="requests" href="${pageContext.request.contextPath}/requests" class="nav-link text-dark font-italic">
      				<i class="bi bi-cart-fill"></i>
                	Demandes
            	</a>
    		</li>
    		<li class="nav-item">
      			<a id="formations" href="${pageContext.request.contextPath}/formations" class="nav-link text-dark font-italic">
      				<i class="bi bi-file-earmark"></i>
                	Formations
            	</a>
    		</li>
  		</c:when>
  		<c:when test="${typologyCode == 'C' }">
  			<li class="nav-item">
      			<a id="employees" href="${pageContext.request.contextPath}/employees" class="nav-link text-dark font-italic">
                	<i class="bi bi-people"></i>
                	Mes employés
            	</a>
    		</li>
  			<li class="nav-item">
      			<a id="requests" href="${pageContext.request.contextPath}/requests" class="nav-link text-dark font-italic">
      				<i class="bi bi-cart-fill"></i>
                	Mes demandes
            	</a>
    		</li>
    		<li class="nav-item">
      			<a id="formations" href="${pageContext.request.contextPath}/formations" class="nav-link text-dark font-italic">
      				<i class="bi bi-file-earmark"></i>
                	Formations
            	</a>
    		</li>
  		</c:when>
  		<c:when test="${typologyCode == 'E' }">
  			<li class="nav-item">
      			<a id="formations" href="${pageContext.request.contextPath}/formations" class="nav-link text-dark font-italic">
      				<i class="bi bi-file-earmark"></i>
                	Mes formations
            	</a>
    		</li>
  		</c:when>
  		<c:otherwise></c:otherwise>
  	</c:choose>
  </ul>
  <ul id="deconnexion" class="nav flex-column bg-white mb-0 fixed-bottom">
  	<li class="nav-item">
  		<a href="${pageContext.request.contextPath}/authentication?signOut" class="nav-link text-dark font-italic">
   			<i class="bi bi-box-arrow-right"></i>
    		Déconnexion
    	</a>
  	</li>
  </ul>
</div>