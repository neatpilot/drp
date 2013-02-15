package org.cn.pilot.drp.basedata.domain;

import java.util.Date;

/**
 * 核算年
 * 
 * @author Pilot
 * 
 */
public class FiscalYearPeriod {

	// id
	private int id;

	// 核算年
	private int fiscalYear;

	// 核算月
	private int fiscalPeriod;

	// 开始日期
	private Date beginDate;

	// 结束日期
	private Date endDate;

	// 状态
	private String periodSts;

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getFiscalPeriod() {
		return fiscalPeriod;
	}

	public void setFiscalPeriod(int fiscalPeriod) {
		this.fiscalPeriod = fiscalPeriod;
	}

	public int getFiscalYear() {
		return fiscalYear;
	}

	public void setFiscalYear(int fiscalYear) {
		this.fiscalYear = fiscalYear;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPeriodSts() {
		return periodSts;
	}

	public void setPeriodSts(String periodSts) {
		this.periodSts = periodSts;
	}
}
