package com.checker.miner;

import java.util.Date;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface MineResultRepository extends PagingAndSortingRepository<MineResult, Long> {
	MineResult findByDateAndParkIDAndEntranceID(@Param(value = "date") Date date,
			@Param(value = "parkID") int parkID, @Param(value = "entranceID") int entranceID);
}
