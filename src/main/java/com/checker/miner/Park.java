package com.checker.miner;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Park {
	@Column
	private String name;

	@Id
	private Long sysId;

	public String getName() {
		return this.name;
	}

	public Long getSysId() {
		return this.sysId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSysId(Long sysId) {
		this.sysId = sysId;
	}
}
