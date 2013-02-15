package org.cn.pilot.drp.flowcard.domain;

import java.math.BigDecimal;

import org.cn.pilot.drp.basedata.domain.AimClient;
import org.cn.pilot.drp.basedata.domain.Item;

/**
 * ������ϸ��Ϣ
 * 
 * @author Pilot
 * 
 */
public class FlowCardDetail {

	/**
	 * ��������
	 */
	private BigDecimal optQty;

	/**
	 * ��������
	 */
	private BigDecimal adjustQty;

	/**
	 * ����ԭ��
	 */
	private String adjustReason;

	/**
	 * ������� Y:���� N:δ����
	 */
	private String adjustFlag;

	/**
	 * �跽�ͻ�
	 */
	private AimClient aimClient;

	/**
	 * ����
	 */
	private Item item;

	/**
	 * ��������Ϣ
	 */
	private FlowCard flowCard;

	public BigDecimal getOptQty() {
		return optQty;
	}

	public void setOptQty(BigDecimal optQty) {
		this.optQty = optQty;
	}

	public BigDecimal getAdjustQty() {
		return adjustQty;
	}

	public void setAdjustQty(BigDecimal adjustQty) {
		this.adjustQty = adjustQty;
	}

	public String getAdjustReason() {
		return adjustReason;
	}

	public void setAdjustReason(String adjustReason) {
		this.adjustReason = adjustReason;
	}

	public String getAdjustFlag() {
		return adjustFlag;
	}

	public void setAdjustFlag(String adjustFlag) {
		this.adjustFlag = adjustFlag;
	}

	public AimClient getAimClient() {
		return aimClient;
	}

	public void setAimClient(AimClient aimClient) {
		this.aimClient = aimClient;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public FlowCard getFlowCard() {
		return flowCard;
	}

	public void setFlowCard(FlowCard flowCard) {
		this.flowCard = flowCard;
	}

}
