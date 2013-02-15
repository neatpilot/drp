package org.cn.pilot.drp.flowcard.domain;

import java.math.BigDecimal;

import org.cn.pilot.drp.basedata.domain.AimClient;
import org.cn.pilot.drp.basedata.domain.Item;

/**
 * 流向单明细信息
 * 
 * @author Pilot
 * 
 */
public class FlowCardDetail {

	/**
	 * 操作数量
	 */
	private BigDecimal optQty;

	/**
	 * 调整数量
	 */
	private BigDecimal adjustQty;

	/**
	 * 调整原因
	 */
	private String adjustReason;

	/**
	 * 调整标记 Y:调整 N:未调整
	 */
	private String adjustFlag;

	/**
	 * 需方客户
	 */
	private AimClient aimClient;

	/**
	 * 物料
	 */
	private Item item;

	/**
	 * 流向单主信息
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
