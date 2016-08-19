package com.checker.miner;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Entrance {
	@Column
	private String name;

	@ManyToOne
	@JoinColumn(name = "park_sys_id")
	private Park park;

	@Id
	private Long sysId;

	public String getName() {
		return this.name;
	}

	public Park getPark() {
		return this.park;
	}

	public Long getSysId() {
		return this.sysId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPark(Park park) {
		this.park = park;
	}

	public void setSysId(Long sysId) {
		this.sysId = sysId;
	}

}
