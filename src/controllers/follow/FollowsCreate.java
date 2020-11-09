package controllers.follow;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityManager;
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
 * Servlet implementation class FollowsCreate
 */
@WebServlet("/follows/create")
public class FollowsCreate extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FollowsCreate() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub

        EntityManager em = DBUtil.createEntityManager();
        em.getTransaction().begin();

        Employee e = em.find(Employee.class, Integer.parseInt(request.getParameter("follow_id")));
        List<Report> reports = em.createNamedQuery("getColleageAllReports", Report.class)
                .setParameter("employee", e)
                .getResultList();

        Integer follow_id = Integer.parseInt(request.getParameter("follow_id"));
        System.out.println(follow_id);

        Follow f = new Follow();

        f.setEmployee((Employee)request.getSession().getAttribute("login_employee"));

        f.setFollowing(e);

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        f.setCreated_at(currentTime);

        Date follow_date = new Date(System.currentTimeMillis());
        f.setFollow_date(follow_date);

        em.persist(f);
        em.getTransaction().commit();
        em.close();

        request.setAttribute("follow_id", follow_id);
        request.setAttribute("reports", reports);
        request.setAttribute("e", e);

//        RequestDispatcher rd = request.getRequestDispatcher("/colleages/index");
//        rd.forward(request, response);

        response.sendRedirect(request.getContextPath() + "/colleages/index?id=" + follow_id);

    }
}
