module bluezone.adapter.forparkingcars.test {

/*
 * DEPENDENCIES: Hexagon, libs, test framework
 */
	requires bluezone.hexagon;	
	
	requires io.github.jmgarridopaz.lib.portsadapters;
	
	requires io.cucumber.java;
	requires junit;
	requires io.cucumber.datatable;
	requires io.cucumber.core;
		
/*
 * PUBLISH: adapter class, runtime access to frameworks
 */
	exports io.github.jmgarridopaz.bluezone.adapter.forparkingcars.test;
	
	opens	io.github.jmgarridopaz.bluezone.adapter.forparkingcars.test.stepdefs
	to		io.cucumber.java,
			picocontainer;
	
}
