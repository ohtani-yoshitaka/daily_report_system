<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
    <div id="top">
    <h2>フォロー中</h2>
    </div>
    <table id="follow_list">
        <tbody>
            <tr>
                <th>氏名</th>
                <th>フォロー日</th>
                <th>フォロー数</th>
                <th>最新の投稿</th>

            </tr>
            <c:forEach var="follow" items="${follows}" varStatus="status">
                <tr>
                    <td class="follow_name"><a href="<c:url value='/colleages/index?id=${follow.following.id}' />"><c:out value="${follow.following.name}" /></a></td>
                    <td class="follow_date"><fmt:formatDate value='${follow.follow_date}' pattern='yyy-MM-dd' /></td>

                   <%--自分のフォローしている従業員のフォロワー数を表示:
                   follower_map = キー値に<follow.following.id = 自分のフォローしている従業員のID>を指定することによって、
                   "自分のフォローしている従業員の"フォロワー数が取得できる--%>
                   <td class="follow_count"><c:out value="${follower_map[follow.following.id]}" /></td>


                   <%--特定の従業員からreport情報は取れるので、さらにレポートのタイトルを表示させたい --%>
                   <c:forEach var="report" items="${report_map[follow.following.id]}" varStatus="status">
                   <td class="report_action">
                   <a href="<c:url value='/reports/show?id=${report.id}' />"><c:out value="${report.title}" /></a></td>

                  <%-- <form method="POST" action="<c:url value='/reports/show?id=${report.id}' />">
                        <input type="hidden" name="back" value="${back}">
                        <input type="submit" value="${report.title}" id="button">
                        </form></td>--%>
                   </c:forEach>
                </tr>
            </c:forEach>





        </tbody>
    </table>




    </c:param>
</c:import>