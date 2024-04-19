package com.revature.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.exceptions.MoonFailException;
import com.revature.models.Moon;
import com.revature.utilities.ConnectionUtil;

public class MoonDao {
    
    public List<Moon> getAllMoons(int currentUserId) {
		try (Connection connection = ConnectionUtil.createConnection())
		{
			String sql = "SELECT moons.id, moons.name, moons.myPlanetId, planets.name FROM moons JOIN planets ON moons.myPlanetId = planets.id WHERE planets.ownerId = ?";
			PreparedStatement prep = connection.prepareStatement(sql);
			prep.setInt(1, currentUserId);
			List<Moon> allMoons = new ArrayList<Moon>();
			ResultSet res = prep.executeQuery();
			while(res.next())
			{
				int mId = res.getInt(1);
				String mName = res.getString(2);
				int mPId = res.getInt(3);
				Moon foundMoon = new Moon();
				foundMoon.setId(mId);
				foundMoon.setName(mName);
				foundMoon.setMyPlanetId(mPId);
				allMoons.add(foundMoon);
			}
			return allMoons;

		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Moon getMoonByName(String moonName) {
		try(Connection connection = ConnectionUtil.createConnection())
		{
			String sql = "SELECT moons.id, moons.name, moons.myPlanetId, planets.name FROM moons JOIN planets ON moons.myPlanetId = planets.id WHERE moons.name = ?";
			PreparedStatement prep = connection.prepareStatement(sql);
			prep.setString(1, moonName);
			Moon searchedMoon = new Moon();
			ResultSet res = prep.executeQuery();
			if(res.next())
			{
				searchedMoon.setId(res.getInt(1));
				searchedMoon.setName(moonName);
				searchedMoon.setMyPlanetId(res.getInt(3));
			}
			return searchedMoon;

		} catch(SQLException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	public Moon getMoonById(int moonId) {
		try (Connection connection = ConnectionUtil.createConnection())
		{
			String sql = "SELECT moons.id, moons.name, moons.myPlanetId, planets.name FROM moons JOIN planets ON moons.myPlanetId = planets.id WHERE moons.id = ?";
			PreparedStatement prep = connection.prepareStatement(sql);
			prep.setInt(1, moonId);
			Moon searchedMoon = new Moon();
			ResultSet res = prep.executeQuery();
			if(res.next())
			{
				searchedMoon.setId(moonId);
				searchedMoon.setName(res.getString(2));
				searchedMoon.setMyPlanetId(res.getInt(3));
			}
			return searchedMoon;

		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Moon createMoon(Moon m) {
		try(Connection connection = ConnectionUtil.createConnection())
		{
			String sql = "INSERT INTO moons (name, myPlanetId) VALUES (?, ?)";
			PreparedStatement prep = connection.prepareStatement(sql);
			//prep.setInt(1, m.getId());
			prep.setString(1, m.getName());
			prep.setInt(2, m.getMyPlanetId());
			Moon madeMoon = new Moon();
			prep.executeUpdate();
			ResultSet res = prep.getGeneratedKeys();
			if(res.next())
			{
				madeMoon.setId(res.getInt(1));
				//madeMoon.setName(res.getString("name"));
				//madeMoon.setMyPlanetId(res.getInt("myPlanetId"));
			}
			return madeMoon;

		} catch(SQLException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	public boolean deleteMoonById(int moonId) {
		boolean stat = false;
		try(Connection connection = ConnectionUtil.createConnection()) {
			String sql = "DELETE FROM moons WHERE id = ?";
			PreparedStatement prep = connection.prepareStatement(sql);
			prep.setInt(1, moonId);
			prep.executeUpdate();
			ResultSet res = prep.getGeneratedKeys();
			stat = true;
			return stat;

		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public List<Moon> getMoonsFromPlanet(int currentUserId, int planetId) {
		try(Connection connection = ConnectionUtil.createConnection()) {
			String sql = "SELECT moons.id, moons.name, moons.myPlanetId, planets.name FROM moons JOIN planets ON moons.myPlanetId = planets.id WHERE planets.ownerId = ? AND moons.myPlanetId = ?";
			List<Moon> planetMoons = new ArrayList<Moon>();
			PreparedStatement prep = connection.prepareStatement(sql);
			prep.setInt(1, currentUserId);
			prep.setInt(2, planetId);
			ResultSet res = prep.executeQuery();
			while(res.next())
			{
				int mId = res.getInt(1);
				String mName = res.getString(2);
				int mPId = res.getInt(3);
				Moon foundMoon = new Moon();
				foundMoon.setId(mId);
				foundMoon.setName(mName);
				foundMoon.setMyPlanetId(mPId);
				planetMoons.add(foundMoon);
			}
			return planetMoons;

		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	// Allows us to check who owns this moon.
	public int getPlanetOwnerId(int moonPId) {
		try(Connection connection = ConnectionUtil.createConnection())
		{
			String sql = "SELECT ownerId FROM planets WHERE id = ?";
			PreparedStatement prep = connection.prepareStatement(sql);
			prep.setInt(1, moonPId);
			int whoOwns = 0;
			ResultSet res = prep.executeQuery();
			if(res.next())
			{
				whoOwns = res.getInt("ownerId");
			}
			return whoOwns;
		} catch(SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
}
