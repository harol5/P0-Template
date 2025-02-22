package com.revature.controller;

import java.util.List;

import com.revature.MainDriver;
import com.revature.models.Moon;
import com.revature.models.Planet;
import com.revature.models.User;
import com.revature.service.MoonService;
import com.revature.service.PlanetService;

import io.javalin.http.Context;
import io.javalin.validation.Validator;


public class PlanetController {

	private PlanetService pService = new PlanetService();

	public void getAllPlanets(Context ctx) {
		ctx.json(pService.getAllPlanets()).status(200);
	}

	public void getPlanetByName(Context ctx) {
		
		User u = ctx.sessionAttribute("user");
		String planetName = ctx.pathParam("name");
		
		Planet p = pService.getPlanetByName(u.getUsername(), planetName);

		if(p.getId() != 0){
			ctx.json(p).status(200);
		}else {
			ctx.result("Planet does not exits").status(404);
		}
	}

	public void getPlanetByID(Context ctx) {
		
		User u = ctx.sessionAttribute("user");
		int planetId = ctx.pathParamAsClass("id", Integer.class).get();

		Planet p = pService.getPlanetById(u.getUsername(), planetId);

		if(p.getId() != 0){
		   ctx.json(p).status(200);
		}else {
		   ctx.result("Planet does not exits").status(404);
		}
	}


	public void createPlanet(Context ctx) {
		
		Planet planetToBeCreated = ctx.bodyAsClass(Planet.class);
		User u = ctx.sessionAttribute("user");

		Planet p = pService.getPlanetByName(u.getUsername(), planetToBeCreated.getName());
		//to make this work, i have to create a new method that get planet by owner id.
		if(planetToBeCreated.getName().equals( p.getName() ) && planetToBeCreated.getOwnerId() == p.getOwnerId()){
			ctx.result("Planet already created").status(404);
		}else {
			Planet createdPlanet = pService.createPlanet(u.getUsername(),planetToBeCreated);
			ctx.json(createdPlanet).status(201);
		}
	}

	public void deletePlanet(Context ctx) {
		
		int planetId = ctx.pathParamAsClass("id", Integer.class).get();
		
		pService.deletePlanetById(planetId);
		ctx.json("Planet successfully deleted").status(202);
	}
}
