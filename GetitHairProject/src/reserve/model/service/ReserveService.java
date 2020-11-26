package reserve.model.service;

import java.sql.Connection;
import java.util.ArrayList;

import common.JDBCTemplate;
import reserve.model.dao.ReserveDao;
import reserve.model.vo.Reserve;
import reserve.model.vo.ReservePageData;

public class ReserveService {
	//custNo로 
	//sql custno로 where cust
	public ArrayList<Reserve> selectAll(int reserveNo){
		ArrayList<Reserve> list = null;
		Connection conn = JDBCTemplate.getConnection();
		list = new ReserveDao().selectAll(conn);
		JDBCTemplate.close(conn);
		return list;		
	}
	public ArrayList<Reserve> selectAllByCust(int CustmoerNo){
		ArrayList<Reserve> list;
		Connection conn = JDBCTemplate.getConnection();
		list = new ReserveDao().selectAllByCust(conn,CustmoerNo);
		JDBCTemplate.close(conn);
		return list;		
	}
	public ArrayList<Reserve> selectAllByDesigner(int DesignerNo){
		ArrayList<Reserve> list;
		Connection conn = JDBCTemplate.getConnection();
		list = new ReserveDao().selectAllByDesigner(conn,DesignerNo);
		JDBCTemplate.close(conn);
		return list;		
	}
	public int insertReserve(Reserve reserve) {
		Connection conn = JDBCTemplate.getConnection();
		int result = 0;
		result = new ReserveDao().insertReserve(conn, reserve);
		commitOrRollback(conn,result);
		JDBCTemplate.close(conn);
		return result;
	}
	public Reserve selectOneReserve(int reserveNo) {
		Connection conn = JDBCTemplate.getConnection();
		Reserve reserve = new ReserveDao().selectOneReserve(conn, reserveNo);
		if(reserve == null) {
			reserve = new Reserve();
		}
		JDBCTemplate.close(conn);
		return reserve;
	}
	public int deleteReserve(int reserveNo) {
		Connection conn = JDBCTemplate.getConnection();
		int result= new ReserveDao().deleteReserve(conn, reserveNo);
		commitOrRollback(conn,result);
		JDBCTemplate.close(conn);
		return result;
	}
	public int updateReserve(Reserve reserve) {
		Connection conn = JDBCTemplate.getConnection();
		int result = new ReserveDao().updateReserve(conn, reserve);
		commitOrRollback(conn, result);
		JDBCTemplate.close(conn);
		return result;
	}
	public ReservePageData reserveSelectList(int reqPage){
		Connection conn = JDBCTemplate.getConnection();
		ReserveDao dao = new ReserveDao();
		int totalCount = dao.getTotalCount(conn);
		int numPerPage = 10;
		int totalPage = totalCount % numPerPage == 0 ? totalCount / numPerPage : totalCount / numPerPage + 1;
		// reqPage = 1 -> start : 1, end : 10
		int start = (reqPage - 1) * numPerPage + 1;
		int end = reqPage * numPerPage;
		ArrayList<Reserve> list = dao.selectList(conn, start, end);

		// 페이지 네비게이션 작성 시작
		int pageNaviSize = 5;
		String pageNavi = "";
		// 페이지네비 시작번호 구하기
		// reqPage : 1~5 -> 1
		int pageNo = ((reqPage - 1) / pageNaviSize) * pageNaviSize + 1;
		// 이전버튼 : 페이지 시작번호가 1이 아닌경우에만 이전버튼 생성
		if (pageNo != 1) {
			pageNavi += "<a class='btn' href='/noticeList?reqPage=" + (pageNo - 1) + "'>이전</a>";
		}

		for (int i = 0; i < pageNaviSize; ++i) {
			if (reqPage == pageNo) {
				// 현재페이지
				pageNavi += "<span class='selectPage'>" + pageNo + "</span>";
			} else {
				pageNavi += "<a class='btn' href='/noticeList?reqPage=" + pageNo + "'>" + pageNo + "</a>";
			}
			++pageNo;
			if (pageNo > totalPage) {
				break;
			}
		}
		if (pageNo <= totalPage) {
			pageNavi += "<a class='btn' href='/noticeList?reqPage=" + pageNo + "'>다음</a>";
		}
		JDBCTemplate.close(conn);
		ReservePageData rpd= new ReservePageData(list, pageNavi);
		return rpd;
	}
	private void commitOrRollback(Connection conn, int result) {
		if(result > 0) {
			JDBCTemplate.commit(conn);
		} else {
			JDBCTemplate.rollback(conn);
		}
	}
	
}
