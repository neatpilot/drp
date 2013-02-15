package org.cn.pilot.drp.basedata.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.cn.pilot.drp.basedata.domain.AimClient;
import org.cn.pilot.drp.basedata.domain.Client;
import org.cn.pilot.drp.util.Constants;
import org.cn.pilot.drp.util.DbUtil;
import org.cn.pilot.drp.util.IdGenerator;
import org.cn.pilot.drp.util.PageModel;
import org.cn.pilot.drp.util.datadict.domain.ClientLevel;

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
	 * 增加分销商或区域;add a client or region into database
	 * 
	 * @param clientOrRegion
	 */
	public void addClientOrRegion(Client clientOrRegion) {
		String sql = "INSERT INTO t_client (id, pid, client_level_id,name, client_id, bank_acct_no,contact_tel, address, zip_code,is_leaf, is_client) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = DbUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			// manually control a transaction
			DbUtil.beginTransaction(conn);

			pstmt.setInt(1, IdGenerator.generate("t_client"));
			pstmt.setInt(2, clientOrRegion.getPid());
			pstmt.setString(3, clientOrRegion.getClientLevel().getId());
			pstmt.setString(4, clientOrRegion.getName());
			pstmt.setString(5, clientOrRegion.getClientId());
			pstmt.setString(6, clientOrRegion.getBankAcctNo());
			pstmt.setString(7, clientOrRegion.getContactTel());
			pstmt.setString(8, clientOrRegion.getAddress());
			pstmt.setString(9, clientOrRegion.getZipCode());
			pstmt.setString(10, clientOrRegion.getIsLeaf());
			pstmt.setString(11, clientOrRegion.getIsClient());
			pstmt.executeUpdate();

			// 修改父节点的叶子属性
			Client parentClientOrRegion = findClientOrRegionById(clientOrRegion.getPid());
			if (Constants.YES.equals(parentClientOrRegion.getIsLeaf())) {
				modifyIsLeafField(conn, parentClientOrRegion.getId(), Constants.NO);
			}

			DbUtil.commitTransaction(conn);
		} catch (SQLException e) {
			e.printStackTrace();
			DbUtil.rollbackTransaction(conn);
		} finally {
			DbUtil.close(pstmt);
			DbUtil.resetConnection(conn);
			DbUtil.close(conn);
		}
	}

	/**
	 * 根据id修改叶子信息; modify isLeaf by id
	 * 
	 * @param conn
	 * @param id
	 * @param isLeaf
	 * @throws SQLException
	 */
	private void modifyIsLeafField(Connection conn, int id, String isLeaf) throws SQLException {
		String sql = "UPDATE t_client SET is_leaf=? WHERE id=?";
		PreparedStatement pstmt = null;

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, isLeaf);
			pstmt.setInt(2, id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} finally {
			DbUtil.close(pstmt);
		}
	}

	/**
	 * modify a client or region
	 * 
	 * @param clientOrRegion
	 *            could be a client or region(but all represented by a Client model)
	 */
	public void modifyClientOrRegion(Client clientOrRegion) {
		String sql = "UPDATE t_client SET client_level_id=?,name =?,bank_acct_no=?,contact_tel=?, address=?,zip_code=? where id=?";
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = DbUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, clientOrRegion.getClientLevel().getId());
			pstmt.setString(2, clientOrRegion.getName());
			pstmt.setString(3, clientOrRegion.getBankAcctNo());
			pstmt.setString(4, clientOrRegion.getContactTel());
			pstmt.setString(5, clientOrRegion.getAddress());
			pstmt.setString(6, clientOrRegion.getZipCode());
			pstmt.setInt(7, clientOrRegion.getId());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(pstmt);
			DbUtil.close(conn);
		}
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
	 * 删除分销商或者区域;delete client or region
	 * 
	 * @param id
	 */
	public void deleteclientOrRegion(int id) {
		Connection conn = null;

		try {
			conn = DbUtil.getConnection();
			DbUtil.beginTransaction(conn);
			// store node(recursionDelNode will delete this node)
			Client client = findClientOrRegionById(id);
			// recursively delete nodes
			recursionDelNode(conn, id);

			// 原先父节点肯定不是叶子，现在检查他的孩子
			if (getChildrenNum(conn, client.getPid()) == 0) {
				modifyIsLeafField(conn, client.getPid(), Constants.YES_FLAG);
			}

			DbUtil.commitTransaction(conn);
		} catch (SQLException e) {
			e.printStackTrace();
			DbUtil.rollbackTransaction(conn);
		} finally {
			DbUtil.resetConnection(conn);
			DbUtil.close(conn);
		}

	}

	/**
	 * 递归从下往上删除分销商; recursively delete clients from leaf
	 * 
	 * @param conn
	 * @param id
	 * @throws SQLException
	 */
	private void recursionDelNode(Connection conn, int id) throws SQLException {
		String sql = "SELECT * FROM t_client WHERE pid=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();

			// 递归从下往上删除 recursively delete nodes from leaf
			// 如果有叶子，先删除叶子
			while (rs.next()) {
				if (Constants.NO_FLAG.equals(rs.getString("is_leaf"))) {
					recursionDelNode(conn, rs.getInt("id"));
				}
				delNode(conn, rs.getInt("id"));
			}
			delNode(conn, id);
		} finally {
			DbUtil.close(rs);
			DbUtil.close(pstmt);
		}
	}

	/**
	 * 根据id来删除分销商;delete client by id
	 * 
	 * @param conn
	 * @param id
	 * @throws SQLException
	 */
	private void delNode(Connection conn, int id) throws SQLException {
		String sql = "DELETE from t_client WHERE id=? ";
		PreparedStatement pstmt = null;

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
		} finally {
			DbUtil.close(pstmt);
		}
	}

	/**
	 * 得到一个节点的子节点数;get a node's children number
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	private int getChildrenNum(Connection conn, int id) throws SQLException {
		int count = 0;
		String sql = "SELECT COUNT(*) as c FROM t_client WHERE pid=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			rs.next();
			count = rs.getInt("c");
		} finally {
			DbUtil.close(rs);
			DbUtil.close(pstmt);
		}
		return count;
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
				.append("b.name as client_level_name, a.bank_acct_no, a.contact_tel, a.address, a.zip_code, ")
				.append("a.is_client, a.is_leaf ")
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

	/**
	 * 根据分销商id判断有否存在对应的分销商;according to client id, if corresponding client exists,return true
	 * 
	 * @param clientId
	 * @return
	 */
	public boolean findClientByClientId(String clientId) {
		boolean result = false;
		String sql = "SELECT COUNT(*) FROM t_client WHERE client_id=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DbUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, clientId);
			rs = pstmt.executeQuery();
			rs.next();
			if (rs.getInt(1) > 0) {
				result = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(rs);
			DbUtil.close(pstmt);
			DbUtil.close(conn);
		}

		return result;

	}

	/**
	 * 查询所有的供方分销商
	 * 
	 * 操作t_client表
	 * 
	 * @param pageNo
	 *            第几页
	 * @param pageSize
	 *            每页多少条
	 * @param queryStr
	 *            查询条件
	 * @return pageMode对象
	 */
	@SuppressWarnings("unchecked")
	public PageModel findAllClient(int pageNo, int pageSize, String queryStr) {
		StringBuffer sbSql = new StringBuffer();
		// sbSql.append("select a.id, a.pid, a.name, a.client_id, b.id as client_level_id, b.name as client_level_name, ")
		// .append("a.bank_acct_no, a.contact_tel, a.address, a.zip_code, a.is_leaf, a.is_client ")
		// .append("from t_client a, t_data_dict b where a.client_level=b.id and a.is_client='Y' ")
		// .append("and (a.client_id like '" + queryStr + "%' or ")
		// .append("a.name like '" + queryStr + "%')  order by a.id ")
		// .append("limit ").append((pageNo - 1) * pageSize).append(", ")
		// .append(pageSize);
		sbSql.append("select * from ")
				.append("(")
				.append("select t.*, rownum rn from ")
				.append("(")
				.append("select a.id, a.pid, a.name, a.client_id, b.id as client_level_id, b.name as client_level_name, ")
				.append("a.bank_acct_no, a.contact_tel, a.address, a.zip_code, a.is_leaf, a.is_client ")
				.append("from t_client a, t_data_dict b where a.client_level_id=b.id and a.is_client='Y' ")
				.append("and (a.client_id like ? or ").append("a.name like ?) order by a.id ")
				.append(") t where rownum<=?").append(") ").append("where rn > ?");
		System.out.println("sql=" + sbSql.toString());
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		PageModel pageModel = null;
		try {
			conn = DbUtil.getConnection();
			pstmt = conn.prepareStatement(sbSql.toString());
			pstmt.setString(1, queryStr + "%");
			pstmt.setString(2, queryStr + "%");
			pstmt.setInt(3, pageNo * pageSize);
			pstmt.setInt(4, (pageNo - 1) * pageSize);
			rs = pstmt.executeQuery();
			List clientList = new ArrayList();
			while (rs.next()) {
				Client client = new Client();
				client.setId(rs.getInt(1));
				client.setPid(rs.getInt(2));
				client.setName(rs.getString(3));
				client.setClientId(rs.getString(4));
				ClientLevel cl = new ClientLevel();
				cl.setId(rs.getString(5));
				cl.setName(rs.getString(6));
				client.setClientLevel(cl);
				client.setBankAcctNo(rs.getString(7));
				client.setContactTel(rs.getString(8));
				client.setAddress(rs.getString(9));
				client.setZipCode(rs.getString(10));
				client.setIsLeaf(rs.getString(11));
				client.setIsClient(rs.getString(12));
				clientList.add(client);
			}
			pageModel = new PageModel();
			pageModel.setPageNo(pageNo);
			pageModel.setPageSize(pageSize);
			pageModel.setList(clientList);
			pageModel.setTotalRecords(getTotalClientRecords(conn, queryStr));

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(rs);
			DbUtil.close(pstmt);
			DbUtil.close(conn);
		}
		return pageModel;
	}

	/**
	 * 根据条件取得供方分销商的记录数
	 * 
	 * @param conn
	 * @param queryStr
	 *            条件
	 * @return 记录数
	 */
	private int getTotalClientRecords(Connection conn, String queryStr) {
		String sql = "select count(*) from t_client where is_client='Y' and " + "(client_id like '" + queryStr
				+ "%' or name like '" + queryStr + "%')";
		int totalRecords = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				totalRecords = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(rs);
			DbUtil.close(pstmt);
		}
		return totalRecords;
	}

	/**
	 * 查询所有的需方客户
	 * 
	 * 操作v_aim_client视图
	 * 
	 * @param pageNo
	 *            第几页
	 * @param pageSize
	 *            每页多少条
	 * @param queryStr
	 *            查询条件
	 * @return pageMode对象
	 */
	public PageModel findAllAimClient(int pageNo, int pageSize, String queryStr) {
		// String sql = "select id, name, level_id, level_name from v_aim_client "
		// + "where (id like '" + queryStr + "%' or name like '"
		// + queryStr + "%') " + " order by id " + "limit " + (pageNo - 1)
		// * pageSize + ", " + pageSize;
		StringBuffer sbSql = new StringBuffer();
		sbSql.append("select * from ")
				.append("(")
				.append("select t.*,rownum rn from ")
				.append("(")
				.append("select id, client_temi_id, name, client_temi_level_id, client_temi_level_name from v_aim_client ")
				.append("where (client_temi_id like '" + queryStr + "%' or name like '" + queryStr + "%') ")
				.append(" order by id").append(") t where rownum <=?").append(") ").append("where rn >= ?");
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		PageModel pageModel = null;
		try {
			conn = DbUtil.getConnection();
			pstmt = conn.prepareStatement(sbSql.toString());
			pstmt.setInt(1, pageNo * pageSize);
			pstmt.setInt(2, (pageNo - 1) * pageSize);
			rs = pstmt.executeQuery();
			List aimClientList = new ArrayList();
			while (rs.next()) {
				AimClient aimClient = new AimClient();
				aimClient.setId(rs.getInt("id"));
				aimClient.setClientTemiId(rs.getString("client_temi_id"));
				aimClient.setName(rs.getString("name"));
				aimClient.setClientTemilevelId(rs.getString("client_temi_level_id"));
				aimClient.setClientTemilevelName(rs.getString("client_temi_level_name"));
				aimClientList.add(aimClient);
			}
			pageModel = new PageModel();
			pageModel.setPageNo(pageNo);
			pageModel.setPageSize(pageSize);
			pageModel.setList(aimClientList);
			pageModel.setTotalRecords(getTotalAimRecords(conn, queryStr));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(rs);
			DbUtil.close(pstmt);
			DbUtil.close(conn);
		}
		return pageModel;

	}

	/**
	 * 根据条件取得需方客户的记录数
	 * 
	 * @param conn
	 * @param queryStr
	 *            条件
	 * @return 记录数
	 */
	private int getTotalAimRecords(Connection conn, String queryStr) {
		String sql = "select count(*) from v_aim_client " + "where client_temi_id like '" + queryStr
				+ "%' or name like '" + queryStr + "%' ";
		int totalRecords = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				totalRecords = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(rs);
			DbUtil.close(pstmt);
		}
		return totalRecords;
	}
}
