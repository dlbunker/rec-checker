package com.checker.miner;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServerSideRepositoryService {
	@Autowired
	EntranceRepository entranceRepository;

	@Autowired
	MineResultRepository mineResultRepository;

	@Autowired
	ParkRepository parkRepository;

	public MineResult findByDateAndEntranceAndPark(LocalDate day, Entrance entrance, Park park) {
		return this.mineResultRepository.findByDateAndEntranceAndPark(day, entrance, park);
	}

	public Entrance findEntranceIfExistsOrCreateNew(long entranceID, Park park) {
		Entrance entrance = this.entranceRepository.findOne(entranceID);
		if (entrance == null) {
			entrance = new Entrance();
			entrance.setSysId(entranceID);
			entrance.setPark(park);
			this.entranceRepository.save(entrance);
		}
		return entrance;
	}

	public Park findParkIfExistsOrCreateNew(long parkID) {
		Park park = this.parkRepository.findOne(parkID);
		if (park == null) {
			park = new Park();
			park.setSysId(parkID);
			this.parkRepository.save(park);
		}
		return park;
	}

	public Park findParkIfExistsOrCreateNew(Park newPark) {
		Long parkID = newPark.getSysId();
		Park park = this.parkRepository.findOne(parkID);
		if (park == null) {
			park = newPark;
			this.parkRepository.save(park);
		}
		return park;
	}

	public void saveNewMineResult(MineResult mineResult) {
		this.mineResultRepository.save(mineResult);

	}
}
