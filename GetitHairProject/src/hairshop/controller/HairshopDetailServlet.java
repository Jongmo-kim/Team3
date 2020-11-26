package hairshop.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import designer.model.service.DesignerListService;
import designer.model.service.DesignerService;
import designer.model.vo.Designer;
import designer.model.vo.DesignerList;
import hairshop.model.service.HairshopService;
import hairshop.model.vo.Hairshop;
import reserve.model.service.ReserveService;
import reserve.model.vo.Reserve;
import review.model.service.ReviewService;
import review.model.vo.Review;


/**
 * Servlet implementation class HairshopDetailServlet
 */
@WebServlet(name = "HairshopDetail", urlPatterns = { "/hairshopDetail" })
public class HairshopDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HairshopDetailServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		int shopNo = Integer.parseInt(request.getParameter("shopNo"));
		Hairshop hs = new HairshopService().selectOneHairshop(shopNo);//hs == null.getNo()
		ArrayList<Review> review = new ReviewService().selectAllReviewByShopNo(hs.getShopNo());
<<<<<<< HEAD
		Designer des = new DesignerService().selectOneDesigner(shopNo);
=======
		
//		Reserve reserve = new ReserveService().selec
>>>>>>> b85e01d830975491f9e02cac419aaec855180dbf
			if(hs != null){
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/hairshopDeta/hairshopDeta.jsp");
				request.setAttribute("hs", hs);
				request.setAttribute("review", review);
				
				rd.forward(request, response);
			}else{
				response.sendRedirect("/hairshop");	
			}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
