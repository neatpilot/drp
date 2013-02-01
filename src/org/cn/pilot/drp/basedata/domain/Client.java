package org.cn.pilot.drp.basedata.domain;

import org.cn.pilot.drp.util.datadict.domain.ClientLevel;

/**
 * 
 * client entity class
 * 
 * @author Pilot
 * 
 */
public class Client {
	private int id;

	private int pid;

	private String name;

	private String clientId;

	private String bankAcctNo;

	private String contactTel;

	private String address;

	private String zipCode;

	private String isLeaf;

	private String isClient;

	private ClientLevel clientLevel= new ClientLevel();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getBankAcctNo() {
		return bankAcctNo;
	}

	public void setBankAcctNo(String bankAcctNo) {
		this.bankAcctNo = bankAcctNo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(String isLeaf) {
		this.isLeaf = isLeaf;
	}

	public String getIsClient() {
		return isClient;
	}

	public void setIsClient(String isClient) {
		this.isClient = isClient;
	}

	public ClientLevel getClientLevel() {
		return clientLevel;
	}

	public void setClientLevel(ClientLevel clientLevel) {
		this.clientLevel = clientLevel;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContactTel() {
		return contactTel;
	}

	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}
}
