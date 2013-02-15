package org.cn.pilot.drp.flowcard.manager.impl;

import java.util.Date;
import java.util.List;

import org.cn.pilot.drp.flowcard.dao.FlowCardDAO;
import org.cn.pilot.drp.flowcard.domain.FlowCard;
import org.cn.pilot.drp.flowcard.manager.FlowCardManager;
import org.cn.pilot.drp.util.ApplicationException;
import org.cn.pilot.drp.util.BeanFactory;
import org.cn.pilot.drp.util.PageModel;
import org.cn.pilot.drp.util.exception.DAOException;

public class FlowCardManagerImpl implements FlowCardManager {
	private FlowCardDAO flowCardDAO = null;

	public FlowCardManagerImpl() {
		this.flowCardDAO = (FlowCardDAO) BeanFactory.getInstance().getDAOObject(FlowCardDAO.class);

	}

	@Override
	public void addFlowCard(FlowCard flowCard) throws ApplicationException {
		try {
			// �������򵥵���
			String flowCardVouNo = flowCardDAO.generateVouNo();
			// �����������Ϣ
			flowCardDAO.addFlowCardMaster(flowCardVouNo, flowCard);
			// ���������ϸ��Ϣ
			flowCardDAO.addFlowCardDetail(flowCardVouNo, flowCard.getFlowCardDetailList());
		} catch (DAOException e) {
			throw new ApplicationException("�������ʧ�ܣ�");
		}
	}

	@Override
	public void delFlowCard(String[] flowCardVouNos) throws ApplicationException {
		try {
			// ɾ��������ϸ
			flowCardDAO.delFlowCardDetail(flowCardVouNos);

			// ɾ����������
			flowCardDAO.delFlowCardMaster(flowCardVouNos);
		} catch (DAOException e) {
			throw new ApplicationException("ɾ������ʧ�ܣ�");
		}
	}

	@Override
	public void modifyFlowCard(FlowCard flowCard) throws ApplicationException {
		// TODO Auto-generated method stub

	}

	@Override
	public PageModel<FlowCard> findFlowCardList(int pageNo, int pageSize, String clientId, Date beginDate, Date endDate)
			throws ApplicationException {
		PageModel<FlowCard> pageModel = null;

		try {
			// ��ȡ��ҳ����
			List<FlowCard> flowCardList = flowCardDAO.findFlowCardList(pageNo, pageSize, clientId, beginDate, endDate);
			// ��ȡ��ѯ����
			int totalRecords = flowCardDAO.getRecordCount(clientId, beginDate, endDate);
			// ��װPageModel
			pageModel = new PageModel<FlowCard>();
			pageModel.setList(flowCardList);
			pageModel.setPageNo(pageNo);
			pageModel.setPageSize(pageSize);
			pageModel.setTotalRecords(totalRecords);
		} catch (DAOException e) {
			throw new ApplicationException("��ҳ��ѯ����ʧ��!");
		}
		return pageModel;
	}

	@Override
	public void auditFlowCard(String[] flowCardVouNos) throws ApplicationException {
		try {
			flowCardDAO.auditFlowCard(flowCardVouNos);
		} catch (DAOException e) {
			throw new ApplicationException("�������ʧ�ܣ�");
		}
	}

	@Override
	public FlowCard findFlowCardDetail(String flowCardVouNo) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

}
