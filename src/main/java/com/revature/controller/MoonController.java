package com.revature.controller;

import com.revature.models.Moon;
import com.revature.service.MoonService;

public class MoonController {
	
	private MoonService moonService;

	public MoonController(MoonService moonService) {
		this.moonService = moonService;
	}

	public void getAllMoons(int currentUserId) {
		String allMoons = moonService.getAllMoons(currentUserId).toString();
		if(!allMoons.equals("[]")) {
			System.out.println("MOONS: " + allMoons);
		} else {
			System.out.println("Sorry, couldn't find anything!");
		}
	}

	public void getMoonByName(int currentUserId, String name) {
		Moon foundMoon = moonService.getMoonByName(currentUserId, name);
		if(foundMoon.getId() != 0)
		{
			System.out.println(foundMoon);
		} else {
			System.out.println("Either we couldn't find it, or you don't own it!");
		}
	}

	public void getMoonById(int currentUserId, int id) {
		Moon foundMoon = moonService.getMoonById(currentUserId, id);
		if(foundMoon.getId() != 0)
		{
			System.out.println(foundMoon);
		} else {
			System.out.println("Either we can't find it, or you don't own it!");
		}
		// TODO: implement
	}

	public void createMoon(int currentUserId, Moon moon) {
		Moon foundMoon = moonService.createMoon(currentUserId, moon);
		if(foundMoon.getId() != 0)
		{
			System.out.println("Moon " + moon.getName() + " created!");
		}
		else {
			System.out.println("Registration failed.");
		}
	}

	public void deleteMoon(int currentUserId, int id) {
		boolean deletedStatus = moonService.deleteMoonById(currentUserId, id);
		//System.out.println(currentUserId + ", " + id);
		if(deletedStatus)
		{
			System.out.println("Deletion successful.");
		} else {
			System.out.println("Deletion failed.");
		}
	}
	
	public void getPlanetMoons(int currentUserId, int myPlanetId) {
		String planetMoons = moonService.getMoonsFromPlanet(currentUserId, myPlanetId).toString();
		if(!planetMoons.equals("[]"))
		{
			System.out.println("MOONS OF THE PLANET: " + planetMoons);
		} else {
			System.out.println("Sorry, couldn't find anything!");
		}
	}
}
