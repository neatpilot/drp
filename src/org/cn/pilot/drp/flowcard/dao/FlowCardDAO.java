package org.cn.pilot.drp.flowcard.dao;

import java.util.Date;
import java.util.List;

import org.cn.pilot.drp.flowcard.domain.FlowCard;
import org.cn.pilot.drp.flowcard.domain.FlowCardDetail;
import org.cn.pilot.drp.util.exception.DAOException;

/**
 * ����DAO��ӿ�
 * 
 * @author Pilot
 * 
 */
public interface FlowCardDAO {
	/**
	 * �������򵥺�
	 * 
	 * @return
	 */
	public String generateVouNo() throws DAOException;

	/**
	 * �����������Ϣ
	 * 
	 * @param flowCardVouNo
	 * @param flowCard
	 * @throws DAOException
	 */
	public void addFlowCardMaster(String flowCardVouNo, FlowCard flowCard) throws DAOException;

	/**
	 * ���������ϸ��Ϣ
	 * 
	 * @param flowCardVouNo
	 * @param flowCardDetailList
	 * @throws DAOException
	 */
	public void addFlowCardDetail(String flowCardVouNo, List<FlowCardDetail> flowCardDetailList) throws DAOException;

	/**
	 * ɾ����������Ϣ
	 * 
	 * @param flowCardVouNos
	 * @throws DAOException
	 */
	public void delFlowCardMaster(String[] flowCardVouNos) throws DAOException;

	/**
	 * ɾ��������ϸ
	 * 
	 * @param flowCardVouNos
	 * @throws DAOException
	 */
	public void delFlowCardDetail(String[] flowCardVouNos) throws DAOException;

	/**
	 * �޸���������Ϣ
	 * 
	 * @param flowCardVouNo
	 * @param flowCard
	 * @throws DAOException
	 */
	public void modifyFlowCardMaster(String flowCardVouNo, FlowCard flowCard) throws DAOException;

	/**
	 * �޸�������ϸ��Ϣ
	 * 
	 * @param flowCardVouNo
	 * @param flowCardDetailList
	 * @throws DAOException
	 */
	public void modifyFlowCardDetail(String flowCardVouNo, List<FlowCardDetail> flowCardDetailList) throws DAOException;

	/**
	 * ��ҳ��ѯ
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
	 * ��������ȡ�ü�¼��
	 * 
	 * @param clientId
	 * @param beginDate
	 * @param endDate
	 * @return
	 * @throws DAOException
	 */
	public int getRecordCount(String clientId, Date beginDate, Date endDate) throws DAOException;

	/**
	 * ��������
	 * 
	 * @param flowCardVouNos
	 * @throws DAOException
	 */
	public void auditFlowCard(String[] flowCardVouNos) throws DAOException;

	/**
	 * ������������Ϣ
	 * 
	 * @param vouNo
	 * @return
	 * @throws DAOException
	 */
	public FlowCard findFlowCardMaster(String vouNo) throws DAOException;

	/**
	 * ����������ϸ����Ϣ�б�
	 * 
	 * @param vouNo
	 * @return
	 * @throws DAOException
	 */
	public List<FlowCardDetail> findFlowCardDetailList(String vouNo) throws DAOException;
}
