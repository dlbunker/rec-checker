package com.checker.miner;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MineResultController {
	
	@RequestMapping("/api/mineResults/searchv2")
	public HttpEntity<MineResult> searchFor(){
		MineResult mineResult = new MineResult();
		

        mineResult.add(linkTo(methodOn(MineResultController.class).searchFor()).withSelfRel());
		
		return new ResponseEntity<>(mineResult, HttpStatus.OK);
	}
	
}
