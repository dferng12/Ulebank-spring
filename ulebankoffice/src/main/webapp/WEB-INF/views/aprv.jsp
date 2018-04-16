<%@ include file="/WEB-INF/views/include.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
   <head>
      <meta charset="UTF-8">
      <meta name="description" content="Cálculo de TAE Varios tipos de interés.">
      <meta name="keywords"
         content="ULe-Bank, calculos, tae, tasa, anual, equivalente">
      <meta name="author"
         content="Alexis Gutiérrez, Camino Fernández, Razvan Raducu">
      <meta name="robots" content="index,follow">
      <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
      <jsp:include page="/WEB-INF/views/OpenGraph.jsp" />
      <meta property="og:determiner" content="the" />
      <meta property="og:title" content="<spring:message code='label.siteservice1-2' />" />
      <meta property="og:url" content="http://ulebank.unileon.es/apr" />
      <meta property="og:description" content="<spring:message code='label.siteservice1-2description'/>"/>
      <link rel="alternate" hreflang="es" href="http://ule-bank.appspot.com/" />
      <!-- <link rel="stylesheet" href="/resources/services/css/bootstrap.min.css"> -->
      <link rel="stylesheet"
         href="/resources/services/css/font-awesome/css/font-awesome.min.css">
      <link rel="stylesheet"
         href="/resources/services/css/styles-responsive.css">
      <script src="/resources/services/js/jquery.min.js"></script>
      <!-- <script src="/resources/services/js/bootstrap.min.js"></script> -->
      <script src="/resources/services/js/jquery-cookie.js"></script>
      <script src="/resources/services/js/script.js"></script>
      <!-- Favicon -->
      <link rel="shortcut icon" href="../favicon.ico" />
      <!-- bootstrap -->
      <link href="resources/template/css/bootstrap.min.css" rel="stylesheet"
         type="text/css" />
      <!-- plugins -->
      <link href="resources/template/css/plugins-css.css" rel="stylesheet"
         type="text/css" />
      <!-- mega menu -->
      <link href="resources/template/css/mega-menu/mega_menu.css"
         rel="stylesheet" type="text/css" />
      <!-- default -->
      <link href="resources/template/css/default.css" rel="stylesheet"
         type="text/css" />
      <!-- main style -->
      <link href="resources/template/css/style.css" rel="stylesheet"
         type="text/css" />
      <!-- responsive -->
      <link href="resources/template/css/responsive.css" rel="stylesheet"
         type="text/css" />
      <!-- custom style -->
      <link href="resources/template/css/custom.css" rel="stylesheet"
         type="text/css" />
      <title>
         <spring:message code="label.siteservice1-2" />
      </title>
   </head>
   <body>
      <div class="page-wrapper">
         <!--=================================
            preloader -->
         <div id="preloader">
            <div class="clear-loading loading-effect">
               <span></span>
            </div>
         </div>
         <!--=================================
            preloader -->
         <!--=================================
            header -->
         <jsp:include page="/WEB-INF/views/header.jsp" />
         <!--=================================
            header -->
         <!--=================================
            inner-intro-->
         <section
            class="inner-intro grayscale bg-services-apr bg-opacity-black-70">
            <div class="container">
               <div class="row text-center intro-title">
                  <h1 class="text-blue">
                     <spring:message code="label.siteservice1-2" />
                  </h1>
                  <p class="text-white">
                     <spring:message code="label.siteservice1-2description" />
                  </p>
                  <ul class="page-breadcrumb">
                     <li>
                        <a href="./">
                           <i class="fa fa-home"></i> 
                           <spring:message
                              code="label.sitehome" />
                        </a>
                        <i class="fa fa-angle-double-right"></i>
                     </li>
                     <li>
                        <a href="/a/services">
                           <spring:message
                              code="label.siteservices" />
                        </a>
                        <i
                           class="fa fa-angle-double-right"></i>
                     </li>
                     <li>
                        <span>
                           <spring:message code="label.siteservice1-2" />
                        </span>
                     </li>
                  </ul>
               </div>
            </div>
         </section>
         <!--=================================
            inner-intro-->
         <!--=================================
            Register-->
         <section class="white-bg page-section-ptb">
            <div class="container">
               <div class="row">
                  <div class="col-md-6 col-md-offset-2">
                     <form:form role="form" method="post" modelAttribute="datosTaeVariosIntereses"
                        id="servicesform">
                        <div id="register-form" class="register-form">
                           <div class="row">
                              <div class="section-field col-md-8">
                                 <label for="name">
                                    <spring:message
                                       code="label.period" />
                                 </label>
                              </div>
                           </div>
                           <div class="row">
                              <div class="section-field col-md-8">
                                 <div class="field-widget">
                                    <spring:message code="label.annual" var="variable1" />
                                    <spring:message code="label.biannual" var="variable2" />
                                    <spring:message code="label.cuatrimestral" var="variable3" />
                                    <spring:message code="label.quarterly" var="variable4" />
                                    <spring:message code="label.bimonthly" var="variable5" />
                                    <spring:message code="label.monthly" var="variable6" />
                                    <form:select path="periodo" id="seleccionPeriodo">
                                       <form:option value="1" label="1 - ${variable1}" />
                                       <form:option value="2" label="2 - ${variable2}" />
                                       <form:option value="3" label="3 - ${variable3}" />
                                       <form:option value="4" label="4 - ${variable4}" />
                                       <form:option value="6" label="6 - ${variable5}" />
                                       <form:option value="12" label="12 - ${variable6}" />
                                    </form:select>
                                 </div>
                              </div>
                              <div class="section-field col-md-1 pl-0">
                                 <span class="tooltip-content text-blue"
                                    data-original-title="<spring:message code="label.typeofperiodinfo"/>"
                                 data-toggle="tooltip" data-placement="top"><i
                                    class="fa fa-question-circle fa-2x" aria-hidden="true"></i></span>
                              </div>
                           </div>
                           <div class="row">
                              <div class="section-field col-md-8">
                                 <h2>
                                    <spring:message
                                       code="label.aprvariosinterestsinfo" />
                                 </h2>
                              </div>
                           </div>
                           <div class="row">
                              <div class="section-field col-md-8">
                                 <spring:message code="label.period"/>
                                 1 <form:errors style="color: red;" path="intereses[0].interes"></form:errors>
                                 
                                 </div>
                                 <div class="row col-md-8 ml-0">
                                 <div class="field-widget">
                                    <i class="fa fa-percent"></i>
                                    <form:input path="intereses[0].interes" required="required" />
                                 </div>
                              </div>
                              <div class="section-field col-md-1">
                                 <span class="tooltip-content text-blue"
                                    data-original-title="<spring:message code="label.nominalinteresttype"/>"
                                 data-toggle="tooltip" data-placement="top"><i
                                    class="fa fa-question-circle fa-2x" aria-hidden="true"></i></span>
                              </div>
                           </div>
                           <div class="row TAEVariosTipoInteres">
                              <div class="section-field col-md-8">
                                 <spring:message code="label.period"/>
                                 2  <form:errors style="color: red;" path="intereses[1].interes"></form:errors>
                                  </div>
                                 <div class="row col-md-8 ml-0">
                                 <div class="field-widget">
                                    <i class="fa fa-percent"></i>
                                    <form:input type="text" name="name" path="intereses[1].interes" required="required" />
                                 </div>
                              </div>
                               <div class="section-field col-md-1">
                                 <span class="tooltip-content text-blue"
                                    data-original-title="<spring:message code="label.nominalinteresttype"/>"
                                 data-toggle="tooltip" data-placement="top"><i
                                    class="fa fa-question-circle fa-2x" aria-hidden="true"></i></span>
                              </div>
                           </div>
                           <div class="row TAEVariosTipoInteres">
                              <div class="section-field col-md-8">
                                 <spring:message code="label.period"/>
                                 3 <form:errors style="color: red;" path="intereses[2].interes"></form:errors>
                                  </div>
                                 <div class="row col-md-8 ml-0">
                                 <div class="field-widget">
                                    <i class="fa fa-percent"></i>
                                    <form:input type="text" name="name" path="intereses[2].interes" required="required" />
                                 </div>
                              </div>
                               <div class="section-field col-md-1">
                                 <span class="tooltip-content text-blue"
                                    data-original-title="<spring:message code="label.nominalinteresttype"/>"
                                 data-toggle="tooltip" data-placement="top"><i
                                    class="fa fa-question-circle fa-2x" aria-hidden="true"></i></span>
                              </div>
                           </div>
                           <div class="row TAEVariosTipoInteres">
                              <div class="section-field col-md-8">
                                 <spring:message code="label.period"/>
                                 4  <form:errors style="color: red;" path="intereses[3].interes"></form:errors>
                                  </div>
                                 <div class="row col-md-8 ml-0">
                                 <div class="field-widget">
                                    <i class="fa fa-percent"></i>
                                    <form:input type="text" name="name" path="intereses[3].interes" required="required" />
                                 </div>
                              </div>
                               <div class="section-field col-md-1">
                                 <span class="tooltip-content text-blue"
                                    data-original-title="<spring:message code="label.nominalinteresttype"/>"
                                 data-toggle="tooltip" data-placement="top"><i
                                    class="fa fa-question-circle fa-2x" aria-hidden="true"></i></span>
                              </div>
                           </div>
                           <div class="row TAEVariosTipoInteres">
                              <div class="section-field col-md-8">
                                 <spring:message code="label.period"/>
                                 5 <form:errors style="color: red;" path="intereses[4].interes"></form:errors>
                                  </div>
                                 <div class="row col-md-8 ml-0">
                                 <div class="field-widget">
                                    <i class="fa fa-percent"></i>
                                    <form:input type="text" name="name" path="intereses[4].interes" required="required" />
                                 </div>
                              </div>
                               <div class="section-field col-md-1">
                                 <span class="tooltip-content text-blue"
                                    data-original-title="<spring:message code="label.nominalinteresttype"/>"
                                 data-toggle="tooltip" data-placement="top"><i
                                    class="fa fa-question-circle fa-2x" aria-hidden="true"></i></span>
                              </div>
                           </div>
                           <div class="row TAEVariosTipoInteres">
                              <div class="section-field col-md-8">
                                 <spring:message code="label.period"/>
                                 6 <form:errors style="color: red;" path="intereses[5].interes"></form:errors>
                                  </div>
                                 <div class="row col-md-8 ml-0">
                                 <div class="field-widget">
                                    <i class="fa fa-percent"></i>
                                    <form:input type="text" name="name" path="intereses[5].interes" required="required" />
                                 </div>
                              </div>
                               <div class="section-field col-md-1">
                                 <span class="tooltip-content text-blue"
                                    data-original-title="<spring:message code="label.nominalinteresttype"/>"
                                 data-toggle="tooltip" data-placement="top"><i
                                    class="fa fa-question-circle fa-2x" aria-hidden="true"></i></span>
                              </div>
                           </div>
                           <div class="row TAEVariosTipoInteres">
                              <div class="section-field col-md-8">
                                 <spring:message code="label.period"/>
                                 7 <form:errors style="color: red;" path="intereses[6].interes"></form:errors>
                                  </div>
                                 <div class="row col-md-8 ml-0">
                                 <div class="field-widget">
                                    <i class="fa fa-percent"></i>
                                    <form:input type="text" name="name" path="intereses[6].interes" required="required" />
                                 </div>
                              </div>
                               <div class="section-field col-md-1">
                                 <span class="tooltip-content text-blue"
                                    data-original-title="<spring:message code="label.nominalinteresttype"/>"
                                 data-toggle="tooltip" data-placement="top"><i
                                    class="fa fa-question-circle fa-2x" aria-hidden="true"></i></span>
                              </div>
                           </div>
                           <div class="row TAEVariosTipoInteres">
                              <div class="section-field col-md-8">
                                 <spring:message code="label.period"/>
                                 8 <form:errors style="color: red;" path="intereses[7].interes"></form:errors>
                                  </div>
                                 <div class="row col-md-8 ml-0">
                                 <div class="field-widget">
                                    <i class="fa fa-percent"></i>
                                    <form:input type="text" name="name" path="intereses[7].interes" required="required" />
                                 </div>
                              </div>
                               <div class="section-field col-md-1">
                                 <span class="tooltip-content text-blue"
                                    data-original-title="<spring:message code="label.nominalinteresttype"/>"
                                 data-toggle="tooltip" data-placement="top"><i
                                    class="fa fa-question-circle fa-2x" aria-hidden="true"></i></span>
                              </div>
                           </div>
                           <div class="row TAEVariosTipoInteres">
                              <div class="section-field col-md-8">
                                 <spring:message code="label.period"/>
                                 9 <form:errors style="color: red;" path="intereses[8].interes"></form:errors>
                                  </div>
                                 <div class="row col-md-8 ml-0">
                                 <div class="field-widget">
                                    <i class="fa fa-percent"></i>
                                    <form:input type="text" name="name" path="intereses[8].interes" required="required" />
                                 </div>
                              </div>
                               <div class="section-field col-md-1">
                                 <span class="tooltip-content text-blue"
                                    data-original-title="<spring:message code="label.nominalinteresttype"/>"
                                 data-toggle="tooltip" data-placement="top"><i
                                    class="fa fa-question-circle fa-2x" aria-hidden="true"></i></span>
                              </div>
                           </div>
                           <div class="row TAEVariosTipoInteres">
                              <div class="section-field col-md-8">
                                 <spring:message code="label.period"/>
                                 10 <form:errors style="color: red;" path="intereses[9].interes"></form:errors>
                                  </div>
                                 <div class="row col-md-8 ml-0">
                                 <div class="field-widget">
                                    <i class="fa fa-percent"></i>
                                    <form:input type="text" name="name" path="intereses[9].interes" required="required" />
                                 </div>
                              </div>
                               <div class="section-field col-md-1">
                                 <span class="tooltip-content text-blue"
                                    data-original-title="<spring:message code="label.nominalinteresttype"/>"
                                 data-toggle="tooltip" data-placement="top"><i
                                    class="fa fa-question-circle fa-2x" aria-hidden="true"></i></span>
                              </div>
                           </div>
                           <div class="row TAEVariosTipoInteres">
                              <div class="section-field col-md-8">
                                 <spring:message code="label.period"/>
                                 11 <form:errors style="color: red;" path="intereses[10].interes"></form:errors>
                                  </div>
                                 <div class="row col-md-8 ml-0">
                                 <div class="field-widget">
                                    <i class="fa fa-percent"></i>
                                    <form:input type="text" name="name" path="intereses[10].interes" required="required" />
                                 </div>
                              </div>
                               <div class="section-field col-md-1">
                                 <span class="tooltip-content text-blue"
                                    data-original-title="<spring:message code="label.nominalinteresttype"/>"
                                 data-toggle="tooltip" data-placement="top"><i
                                    class="fa fa-question-circle fa-2x" aria-hidden="true"></i></span>
                              </div>
                           </div>
                           <div class="row TAEVariosTipoInteres">
                              <div class="section-field col-md-8">
                                 <spring:message code="label.period"/>
                                 12 <form:errors style="color: red;" path="intereses[11].interes"></form:errors>
                                  </div>
                                 <div class="row col-md-8 ml-0">
                                 <div class="field-widget">
                                    <i class="fa fa-percent"></i>
                                    <form:input type="text" name="name" path="intereses[11].interes" required="required" />
                                 </div>
                              </div>
                               <div class="section-field col-md-1">
                                 <span class="tooltip-content text-blue"
                                    data-original-title="<spring:message code="label.nominalinteresttype"/>"
                                 data-toggle="tooltip" data-placement="top"><i
                                    class="fa fa-question-circle fa-2x" aria-hidden="true"></i></span>
                              </div>
                           </div>
                           <div class="bg-info">
                              <h3>
                                 <c:out value="${model.resultado}" />
                              </h3>
                           </div>
                           <div class="row">
                              <div class="section-field col-md-8">
                                 <div class="row text-center intro-title">
                                    <p class="modal-link last-element">
                                       <a href="#" data-toggle="modal"
                                          data-target="#modalExplicativo">
                                          <spring:message
                                             code="label.whatisapr" />
                                       </a>
                                    </p>
                                 </div>
                                 <a class="button mt-20" id="submitservices"
                                    onclick="document.getElementById('servicesform').submit()">
                                    <span>
                                       <spring:message code="label.calculate" />
                                    </span>
                                    <i
                                       class="fa fa-calculator"></i>
                                 </a>
                                 <!-- En caso de que algún usuario tenga desactivado javascript, saldrá este mensaje por defecto. -->
                                 <noscript>
                                    <input type="submit"
                                       value="<spring:message code="label.calculate"/>" />
                                 </noscript>
                              </div>
                           </div>
                        </div>
                     </form:form>
                  </div>
                  <!-- 					Descripción lateral  -->
					<div class="col-md-4" >
					<p style="text-align: justify;"><spring:message code="label.aprlateral" /></p>
						
					</div>
               </div>
            </div>
         </section>
         <!--=================================
            Register-->
         <!--=================================
            Footer-->
         <jsp:include page="/WEB-INF/views/footer.jsp" />
         <!--=================================
            Footer-->
         <!-- Modal -->
         <div class="modal fade" id="modalExplicativo" tabindex="-1"
            role="dialog" aria-labelledby="modalExplicativoLabel">
            <div class="modal-dialog modal-lg" role="document">
               <div class="modal-content">
                  <div class="modal-header">
                     <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                     <span aria-hidden="true">&times;</span>
                     </button>
                     <h4 class="modal-title" id="modalExplicativoLabel">
                        <spring:message code="label.whatisaprtitle" />
                     </h4>
                  </div>
                  <div class="modal-body" style="text-align: justify;">
                     <p><spring:message code="label.whatisaprbody" /></p>
                     <p><spring:message code="label.whatisapr1" /></p>
                     <p><spring:message code="label.whatisapr2" /></p>
                     <p><spring:message code="label.whatisapr3" /></p>
                  </div>
                  <div class="modal-footer">
                     <button type="button" data-dismiss="modal">
                        <spring:message code="label.close" />
                     </button>
                  </div>
               </div>
            </div>
         </div>
         <!-- Fin Modal -->
         <!-- MODAL RESULTADOS -->
         <div class="modal fade" id="modalResultados" tabindex="-1"
            role="dialog" aria-labelledby="modalResultadosLabel">
            <div class="modal-dialog modal-lg" role="document">
               <div class="modal-content">
                  <div class="modal-header">
                     <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                     <span aria-hidden="true">&times;</span>
                     </button>
                     <h4 class="modal-title" id="modalResultadosLabel">
                        <spring:message code="label.simulationresult" />
                     </h4>
                  </div>
                  <div class="modal-body">
                     <table class="table">
                        <tr class="text-center">
                           <td>
                              <h3>
                                 <spring:message code="label.APR"/>
                                 ${TAE} %
                              </h3>
                           </td>
                        </tr>
                     </table>
                  </div>
               </div>
               <div class="modal-footer">
                  <button type="button" class="btn btn-default" data-dismiss="modal">
                     <spring:message code="label.close" />
                  </button>
               </div>
            </div>
         </div>
         <!-- FIN MODAL RESULTADOS -->
      </div>
      <script>
         /* Script para hacer visibles u ocultar los campos de los intereses */
         var seleccion = $("#seleccionPeriodo");
         //Selecciono todos los elementos de la clase TAEVarios... Son 11 en total porque el primero se va a ver siempre.
         var arrayIntereses = $(".TAEVariosTipoInteres");
         //Según carga la página compruebo el valor seleccionado y muestro sólo esos.
         if(seleccion.val() > 1){
         $.each(arrayIntereses, function(index) {
         			if (index >= seleccion.val()-1) {
         				arrayIntereses.eq(index).hide();
         			}
         		});
         } else arrayIntereses.hide();
         //Al haber algún cambio en la seleccion del periodo, miro el valor y muestro tantos campos.
         seleccion.change(function() {
         	if (seleccion.val() == 12) {
         		arrayIntereses.show(500);
         	} else if (seleccion.val() == 1) {
         		arrayIntereses.hide(500);
         	} else if (seleccion.val() == 6) {
         		ocultarMostrarCampos(5);
         	} else if (seleccion.val() == 2) {
         		ocultarMostrarCampos(1);
         	} else if (seleccion.val() == 3) {
         		ocultarMostrarCampos(2);
         	} else if (seleccion.val() == 4) {
         		ocultarMostrarCampos(3);
         	}
         })
         
         function ocultarMostrarCampos(num){
         	//El algoritmo está hecho de esta forma para que no de la sensación de que está mal hecho al recargar todos los elementos.
         	//Es mucho más óptimo hacer un .hide() a todo el array y mostrar los 2, 3, o 6 primeros pero se produce lo anteriormente
         	//mencionado
         	$.each(arrayIntereses, function(index) {
         		if (index < num) {
         			arrayIntereses.eq(index).show(500);
         		} else {
         			arrayIntereses.eq(index).hide(500);
         		}
         	});
         }
         
      </script>
      <script src="/resources/services/js/tooltip-script.js"></script>
      <!-- bootstrap -->
      <script type="text/javascript"
         src="resources/template/js/bootstrap.min.js"></script>
      <!-- plugins-jquery -->
      <script type="text/javascript"
         src="resources/template/js/plugins-jquery.js"></script>
      <!-- mega menu -->
      <script type="text/javascript"
         src="resources/template/js/mega-menu/mega_menu.js"></script>
      <!-- custom -->
      <script type="text/javascript" src="resources/template/js/custom.js"></script>
   </body>
</html>