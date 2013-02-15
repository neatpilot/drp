package org.cn.pilot.drp.flowcard.manager;

import java.util.Date;

import org.cn.pilot.drp.flowcard.domain.FlowCard;
import org.cn.pilot.drp.util.ApplicationException;
import org.cn.pilot.drp.util.PageModel;

/**
 * 流向单维护业务层接口
 * 
 * @author Pilot
 * 
 */
public interface FlowCardManager {

	/**
	 * 添加流向单
	 * 
	 * @param flowCard
	 * @throws ApplicationException
	 */
	public void addFlowCard(FlowCard flowCard) throws ApplicationException;

	/**
	 * 删除流向单
	 * 
	 * @param flowCardVouNos
	 * @throws ApplicationException
	 */
	public void delFlowCard(String[] flowCardVouNos) throws ApplicationException;

	/**
	 * 修改流向单
	 * 
	 * @param flowCard
	 * @throws ApplicationException
	 */
	public void modifyFlowCard(FlowCard flowCard) throws ApplicationException;

	/**
	 * 分页查询
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param clientId
	 *            查询条件：用户id
	 * @param beginDate
	 *            查询条件： 开始日期
	 * @param endDate
	 *            查询调价： 结束日期
	 * @return
	 * @throws ApplicationException
	 */
	public PageModel<FlowCard> findFlowCardList(int pageNo, int pageSize, String clientId, Date beginDate, Date endDate)
			throws ApplicationException;

	/**
	 * 送审流向单
	 * 
	 * @param flowCardVouNos
	 * @throws ApplicationException
	 */
	public void auditFlowCard(String[] flowCardVouNos) throws ApplicationException;

	/**
	 * 根据流向单单号查询详细信息
	 * 
	 * @param flowCardVouNo
	 * @return
	 * @throws ApplicationException
	 */
	public FlowCard findFlowCardDetail(String flowCardVouNo) throws ApplicationException;
}
