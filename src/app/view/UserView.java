package app.view;

import app.exeption.InvalidEntityDataException;
import app.exeption.NonexistingEntityException;
import app.model.User;

import java.time.LocalDateTime;
import java.util.Scanner;

public class UserView {



    // update by admin
//    Scanner sc = new Scanner(System.in);
//    String choice;
//    long id;
//        System.out.println("ID of user to update: ");
//        do {
//        System.out.println("ID of user to update: ");
//        id = sc.nextLong();
//        try {
//            user = getUserById(id);
//        } catch (NonexistingEntityException e) {
//            System.out.println("User with id: " + id + " not found, do you wanna try again?  ");
//            choice = sc.nextLine();
//            if (choice.equals("no")) break;
//        }
//    } while (true);
//
//        if (user != null) {
//        int input = 0;
//        do {
//            System.out.println("1  Chane Username ");
//            System.out.println("2  Chane password ");
//            System.out.println("3  Change email");
//            System.out.println("4  Chane First name ");
//            System.out.println("5  Change Last Name");
//            System.out.println("6  Chane Phone ");
//            System.out.println("7  Go to main menu");
//            do {
//                System.out.println("Enter your choice (1 - 7): ");
//                var choiceStr = sc.nextLine();
//                try {
//                    input = Integer.parseInt(choiceStr);
//                } catch (NumberFormatException ex) {
//                    System.out.println("Error: Invalid choice. Please enter a valid number between 1 and 7");
//                }
//            } while (input < 1 || input > 7);
//
//            switch (input) {
//                case 1:
//                    // Chane Username
//                    System.out.println("Username name is: '" + user.getUserName() + "' type the new one: ");
//                    do {
//                        String username = sc.nextLine();
//                        try {
//                            userValidation.isValidUsername(username);
//                        } catch (InvalidEntityDataException e) {
//                            System.out.println(e.getMessage());
//                            System.out.println("Try again:");
//                            continue;
//                        }
//                        user.setUserName(username);
//                        break;
//                    } while (true);
//                    break;
//                case 2:
//                    // change password
//                    user.setPassword(changePassword(user.getId()));
//                    break;
//                case 3:
//                    // Change email
//                    String email;
//                    System.out.println("Email is: '" + user.getEmail() + "' type the new one: ");
//                    do {
//                        email = sc.nextLine();
//                        try {
//                            userValidation.isValidEmailAddress(email);
//                        } catch (InvalidEntityDataException e) {
//                            System.out.println(e.getMessage());
//                            System.out.println("Try again:");
//                            continue;
//                        }
//                        user.setEmail(email);
//                        break;
//                    } while (true);
//                    break;
//                case 4:
//                    // Chane First name
//                    String firstName;
//                    System.out.println("First name is: '" + user.getFirstName() + "', type the new one:");
//                    do {
//                        firstName = sc.nextLine();
//                        if (firstName.trim().length() < 2 || firstName.trim().length() > 15) {
//                            System.out.println("First Name length should be between 2 and 15 characters. Try again");
//                            System.out.println("First name: ");
//                            continue;
//                        }
//                        user.setFirstName(firstName);
//                        break;
//                    } while (true);
//                    break;
//                case 5:
//                    // Change last name
//                    System.out.println("Last name is: '" + user.getLastName() + "', type the new one:");
//                    do {
//                        String lastName = sc.nextLine();
//                        if (lastName.trim().length() < 2 || lastName.trim().length() > 15) {
//                            System.out.println("Last Name length should be between 2 and 15 characters. Try again");
//                            System.out.println("Last name: ");
//                            continue;
//                        }
//                        user.setLastName(lastName);
//                        break;
//                    } while (true);
//                    break;
//                case 6:
//                    // Chane Phone
//                    String phone;
//                    System.out.println("Phone number is: '" + user.getPhone() + "' type the new one: ");
//                    do {
//                        phone = sc.nextLine();
//                        try {
//                            userValidation.isPhoneValid(phone);
//                        } catch (InvalidEntityDataException e) {
//                            System.out.println(e.getMessage());
//                            System.out.println("Try again:");
//                            continue;
//                        }
//                        user.setEmail(phone);
//                        break;
//                    } while (true);
//                    break;
//                case 7:
//                    break;
//            }
//        } while (input != 7);
//        user.setModified(LocalDateTime.now());
//    }
//        return userRepo.update(user);
}
