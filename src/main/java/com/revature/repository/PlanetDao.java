package com.revature.repository;

import java.util.List;
import java.util.ArrayList;

import com.revature.models.Planet;
import com.revature.utilities.ConnectionUtil;
import java.sql.*;

public class PlanetDao {
    
    public List<Planet> getAllPlanets(int ownerId) {
		try(Connection connection = ConnectionUtil.createConnection())
		{
			String sql = "SELECT * FROM planets WHERE ownerId = ?";
			PreparedStatement prep = connection.prepareStatement(sql);
			prep.setInt(1, ownerId);
			List<Planet> allPlanets = new ArrayList<Planet>();
			ResultSet res = prep.executeQuery();
			while(res.next())
			{
				int pId = res.getInt("id");
				String pName = res.getString("name");
				//int oId = res.getInt("ownerId");
				// This one isn't needed, because if there's anything in res at all, that means our ownerId matched.
				Planet foundPlanet = new Planet();
				foundPlanet.setId(pId);
				foundPlanet.setName(pName);
				foundPlanet.setOwnerId(ownerId);
				allPlanets.add(foundPlanet);
			}
			return allPlanets;
		} catch(SQLException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	public Planet getPlanetByName(String planetName) {
		try(Connection connection = ConnectionUtil.createConnection())
		{
			String sql = "SELECT * FROM planets WHERE name = ?";
			PreparedStatement prep = connection.prepareStatement(sql);
			prep.setString(1, planetName);
			Planet searchedPlanet = new Planet();
			ResultSet res = prep.executeQuery();
			if(res.next())
			{
				searchedPlanet.setId(res.getInt("id"));
				searchedPlanet.setOwnerId(res.getInt("ownerId"));
				searchedPlanet.setName(planetName);
			}
			return searchedPlanet;

		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Planet getPlanetById(int planetId) {
		try(Connection connection = ConnectionUtil.createConnection())
		{
			String sql = "SELECT * FROM planets WHERE id = ?";
			PreparedStatement prep = connection.prepareStatement(sql);
			prep.setInt(1, planetId);
			Planet searchedPlanet = new Planet();
			ResultSet res = prep.executeQuery();
			if(res.next())
			{
				searchedPlanet.setId(planetId);
				searchedPlanet.setOwnerId(res.getInt("ownerId"));
				searchedPlanet.setName(res.getString("name"));
			}
			return searchedPlanet;
		} catch(SQLException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	public Planet createPlanet(int ownerId, Planet p) {
		try(Connection connection = ConnectionUtil.createConnection())
		{
			String sql = "INSERT INTO planets (name, ownerId) VALUES (?, ?)";
			PreparedStatement prep = connection.prepareStatement(sql);
			prep.setString(1, p.getName());
			prep.setInt(2, ownerId);
			Planet createdPlanet = new Planet();
			prep.executeUpdate();
			ResultSet res = prep.getGeneratedKeys();
			if(res.next())
			{
			//	createdPlanet.setName(res.getString(2));
				createdPlanet.setOwnerId(ownerId);
				createdPlanet.setId(res.getInt(1));
			}
			return createdPlanet;

		} catch(SQLException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	public boolean deletePlanetById(int planetId) {
		boolean stat = false;
		try(Connection connection = ConnectionUtil.createConnection())
		{
			String sql2 = "DELETE FROM moons WHERE moons.id IN (SELECT moons.id FROM moons JOIN planets ON moons.myPlanetId = planets.id WHERE moons.myPlanetId = ?)";
			PreparedStatement prep2 = connection.prepareStatement(sql2);
			prep2.setInt(1, planetId);
			prep2.executeUpdate();
			ResultSet res2 = prep2.getGeneratedKeys();

			String sql = "DELETE FROM planets WHERE id = ?";
			PreparedStatement prep = connection.prepareStatement(sql);
			prep.setInt(1, planetId);
			prep.executeUpdate();
			ResultSet res = prep.getGeneratedKeys();

			stat = true;
			return stat;

		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
