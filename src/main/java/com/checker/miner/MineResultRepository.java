package com.checker.miner;

import java.time.LocalDate;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface MineResultRepository extends PagingAndSortingRepository<MineResult, Long> {
	MineResult findByDateAndEntranceAndPark(LocalDate day, Entrance entrance, Park park);
}
