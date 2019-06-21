package com.skilldistillery.filmquery.database;

import java.sql.*;
import java.util.*;

import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class DatabaseAccessorObject implements DatabaseAccessor {

	private static final String URL = "jdbc:mysql://localhost:3306/sdvid?useSSL=false";
	private static final String user = "student";
	private static final String pass = "student"; 
	
  @SuppressWarnings("null")
@Override
  public Film findFilmById(int filmId) throws SQLException {
	Film film = null;
	
	Connection conn = DriverManager.getConnection(URL, user, pass);
	String sql = "SELECT id, title, description, release_year, language_id, rental_duration, rental_rate, "
			+ "length, replacement_cost, rating, special_features FROM film WHERE id = ?";
	PreparedStatement pstmt = conn.prepareStatement(sql);
	pstmt.setInt(1, filmId);
	ResultSet rs = pstmt.executeQuery();
	
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
		
		
	}
	rs.close();
	pstmt.close();
	conn.close();
	
    return film;
  }

@Override
public Actor findActorById(int actorId) throws SQLException {
	Actor actor = null;
	Connection conn = DriverManager.getConnection(URL, user, pass);
	String sql = "SELECT id, first_name, last_name FROM actor WHERE id = actorId";
	PreparedStatement pstmt = conn.prepareStatement(sql);
	pstmt.setInt(1, actorId);
	ResultSet rs = pstmt.executeQuery();
	
	if (rs.next()) {
		actor = new Actor();
		actor.setFirstName(rs.getString("first_name"));
		actor.setLastName(rs.getString("last_name"));
	}
	
	rs.close();
	pstmt.close();
	conn.close();
	
	return actor;
}

@Override
public List<Actor> findActorsByFilmId(int filmId) {
	// TODO Auto-generated method stub
	return null;
}

}