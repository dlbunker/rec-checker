package com.checker.miner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller that handles generation and processing of incoming mine requests
 * @author craftmaster2190
 *
 */

@RestController
@RequestMapping(value = "/api")
public class DataMinerController {
	/**
	*
	*/
	
	@Autowired
	SeleniumPageService seleniumPageService;
	
	@RequestMapping(value = "mine", method = RequestMethod.GET)
	public @ResponseBody Object getMine(@RequestParam(value = "url", required = true) String url,
			@RequestParam(value = "script", required = true) String script) {
		MineRequest mineRequest = new MineRequest();
		mineRequest.setUrl(url);
		mineRequest.setScript(script);
		
		return mineRequest;
	}
	
	@RequestMapping(value = "mine", method = RequestMethod.POST, 
		    headers = "content-type=application/x-www-form-urlencoded")
	public @ResponseBody Object postMine(@RequestParam(value = "url", required = true) String url,
			@RequestParam(value = "script", required = true) String script) {
		MineRequest mineRequest = new MineRequest();
		mineRequest.setUrl(url);
		mineRequest.setScript(script);
		
		return mineRequest;
	}
	
	@RequestMapping(value = "mine", method = RequestMethod.POST, 
		    headers = "content-type=application/json")
	public @ResponseBody Object postMine(@RequestBody(required = true) MineRequest mineRequest) {
		if(!mineRequest.hasUrl()){
			throw new HttpMessageNotReadableException("JSON object must have a url.");
		}
		if(!mineRequest.hasScript()){
			throw new HttpMessageNotReadableException("JSON object must have a script.");
		}
		
		SeleniumResultsPage resultsPage = seleniumPageService.get(mineRequest);
		return resultsPage;
	}
}
