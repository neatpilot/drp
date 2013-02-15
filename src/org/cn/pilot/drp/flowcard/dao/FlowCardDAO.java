package org.cn.pilot.drp.flowcard.dao;

import java.util.Date;
import java.util.List;

import org.cn.pilot.drp.flowcard.domain.FlowCard;
import org.cn.pilot.drp.flowcard.domain.FlowCardDetail;
import org.cn.pilot.drp.util.exception.DAOException;

/**
 * 流向单DAO层接口
 * 
 * @author Pilot
 * 
 */
public interface FlowCardDAO {
	/**
	 * 生成流向单号
	 * 
	 * @return
	 */
	public String generateVouNo() throws DAOException;

	/**
	 * 添加流向单主信息
	 * 
	 * @param flowCardVouNo
	 * @param flowCard
	 * @throws DAOException
	 */
	public void addFlowCardMaster(String flowCardVouNo, FlowCard flowCard) throws DAOException;

	/**
	 * 添加流向单明细信息
	 * 
	 * @param flowCardVouNo
	 * @param flowCardDetailList
	 * @throws DAOException
	 */
	public void addFlowCardDetail(String flowCardVouNo, List<FlowCardDetail> flowCardDetailList) throws DAOException;

	/**
	 * 删除流向单主信息
	 * 
	 * @param flowCardVouNos
	 * @throws DAOException
	 */
	public void delFlowCardMaster(String[] flowCardVouNos) throws DAOException;

	/**
	 * 删除流向单明细
	 * 
	 * @param flowCardVouNos
	 * @throws DAOException
	 */
	public void delFlowCardDetail(String[] flowCardVouNos) throws DAOException;

	/**
	 * 修改流向单主信息
	 * 
	 * @param flowCardVouNo
	 * @param flowCard
	 * @throws DAOException
	 */
	public void modifyFlowCardMaster(String flowCardVouNo, FlowCard flowCard) throws DAOException;

	/**
	 * 修改流向单明细信息
	 * 
	 * @param flowCardVouNo
	 * @param flowCardDetailList
	 * @throws DAOException
	 */
	public void modifyFlowCardDetail(String flowCardVouNo, List<FlowCardDetail> flowCardDetailList) throws DAOException;

	/**
	 * 分页查询
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param clientId
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public List<FlowCard> findFlowCardList(int pageNo, int pageSize, String clientId, Date beginDate, Date endDate)
			throws DAOException;

	/**
	 * 根据条件取得记录数
	 * 
	 * @param clientId
	 * @param beginDate
	 * @param endDate
	 * @return
	 * @throws DAOException
	 */
	public int getRecordCount(String clientId, Date beginDate, Date endDate) throws DAOException;

	/**
	 * 送审流向单
	 * 
	 * @param flowCardVouNos
	 * @throws DAOException
	 */
	public void auditFlowCard(String[] flowCardVouNos) throws DAOException;

	/**
	 * 返回流向单主信息
	 * 
	 * @param vouNo
	 * @return
	 * @throws DAOException
	 */
	public FlowCard findFlowCardMaster(String vouNo) throws DAOException;

	/**
	 * 返回流向明细主信息列表
	 * 
	 * @param vouNo
	 * @return
	 * @throws DAOException
	 */
	public List<FlowCardDetail> findFlowCardDetailList(String vouNo) throws DAOException;
}
