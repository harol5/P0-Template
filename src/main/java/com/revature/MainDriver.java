package com.revature;


import com.revature.controller.RequestMapping;

import io.javalin.Javalin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainDriver {

	public static Logger logger = LoggerFactory.getLogger(MainDriver.class);

	public static void main(String[] args) {

		logger.trace("This log was created at the trace level");
		logger.debug("this log was created at the debug level");
		logger.info("Application starting!");
		logger.warn("This log was created at the warn level");
		logger.error("This log was created at the Error level");

		Javalin app = Javalin.create(confg ->{
			confg.plugins.enableDevLogging();
		});
		RequestMapping.setupEndpoints(app);
		app.start(7000);
	}
}
