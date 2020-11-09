package controllers.colleage;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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
 * Servlet implementation class ColleageIndexServlet
 */
@WebServlet("/colleages/index")
public class ColleagesIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ColleagesIndexServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        Employee login_employee = (Employee)request.getSession().getAttribute("login_employee");

        Employee emp = em.find(Employee.class, Integer.parseInt(request.getParameter("id")));

        int page = 1;
        try{
            page = Integer.parseInt(request.getParameter("page"));
        } catch(Exception e) {
            page = 1;
        }


        //取得したい同僚のレポート
        List<Report> reports = em.createNamedQuery("getColleageAllReports", Report.class)
                                 .setParameter("employee", emp)
                                 .setFirstResult(15 * (page - 1))
                                 .setMaxResults(15)
                                 .getResultList();

        long rp_count = (long)em.createNamedQuery("getMyReportsCount", Long.class)
                                 .setParameter("employee", emp)
                                 .getSingleResult();

        //フォローしているかどうかのチェック
        Boolean check_result = false;
        Follow f = null;

        try {
             f = em.createNamedQuery("checkFollow", Follow.class)
                  .setParameter("following", emp)
                  .setParameter("employee", login_employee)
                  .getSingleResult();
        } catch(NoResultException ex) {}

        em.close();

        //フォローしているかどうかでの条件分岐
        if(f != null) {
            check_result = true;
        }

        if(!check_result) {
            // 認証できなかった場合
            String notFollow = new String();
            request.setAttribute("n", notFollow);
        } else {
            // 認証できた場合
            String yesFollow = new String();
            request.setAttribute("y", yesFollow);
        }


        Integer id = Integer.parseInt(request.getParameter("id"));
        request.setAttribute("id", id);
        request.setAttribute("reports", reports);
        request.setAttribute("e", emp);
        request.setAttribute("page", page);
        request.setAttribute("rp_count", rp_count);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/colleages/index.jsp");
        rd.forward(request, response);
    }
}
