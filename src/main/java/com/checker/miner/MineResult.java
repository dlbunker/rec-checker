package com.checker.miner;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class MineResult {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column
	private Date date;

	@Column
	private int parkID;
	
	@Column
	private int entranceID;

	public MineResult() {

	}

	public MineResult(Date date, int parkID, int entranceID) {
		setDate(date);
		setParkID(parkID);
		setEntranceID(entranceID);
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getEntranceID() {
		return this.entranceID;
	}

	public void setEntranceID(int entranceID) {
		this.entranceID = entranceID;
	}

	public int getParkID() {
		return this.parkID;
	}

	public void setParkID(int parkID) {
		this.parkID = parkID;
	}
}
