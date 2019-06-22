package com.skilldistillery.filmquery.app;

import java.io.IOException;
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
		
		switch (userInput) {
		case 1:
			System.out.println("Enter film id:");
			int userIdInput = input.nextInt();
			input.nextLine();
			Film film = db.findFilmById(userIdInput);
			if (film instanceof Film) {
				System.out.println(film.toString());
			} else {
				System.out.println("Film not available");
			}
			break;
		case 2: 
			System.out.println("Enter keyword:");
			String userKeyword = input.nextLine();
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
