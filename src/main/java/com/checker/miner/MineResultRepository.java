package com.checker.miner;

import java.time.LocalDate;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface MineResultRepository extends PagingAndSortingRepository<MineResult, Long> {
	MineResult findByDateAndEntranceSysIdAndParkSysId(LocalDate day, long entranceSysId, long parkSysId);
}
