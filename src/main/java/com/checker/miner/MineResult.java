package com.checker.miner;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.springframework.hateoas.ResourceSupport;

@Entity
public class MineResult extends ResourceSupport {
	@Column
	private LocalDate date;

	@ManyToOne
	private Entrance entrance;
	@ManyToOne
	private Park park;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long sysId;

	public LocalDate getDate() {
		return this.date;
	}

	public Entrance getEntrance() {
		return this.entrance;
	}

	public Park getPark() {
		return this.park;
	}

	public Long getSysId() {
		return this.sysId;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public void setEntrance(Entrance entrance) {
		this.entrance = entrance;
	}

	public void setPark(Park park) {
		this.park = park;
	}

	public void setSysId(Long sysId) {
		this.sysId = sysId;
	}

}
