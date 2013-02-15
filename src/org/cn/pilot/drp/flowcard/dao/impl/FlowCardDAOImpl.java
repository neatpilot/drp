package org.cn.pilot.drp.flowcard.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.cn.pilot.drp.basedata.domain.Client;
import org.cn.pilot.drp.flowcard.dao.FlowCardDAO;
import org.cn.pilot.drp.flowcard.domain.FlowCard;
import org.cn.pilot.drp.flowcard.domain.FlowCardDetail;
import org.cn.pilot.drp.sysmgr.domain.User;
import org.cn.pilot.drp.util.database.ConnectionManager;
import org.cn.pilot.drp.util.exception.DAOException;

public class FlowCardDAOImpl implements FlowCardDAO {

	@Override
	public String generateVouNo() throws DAOException {
		String sql = "SELECT MAX(flow_card_no) FROM t_flow_card_master WHERE SUBSTR(flow_card_no,1,8)=?";
		String today = new SimpleDateFormat("yyyyMMdd").format(new Date());
		String vouNo = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = ConnectionManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, today);
			rs = pstmt.executeQuery();
			rs.next();
			long max = rs.getLong(1);
			if (max != 0) {
				vouNo = String.valueOf(max + 1);
			} else {
				vouNo = today + "0001";
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException(e);
		} finally {
			ConnectionManager.close(rs);
			ConnectionManager.close(pstmt);
		}
		return vouNo;
	}

	@Override
	public void addFlowCardMaster(String flowCardVouNo, FlowCard flowCard) throws DAOException {
		StringBuffer sbSql = new StringBuffer();
		sbSql.append("insert into t_flow_card_master (flow_card_no, opt_type, fiscal_year_period_id, ")
				.append("client_id, recorder_id, opt_date, vou_sts) ").append("values (?, ?, ?, ?, ?, ?, ?) ");
		PreparedStatement pstmt = null;
		try {
			Connection conn = ConnectionManager.getConnection();
			pstmt = conn.prepareStatement(sbSql.toString());
			pstmt.setString(1, flowCardVouNo);
			pstmt.setString(2, flowCard.getOptType());
			pstmt.setInt(3, flowCard.getFiscalYearPeriod().getId());
			pstmt.setInt(4, flowCard.getClient().getId());
			pstmt.setString(5, flowCard.getRecorder().getUserId());
			pstmt.setTimestamp(6, new Timestamp(flowCard.getOptDate().getTime()));
			pstmt.setString(7, flowCard.getVouSts());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException(e);
		} finally {
			ConnectionManager.close(pstmt);
		}
	}

	@Override
	public void addFlowCardDetail(String flowCardVouNo, List<FlowCardDetail> flowCardDetailList) throws DAOException {
		StringBuffer sbSql = new StringBuffer();
		sbSql.append("insert into t_flow_card_detail (flow_card_no, aim_client_id, item_no, opt_qty, adjust_flag) ")
				.append("values (?, ?, ?, ?, ?) ");
		PreparedStatement pstmt = null;
		try {
			Connection conn = ConnectionManager.getConnection();
			pstmt = conn.prepareStatement(sbSql.toString());
			for (Iterator<FlowCardDetail> iter = flowCardDetailList.iterator(); iter.hasNext();) {
				FlowCardDetail flowCardDetail = iter.next();
				pstmt.setString(1, flowCardVouNo);
				pstmt.setInt(2, flowCardDetail.getAimClient().getId());
				pstmt.setString(3, flowCardDetail.getItem().getItemNo());
				pstmt.setBigDecimal(4, flowCardDetail.getOptQty());
				pstmt.setString(5, flowCardDetail.getAdjustFlag());
				pstmt.addBatch();
			}
			pstmt.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException(e);
		} finally {
			ConnectionManager.close(pstmt);
		}
	}

	@Override
	public void delFlowCardMaster(String[] flowCardVouNos) throws DAOException {
		StringBuffer sbStr = new StringBuffer();
		for (int i = 0; i < flowCardVouNos.length; i++) {
			sbStr.append("?");
			if (i < (flowCardVouNos.length - 1)) {
				sbStr.append(",");
			}
		}
		String sql = "delete from t_flow_card_master where flow_card_no in (" + sbStr + ")";
		PreparedStatement pstmt = null;
		try {
			Connection conn = ConnectionManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			for (int i = 0; i < flowCardVouNos.length; i++) {
				pstmt.setString(i + 1, flowCardVouNos[i]);
			}
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException(e);
		} finally {
			ConnectionManager.close(pstmt);
		}

	}

	@Override
	public void delFlowCardDetail(String[] flowCardVouNos) throws DAOException {
		StringBuffer sbStr = new StringBuffer();
		for (int i = 0; i < flowCardVouNos.length; i++) {
			sbStr.append("?");
			if (i < (flowCardVouNos.length - 1)) {
				sbStr.append(",");
			}
		}
		String sql = "delete from t_flow_card_detail where flow_card_no in (" + sbStr.toString() + ")";
		PreparedStatement pstmt = null;
		try {
			Connection conn = ConnectionManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			for (int i = 0; i < flowCardVouNos.length; i++) {
				pstmt.setString(i + 1, flowCardVouNos[i]);
			}
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("FlowCardDaoImpl-->>delFlowCardDetail, exception:" + e);
			throw new DAOException(e);
		} finally {
			ConnectionManager.close(pstmt);
		}
	}

	@Override
	public void modifyFlowCardMaster(String flowCardVouNo, FlowCard flowCard) throws DAOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void modifyFlowCardDetail(String flowCardVouNo, List<FlowCardDetail> flowCardDetailList) throws DAOException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<FlowCard> findFlowCardList(int pageNo, int pageSize, String clientId, Date beginDate, Date endDate)
			throws DAOException {
		StringBuffer sbSql = new StringBuffer();
		sbSql.append("select FLOW_CARD_NO, CLIENT_ID, CLIENT_NAME, USER_NAME, OPT_DATE ")
				.append("from ")
				.append("( ")
				.append("select ROWNUM AS RN, FLOW_CARD_NO, CLIENT_ID, CLIENT_NAME, USER_NAME, OPT_DATE ")
				.append("from ")
				.append("( ")
				.append("select a.FLOW_CARD_NO, b.CLIENT_ID, b.NAME as CLIENT_NAME, c.USER_NAME, a.OPT_DATE ")
				.append("from t_flow_card_master a join t_client b on a.CLIENT_ID=b.ID join t_user c on a.RECORDER_ID=c.USER_ID where a.VOU_STS='N' and ");
		if (clientId != null && !"".equals(clientId)) {
			sbSql.append(" b.CLIENT_ID=? and ");
		}
		sbSql.append("  a.OPT_DATE between ? and ? ");
		sbSql.append("order by a.FLOW_CARD_NO ").append(") where ROWNUM <=? ").append(") where RN > ? ");
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<FlowCard> flowCardList = new ArrayList<FlowCard>();
		try {
			Connection conn = ConnectionManager.getConnection();
			pstmt = conn.prepareStatement(sbSql.toString());
			if (clientId != null && !"".equals(clientId)) {
				pstmt.setString(1, clientId);
				pstmt.setTimestamp(2, new Timestamp(beginDate.getTime()));
				pstmt.setTimestamp(3, new Timestamp(endDate.getTime()));
				pstmt.setInt(4, pageNo * pageSize);
				pstmt.setInt(5, (pageNo - 1) * pageSize);
			} else {
				pstmt.setTimestamp(1, new Timestamp(beginDate.getTime()));
				pstmt.setTimestamp(2, new Timestamp(endDate.getTime()));
				pstmt.setInt(3, pageNo * pageSize);
				pstmt.setInt(4, (pageNo - 1) * pageSize);
			}
			rs = pstmt.executeQuery();
			while (rs.next()) {
				FlowCard flowCard = new FlowCard();
				flowCard.setFlowCardNo(rs.getString("FLOW_CARD_NO"));

				Client client = new Client();
				client.setClientId(rs.getString("CLIENT_ID"));
				client.setName(rs.getString("CLIENT_NAME"));
				flowCard.setClient(client);

				User recorder = new User();
				recorder.setUserName(rs.getString("USER_NAME"));
				flowCard.setRecorder(recorder);

				flowCard.setOptDate(rs.getTimestamp("OPT_DATE"));

				flowCardList.add(flowCard);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException(e);
		} finally {
			ConnectionManager.close(rs);
			ConnectionManager.close(pstmt);
		}
		return flowCardList;
	}

	@Override
	public int getRecordCount(String clientId, Date beginDate, Date endDate) throws DAOException {
		String sql = "select count(*) from t_flow_card_master a join t_client b on a.CLIENT_ID=b.ID where ";
		if (clientId != null && !"".equals(clientId)) {
			sql += " b.CLIENT_ID=? and ";
		}
		sql += " a.OPT_DATE between ? and ? ";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count = 0;
		try {
			Connection conn = ConnectionManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			if (clientId != null && !"".equals(clientId)) {
				pstmt.setString(1, clientId);
				pstmt.setTimestamp(2, new Timestamp(beginDate.getTime()));
				pstmt.setTimestamp(3, new Timestamp(endDate.getTime()));
			} else {
				pstmt.setTimestamp(1, new Timestamp(beginDate.getTime()));
				pstmt.setTimestamp(2, new Timestamp(endDate.getTime()));
			}
			rs = pstmt.executeQuery();
			rs.next();
			count = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException(e);
		} finally {
			ConnectionManager.close(rs);
			ConnectionManager.close(pstmt);
		}
		return count;
	}

	@Override
	public void auditFlowCard(String[] flowCardVouNos) throws DAOException {
		StringBuffer sbStr = new StringBuffer();
		for (int i = 0; i < flowCardVouNos.length; i++) {
			sbStr.append("?");
			if (i < (flowCardVouNos.length - 1)) {
				sbStr.append(",");
			}
		}
		String sql = "update t_flow_card_master set vou_sts='S' where flow_card_no in(" + sbStr.toString() + ")";
		PreparedStatement pstmt = null;
		try {
			Connection conn = ConnectionManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			for (int i = 0; i < flowCardVouNos.length; i++) {
				pstmt.setString(i + 1, flowCardVouNos[i]);
			}
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("FlowCardDaoImpl-->>auditFlowCard, exception:" + e);
			throw new DAOException(e);
		} finally {
			ConnectionManager.close(pstmt);
		}
	}

	@Override
	public FlowCard findFlowCardMaster(String vouNo) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FlowCardDetail> findFlowCardDetailList(String vouNo) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

}
