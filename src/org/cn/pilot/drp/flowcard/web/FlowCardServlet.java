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
 * ����ά��Servlet
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
		// //��Ŀ�����ɴ������
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
	 * ��ʾ����ӡ�ҳ��
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
		// ���������̴���
		String clientId = req.getParameter("clientInnerId");
		// �跽�ͻ�����
		String[] aimIds = req.getParameterValues("aimInnerId");
		// ���ϴ���
		String[] itemNos = req.getParameterValues("itemNo");
		// ��������
		String[] qty = req.getParameterValues("qty");

		FlowCard flowCard = new FlowCard();
		// �������ͣ�
		flowCard.setOptType("A");
		// ȡ�û�ƺ�����,���������Ӧ�ô�session��ȡ��,��¼�Ϳ��Ի�֪��ǰ������
		FiscalYearPeriod fiscalYearPeriod = new FiscalYearPeriod();
		fiscalYearPeriod.setId(4);
		flowCard.setFiscalYearPeriod(fiscalYearPeriod);

		// ������
		Client client = new Client();
		client.setId(Integer.parseInt(clientId));
		flowCard.setClient(client);
		// ȡ��¼����
		flowCard.setRecorder(getUser());
		// ��������
		flowCard.setOptDate(new Date());
		// ����״̬
		flowCard.setVouSts("N");

		List<FlowCardDetail> flowCardDetailList = new ArrayList();
		for (int i = 0; i < aimIds.length; i++) {
			FlowCardDetail flowCardDetail = new FlowCardDetail();

			// �跽�ͻ�
			AimClient aimClient = new AimClient();
			aimClient.setId(Integer.parseInt(aimIds[i]));
			flowCardDetail.setAimClient(aimClient);

			// ����
			Item item = new Item();
			item.setItemNo(itemNos[i]);
			flowCardDetail.setItem(item);

			// ��������
			flowCardDetail.setOptQty(new BigDecimal(qty[i]));

			// �������
			flowCardDetail.setAdjustFlag("N");

			flowCardDetailList.add(flowCardDetail);
		}

		flowCard.setFlowCardDetailList(flowCardDetailList);

		// //ͨ������ȡ��ҵ���߼�����
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
		// ��һ�ν��� && ��ҳ
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
			throw new ApplicationException("��ҳ��ѯʧ��");
		}
		PageModel pageModel = flowCardManager.findFlowCardList(pageNo, pageSize, clientId, dBeginDate, dEndDate);

		req.setAttribute("dBeginDate", dBeginDate);
		req.setAttribute("dEndDate", dEndDate);
		req.setAttribute("pageModel", pageModel);

		req.getRequestDispatcher("/flowcard/flow_card_maint.jsp").forward(req, resp);
	}

}
