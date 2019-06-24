package com.skilldistillery.filmquery.database;

import java.sql.*;
import java.util.*;

import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class DatabaseAccessorObject implements DatabaseAccessor {

	private static final String URL = "jdbc:mysql://localhost:3306/sdvid?useSSL=false";
	private static final String user = "student";
	private static final String pass = "student";

	@Override
	public Film findFilmById(int filmId) {
		Film film = null; 
		String sql = "SELECT film.id, film.title, film.description, film.release_year, film.language_id, film.rental_duration,  \n" + 
				"film.rental_rate, film.length, film.replacement_cost, film.rating, film.special_features, language.name  FROM \n" + 
				"film  JOIN language ON film.language_id = language.id WHERE film.id = ?";

		try (Connection conn = DriverManager.getConnection(URL, user, pass);
			PreparedStatement pstmt = conn.prepareStatement(sql);){
			
			pstmt.setInt(1, filmId);
			ResultSet rs = pstmt.executeQuery();
			film = setFilmData(film, rs);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return film;
	}

	@Override
	public Actor findActorById(int actorId) {
		Actor actor = null;
		String sql = "SELECT id, first_name, last_name FROM actor WHERE id = ?";
		
		try (Connection conn = DriverManager.getConnection(URL, user, pass);
			PreparedStatement pstmt = conn.prepareStatement(sql);){
			
			pstmt.setInt(1, actorId);
			ResultSet rs = pstmt.executeQuery();
			
			if (rs.next()) {
				actor = new Actor();
				actor.setId(rs.getInt("id"));
				actor.setFirstName(rs.getString("first_name"));
				actor.setLastName(rs.getString("last_name"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return actor;
	}

	@Override
	public List<Actor> findActorsByFilmId(int filmId){
		List<Actor> actors = new ArrayList<>();
		Actor actor = null;
		String sql = "SELECT actor.id, actor.first_name, actor.last_name " + "FROM actor JOIN film_actor "
				+ "ON actor.id = film_actor.actor_id " + "JOIN film " + "ON film_actor.film_id = film.id WHERE film.id = ?";
		
		try (Connection conn = DriverManager.getConnection(URL, user, pass);
			PreparedStatement pstmt = conn.prepareStatement(sql);){
			
			pstmt.setInt(1, filmId);
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				int id = rs.getInt("actor.id");
				String firstName = rs.getString("actor.first_name");
				String lastName = rs.getString("actor.last_name");
				actor = new Actor(id, firstName, lastName);
				
				actors.add(actor);			
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return actors;
	}
	
	@Override
	public List<Film> findFilmByKeyword(String keyword) {
		List<Film> films = new ArrayList<>();
		Film film = null;
		String sql = "SELECT film.id, film.title, film.description, film.release_year, film.language_id, film.rental_duration,\n" + 
				"				film.rental_rate, film.length, film.replacement_cost, film.rating, film.special_features, language.name  FROM \n" + 
				"				film  JOIN language ON film.language_id = language.id WHERE film.title LIKE ? OR film.description LIKE ?";
		
		try(Connection conn = DriverManager.getConnection(URL, user, pass);
			PreparedStatement pstmt = conn.prepareStatement(sql);) {
			
			pstmt.setString(1, "%" + keyword + "%");
			pstmt.setString(2, "%" + keyword + "%");
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				film = setFilmData(film, rs);
				films.add(film);
			}
			
		}	catch(SQLException e) {
			e.printStackTrace();
		}
		return films;
	}
	
	public Film setFilmData(Film film, ResultSet rs) {
		try {
			if (rs.next()) {
				film = new Film();
				film.setId(rs.getInt("id"));
				film.setTitle(rs.getString("title"));
				film.setDescription(rs.getString("description"));
				film.setReleaseYear(rs.getInt("release_year"));
				film.setLanguageId(rs.getInt("language_id"));
				film.setRentalDuration(rs.getInt("rental_duration"));
				film.setLength(rs.getInt("length"));
				film.setRentalRate(rs.getDouble("rental_rate"));
				film.setReplacementCost(rs.getDouble("replacement_cost"));
				film.setRating(rs.getString("rating"));
				film.setSpecialFeatures(rs.getString("special_features"));	
				film.setLanguage(rs.getString("language.name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return film;
	}

}
