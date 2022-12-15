package com.revature.service;

import java.sql.SQLException;
import java.util.List;

import com.revature.models.Moon;
import com.revature.repository.MoonDao;

public class MoonService {

	private MoonDao dao;

	public MoonService(){
		this.dao = new MoonDao();
	}

	public List<Moon> getAllMoons() {
		try {
			return this.dao.getAllMoons();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Moon getMoonByName(String username, String moonName) {
		return this.dao.getMoonByName(username,moonName);
	}

	public Moon getMoonById(String username, int moonId) {
		return this.dao.getMoonById(username,moonId);
	}

	public Moon createMoon(String username, Moon m) {
		return this.dao.createMoon(username,m);
	}

	public void deleteMoonById(int moonId) {
		this.dao.deleteMoonById(moonId);
	}

	public List<Moon> getMoonsFromPlanet(int planetId) {
		try {
			return this.dao.getMoonsFromPlanet(planetId);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

//	public static void main(String[] args) {
//		MoonService moonService = new MoonService();
//		System.out.println(moonService.getMoonById("Juan", 1));
//	}
}
