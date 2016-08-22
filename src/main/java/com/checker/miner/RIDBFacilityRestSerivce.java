package com.checker.miner;

import java.util.ArrayList;
import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.checker.ApplicationProperties;
import com.checker.settings.SettingIntepreterService;
import com.fasterxml.jackson.databind.JsonNode;

@Service
public class RIDBFacilityRestSerivce {

	private static final String[] EMPTY_STRING_ARRAY = new String[] {};

	@Autowired
	ApplicationProperties applicationProperties;
	HttpEntity<String> httpEntity;

	HttpHeaders httpHeaders = new HttpHeaders();

	Logger logger = Logger.getLogger(RIDBFacilityRestSerivce.class);
	RestTemplate restTemplate = new RestTemplate();
	@Autowired
	ServerSideRepositoryService serverSideRepositoryService;

	@Autowired
	SettingIntepreterService settingIntepreterService;

	final String urlBase = "https://ridb.recreation.gov/api/v1/facilities?query=";

	private void addParkToListAndDatasource(ArrayList<Park> parksList, String facilityName, long legacyFacilityID) {
		Park park = new Park();
		park.setName(facilityName);
		park.setSysId(legacyFacilityID);
		park = this.serverSideRepositoryService.findParkIfExistsOrCreateNew(park);
		parksList.add(park);
	}

	private void extractParksFromJSONArray(ArrayList<Park> parksList, JsonNode body) {
		JsonNode recData = body.get("RECDATA");
		for (final JsonNode facility : recData) {
			String facilityName = facility.get("FacilityName").toString();
			long legacyFacilityID = facility.get("LegacyFacilityID").asLong();
			this.addParkToListAndDatasource(parksList, facilityName, legacyFacilityID);
		}
	}

	public ArrayList<Park> getParks() {
		String[] searches = this.getSearchesFromSettings();

		this.logger.log(Level.DEBUG, Arrays.toString(searches));

		ArrayList<Park> parksList = new ArrayList<>();
		for (String searchTerm : searches) {
			String url = this.urlBase + searchTerm;

			JsonNode parksJSONArray = this.getParksJSONArrayFromRestEndpoint(url);
			this.extractParksFromJSONArray(parksList, parksJSONArray);
		}
		return parksList;
	}

	private JsonNode getParksJSONArrayFromRestEndpoint(String url) {
		ResponseEntity<JsonNode> exchange = this.restTemplate.exchange(url, HttpMethod.GET, this.httpEntity,
				JsonNode.class);
		JsonNode parksJSONArray = exchange.getBody();
		return parksJSONArray;
	}

	private String[] getSearchesFromSettings() {
		return this.settingIntepreterService.getSettingAsStringArray("searches", EMPTY_STRING_ARRAY);
	}

	@PostConstruct
	public void init() {
		this.httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		this.httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		this.httpHeaders.add("apikey", this.applicationProperties.getRidbApiKey());

		this.httpEntity = new HttpEntity<>(this.httpHeaders);
	}

}
