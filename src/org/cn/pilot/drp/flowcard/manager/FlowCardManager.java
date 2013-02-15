package org.cn.pilot.drp.flowcard.manager;

import java.util.Date;

import org.cn.pilot.drp.flowcard.domain.FlowCard;
import org.cn.pilot.drp.util.ApplicationException;
import org.cn.pilot.drp.util.PageModel;

/**
 * ����ά��ҵ���ӿ�
 * 
 * @author Pilot
 * 
 */
public interface FlowCardManager {

	/**
	 * �������
	 * 
	 * @param flowCard
	 * @throws ApplicationException
	 */
	public void addFlowCard(FlowCard flowCard) throws ApplicationException;

	/**
	 * ɾ������
	 * 
	 * @param flowCardVouNos
	 * @throws ApplicationException
	 */
	public void delFlowCard(String[] flowCardVouNos) throws ApplicationException;

	/**
	 * �޸�����
	 * 
	 * @param flowCard
	 * @throws ApplicationException
	 */
	public void modifyFlowCard(FlowCard flowCard) throws ApplicationException;

	/**
	 * ��ҳ��ѯ
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param clientId
	 *            ��ѯ�������û�id
	 * @param beginDate
	 *            ��ѯ������ ��ʼ����
	 * @param endDate
	 *            ��ѯ���ۣ� ��������
	 * @return
	 * @throws ApplicationException
	 */
	public PageModel<FlowCard> findFlowCardList(int pageNo, int pageSize, String clientId, Date beginDate, Date endDate)
			throws ApplicationException;

	/**
	 * ��������
	 * 
	 * @param flowCardVouNos
	 * @throws ApplicationException
	 */
	public void auditFlowCard(String[] flowCardVouNos) throws ApplicationException;

	/**
	 * �������򵥵��Ų�ѯ��ϸ��Ϣ
	 * 
	 * @param flowCardVouNo
	 * @return
	 * @throws ApplicationException
	 */
	public FlowCard findFlowCardDetail(String flowCardVouNo) throws ApplicationException;
}
