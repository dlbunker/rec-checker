package com.checker.miner;

import java.util.Date;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface MineResultRepository extends PagingAndSortingRepository<MineResult, Long> {
	MineResult findByDateAndEntranceSysIdAndParkSysId(Date day, long entranceSysId, long parkSysId);
}
