<%@ include file="/WEB-INF/template/include.jsp"%>

<%--
  ~ The contents of this file are subject to the OpenMRS Public License
  ~ Version 2.0 (the "License"); you may not use this file except in
  ~ compliance with the License. You may obtain a copy of the License at
  ~ http://license.openmrs.org
  ~
  ~ Software distributed under the License is distributed on an "AS IS"
  ~ basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
  ~ License for the specific language governing rights and limitations
  ~ under the License.
  ~
  ~ Copyright (C) OpenMRS, LLC.  All Rights Reserved.
  --%>
<ul id="menu">
	<li class="first">
		<a href="${pageContext.request.contextPath}/admin"><openmrs:message code="admin.title.short"/></a>
	</li>
	<openmrs:hasPrivilege privilege="Manage Billing Handlers">
		<li <c:if test='<%= request.getRequestURI().contains("billableobjects/handlers") %>'>class="active"</c:if>>
			<a href="${pageContext.request.contextPath}/module/openhmis/billableobjects/handlers.form">
				<spring:message code="openhmis.billableobjects.admin.handlers"/>
			</a>
		</li>
	</openmrs:hasPrivilege>
	<openmrs:hasPrivilege privilege="Manage Billing Handlers">
		<li <c:if test='<%= request.getRequestURI().contains("billableobjects/associators") %>'>class="active"</c:if>>
			<a href="${pageContext.request.contextPath}/module/openhmis/billableobjects/associators.form">
				<spring:message code="openhmis.billableobjects.admin.associators"/>
			</a>
		</li>
	</openmrs:hasPrivilege>
</ul>