<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
--%>

<%@ include file="/html/taglib/init.jsp" %>

<%
List<UserGroup> results = (List<UserGroup>)request.getAttribute("liferay-ui:user-group-search-results:results");
SearchContainer searchContainer = (SearchContainer)request.getAttribute("liferay-ui:user-group-search-results:searchContainer");
UserGroupDisplayTerms searchTerms = (UserGroupDisplayTerms)request.getAttribute("liferay-ui:user-group-search-results:searchTerms");
Boolean searchWithIndex = GetterUtil.getBoolean((Boolean)request.getAttribute("liferay-ui:user-group-search-results:searchWithIndex"));
Integer total = (Integer)request.getAttribute("liferay-ui:user-group-search-results:total");
LinkedHashMap<String, Object> userGroupParams = (LinkedHashMap<String, Object>)request.getAttribute("liferay-ui:user-group-search-results:userGroupParams");
%>

<c:choose>
	<c:when test="<%= searchWithIndex %>">
		<%@ include file="/html/taglib/ui/user_group_search_results/user_group_search_results_index.jspf" %>
	</c:when>
	<c:otherwise>
		<%@ include file="/html/taglib/ui/user_group_search_results/user_group_search_results_database.jspf" %>
	</c:otherwise>
</c:choose>