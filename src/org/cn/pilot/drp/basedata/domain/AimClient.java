package org.cn.pilot.drp.basedata.domain;

/**
 * �跽�ͻ�
 * 
 * @author Pilot
 * 
 */
public class AimClient {

	// ����
	private int id;

	// �����̻��ն�ID
	private String clientTemiId;

	// ����
	private String name;

	// ����ID
	private String clientTemilevelId;

	// ��������
	private String clientTemilevelName;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getClientTemiId() {
		return clientTemiId;
	}

	public void setClientTemiId(String clientTemiId) {
		this.clientTemiId = clientTemiId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClientTemilevelId() {
		return clientTemilevelId;
	}

	public void setClientTemilevelId(String clientTemilevelId) {
		this.clientTemilevelId = clientTemilevelId;
	}

	public String getClientTemilevelName() {
		return clientTemilevelName;
	}

	public void setClientTemilevelName(String clientTemilevelName) {
		this.clientTemilevelName = clientTemilevelName;
	}
}