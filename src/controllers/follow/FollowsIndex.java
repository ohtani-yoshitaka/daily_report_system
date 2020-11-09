package controllers.follow;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Follow;
import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class FollowsIndex
 */
@WebServlet("/follows/index")
public class FollowsIndex extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FollowsIndex() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

          EntityManager em = DBUtil.createEntityManager();
          Employee login_employee = (Employee) request.getSession().getAttribute("login_employee");


       // 自分がフォローしているフォロークラスの情報を取得する
          List<Follow> follows = em.createNamedQuery("getFollowList", Follow.class)
              .setParameter("employee", login_employee)
              .getResultList();

          // "特定の従業員"のフォロワー数を格納する変数
          long follower_count;

          // "特定の従業員"の従業員IDとフォロワー数を格納する変数
          HashMap<Integer, Long> follower_map = new HashMap<Integer, Long>();

          // "特定の従業員"のフォロワー数を取得する
          for (Follow follow : follows) {
              follower_count = (long) em.createNamedQuery("getFollowerCount", Long.class)
              // 特定の従業員＝follow.followingをパラメータとして渡す。（follow.following = Employee型なので以下でOK！）
              .setParameter("employee", follow.getFollowing())
              .getSingleResult();
              // "特定の従業員の従業員ID"とフォロワー数の組み合わせ（HashMap）に追加(=put)
              follower_map.put(follow.getFollowing().getId(), follower_count);
          }

          //特定の従業員の従業員IDと最新のレポートを格納する変数
          HashMap<Integer, List<Report>> report_map = new HashMap<Integer, List<Report>>();

          //特定の従業員の最新のレポートを取得する
          for (Follow follow : follows) {
              List<Report> latest_report = em.createNamedQuery("getColleageAllReports", Report.class)
              // 特定の従業員＝follow.followingをパラメータとして渡す。（follow.following = Employee型なので以下でOK！）
              .setParameter("employee", follow.getFollowing())
              .setMaxResults(1)
              .getResultList();
              //特定の従業員の従業員IDと最新レポートの組み合わせ（HashMap）に追加（=put）
              report_map.put(follow.getFollowing().getId(), latest_report);
          }

          em.close();

          request.setAttribute("follows", follows);
          request.setAttribute("follower_map", follower_map);
          request.setAttribute("report_map", report_map);
          RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/follows/index.jsp");
          rd.forward(request, response);
    }
}

