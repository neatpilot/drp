package org.cn.pilot.drp.flowcard.web;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cn.pilot.drp.basedata.domain.AimClient;
import org.cn.pilot.drp.basedata.domain.Client;
import org.cn.pilot.drp.basedata.domain.FiscalYearPeriod;
import org.cn.pilot.drp.basedata.domain.Item;
import org.cn.pilot.drp.flowcard.domain.FlowCard;
import org.cn.pilot.drp.flowcard.domain.FlowCardDetail;
import org.cn.pilot.drp.flowcard.manager.FlowCardManager;
import org.cn.pilot.drp.util.Constants;
import org.cn.pilot.drp.util.PageModel;
import org.cn.pilot.drp.util.exception.ApplicationException;
import org.cn.pilot.drp.util.servlet.BaseServlet;

/**
 * 流向单维护Servlet
 * 
 * @author Pilot
 * 
 */
public class FlowCardServlet extends BaseServlet {
	private FlowCardManager flowCardManager;

	@Override
	public void init() throws ServletException {
		flowCardManager = (FlowCardManager) getBeanFactory().getServiceObject(FlowCardManager.class);
		// TransactionHandler transactionHandler = new TransactionHandler();
		// //对目标生成代理对象
		// flowCardManager = (FlowCardManager)transactionHandler.newProxyInstance(flowCardManager);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (Constants.SHOW_ADD.equals(getCommand())) {
			showAdd(req, resp);
		} else if (Constants.ADD.equals(getCommand())) {
			add(req, resp);
		} else if (Constants.DELETE.equals(getCommand())) {
			del(req, resp);
		} else if (Constants.MODIFY.equals(getCommand())) {

		} else if (Constants.AUDIT.equals(getCommand())) {
			audit(req, resp);
		} else {
			search(req, resp);
		}
	}

	/**
	 * 显示“添加”页面
	 * 
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void showAdd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendRedirect(req.getContextPath() + "/flowcard/flow_card_add.jsp");
	}

	private void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 供方分销商代码
		String clientId = req.getParameter("clientInnerId");
		// 需方客户代码
		String[] aimIds = req.getParameterValues("aimInnerId");
		// 物料代码
		String[] itemNos = req.getParameterValues("itemNo");
		// 操作数量
		String[] qty = req.getParameterValues("qty");

		FlowCard flowCard = new FlowCard();
		// 操作类型，
		flowCard.setOptType("A");
		// 取得会计核算期,正常情况下应该从session中取得,登录就可以获知当前核算期
		FiscalYearPeriod fiscalYearPeriod = new FiscalYearPeriod();
		fiscalYearPeriod.setId(4);
		flowCard.setFiscalYearPeriod(fiscalYearPeriod);

		// 分销商
		Client client = new Client();
		client.setId(Integer.parseInt(clientId));
		flowCard.setClient(client);
		// 取得录入人
		flowCard.setRecorder(getUser());
		// 操作日期
		flowCard.setOptDate(new Date());
		// 单据状态
		flowCard.setVouSts("N");

		List<FlowCardDetail> flowCardDetailList = new ArrayList();
		for (int i = 0; i < aimIds.length; i++) {
			FlowCardDetail flowCardDetail = new FlowCardDetail();

			// 需方客户
			AimClient aimClient = new AimClient();
			aimClient.setId(Integer.parseInt(aimIds[i]));
			flowCardDetail.setAimClient(aimClient);

			// 物料
			Item item = new Item();
			item.setItemNo(itemNos[i]);
			flowCardDetail.setItem(item);

			// 操作数量
			flowCardDetail.setOptQty(new BigDecimal(qty[i]));

			// 调整标记
			flowCardDetail.setAdjustFlag("N");

			flowCardDetailList.add(flowCardDetail);
		}

		flowCard.setFlowCardDetailList(flowCardDetailList);

		// //通过工厂取得业务逻辑对象
		// FlowCardManager flowCardManager = (FlowCardManager)getBeanFactory().getServiceObject(FlowCardManager.class);
		flowCardManager.addFlowCard(flowCard);

		// response.sendRedirect(request.getContextPath() + "/flowcard/flow_card_maint.jsp");
		// response.sendRedirect(request.getContextPath() + "/servlet/flowcard/FlowCardServlet?command=" +
		// Constants.QUERY);
		resp.sendRedirect(req.getContextPath() + "/servlet/flowcard/FlowCardServlet");
	}

	private void del(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String[] flowCardVouNos = req.getParameterValues("selectFlag");
		flowCardManager.delFlowCard(flowCardVouNos);
		resp.sendRedirect(req.getContextPath() + "/servlet/flowcard/FlowCardServlet");
	}

	private void audit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String[] flowCardVouNos = req.getParameterValues("selectFlag");
		flowCardManager.auditFlowCard(flowCardVouNos);
		resp.sendRedirect(req.getContextPath() + "/servlet/flowcard/FlowCardServlet");
	}

	private void search(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 第一次进入 && 分页
		String clientId = req.getParameter("clientId");
		String beginDate = req.getParameter("beginDate");
		String endDate = req.getParameter("endDate");
		int pageNo = 1;
		if (req.getParameter("pageNo") != null && !"".equals(req.getParameter("pageNo"))) {
			pageNo = Integer.parseInt(req.getParameter("pageNo"));
		}
		int pageSize = Integer.parseInt(this.getServletContext().getInitParameter("page-size"));

		Date dBeginDate = null;
		Date dEndDate = null;
		try {
			if (beginDate != null && !"".equals(beginDate)) {
				dBeginDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(beginDate + " 00:00:00");
			} else {
				dBeginDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(new SimpleDateFormat("yyyy-MM-dd")
						.format(new Date()) + " 00:00:00");
			}
			if (endDate != null && !"".equals(endDate)) {
				dEndDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endDate + " 23:59:59");
			} else {
				dEndDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(new SimpleDateFormat("yyyy-MM-dd")
						.format(new Date()) + " 23:59:59");
			}
		} catch (ParseException e1) {
			throw new ApplicationException("分页查询失败");
		}
		PageModel pageModel = flowCardManager.findFlowCardList(pageNo, pageSize, clientId, dBeginDate, dEndDate);

		req.setAttribute("dBeginDate", dBeginDate);
		req.setAttribute("dEndDate", dEndDate);
		req.setAttribute("pageModel", pageModel);

		req.getRequestDispatcher("/flowcard/flow_card_maint.jsp").forward(req, resp);
	}

}
