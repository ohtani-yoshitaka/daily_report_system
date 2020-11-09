<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:import url="../layout/app.jsp">
    <c:param name="content">

<div id="top">
    <h2>${e.name}さんの日報一覧：</h2>
</div>
<div id="top">
    <c:if test="${sessionScope.login_employee.id != id}">
     <c:if test="${n != null}">
     <form method="POST" action="<c:url value='/follows/create' />">
        <input type="hidden" name="follow_id" value="${id}">
        <input type="submit" value="フォローする" id="button-1">
     </form>
     </c:if>
     <c:if test="${y != null}">
     <form method="POST" action="<c:url value='/follows/destroy' />">
        <input type="hidden" name="follow_id" value="${id}">
        <input type="submit" value="フォロー解除" id="button-2">
     </form>
     </c:if>
    </c:if>
</div>

<%-- <p>id;<c:out value="${id}"  /></p>
<P>フォロワー数：<c:out value="${fc}" /></p> --%>

    <table id="report_list">
        <tbody>
            <tr>
                <th class="report_date">日付</th>
                <th class="report_title">タイトル</th>
                <th class="report_action">操作</th>
            </tr>
            <c:forEach var="report" items="${reports}" varStatus="status">
                <tr>
                    <td class="report_date"><fmt:formatDate value='${report.report_date}' pattern='yyy-MM-dd' /></td>
                    <td class="report_title">${report.title}</td>
                    <td class="report_action"><a href="<c:url value='/reports/show?id=${report.id}' />">詳細を見る</a></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <div id="pagination">
            （全 ${rp_count} 件）<br />
            <c:forEach var="i" begin="1" end="${((rp_count - 1) / 15) + 1}" step="1">
                <c:choose>
                    <c:when test="${i == page}">
                        <c:out value="${i}" />&nbsp;
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value='/colleages/index?page=${i}' />"><c:out value="${i}" /></a>&nbsp;
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
    </c:param>
</c:import>