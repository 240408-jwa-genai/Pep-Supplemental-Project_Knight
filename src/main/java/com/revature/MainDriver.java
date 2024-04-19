package com.revature;

import java.util.Scanner;

import com.revature.controller.UserController;
import com.revature.models.User;
import com.revature.models.Authent;
import com.revature.repository.UserDao;
import com.revature.service.UserService;
import com.revature.models.Planet;
import com.revature.repository.PlanetDao;
import com.revature.service.PlanetService;
import com.revature.controller.PlanetController;
import com.revature.models.Moon;
import com.revature.repository.MoonDao;
import com.revature.service.MoonService;
import com.revature.controller.MoonController;

public class MainDriver {

    public static int loggedInUserId = 0;

    public static UserDao userDao = new UserDao();
    public static UserService userService = new UserService(userDao);
    public static UserController userController = new UserController(userService);
    public static PlanetDao planetDao = new PlanetDao();
    public static PlanetService planetService = new PlanetService(planetDao);
    public static PlanetController planetController = new PlanetController(planetService);
    public static MoonDao moonDao = new MoonDao();
    public static MoonService moonService= new MoonService(moonDao);
    public static MoonController moonController = new MoonController(moonService);

    /*
        An example of how to get started with the registration business and software requirements has been
        provided in this code base. Feel free to use it yourself, or adjust it to better fit your own vision
        of the application. The affected classes are:
            MainDriver
            UserController
            UserService
     */
    public static void main(String[] args) {
        // TODO: implement main method to initialize layers and run the application

        // We are using a Try with Resources block to auto close our scanner when we are done with it
        try (Scanner scanner = new Scanner(System.in)){
            /*
                The userIsActive boolean is used to control our code loop. While the user is "active" the code
                will loop. When prompted, the user can enter "q" to quit the program
             */
            boolean userIsActive = true;
            while (userIsActive){
                if(loggedInUserId == 0) {
                    System.out.println("\nHello! Welcome to the Planetarium! Enter 1 to register an account, 2 to log in, q to quit");
                    String userChoice = scanner.nextLine();
                    if (userChoice.equals("1")) {
                        // remind the user of the choice they made
                        System.out.println("You chose to register an account!");

                        // get the prospective username of the new user
                        System.out.print("Please enter your desired username: ");
                        String potentialUsername = scanner.nextLine();

                        // get the prospective password of the new user
                        System.out.print("Please enter your desired password: ");
                        String potentialPassword = scanner.nextLine();

                        // create a User object and provide it with the username and password
                        // keep in mind the id will be set by the database if the username and password
                        // are valid
                        User potentialUser = new User();
                        potentialUser.setUsername(potentialUsername);
                        potentialUser.setPassword(potentialPassword);

                        // pass the data into the service layer for validation
                        userController.register(potentialUser);

                    } else if (userChoice.equals("2")) {
                        System.out.println("\nYou chose to log in!");
                        System.out.print("Please enter your username: ");
                        String username = scanner.nextLine();

                        System.out.print("Please enter your password: ");
                        String password = scanner.nextLine();

                        Authent credentials = new Authent();
                        credentials.setUsername(username);
                        credentials.setPassword(password);

                        userController.authenticate(credentials);
                        // From here, loggedInUserID should equal that of the logged-in user, assuming they even logged in
                        // So, we'll use our MainDriver.loggedInUserID from here on to keep track of who is logged in.


                    } else if (userChoice.equals("q")) {
                        System.out.println("Goodbye!");
                        userIsActive = false;
                    } else {
                        System.out.println("Invalid choice, please try again");
                    }
                } else {
                    System.out.println("Enter 1 to manage planets, 2 to manage moons, 3 to log out!");
                    String secondUserChoice = scanner.nextLine();
                    if(secondUserChoice.equals("1"))
                    {
                      //  System.out.println("PLANETS: ");
                        //planetController.getAllPlanets(loggedInUserId);
                        // TODO: Implement ability to see just your planets in numbered list form
                        System.out.println("Do you want to add, remove, or view a planet? Enter 1 to add, 2 to remove, 3 to view, and c to cancel.");
                        String thirdUserChoice = scanner.nextLine();
                        if(thirdUserChoice.equals("1"))
                        {
                            // TODO: Implement ability to add a planet
                            System.out.println("How exciting!");
                            System.out.println();
                            System.out.println("What would you like to name it?");
                            String planetToBe = scanner.nextLine();
                            // From here, load up the service layer! It'll check if the planet already exists or not, along with whether it meets requirements
                            Planet discoveredPlanet = new Planet();
                            discoveredPlanet.setOwnerId(loggedInUserId);
                            discoveredPlanet.setName(planetToBe);
                            planetController.createPlanet(loggedInUserId, discoveredPlanet);
                        }else if(thirdUserChoice.equals("2")) {
                            // TODO: Implement ability to remove a planet
                            System.out.println("You got it!");
                            System.out.println();
                            System.out.println("Which planet would you like to remove? Enter its ID here!");
                            //String planetToBeRemoved = scanner.nextLine();
                            if(scanner.hasNextInt())
                            {
                                int planetToBeRemoved = scanner.nextInt();
                                planetController.deletePlanet(loggedInUserId, planetToBeRemoved);
                                System.out.print(scanner.nextLine());

                            } else {
                                System.out.println("Sorry, I didn't catch that.");
                                System.out.print(scanner.nextLine());
                            }
                            // From here, load up the service layer! It'll check if the planet even exists first, as well as whether you have the right to remove it.

                        }
                        else if(thirdUserChoice.equals("3"))
                        {
                            System.out.println("Enter 1 to Search by Name, 2 to Search by ID, 3 to search all, or c to cancel.");
                            String fourthUserChoice = scanner.nextLine();
                            if(fourthUserChoice.equals("1"))
                            {
                                System.out.println("Which planet would you like to view? Remember to check your spelling!");
                                String planetToView = scanner.nextLine();
                                planetController.getPlanetByName(loggedInUserId, planetToView);
                            }
                            else if(fourthUserChoice.equals("2"))
                            {
                                System.out.println("Which planet would you like to view? Enter its ID number below!");
                                if(scanner.hasNextInt())
                                {
                                    int planetToView = scanner.nextInt();
                                    planetController.getPlanetByID(loggedInUserId, planetToView);
                                    // There would appear to be a line of input that scanner gets here, for some reason. This will just clear it out.
                                    System.out.print(scanner.nextLine());
                                }
                                else
                                {
                                    System.out.println("Sorry, that wasn't an integer");
                                    System.out.print(scanner.nextLine());
                                }
                                //int planetToView = scanner.nextInt();

                            }
                            else if(fourthUserChoice.equals("3"))
                            {
                                planetController.getAllPlanets(loggedInUserId);
                            }
                            else if(fourthUserChoice.equals("c"))
                            {
                                System.out.println("Cancelled");
                            }
                            else
                            {
                                System.out.println("Sorry, I didn't catch that");
                            }

                        }
                        else if(thirdUserChoice.equals("c"))
                        {
                            System.out.println("Cancelled");
                        }
                        else
                        {
                            System.out.println("Sorry, I didn't catch that.");
                        }
                    } else if(secondUserChoice.equals("2"))
                    {
                        System.out.println("Do you want to add a moon, remove a moon, or view your moons? Enter 1 to add, 2 to remove, 3 to view, and c to cancel");
                        String fifthUserChoice = scanner.nextLine();
                        if(fifthUserChoice.equals("1"))
                        {
                            System.out.println("Which planet did you find it around? Enter its ID here!");
                            if(scanner.hasNextInt())
                            {
                                int mPlanet = scanner.nextInt();
                                System.out.print(scanner.nextLine());
                                // Right here, we use planetService to check if the logged in user owns the planet they're trying to put a moon around.
                                // This is mostly so that we do not have to import Planet or any of its related classes in the Moon's related classes
                                // As the moons only recognize which planet they are owned by, so to find out whether or not the user has access, we need to check that planet.
                                if(planetService.getPlanetById(loggedInUserId, mPlanet).getOwnerId() == loggedInUserId) {
                                    System.out.println("What do you want to name the moon?");
                                    String mName = scanner.nextLine();
                                    Moon newMoon = new Moon();
                                    newMoon.setName(mName);
                                    newMoon.setMyPlanetId(mPlanet);
                                    moonController.createMoon(loggedInUserId, newMoon);
                                }
                                else {
                                    System.out.println("Either we couldn't find it, or you don't own it!");
                                }
                            }
                            else {
                                System.out.println("Sorry, that wasn't an integer.");
                                System.out.print(scanner.nextLine());
                            }
                        }
                        else if(fifthUserChoice.equals("2")) {
                            System.out.println("You got it!");
                            System.out.println("Which moon would you like to remove?");
                            if(scanner.hasNextInt()) {
                                int nonMoonId = scanner.nextInt();
                                System.out.print(scanner.nextLine());
                                moonController.deleteMoon(loggedInUserId, nonMoonId);
                            } else {
                                System.out.println("Sorry, that wasn't an integer.");
                                System.out.print(scanner.nextLine());
                            }

                        } else if(fifthUserChoice.equals("3")) {
                            System.out.println("Enter 1 to Search by Name, 2 to Search by ID, 3 to Search by Planet ID, 4 to View All Moons, and c to cancel.");
                            String sixthChoice = scanner.nextLine();
                            if(sixthChoice.equals("1"))
                            {
                                System.out.println("Which moon would you like to view? Remember to check your spelling!");
                                String moonToView = scanner.nextLine();
                                moonController.getMoonByName(loggedInUserId, moonToView);
                            }else if(sixthChoice.equals("2"))
                            {
                                System.out.println("Which moon would you like to view? Enter its ID number below!");
                                if(scanner.hasNextInt())
                                {
                                    int moonId = scanner.nextInt();
                                    System.out.print(scanner.nextLine());
                                    moonController.getMoonById(loggedInUserId, moonId);
                                } else {
                                    System.out.println("Sorry, that wasn't an Integer.");
                                    System.out.print(scanner.nextLine());
                                }

                            } else if(sixthChoice.equals("3")) {
                                System.out.println("Which planet's moons would you like to view? Enter the Planet's ID number below!");
                                if(scanner.hasNextInt())
                                {
                                    int planetId = scanner.nextInt();
                                    System.out.print(scanner.nextLine());
                                    moonController.getPlanetMoons(loggedInUserId, planetId);
                                }

                            }else if(sixthChoice.equals("4")) {
                                moonController.getAllMoons(loggedInUserId);

                            }else if(sixthChoice.equals("c")) {
                                System.out.println("Cancelled");

                            }else {
                                System.out.println("Sorry, I didn't catch that.");
                            }

                        } else if(fifthUserChoice.equals("c")) {
                            System.out.println("Cancelled");
                        } else {
                            System.out.println("Sorry, I didn't catch that.");
                        }
                    } else if(secondUserChoice.equals("3")) {
                        System.out.println("Logging out...");
                        //loggedInUserId = 0;
                        userController.logout();
                        System.out.println("Logged out!");
                    } else {
                        System.out.println("Sorry, I didn't catch that.");
                    }

                }
            }

        }


    }

}
