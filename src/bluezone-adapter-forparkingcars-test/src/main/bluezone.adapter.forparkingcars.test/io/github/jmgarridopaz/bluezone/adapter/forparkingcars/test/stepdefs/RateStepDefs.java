
package io.github.jmgarridopaz.bluezone.adapter.forparkingcars.test.stepdefs;


import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.jmgarridopaz.bluezone.hexagon.driver.forparkingcars.MoneyDto;
import io.github.jmgarridopaz.bluezone.hexagon.driver.forparkingcars.RateData;
import io.github.jmgarridopaz.bluezone.hexagon.driver.forparkingcars.TimeIntervalDto;
import io.github.jmgarridopaz.bluezone.hexagon.driver.forparkingcars.TimeTableDto;


public class RateStepDefs {
	
	private final ScenarioContext scenarioContext;

	public RateStepDefs ( ScenarioContext scenarioContext ) {
		this.scenarioContext = scenarioContext;
	}

	
	@When("I request all the rates indexed by name")
	public void iRequestAllTheRatesIndexedByName() {
		Map<String,RateData> rates = this.scenarioContext.forParkingCars().getAllRatesByName();
		this.scenarioContext.setRates(rates);
	}


	@Then("I should get the following rates:")
	public void iShouldGetTheFollowingRates ( Map<String,RateData> expectedRates ) {
		Map<String,RateData> returnedRates = this.scenarioContext.rates();
		String assertionErrorMsg = "Expected rates ("+expectedRates+") is not equal to returned rates ("+returnedRates+")";
		assertEquals ( assertionErrorMsg, expectedRates, returnedRates );
	}

	
	@DataTableType
    public RateData rateEntry ( Map<String, String> entry ) {
		
		MoneyDto costPerHour = new MoneyDto();
		costPerHour.setAmount(new BigDecimal(entry.get("costPerHourAmount")));
		costPerHour.setCurrencySymbol(entry.get("costPerHourCurrencySymbol"));
		
		TimeTableDto timeTable = new TimeTableDto();
		timeTable.setIntervalsByDayOfWeek ( intervalsByDayOfWeek(entry) );
		
		RateData rate = new RateData();
		rate.setName(entry.get("name"));
		rate.setCostPerHour(costPerHour);
		rate.setMinMinutesAllowed(Integer.parseInt(entry.get("minMinutesAllowed")));
		rate.setMaxMinutesAllowed(Integer.parseInt(entry.get("maxMinutesAllowed")));
		rate.setTimetable(timeTable);
		
        return rate;
    }
	

	private Map<DayOfWeek, List<TimeIntervalDto>> intervalsByDayOfWeek ( Map<String, String> dataTableEntry ) {
    	Map<DayOfWeek, List<TimeIntervalDto>> intervalsByDayOfWeek = new HashMap<DayOfWeek, List<TimeIntervalDto>>();
		for ( DayOfWeek dayOfWeek : DayOfWeek.values() ) {
    		String intervals = dataTableEntry.get ( dayOfWeek.name().toLowerCase() );
    		List<TimeIntervalDto> timeIntervals = new ArrayList<TimeIntervalDto>();
    		if ( intervals != null ) {
    			for ( String interval : intervals.split(" ") ) {
    				TimeIntervalDto timeInterval = new TimeIntervalDto();
    				LocalTime minTime = LocalTime.parse ( interval.split("-")[0], DateTimeFormatter.ofPattern("HH:mm") );
    				LocalTime maxTime = LocalTime.parse ( interval.split("-")[1], DateTimeFormatter.ofPattern("HH:mm") );
    				timeInterval.setMinTime(minTime);
    				timeInterval.setMaxTime(maxTime);
    				timeIntervals.add(timeInterval);
    			}
    		}
    		intervalsByDayOfWeek.put ( dayOfWeek, timeIntervals );    		
    	}
		return intervalsByDayOfWeek;
	}
	
}
