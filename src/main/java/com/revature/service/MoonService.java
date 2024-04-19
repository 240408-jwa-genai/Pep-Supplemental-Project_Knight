package com.revature.service;

import java.util.List;

import com.revature.models.Moon;
import com.revature.repository.MoonDao;

public class MoonService {

	private MoonDao dao;

	public MoonService(MoonDao dao) {
		this.dao = dao;
	}

	public List<Moon> getAllMoons(int currentUserId) {
		List<Moon> myMoons = dao.getAllMoons(currentUserId);
		return myMoons;
	}

	public Moon getMoonByName(int currentUserId, String moonName) {
		Moon possibleMoon = dao.getMoonByName(moonName);
		if(possibleMoon.getId() != 0) {
			if(dao.getPlanetOwnerId(possibleMoon.getMyPlanetId()) == currentUserId) {
				return possibleMoon;
			}
		}
		return new Moon();
	}

	public Moon getMoonById(int currentUserId, int moonId) {
		Moon possibleMoon = dao.getMoonById(moonId);
		if(possibleMoon.getId() != 0) {
			if(dao.getPlanetOwnerId(possibleMoon.getMyPlanetId()) == currentUserId) {
				return possibleMoon;
			}
		}
		return new Moon();
	}

	public Moon createMoon(int currentUserId, Moon m) {
		if(m.getName().length() <= 30)
		{
			int checkOwn = dao.getPlanetOwnerId(m.getMyPlanetId());
			if(checkOwn == currentUserId)
			{
				Moon checkMoon = dao.getMoonByName(m.getName());
				if(checkMoon.getId() == 0 )
				{
					Moon createdMoon = dao.createMoon(m);
					if(createdMoon.getId() != 0)
					{
						return createdMoon;
					}
				}
			}

		}
		return new Moon();
	}

	public boolean deleteMoonById(int currentUserId, int moonId) {
		Moon foundMoon = dao.getMoonById(moonId);
		System.out.println(currentUserId + ", " + moonId + ", " + dao.getPlanetOwnerId(foundMoon.getMyPlanetId()));
		if(dao.getPlanetOwnerId(moonId) == currentUserId) {
			System.out.println("Owner Authenticated.");
			return dao.deleteMoonById(moonId);
		} else {
			return false;
		}
	}

	public List<Moon> getMoonsFromPlanet(int currentUserId, int myPlanetId) {
		List<Moon> planetMoons = dao.getMoonsFromPlanet(currentUserId, myPlanetId);
		// TODO Auto-generated method stub
		return planetMoons;
	}
}
