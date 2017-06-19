<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<link rel="stylesheet"
			href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<script type="text/javascript">
		var timeout = ${timeout};
				
        function timedRefresh(timeoutPeriod) {
    		setTimeout("location.reload(true);",timeoutPeriod);
		} 
    </script>
	    
	<script type="text/javascript">
		var playVar = ${play};
		
	    function notification() {
	    	if(${play} == 2){
		    	document.getElementById('playerCr').play();
		    	setTimeout(stopCritical, 7000);
	    	} else if(${play} == 1) {
		    	document.getElementById('playerWar').play();
		    	setTimeout(stopWar, 7000);
	    	}
	      }
	    
	    function stopCritical() {
	    	document.getElementById('playerCr').pause();
	      }
	    
	    function stopWar() {
	    	document.getElementById('playerWar').pause();
	      }
	    
    </script>

	<title><spring:message code="main.form.title.label"/></title>
</head>
<body  onload="JavaScript:timedRefresh(timeout);notification();">
	<a name="top"></a> 
	<section>
	
		<div class="jumbotron">
			<div class="container">
				<h2><spring:message code="main.form.header.label"/></h2>
				
				<div class="dropdown  pull-right">
				  	<button class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown"><span class="glyphicon-refresh glyphicon"/></span> Autorefresh every ${timeoutText} seconds
				  	<span class="caret"></span></button>
				  	<ul class="dropdown-menu">
					    <li><a href=" <spring:url value="/refresh?time=10000" /> "><spring:message code="main.form.dropdown.refreshtime.1row"/></a></li>
					    <li><a href="<spring:url value="/refresh?time=15000" />"><spring:message code="main.form.dropdown.refreshtime.2row"/></a></li>
					    <li><a href="<spring:url value="/refresh?time=20000" />"><spring:message code="main.form.dropdown.refreshtime.3row"/></a></li>
				  	</ul>
				</div>

				<p>
				<c:choose>
					<c:when test="${prStatus}">
						<a href=" <spring:url value="/run" /> " class="btn btn-primary btn-lg disabled">
						<span class="glyphicon-repeat glyphicon"/></span> Started <span class="badge">${activeMonitoredURLs}</span>
						</a>
						
						<a href=" <spring:url value="/stop" /> " class="btn btn-lg active">
						<span class="glyphicon-stop glyphicon"/></span> Stop monitoring
						</a>					
					</c:when>
					
					<c:otherwise>
						<a href=" <spring:url value="/run" /> " class="btn btn-primary btn-lg active">
						<span class="glyphicon-repeat glyphicon"/></span> Start monitoring <span class="badge">${activeMonitoredURLs}</span>
						</a>
						
						<a href=" <spring:url value="/stop" /> " class="btn btn-lg disabled">
						<span class="glyphicon-stop glyphicon"/></span> Stopped
						</a>					
					</c:otherwise>
				</c:choose>
				</p>

				<p><a href=" <spring:url value="/monitoring" /> " class="btn btn-link pull-right">Refresh now</a></p>
			</div>
		</div>
	</section>

	<section class="container">
		<a href=" <spring:url value="/add" /> " class="btn btn-success">
		<span class="glyphicon-plus glyphicon" /></span> Create URL
		</a><p>
	</section>

	<c:choose>
		<c:when test="${showForm == 'Add'}">
			<section class="container">
				<form:form modelAttribute="newURLConfig" class="form-horizontal">
					<fieldset>
						<legend>Add new URL</legend>

						<div class="form-group">
							<label class="control-label col-lg-2 col-lg-2" for="urlId">URL
								Id </label>
							<div class="col-lg-10">
								<form:input id="urlId" path="urlId" type="text"	class="form:input-large"/>
							</div>
						</div>

						<div class="form-group">
							<label class="control-label col-lg-2" for="url">URL </label>
							<div class="col-lg-10">
								<form:textarea id="url" path="url" rows="2" />
							</div>
						</div>

						<div class="form-group">
							<label class="control-label col-lg-2 col-lg-2" for="monitoringPeriod">Monitoring frequency </label>
							<div class="col-lg-10">
								<form:input id="monitoringPeriod" path="monitoringPeriod" type="text" class="form:input-large" /> seconds
							</div>
						</div>

						<div class="form-group">
							<label class="control-label col-lg-2 col-lg-2" for="resposeTimeOK">Response time </label>
							<div class="col-lg-10">
								<span class="label label-default">OK</span>&nbsp;
								<form:input id="resposeTimeOK" path="resposeTime.timeForOK"	type="text" class="form:input-large" />	&nbsp;&nbsp; 
								<span class="label label-default">Warning</span>&nbsp;
								<form:input id="resposeTimeWarning"	path="resposeTime.timeForWarning" type="text" class="form:input-large" /> &nbsp;&nbsp; 
								<span class="label label-default">Critical</span>&nbsp;
								<form:input id="resposeTimeCritical" path="resposeTime.timeForCritical" type="text"	class="form:input-large" />	&nbsp;&nbsp;
							</div>
						</div>

						<div class="form-group">
							<label class="control-label col-lg-2 col-lg-2" for="responseCode">Response code </label>
							<div class="col-lg-10">
								<form:input id="responseCode" path="responseCode" type="text" class="form:input-large" />
								<font size="1">(1xx-Informational, 2xx-Success,	3xx-Redirection, 4xx-Client Error, 5xx-Server Error)</font>
							</div>
						</div>

						<div class="form-group">
							<label class="control-label col-lg-2 col-lg-2"
								for="responseLengthMin">Response size </label>
							<div class="col-lg-10">
								<span class="label label-default">Min</span>&nbsp;
								<form:input id="responseLengthMin" path="responseLength.minLength" type="text" class="form:input-large" />	bytes&nbsp;&nbsp; 
								<span class="label label-default">Max</span>&nbsp;
								<form:input id="responseLengthMax" path="responseLength.maxLength" type="text" class="form:input-large" />	bytes&nbsp;&nbsp;
							</div>
						</div>

						<div class="form-group">
							<label class="control-label col-lg-2" for="responseFindSubstring">Find substring </label>
							<div class="col-lg-10">
								<form:textarea id="responseFindSubstring" path="responseFindSubstring" rows="2" />
							</div>
						</div>

						<div class="form-group">
							<label class="control-label col-lg-2" for="monitored">Active monitoring</label>
							<div class="col-lg-10">
								<form:radiobutton path="monitored" value="true" />
								Run
								<form:radiobutton path="monitored" value="false" />
								Pause
							</div>
						</div>

						<div class="form-group">
							<div class="col-lg-offset-2 col-lg-10">
								<input type="submit" id="btnAdd" class="btn btn-primary" value="Add" />
								<a href=" <spring:url value="/monitoring" /> " class="btn btn-warning"><span class="glyphicon-share-alt glyphicon"/></span> Cancel</a>
							</div>
						</div>

					</fieldset>
				</form:form>
				<hr>
			</section>
		</c:when>
		<c:when test="${showForm == 'Edit'}">
	     	<section class="container">
				<form:form modelAttribute="editURLConfig" class="form-horizontal">
					<fieldset>
						<legend>Edit URL #${editURLConfig.urlId}</legend>

						<div class="form-group">
							<label class="control-label col-lg-2 col-lg-2" for="urlId">URL Id </label>
							<div class="col-lg-10">
								<form:input id="urlId" path="urlId" type="text"	class="form:input-large" value="${urlId}"/>
							</div>
						</div>

						<div class="form-group">
							<label class="control-label col-lg-2" for="url">URL </label>
							<div class="col-lg-10">
								<form:textarea id="url" path="url" rows="2" value="${url}"/>
							</div>
						</div>

						<div class="form-group">
							<label class="control-label col-lg-2 col-lg-2" for="monitoringPeriod">Monitoring frequency </label>
							<div class="col-lg-10">
								<form:input id="monitoringPeriod" path="monitoringPeriod" type="text" class="form:input-large" value="${monitoringPeriod}"/> seconds
							</div>
						</div>

						<div class="form-group">
							<label class="control-label col-lg-2 col-lg-2" for="resposeTimeOK">Response time </label>
							<div class="col-lg-10">
								<span class="label label-default">OK</span>&nbsp;
								<form:input id="resposeTimeOK" path="resposeTime.timeForOK" type="text" class="form:input-large" value="${resposeTime.timeForOK}"/>	&nbsp;&nbsp; 
								<span class="label label-default">Warning</span>&nbsp;
								<form:input id="resposeTimeWarning"	path="resposeTime.timeForWarning" type="text" class="form:input-large" value="${resposeTime.timeForWarning}"/>
								&nbsp;&nbsp; <span class="label label-default">Critical</span>&nbsp;
								<form:input id="resposeTimeCritical" path="resposeTime.timeForCritical" type="text"	class="form:input-large" value="${resposeTime.timeForCritical}"/> &nbsp;&nbsp;
							</div>
						</div>

						<div class="form-group">
							<label class="control-label col-lg-2 col-lg-2" for="responseCode">Response code </label>
							<div class="col-lg-10">
								<form:input id="responseCode" path="responseCode" type="text" class="form:input-large" />
								<font size="1" value="${responseCode}">(1xx-Informational, 2xx-Success,	3xx-Redirection, 4xx-Client Error, 5xx-Server Error)</font>
							</div>
						</div>

						<div class="form-group">
							<label class="control-label col-lg-2 col-lg-2"
								for="responseLengthMin">Response size </label>
							<div class="col-lg-10">
								<span class="label label-default">Min</span>&nbsp;
								<form:input id="responseLengthMin" path="responseLength.minLength" type="text" class="form:input-large" value="${responseLength.minLength}"/> bytes&nbsp;&nbsp; <span class="label label-default">Max</span>&nbsp;
								<form:input id="responseLengthMax"	path="responseLength.maxLength" type="text"	class="form:input-large" value="${responseLength.maxLength}"/> bytes&nbsp;&nbsp;
							</div>
						</div>

						<div class="form-group">
							<label class="control-label col-lg-2" for="responseFindSubstring">Find substring </label>
							<div class="col-lg-10">
								<form:textarea id="responseFindSubstring" path="responseFindSubstring" rows="2" value="${responseFindSubstring}"/>
							</div>
						</div>

						<div class="form-group">
							<div class="col-lg-offset-2 col-lg-10">
								<input type="submit" id="btnEdit" class="btn btn-primary" value="Save" />
								<a href=" <spring:url value="/monitoring" /> " class="btn btn-warning"><span class="glyphicon-share-alt glyphicon"/></span> Cancel</a>
							</div>
						</div>
					</fieldset>
				</form:form>
				<hr>
			</section>
	    </c:when>
		<c:otherwise>
			<!-- show nothing -->
		</c:otherwise>
	</c:choose>

	<section class="container">
		<h3>URLs for monitoring </h3>
		<table class="table table-bordered">
			<thead>
				<tr>
					<th>#</th>
					<th>URL</th>
					<th>Period</th>
					<th colspan="3">Response Time</th>
					<th>Response Code</th>
					<th colspan="2">Response Size (bytes)</th>
					<th>Substring</th>
					<th colspan="3">Control elements</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${monitoringURLs}" var="monitoringURL">
					<tr>
						<td>${monitoringURL.urlId}</td>
						<td>${monitoringURL.url}</td>
						<td>${monitoringURL.monitoringPeriod}</td>

						<td>OK: ${monitoringURL.resposeTime.timeForOK}</td>
						<td>WARNING: ${monitoringURL.resposeTime.timeForWarning}</td>
						<td>CRITICAL: ${monitoringURL.resposeTime.timeForCritical}</td>

						<td>${monitoringURL.responseCode}</td>

						<td>MIN: ${monitoringURL.responseLength.minLength}</td>
						<td>MAX: ${monitoringURL.responseLength.maxLength}</td>

						<td>${monitoringURL.responseFindSubstring}</td>
						<td>
						<c:choose>
							    <c:when test="${monitoringURL.monitored == false}">
							    <a href=" <spring:url value="/pause/${monitoringURL.urlId}" /> " class="btn btn-info btn-xs"><span class="glyphicon-play glyphicon"/></span> Run</a>
							    </c:when>
							    <c:when test="${monitoringURL.monitored == true}">
							     <a href=" <spring:url value="/pause/${monitoringURL.urlId}" /> " class="btn btn-info btn-xs"><span class="glyphicon-pause glyphicon"/></span> Pause</a>
							    </c:when>
						</c:choose>
						</td>
						
						<td><a href=" <spring:url value="/edit/${monitoringURL.urlId}" /> " class="btn btn-warning btn-xs"><span class="glyphicon-edit glyphicon"/></span> Edit</a></td>
						
						<td><a href=" <spring:url value="/delete/${monitoringURL.urlId}" /> " class="btn btn-danger btn-xs"><span class="glyphicon-remove glyphicon"/></span> Delete</a></td>
							
					</tr>
				</c:forEach>

			</tbody>
		</table>
	</section>
	<p>
	<section class="container">
		<h3>The result of the monitoring </h3>
		<table class="table table-striped">
			<thead>
				<tr>
					<th>#</th>
					<th>Time</th>
					<th>Status</th>
					<th>Response Time (mc)</th>
					<th>Response Code</th>
					<th>Response Size (bytes)</th>
					<th>Substring</th>
					<th>Show details</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${resultURLs}" var="resultURL">
					<tr>
						<td>${resultURL.urlId}</td>
						<td>${resultURL.timeSnapshot}</td>
						<td>${resultURL.resultStatus}</td>
						<td>${resultURL.responseTime}</td>
						<td>${resultURL.responseCode}</td>
						<td>${resultURL.responseLength}</td>
						<td>${resultURL.findSubstring}</td>
						<td>
						<a href=" <spring:url value="/details?id=${resultURL.urlId}" /> " class="btn btn-primary btn-xs">
							<span class="glyphicon-info-sign glyphicon"/></span> Details
						</a>
						</td>
					</tr>
					</c:forEach>
					
			</tbody>
		</table>
	</section>
	
	<p>
	<section class="container">
		<h4>Details for URL ${monitoringId} </h4>
		<table class="table table-smd">
			<thead>
				<tr>
					<th>HTTP Response Headers</th>
					<th>Value</th>
				</tr>
			</thead>
			<tbody>
					<c:forEach items="${monitoringURLDetails}" var="monitoringURLDetail">
					<tr>
						<td>${monitoringURLDetail.key}</td>
						<td>${monitoringURLDetail.value}</td>
					</tr>
					</c:forEach>
			</tbody>
		</table>
	</section>
	
	<section class="container">
		<a href="#top" class="btn btn-secondary pull-right"> TOP </a>
		<audio id="playerCr" src="<c:url value='/resource/sounds/critical.mp3' />"></audio>
		<audio id="playerWar" src="<c:url value='/resource/sounds/warning.mp3' />"></audio>
	</section>
</body>
</html>