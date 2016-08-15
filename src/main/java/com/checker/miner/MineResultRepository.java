package com.checker.miner;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface MineResultRepository extends PagingAndSortingRepository<MineResult, Long> {
	MineResult findByDateAndParkIDAndEntranceID(@Param(value = "date") Date date, @Param(value = "parkID") int parkID,
			@Param(value = "entranceID") int entranceID);
	
	List<MineResult> findBySysIdOrDateLikeOrParkIDLikeOrEntranceIDLike(@Param(value = "sysId") long sysId, @Param(value = "date") Date date,
			@Param(value = "parkID") int parkID, @Param(value = "entranceID") int entranceID);
	
	List<MineResult> findByDateLikeAndParkIDLikeAndEntranceIDLike(@Param(value = "date") Date date, @Param(value = "parkID") int parkID,
			@Param(value = "entranceID") int entranceID);

	List<MineResult> findByDateLikeAndParkIDLike(@Param(value = "date") Date date, @Param(value = "parkID") int parkID);
	
	List<MineResult> findByDateLikeAndEntranceIDLike(@Param(value = "date") Date date,
			@Param(value = "entranceID") int entranceID);
	
	List<MineResult> findByParkIDLikeAndEntranceIDLike(@Param(value = "parkID") int parkID,
			@Param(value = "entranceID") int entranceID);
	
	List<MineResult> findByDateLike(@Param(value = "date") Date date);
	
	List<MineResult> findByParkIDLike(@Param(value = "parkID") int parkID);
	
	List<MineResult> findByEntranceIDLike(@Param(value = "entranceID") int entranceID);
}
