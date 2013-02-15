package org.cn.pilot.drp.flowcard.domain;

import java.util.Date;
import java.util.List;

import org.cn.pilot.drp.basedata.domain.Client;
import org.cn.pilot.drp.basedata.domain.FiscalYearPeriod;
import org.cn.pilot.drp.sysmgr.domain.User;

/**
 * ��������Ϣ
 * 
 * @author Pilot
 * 
 */
public class FlowCard {

	/**
	 * ���򵥺�yyyymmdd0001
	 */
	private String flowCardNo;

	/**
	 * �������ͣ�A:���� B:�̵㵥��
	 */
	private String optType;

	/**
	 * ��������
	 */
	private Date optDate;

	/**
	 * ����״̬ S:���� N:¼��
	 */
	private String vouSts;

	/**
	 * ��������
	 */
	private Date adjustDate;

	/**
	 * �������
	 */
	private Date spotDate;

	/**
	 * �������
	 */
	private Date spotDesc;

	/**
	 * ��������
	 */
	private Date confDate;

	/**
	 * ¼����
	 */
	private User recorder;

	/**
	 * ������
	 */
	private User adjuster;

	/**
	 * ������
	 */
	private User confirmer;

	/**
	 * �����
	 */
	private User spotter;

	/**
	 * ����������
	 */
	private Client client;

	/**
	 * ��ƺ�����
	 */
	private FiscalYearPeriod fiscalYearPeriod;

	/**
	 * ������ϸ��Ϣ
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
