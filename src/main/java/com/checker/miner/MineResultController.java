package com.checker.miner;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.checker.settings.DateRange;

@RestController
public class MineResultController {

	@Autowired
	MineResultRepositorySearchV2Implementation mineResultRepositorySearchV2Implementation;

	@RequestMapping("/api/mineResults/searchv2")
	public HttpEntity<List<MineResult>> searchFor(@RequestParam(value = "date", required = false) String date,
			@RequestParam(value = "park", required = false) String park,
			@RequestParam(value = "parkID", required = false) Integer parkID,
			@RequestParam(value = "entrance", required = false) String entrance,
			@RequestParam(value = "entranceID", required = false) Integer entranceID) {

		Date date2 = DateRange.stringToDate(date);

		List<MineResult> list = this.mineResultRepositorySearchV2Implementation.customSearch(date2, park, parkID,
				entrance, entranceID);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}

}
