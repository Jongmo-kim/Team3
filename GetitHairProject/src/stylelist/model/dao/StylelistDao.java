package stylelist.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import common.JDBCTemplate;
import designer.model.service.DesignerService;
import designer.model.vo.Designer;
import shopprice.model.vo.ShopPrice;
import style.model.service.StyleService;
import style.model.vo.Style;
import stylelist.model.vo.Stylelist;

public class StylelistDao {

	public Stylelist selectOneStylelist(Connection conn, int no) {
		Stylelist stylelist = new Stylelist();
		String sql = "select * from style_list where stylelist_no = ?";
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			rset = pstmt.executeQuery();
			if(rset.next()) {
				stylelist = getStylelistFromRset(rset);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
			JDBCTemplate.close(rset);
		}
		
		return stylelist;
	}

	private Stylelist getStylelistFromRset(ResultSet rset) {
		Stylelist stylelist = new Stylelist();
		try {
			ShopPrice shopprice = new ShopPriceService().selectOneShopPrice(rset.getInt("shop_price_no"));
			stylelist.setShopPrice(shopprice);
			stylelist.setDesigner(getDesignerByNo(rset.getInt("designer_no")));
			stylelist.setStyle(getStyleByNo(rset.getInt("style_no")));
			stylelist.setStylelistNo(rset.getInt("stylelist_no"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return stylelist;
	}
	private Style getStyleByNo(int no) {
		Style style = new StyleService().selectOneStyle(no);
		if(style == null) {
			style = new Style();
			style.setStyleNo(-1);
	      }
		return style;
	}
	  private Designer getDesignerByNo(int no) {
	      Designer designer = new DesignerService().selectOneDesigner(no);
	      if(designer == null) {
	         designer = new Designer();
	         designer.setDesignerNo(-1);
	      }
	      return designer;
	   }
}
