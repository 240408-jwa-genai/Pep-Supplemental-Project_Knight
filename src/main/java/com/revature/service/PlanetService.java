package com.revature.service;

import java.util.List;

import com.revature.models.Planet;
import com.revature.repository.PlanetDao;

public class PlanetService {

	private PlanetDao dao;

	public PlanetService(PlanetDao dao){
		this.dao = dao;
	}

	public List<Planet> getAllPlanets(int userId) {
		List<Planet> myPlanets = dao.getAllPlanets(userId);
		return myPlanets;
	}

	public Planet getPlanetByName(int ownerId, String planetName) {
		Planet possiblePlanet = dao.getPlanetByName(planetName);
		if(possiblePlanet.getId() != 0 )
		{
			if(possiblePlanet.getOwnerId() == ownerId)
			{
				return possiblePlanet;
			}
		}
		return new Planet();
	}

	public Planet getPlanetById(int ownerId, int planetId) {
		Planet possiblePlanet = dao.getPlanetById(planetId);
		if(possiblePlanet.getId() != 0)
		{
			if(possiblePlanet.getOwnerId() == ownerId)
			{
				return possiblePlanet;
			}
		}
		return new Planet();
	}

	public Planet createPlanet(int ownerId, Planet planet) {
		if(planet.getName().length() <= 30) {
			Planet checkPlanet = dao.getPlanetByName(planet.getName());
			if(checkPlanet.getId() == 0) {
				Planet createdPlanet = dao.createPlanet(ownerId, planet);
				if (createdPlanet.getId() != 0) {
					return createdPlanet;
				}
			}
		}
		return new Planet();
	}

	public boolean deletePlanetById(int planetId) {
		return dao.deletePlanetById(planetId);
	}
}
