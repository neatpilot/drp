package org.cn.pilot.drp.basedata.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.cn.pilot.drp.util.Constants;
import org.cn.pilot.drp.util.configuration.ConnectionManager;

public class ClientTreeReader {

	private StringBuffer treeHTMLBuffer = new StringBuffer();
	
	/**
	 * return tree structure HTML code 
	 * @return
	 */
	public String getClientTreeHTMLString() {
		Connection conn = null;
		try {
			conn = ConnectionManager.getConnection();
			readClientTree(conn, 0, 0);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			ConnectionManager.close(conn);
		}
		return treeHTMLBuffer.toString();
	}
	
	/**
	 * recursive method to get a TREE HTML code
	 * 
	 * @param conn
	 * @param id
	 *            :parent id
	 * @param level
	 *            : user for 'div' in client_tree.jsp
	 * @throws SQLException
	 */
	private void readClientTree(Connection conn, int id, int level) throws SQLException {
		String sql = "SELECT * FROM t_client where pid=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			while(rs.next()){
				treeHTMLBuffer.append("<div>");
				treeHTMLBuffer.append("\n");
				for (int i=0; i<level; i++) {
					treeHTMLBuffer.append("<img src=\"../images/white.gif\">");
					treeHTMLBuffer.append("\n");
				}
				
				//if ("N".equals(rs.getString("is_leaf"))) {
				if (Constants.NO.equals(rs.getString("is_leaf"))) {
					treeHTMLBuffer.append("<img alt=\"Õ¹¿ª\" style=\"cursor:hand;\" onClick=\"display('" + rs.getInt("id") + "');\" id=\"img" + rs.getInt("id") + "\" src=\"../images/plus.gif\">");
					treeHTMLBuffer.append("\n");
					treeHTMLBuffer.append("<img id=\"im" + rs.getInt("id") + "\" src=\"../images/closedfold.gif\">");
					treeHTMLBuffer.append("\n");
					treeHTMLBuffer.append("<a href=\"client_node_crud.jsp?id=" + rs.getInt("id") + "\" target=\"clientDispAreaFrame\">" + rs.getString("name") + "</a>");
					treeHTMLBuffer.append("\n");
					treeHTMLBuffer.append("<div style=\"display:none;\" id=\"div" + rs.getInt("id") + "\">");
					treeHTMLBuffer.append("\n");
					readClientTree(conn, rs.getInt("id"), level + 1);
					treeHTMLBuffer.append("</div>");
					treeHTMLBuffer.append("\n");
				}else {
					treeHTMLBuffer.append("<img src=\"../images/minus.gif\">");
					treeHTMLBuffer.append("\n");
					treeHTMLBuffer.append("<img src=\"../images/openfold.gif\">");
					treeHTMLBuffer.append("\n");
					//if ("Y1".equals(rs.getString("is_client"))) {
					if (Constants.YES.equals(rs.getString("is_client"))) {
						treeHTMLBuffer.append("<a href=\"client_crud.jsp?id=" + rs.getInt("id") + "\" target=\"clientDispAreaFrame\">" + rs.getString("name") +  "</a>");
					}else {
						treeHTMLBuffer.append("<a href=\"client_node_crud.jsp?id=" + rs.getInt("id") + "\" target=\"clientDispAreaFrame\">" + rs.getString("name") + "</a>");
					}
					treeHTMLBuffer.append("\n");
				}
				treeHTMLBuffer.append("</div>");
				treeHTMLBuffer.append("\n");
			}
		} finally {
			ConnectionManager.close(pstmt);
			ConnectionManager.close(rs);
		}
	}
}
