package com.skilldistillery.filmquery.app;

import java.io.IOException;
import java.lang.module.FindException;
import java.sql.SQLException;
import java.util.*;

import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
import com.skilldistillery.filmquery.entities.*;

public class FilmQueryApp {

	DatabaseAccessor db = new DatabaseAccessorObject();
	boolean userHasLeft = false;

	public static void main(String[] args) {
		FilmQueryApp app = new FilmQueryApp();
		app.launch();
	}


	private void launch() {
		Scanner input = new Scanner(System.in);
		while (!userHasLeft) {
			try {
				startUserInterface(input);
			} catch (Exception e) {
				System.out.println();
				invalidUserInput(input);
				System.out.println();
				input.nextLine();
			}	
		}

		input.close();
	}

	private void startUserInterface(Scanner input) throws Exception {
		System.out.println("Good afternoon.  Select a method to search for a movie");
		System.out.println("1: Search by film id");
		System.out.println("2: Search by keyword");
		System.out.println("3: Exit");
		
		int userInput = input.nextInt();
		input.nextLine();
		
		Film film = null;
		List<Film> films = new ArrayList<>();
		switch (userInput) {
		case 1:
			System.out.println("Enter film id:");
			int userIdInput = input.nextInt();
			input.nextLine();
			film = db.findFilmById(userIdInput);
			if (film instanceof Film) {
				System.out.println(film.toString());
				List<Actor> actors = db.findActorsByFilmId(film.getId());
				System.out.println("Actors:\t\t[");
				
				for (Actor star: actors) {
					System.out.println("\t\t" + star);
				}
				System.out.println("\t\t]");
				System.out.println();
				
			} else {
				System.out.println("Film not available");
			}
			break;
		case 2: 
			System.out.println("Enter keyword:");
			String userKeyword = input.nextLine();
			films = db.findFilmByKeyword(userKeyword);
			if (films.size() > 0) {
				for (Film movie: films) {
					System.out.println(movie);
					System.out.println("Actors:\t\t[");
					List<Actor> actors = db.findActorsByFilmId(movie.getId());
						for (Actor star: actors) {
							System.out.println("\t\t" + star);
						}
						System.out.println("\t\t]");
						System.out.println();
				}
			} else {
				System.out.println("Sorry, no film available including: " + userKeyword);
				System.out.println();
			}
			break;
		case 3:
			System.out.println("Thank you.  Come again!");
			userHasLeft = true;
			break;
			default: 
				invalidUserInput(input);
				break;
		}

	}
	private void invalidUserInput(Scanner input) {
		System.out.println("Invalid input. Please try again");
	}

}
