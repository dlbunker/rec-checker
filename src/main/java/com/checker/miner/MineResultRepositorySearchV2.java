package com.checker.miner;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface MineResultRepositorySearchV2 {
	public List<MineResult> customSearch(Date date, String park, Integer parkID, String entrance, Integer entranceID);
}
