package com.revature.controller;

import com.revature.models.Planet;
import com.revature.service.PlanetService;

public class PlanetController {
	
	private PlanetService planetService;

	public PlanetController(PlanetService planetService){
		this.planetService = planetService;
	}

	public void getAllPlanets(int currentUserId) {
		String allPlanets = planetService.getAllPlanets(currentUserId).toString();
		if(!allPlanets.equals("[]")) {
			System.out.println("PLANETS: " + allPlanets);
		} else {
			System.out.println("Sorry, couldn't find anything!");
		}
	}

	public void getPlanetByName(int currentUserId, String name) {
		Planet foundPlanet = planetService.getPlanetByName(currentUserId, name);
		if(foundPlanet.getId() != 0)
		{
			System.out.println(foundPlanet);
		} else {
			System.out.println("Either we couldn't find it, or you don't own it!");
		}
	}

	public void getPlanetByID(int currentUserId, int id) {
		Planet foundPlanet = planetService.getPlanetById(currentUserId, id);
		if(foundPlanet.getId() != 0)
		{
			System.out.println(foundPlanet);
		}
		else {
			System.out.println("Either we couldn't find it, or you don't own it!");
		}
	}

	public void createPlanet(int currentUserId, Planet planet) {
		Planet discoveredPlanet = planetService.createPlanet(currentUserId, planet);
		if(discoveredPlanet.getId() != 0)
		{
			System.out.println("Planet " + planet.getName() + " created!");

		}
		else
		{
			System.out.println("Registration failed.");
		}
	}

	public void deletePlanet(int currentUserId, int id) {
		boolean deletedStatus = false;
		if(planetService.getPlanetById(currentUserId, id).getOwnerId() == currentUserId)
		{
			deletedStatus = planetService.deletePlanetById(id);
		}
		if(deletedStatus)
		{
			System.out.println("Deletion successful");
		}
		else {
			System.out.println("Deletion failed.");
		}
	}
}
