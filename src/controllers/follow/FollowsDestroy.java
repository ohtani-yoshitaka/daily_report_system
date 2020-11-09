package controllers.follow;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Follow;
import utils.DBUtil;

/**
 * Servlet implementation class FollowsDestroy
 */
@WebServlet("/follows/destroy")
public class FollowsDestroy extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FollowsDestroy() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        //自分の情報を取得する
        Employee login_employee = (Employee)request.getSession().getAttribute("login_employee");

        //フォロー解除したい同僚を取得する
        Employee e = em.find(Employee.class, Integer.parseInt(request.getParameter("follow_id")));

        //フォローしている状態を一件取り出す
        Follow f = em.createNamedQuery("checkFollow", Follow.class)
                .setParameter("following", e)
                .setParameter("employee", login_employee)
                .getSingleResult();

        Integer follow_id = Integer.parseInt(request.getParameter("follow_id"));

        em.getTransaction().begin();
        em.remove(f);
        em.getTransaction().commit();
        em.close();

        request.setAttribute("follow_id", follow_id);

        response.sendRedirect(request.getContextPath() + "/colleages/index?id=" + follow_id);
    }


}
