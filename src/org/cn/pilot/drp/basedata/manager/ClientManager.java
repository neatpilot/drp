package org.cn.pilot.drp.basedata.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.cn.pilot.drp.basedata.domain.Client;
import org.cn.pilot.drp.util.DbUtil;
import org.cn.pilot.drp.util.domain.ClientLevel;

/**
 * manager client info
 * 
 * @author Pilot
 * 
 */
public class ClientManager {

	private static ClientManager instance = new ClientManager();

	private ClientManager() {

	}

	public static ClientManager getInstance() {
		return instance;
	}

	/**
	 * return TREE HTML code
	 * 
	 * @return
	 */
	public String getClientTreeHTMLString() {
		return new ClientTreeReader().getClientTreeHTMLString();
	}

	/**
	 * find client or region by id
	 * 
	 * @param id
	 * @return
	 */
	public Client findClientOrRegionById(int id) {
		// left join
		StringBuffer sbSql = new StringBuffer();
		sbSql.append("select a.id, a.pid, a.name, a.client_id, a.client_level_id, ")
				.append("b.name as client_level_name, a.bank_acct_no, a.contact_tel, a.address, a.zip_code, ").append("a.is_client, a.is_leaf ")
				.append("from t_client a left join t_data_dict b on a.client_level_id=b.id where a.id=?");

		Client client = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = DbUtil.getConnection();
			pstmt = conn.prepareStatement(sbSql.toString());
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				client = new Client();
				client = new Client();
				client.setId(rs.getInt("id"));
				client.setPid(rs.getInt("pid"));
				client.setName(rs.getString("name"));
				client.setClientId(rs.getString("client_id"));
				client.setBankAcctNo(rs.getString("bank_acct_no"));
				client.setContactTel(rs.getString("contact_tel"));
				client.setAddress(rs.getString("address"));
				client.setZipCode(rs.getString("zip_code"));
				client.setIsClient(rs.getString("is_client"));
				client.setIsLeaf(rs.getString("is_leaf"));

				// client level
				ClientLevel clientLevel = new ClientLevel();
				clientLevel.setId(rs.getString("client_level_id"));
				clientLevel.setName(rs.getString("client_level_name"));
				client.setClientLevel(clientLevel);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(rs);
			DbUtil.close(pstmt);
			DbUtil.close(conn);
		}

		return client;
	}

}
