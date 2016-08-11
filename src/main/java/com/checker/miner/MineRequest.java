package com.checker.miner;

import org.hibernate.validator.internal.constraintvalidators.hv.URLValidator;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * A representation of a mine request
 * 
 * @author craftmaster2190
 *
 */
public class MineRequest {
	private String url;
	private String script;

	@Autowired
	@JsonIgnore
	private URLValidator urlValidator;

	@JsonIgnore
	private String[] schemes = { "http://", "https://" };

	public MineRequest() {

	}

	public MineRequest(String url, String script) {
		this.url = url;
		this.script = script;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		if (url.startsWith("http://") || url.startsWith("https://")) {
			this.url = url;
		} else if (url.startsWith("//")) {
			this.url = "http:" + url;
		} else {
			this.url = "http://" + url;
		}
	}

	public boolean hasUrl() {
		return !(this.url == null || this.url.trim().equals(""));
	}

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}

	public boolean hasScript() {
		return !(this.script == null || this.script.trim().equals(""));
	}

}
