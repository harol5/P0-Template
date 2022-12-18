package com.revature.repository;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

import com.revature.MainDriver;
import com.revature.models.Planet;
import com.revature.models.User;
import com.revature.utilities.ConnectionUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlanetDao {

	public static Logger logger = LoggerFactory.getLogger(MainDriver.class);

	/*
	 * added the throws clause to the method signature because the alternative is to return an empty
	 * arraylist, but the method could succeed with no planets returned, so this is not an ideal solution.
	 * Instead we will let the service layer and/or API handle the exception being thrown
	 */
    public List<Planet> getAllPlanets() throws SQLException{
		try(Connection connection = ConnectionUtil.createConnection())  {
			String sql = "select * from planets";
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			List<Planet> planets = new ArrayList<>();
			while(rs.next()){ // the resultset next method returns a boolean, so we can use it in our loop
				Planet planet = new Planet();
				planet.setId(rs.getInt(1));
				planet.setName(rs.getString(2));
				planet.setOwnerId(rs.getInt(3));
				planets.add(planet);
			}
			return planets;
		}
	}

	public Planet getPlanetByName(String owner, String planetName) {
		try(Connection connection = ConnectionUtil.createConnection()) {
			String sql = "select * from planets where name = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, planetName);
			ResultSet rs = ps.executeQuery();
			rs.next();
			Planet planet = new Planet();
			planet.setId(rs.getInt(1));
			planet.setName(rs.getString(2));
			planet.setOwnerId(rs.getInt(3));
			return planet;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return new Planet();
		}

	}

	public Planet getPlanetById(String username, int planetId) {
		try(Connection connection = ConnectionUtil.createConnection()) {
			String sql = "select * from planets where id = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, planetId);
			ResultSet rs = ps.executeQuery();
			rs.next();
			Planet planet = new Planet();
			planet.setId(rs.getInt(1));
			planet.setName(rs.getString(2));
			planet.setOwnerId(rs.getInt(3));
			return planet;
		} catch (SQLException e) {
			logger.debug(e.getMessage());
			return new Planet();
		}
	}

	public Planet createPlanet(String username, Planet p) {

		try(Connection conn = ConnectionUtil.createConnection()) {
			// 1. craft the query
			String sql = "insert into planets values (default,?,?)"; // ? are placeholders for info we will provide
			/*
			 * we need to provide the prepareStatement method with the sql to be executed, and because we
			 * are returning a User object in the overall method we need to get the generated id from
			 * the user that is created
			*/
			PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			// 2. provide relevant information
			/*
			 * keep in mind, when working with JDBC and entering/retrieving info indexing starts at 1, not at 0
			 */
			ps.setString(1, p.getName());// set name first because of order in table
			ps.setInt(2, p.getOwnerId());// set ownerId second

			// 3. execute query
			ps.execute();// execute the statement
			ResultSet rs = ps.getGeneratedKeys();// get the generated id and save it in a result set

			// 4. handle the results
			Planet newPlanet = new Planet();
			rs.next(); // anytime you need to get info from a result set you must call next() once
			int newId = rs.getInt("id");
			newPlanet.setId(newId);
			newPlanet.setName(p.getName());
			newPlanet.setOwnerId(p.getOwnerId());
			return newPlanet;
		} catch (SQLException e){
			System.out.println(e.getMessage()); // perhaps log this info in your projects?
			return new Planet();
		}
	}

	public void deletePlanetById(int planetId) {
		try(Connection connection = ConnectionUtil.createConnection()) {
			String sql = "delete from planets where id = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, planetId);
			int rowsAffected = ps.executeUpdate();
			System.out.println("Rows affected: " + rowsAffected);
		} catch (SQLException e) {
			System.out.println(e.getMessage()); // good spot to add some logging?
		}
	}
}
