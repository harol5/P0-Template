package com.revature.repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Moon;
import com.revature.utilities.ConnectionUtil;

public class MoonDao {
    
    public List<Moon> getAllMoons() throws SQLException{
		try(Connection conn = ConnectionUtil.createConnection()){
			String sql = "select * from moons";
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			List<Moon> moons = new ArrayList<>();
			while (rs.next()){
				Moon moon = new Moon();
				moon.setId(rs.getInt(1));
				moon.setName(rs.getString(2));
				moon.setMyPlanetId(rs.getInt(3));
				moons.add(moon);
			}
			return moons;
		}
	}

	public Moon getMoonByName(String username, String moonName) {
		try(Connection conn = ConnectionUtil.createConnection()){
			String sql = "select * from moons where name = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, moonName);
			ResultSet rs = ps.executeQuery();
			rs.next();
			Moon moon = new Moon();
			moon.setId(rs.getInt(1));
			moon.setName(rs.getString(2));
			moon.setMyPlanetId(rs.getInt(3));
			return moon;
		}catch (SQLException e){
			System.out.println(e.getMessage());
			return new Moon();
		}
	}

	public Moon getMoonById(String username, int moonId) {
		try(Connection conn = ConnectionUtil.createConnection()){
			String sql = "select * from moons where id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, moonId);
			ResultSet rs = ps.executeQuery();
			rs.next();
			Moon moon = new Moon();
			moon.setId(rs.getInt(1));
			moon.setName(rs.getString(2));
			moon.setMyPlanetId(rs.getInt(3));
			return moon;
		}catch (SQLException e){
			System.out.println(e.getMessage());
			return new Moon();
		}
	}

	public Moon createMoon(String username, Moon m) {
		try(Connection conn = ConnectionUtil.createConnection()){
			String sql = "insert into moons values (default,?,?)";
			//------------------------------
			PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, m.getName());
			ps.setInt(2, m.getMyPlanetId());
			ps.execute();
			//------------------------------
			ResultSet rs = ps.getGeneratedKeys();
			//-----------------------------
			rs.next();
			Moon newMoon = new Moon();
			int newId = rs.getInt("id");
			newMoon.setId(newId);
			newMoon.setName(m.getName());
			newMoon.setMyPlanetId(m.getMyPlanetId());
			return newMoon;
		}catch (SQLException e){
			System.out.println(e.getMessage());
			return new Moon();
		}
	}

	public void deleteMoonById(int moonId) {
		try(Connection conn = ConnectionUtil.createConnection()){
			String sql = "delete from moons where id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, moonId);
			int rowsAffected = ps.executeUpdate();
			System.out.println(rowsAffected);
		}catch (SQLException e){
			System.out.println(e.getMessage());
		}
	}

	public List<Moon> getMoonsFromPlanet(int planetId) throws SQLException{
		try(Connection conn = ConnectionUtil.createConnection()){
			String sql = "select * from moons where myplanetid = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, planetId);
			ResultSet rs = ps.executeQuery();
			List<Moon> moonsFromPlanets = new ArrayList<>();
			while (rs.next()){
				Moon moon = new Moon();
				moon.setId(rs.getInt(1));
				moon.setName(rs.getString(2));
				moon.setMyPlanetId(rs.getInt(3));
				moonsFromPlanets.add(moon);
			}
			return moonsFromPlanets;
		}
	}

//	public static void main(String[] args) {
//		MoonDao moonDao = new MoonDao();
//		Moon newMoon = new Moon();
//		newMoon.setName("adrastea");
//		newMoon.setMyPlanetId(2);
//		System.out.println(moonDao.createMoon("Harol", newMoon));
//		System.out.println(moonDao.getMoonByName(" ","moon"));
//		System.out.println(moonDao.getMoonById(" ", 1));
//		moonDao.deleteMoonById(3);
//		try {
//			System.out.println(moonDao.getMoonsFromPlanet(2));
//		} catch (SQLException e) {
//			System.out.println(e.getMessage());
//		}
//	}
}
