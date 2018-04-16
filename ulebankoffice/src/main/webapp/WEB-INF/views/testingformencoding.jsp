<%@ include file="/WEB-INF/views/include.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ page contentType="text/html; charset=UTF-8"%>

<%@ page
	import="com.google.appengine.api.blobstore.BlobstoreServiceFactory"%>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreService"%>
<%
    BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
%>

<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1" />

<link rel="stylesheet"
	href="/resources/services/css/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet"
	href="/resources/services/css/styles-responsive.css">

<script src="/resources/services/js/jquery.min.js"></script>

<!-- <script src="/resources/services/js/bootstrap.min.js"></script> -->

<script src="/resources/services/js/jquery-cookie.js"></script>

<script src="/resources/services/js/script.js"></script>

<script src="/resources/services/js/Chart.js"></script>

<!-- Favicon -->
<link rel="shortcut icon" href="/../favicon.ico" type="image/gif" />

<!-- bootstrap -->
<link href="/resources/template/css/bootstrap.min.css" rel="stylesheet"
	type="text/css" />

<!-- plugins -->
<link href="/resources/template/css/plugins-css.css" rel="stylesheet"
	type="text/css" />

<!-- mega menu -->
<link href="/resources/template/css/mega-menu/mega_menu.css"
	rel="stylesheet" type="text/css" />

<!-- default -->
<link href="/resources/template/css/default.css" rel="stylesheet"
	type="text/css" />

<!-- main style -->
<link href="/resources/template/css/style.css" rel="stylesheet"
	type="text/css" />

<!-- responsive -->
<link href="/resources/template/css/responsive.css" rel="stylesheet"
	type="text/css" />

<!-- custom style -->
<link href="/resources/template/css/custom.css" rel="stylesheet"
	type="text/css" />

<title><spring:message code="label.newquery" /></title>

<!-- GOOGLE ANALYTICS TRACKER -->
<script>
  			(function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
  			(i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
  			m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
  			})(window,document,'script','//www.google-analytics.com/analytics.js','ga');

  			ga('create', 'UA-72492123-1', 'auto');
  			ga('send', 'pageview');
		</script>

</head>

<body>

	<div class="page-wrapper">
		<!--=================================
 preloader -->



		<!--=================================
 preloader -->

		<!--=================================
 header -->
		<jsp:include page="/WEB-INF/views/header.jsp" />

		<!--=================================
 header -->
		<section class="white-bg page-section-ptb">
			<div class="container">
				<div class="row">
					<div class="col-md-8 col-md-offset-2">
						<form action="/encodingTesting" role="form" method="post"
							id="servicesform" enctype="multipart/form-data">
							<div id="register-form" class="register-form">

								<!-- --------- -->
								<p>RECIBIDO : ${stringrecibida }</p>

								<div class="row">
									<div class="section-field col-md-11">
										<label for="name"><spring:message code="label.topic" />:</label>
									</div>
								</div>
								<div class="row">
									<div class="section-field col-md-11">
										<div class="field-widget">
											<i class="fa fa-exclamation"></i> <input id="String"
												type="text" name="String" />
										</div>
									</div>
									<div class="section-field col-md-1">
										<span class="tooltip-content text-blue"
											data-original-title="<spring:message code="label.queryTopicInfo"/>"
											data-toggle="tooltip" data-placement="top"><i
											class="fa fa-question-circle fa-2x" aria-hidden="true"></i></span>
									</div>
								</div>
								<a class="button mt-20" id="submitservices"
									onclick="document.getElementById('servicesform').submit()">
									<span><spring:message code="label.sendquery" /></span> <i
									class="fa fa-paper-plane"></i>
								</a>
								<!-- En caso de que algún usuario tenga desactivado javascript, saldrá este mensaje por defecto. -->
								<noscript>
									<input type="submit"
										value="<spring:message code="label.sendquery"/>" />
								</noscript>
							</div>

						</form>
					</div>
				</div>
			</div>
		</section>

	</div>
</body>
</html>
