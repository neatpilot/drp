package org.cn.pilot.drp.util.domain;

/**
 * abstract dictionary
 * 
 * @author Pilot
 * 
 */
public abstract class AbstractDataDict {

	private String id;

	private String name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}