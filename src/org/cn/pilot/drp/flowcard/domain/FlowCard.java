package org.cn.pilot.drp.flowcard.domain;

import java.util.Date;
import java.util.List;

import org.cn.pilot.drp.basedata.domain.Client;
import org.cn.pilot.drp.basedata.domain.FiscalYearPeriod;
import org.cn.pilot.drp.sysmgr.domain.User;

/**
 * 流向单主信息
 * 
 * @author Pilot
 * 
 */
public class FlowCard {

	/**
	 * 流向单号yyyymmdd0001
	 */
	private String flowCardNo;

	/**
	 * 操作类型：A:流向单 B:盘点单据
	 */
	private String optType;

	/**
	 * 操作日期
	 */
	private Date optDate;

	/**
	 * 单据状态 S:送审 N:录入
	 */
	private String vouSts;

	/**
	 * 调整日期
	 */
	private Date adjustDate;

	/**
	 * 抽查日期
	 */
	private Date spotDate;

	/**
	 * 抽查描述
	 */
	private Date spotDesc;

	/**
	 * 复审日期
	 */
	private Date confDate;

	/**
	 * 录入人
	 */
	private User recorder;

	/**
	 * 调整人
	 */
	private User adjuster;

	/**
	 * 复审人
	 */
	private User confirmer;

	/**
	 * 抽查人
	 */
	private User spotter;

	/**
	 * 供方分销商
	 */
	private Client client;

	/**
	 * 会计核算期
	 */
	private FiscalYearPeriod fiscalYearPeriod;

	/**
	 * 流向单明细信息
	 */
	private List<FlowCardDetail> flowCardDetailList;

	public String getFlowCardNo() {
		return flowCardNo;
	}

	public void setFlowCardNo(String flowCardNo) {
		this.flowCardNo = flowCardNo;
	}

	public String getOptType() {
		return optType;
	}

	public void setOptType(String optType) {
		this.optType = optType;
	}

	public Date getOptDate() {
		return optDate;
	}

	public void setOptDate(Date optDate) {
		this.optDate = optDate;
	}

	public String getVouSts() {
		return vouSts;
	}

	public void setVouSts(String vouSts) {
		this.vouSts = vouSts;
	}

	public Date getAdjustDate() {
		return adjustDate;
	}

	public void setAdjustDate(Date adjustDate) {
		this.adjustDate = adjustDate;
	}

	public Date getSpotDate() {
		return spotDate;
	}

	public void setSpotDate(Date spotDate) {
		this.spotDate = spotDate;
	}

	public Date getSpotDesc() {
		return spotDesc;
	}

	public void setSpotDesc(Date spotDesc) {
		this.spotDesc = spotDesc;
	}

	public Date getConfDate() {
		return confDate;
	}

	public void setConfDate(Date confDate) {
		this.confDate = confDate;
	}

	public User getRecorder() {
		return recorder;
	}

	public void setRecorder(User recorder) {
		this.recorder = recorder;
	}

	public User getAdjuster() {
		return adjuster;
	}

	public void setAdjuster(User adjuster) {
		this.adjuster = adjuster;
	}

	public User getConfirmer() {
		return confirmer;
	}

	public void setConfirmer(User confirmer) {
		this.confirmer = confirmer;
	}

	public User getSpotter() {
		return spotter;
	}

	public void setSpotter(User spotter) {
		this.spotter = spotter;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public FiscalYearPeriod getFiscalYearPeriod() {
		return fiscalYearPeriod;
	}

	public void setFiscalYearPeriod(FiscalYearPeriod fiscalYearPeriod) {
		this.fiscalYearPeriod = fiscalYearPeriod;
	}

	public List<FlowCardDetail> getFlowCardDetailList() {
		return flowCardDetailList;
	}

	public void setFlowCardDetailList(List<FlowCardDetail> flowCardDetailList) {
		this.flowCardDetailList = flowCardDetailList;
	}
}
