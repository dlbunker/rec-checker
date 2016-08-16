package com.checker.miner;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface MineResultRepositorySearchV2 {
	public List<MineResult> customSearch(LocalDate date, String park, Integer parkID, String entrance,
			Integer entranceID);
}
