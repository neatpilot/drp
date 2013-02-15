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
			// 生成流向单单号
			String flowCardVouNo = flowCardDAO.generateVouNo();
			// 添加流向单主信息
			flowCardDAO.addFlowCardMaster(flowCardVouNo, flowCard);
			// 添加流向单明细信息
			flowCardDAO.addFlowCardDetail(flowCardVouNo, flowCard.getFlowCardDetailList());
		} catch (DAOException e) {
			throw new ApplicationException("添加流向单失败！");
		}
	}

	@Override
	public void delFlowCard(String[] flowCardVouNos) throws ApplicationException {
		try {
			// 删除流向单明细
			flowCardDAO.delFlowCardDetail(flowCardVouNos);

			// 删除流向单主表
			flowCardDAO.delFlowCardMaster(flowCardVouNos);
		} catch (DAOException e) {
			throw new ApplicationException("删除流向单失败！");
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
			// 获取分页数据
			List<FlowCard> flowCardList = flowCardDAO.findFlowCardList(pageNo, pageSize, clientId, beginDate, endDate);
			// 获取查询总数
			int totalRecords = flowCardDAO.getRecordCount(clientId, beginDate, endDate);
			// 组装PageModel
			pageModel = new PageModel<FlowCard>();
			pageModel.setList(flowCardList);
			pageModel.setPageNo(pageNo);
			pageModel.setPageSize(pageSize);
			pageModel.setTotalRecords(totalRecords);
		} catch (DAOException e) {
			throw new ApplicationException("分页查询流向单失败!");
		}
		return pageModel;
	}

	@Override
	public void auditFlowCard(String[] flowCardVouNos) throws ApplicationException {
		try {
			flowCardDAO.auditFlowCard(flowCardVouNos);
		} catch (DAOException e) {
			throw new ApplicationException("添加流向单失败！");
		}
	}

	@Override
	public FlowCard findFlowCardDetail(String flowCardVouNo) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

}
