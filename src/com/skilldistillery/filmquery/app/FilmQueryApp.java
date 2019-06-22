package com.skilldistillery.filmquery.app;

import java.sql.SQLException;
import java.util.*;

import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
import com.skilldistillery.filmquery.entities.*;

public class FilmQueryApp {
  
  DatabaseAccessor db = new DatabaseAccessorObject();

  public static void main(String[] args) throws SQLException {
    FilmQueryApp app = new FilmQueryApp();
    app.test();
//    app.launch();
  }

  private void test() throws SQLException {
//	  example call
    Film film = db.findFilmById(1);
    System.out.println(film);
//	  example call
    Actor actor = db.findActorById(1);
    System.out.println(actor);
    List<Actor> actors = db.findActorsByFilmId(1);
    System.out.println(actors);
  }

  private void launch() {
    Scanner input = new Scanner(System.in); 
    startUserInterface(input);
    
    input.close();
  }

  private void startUserInterface(Scanner input) {
//	  System.out.println("Good afternoon.  Search for a movie");
//	  int userInput = input.nextInt();
//	  System.out.println("1: Search by film id");
//	  switch(userInput) {
//	  case 1: System.out.println("Enter film id:");
//	  int userIdInput = input.nextInt();
//	  findFilmById(userIdInput);
	  }
    
//  }

}
