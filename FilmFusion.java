import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class FilmFusion {

    //Initialize arrays and variables
    public static Movie[] movies = new Movie[10];
    public static String title, description, leadActor, leadActress, supportActor1, supportActor2, directorName, producerName, studioName, mpaaRating;
    public static int year, boxOffice;
    public static boolean found = false;
    public static String userSearch;

    public static void main(String args[])  
    {
        //Initialize scanner
        Scanner input = new Scanner(System.in);
        
        //Welcome user and display main menu    
        System.out.println("\nHello!\n");
        mainMenu(input);
     

    }


    //User login method
    public static boolean userAuth(Scanner input) 
    {
        String expectedUsername = "admin";
        String expectedPassword = "password";
        String username, password;

        try {

            //Ask user for inputs
            System.out.print("Enter the username: ");
            username = input.next();

            System.out.print("Enter the password: ");
            password = input.next();

            if (username.equals(expectedUsername) && password.equals(expectedPassword)) 
            {
                //Authentication successful
                return true; 
            } 
            
            else 
            {
                //Authentication failed
                return false; 
            }

        } catch (Exception e) {
            // Catch any exceptions (e.g., input/output errors)
            System.out.println("Error during authentication!");

            // Return false for any exceptions
            return false; 
        }
    }

    



    //Main menu method
    public static void mainMenu(Scanner input)
    {
        int userMainSelection = 0;
        boolean finished = false;

        do
        {
            System.out.println("\n1 - Admin Menu");
            System.out.println("2 - User Menu");
            System.out.println("3 - Display All Movies");
            System.out.println("4 - Exit\n");
            

            //Get user choice, with validation integrated
                do
                {
                    try 
                    {
                        System.out.print("What would you like to do?: ");
                        userMainSelection = input.nextInt();
                        
                        // Add this line to consume the newline character
                        input.nextLine();

                        if (userMainSelection < 1 || userMainSelection > 4)
                        {
                            System.out.println("\nPlease choose a valid option!\n");
                        }
                    } 
                    
                    catch (InputMismatchException e) 
                    {
                        System.out.println("\nEnter a number!\n");
                        input.nextLine();
                    }
                    

                } while(userMainSelection < 1 || userMainSelection > 4);


                //Excecute chosen command based on user input
                switch (userMainSelection) 
                {
                    case 1:
                        //Go to admin menu
                        adminMenu(input);
                        

                        //Break out of case
                        break;
                        

                    case 2:
                        //Go to user menu
                        userMenu(input);

                        //Break out of case
                        break;

                    case 3:
                        //Display all the movies
                        viewMovie();

                        //Break out of case
                        break;

                    case 4:
                        System.out.print("Are you sure you want to quit? Yes or No: ");
                        String userConfirmation = input.next();
            
                        // set variable to break out of loop
                        if (userConfirmation.equalsIgnoreCase("Yes")) {
                            finished = true;
                        }
            
                        else {
                            finished = false;
                        }
            
                        //Break out of switch statement
                        break;
                }

            } while(!finished);
    }



    //Admin menu method
    public static void adminMenu(Scanner input)
    {
        //Initialize variables
        int userAdminSelection = 0;
        boolean authenticated = userAuth(input);
        boolean returnToMainMenu = false;

        if (authenticated) {
            System.out.println("\nAuthentication successful - Welcome, admin!");

            do
            {
                System.out.println("\n1 - Add New Movie");
                System.out.println("2 - Update Movie");
                System.out.println("3 - Delete Movie");
                System.out.println("4 - Return to Main Menu\n");
        
                do
                {
                    try 
                    {
                        System.out.print("What would you like to do?: ");
                        userAdminSelection = input.nextInt();
                        
                        if (userAdminSelection < 1 || userAdminSelection > 4)
                        {
                            System.out.println("\nPlease choose a valid option!\n");
                        }
                    } 
                    
                    catch (InputMismatchException e) 
                    {
                        System.out.println("\nEnter a number!\n");
                        input.nextLine();
                    }


                    

                } while (userAdminSelection < 1 || userAdminSelection > 4);

                //Excecute chosen command based on user input
                    switch (userAdminSelection) {
                        case 1:

                            // Add this line to consume the newline character
                            input.nextLine();
                        
                            //Call method to add a new movie
                            addMovie(input);

                            //Break out of switch statement
                            break;

                        case 2:
                             // Add this line to consume the newline character
                            input.nextLine();

                            // Search for a movie
                            List<MovieSearchResult> upSearchResults = searchMovie(input);

                            //Call update method
                            updateMovie(input, upSearchResults);

                            //Break out of switch statement
                            break;

                        case 3:
                            // Add this line to consume the newline character
                            input.nextLine();
                        
                            // Search for a movie
                            List<MovieSearchResult> delSearchResults = searchMovie(input);

                            //Call delete method
                            deleteMovie(input, delSearchResults);

                            // Break out of switch statement
                            break;

                        case 4:
                            // Return to main menu
                            returnToMainMenu = true;

                            //Break out of switch statement
                            break;


                    }

            } while (!returnToMainMenu);

        } 
        
        else 
        {
            System.out.println("Authentication failed! Access denied.");
        }
        
    }


    //User menu method
    public static void userMenu(Scanner input)
    {
        
        int userSelection = 0;
        Movie selectedMovie = null;

        System.out.println("\nWelcome User!\n");

        //List user menu options with error checking integrated
        while (true) 
        {
            System.out.println("1 - Search for movie to rate, comment on, or like comments for");
            System.out.println("2 - Return to main menu\n");
    
            try 
            {
                System.out.print("What would you like to do?: ");
                userSelection = input.nextInt();
            }  
            catch (InputMismatchException e)       
            {
                System.out.println("\nEnter a number!\n");
                input.nextLine();

                // Restart the loop
                continue;  
            }

            if (userSelection < 1 || userSelection > 2)
            {
                System.out.println("\nPlease choose a valid option!\n");
            }

            //Execute based on user selection
            switch (userSelection) 
            {
                case 1:
                // Consume the newline character
                input.nextLine();

                // Search for a movie
                List<MovieSearchResult> searchResults = searchMovie(input);

                // Display search results
                if (!searchResults.isEmpty()) 
                {
                    System.out.println("\nSearch Results:");

                    for (MovieSearchResult result : searchResults) 
                    {
                        System.out.println(result.getMovieNumber() + " - " + result.getMovie().getTitle());
                    }

                    // Allow the user to pick a movie from the search results
                    selectedMovie = pickMovieFromSearchResults(input, searchResults);

                    // Display details of the selected movie
                    if (selectedMovie != null) 
                    {
                            //Initialize variable
                            int movieOption = 0;

                            // Loop for user options after selecting a movie
                            outerLoop: while (true) 
                            {

                                //Show selected movie details
                                displaySearchedMovie(selectedMovie);

                                //Display options for selected movie
                                System.out.println("\n1 - Rate the movie: " + selectedMovie.getTitle());
                                System.out.println("2 - Add a comment for the movie: "+ selectedMovie.getTitle());
                                System.out.println("3 - Like a comment for the movie: "+ selectedMovie.getTitle());
                                System.out.println("4 - Return to user menu\n");    

                                do
                                {
                                    try {
                                        System.out.print("What would you like to do?: ");
                                        movieOption = input.nextInt();

                                        if (movieOption < 1 || movieOption > 4)
                                        {
                                            System.out.println("\nPlease choose a valid option!\n");
                                        }                                       
                                    } 
                                    
                                    catch (InputMismatchException e) 
                                    {
                                        System.out.println("\nEnter a number!\n");
                                        input.nextLine();
                                    }

                                    
                                } while (movieOption < 1 || movieOption > 4);


                                switch (movieOption) {
                                    case 1:
                                        // Consume the newline character
                                        input.nextLine();
                                        
                                        //Rate a movie
                                        Movie.rateMovie(input, selectedMovie);
                                        
                                        //Break out of case 
                                        break;

                                    case 2:
                                        // Consume the newline character
                                        input.nextLine();

                                        //Add comments about a movie
                                        Movie.addComment(input, selectedMovie);
                                        
                                        //Break out of case 
                                        break;

                                    case 3:
                                        // Consume the newline character
                                        input.nextLine();

                                        //Add comments about a movie
                                        Movie.commentLike(input, selectedMovie);

                                        //Break out of case 
                                        break;

                                    case 4:
                                        // Break out of both loops to return to user menu
                                        break outerLoop;
                                }
                            }
                            
                        }

                    }
                    
                //Break out of case 
                break;

                case 2:
                    //Return to main menu
                    return;
                
            }
        }


    }


    // Validation method for non-empty strings
    private static String validateFieldNotEmpty(Scanner input) 
    {
        String value = input.nextLine().trim();
    
        while (value.isEmpty()) 
        {
            System.out.println("Input cannot be empty. Please enter a valid value.");
            System.out.print("Enter again: ");
            value = input.nextLine().trim();
        }
            return value;
        } 

        // Validation method for Year
        private static int validateYear(Scanner input) 
        {
            boolean validInput = false;
            int year = 0;

            while (!validInput) 
            {
                try 
                {
                    year = input.nextInt();

                    if (year >= 1880 && year <= 2023) {
                        validInput = true;
                    } 
                    else {
                        System.out.println("Invalid input! Enter a year between 1880 and 2023.");
                        System.out.print("Enter a valid year: ");

                    }

                } catch (Exception e) {
                    System.out.println("Invalid input for release year!");
                    System.out.print("Enter a valid year: ");

                    // Clear invalid input
                    input.nextLine();
                }
            }

            return year;
        }

        // Validation method for MPAA Rating
        private static String validateMPAA(Scanner input) 
        {
            List<String> mpaaList = Arrays.asList("G", "PG", "PG13", "R");
            boolean validInput = false;

            // Consume the newline character
            input.nextLine();

            while (!validInput) 
            {
                mpaaRating = input.nextLine().toUpperCase();
 

                // Check for valid MPAA rating
                if (mpaaList.contains(mpaaRating)) {
                    validInput = true;
                } 
                else {

                    System.out.println("Sorry! That is not a valid MPAA Rating.");
                    System.out.print("Enter a valid rating: ");
                }
            }

            return mpaaRating;
        }

        // Validation method for Box Office
        private static int validateBoxOffice(Scanner input) 
        {
            boolean validInput = false;
            int boxOffice = 0;

            while (!validInput) 
            {
                try {
                    boxOffice = input.nextInt();

                    // Set valid input to true
                    validInput = true;

                } catch (Exception e) {
                    System.out.println("Invalid input for box office!");
                    System.out.print("Enter a valid number: ");


                    // Clear invalid input
                    input.nextLine();
                }
            }

            return boxOffice;
        }

        //Method that adds a movie (admin option)
        public static void addMovie(Scanner input) 
        {
    
            // Get all the data for the movie
            System.out.print("\nEnter the movie title: ");
            title = validateFieldNotEmpty(input);
    
            System.out.print("Enter the description: ");
            description = validateFieldNotEmpty(input);
    
            System.out.print("Enter the lead actor: ");
            leadActor = validateFieldNotEmpty(input);
    
            System.out.print("Enter the lead actress: ");
            leadActress = validateFieldNotEmpty(input);
    
            System.out.print("Enter the first support actor: ");
            supportActor1 = validateFieldNotEmpty(input);
    
            System.out.print("Enter the second support actor: ");
            supportActor2 = validateFieldNotEmpty(input);
    
            System.out.print("Enter the director's name: ");
            directorName = validateFieldNotEmpty(input);
    
            System.out.print("Enter the producer's name: ");
            producerName = validateFieldNotEmpty(input);
    
            System.out.print("Enter the studio name: ");
            studioName = validateFieldNotEmpty(input);
        
            System.out.print("Enter the release year: ");
            year = validateYear(input);

            System.out.print("Enter the MPAA rating: ");
            mpaaRating = validateMPAA(input);

            
            System.out.print("Enter the box office: ");
            boxOffice = validateBoxOffice(input);


            //Set found to false
            found = false;

            // Add the new movie to the array
            for (int i = 0; i < movies.length; i++) 
            {
                //If this is a valid object
                if (movies[i] == null) 
                {
                    movies[i] = new Movie(title, description, leadActor, leadActress, supportActor1, supportActor2, directorName, producerName, 
                    studioName, mpaaRating, year, boxOffice);

                    //Set found to true
                    found = true;

                    //Break out of loop
                    break;
                }
            }

            //Display if no movies are found or display confirmation message
            if (!found) 
            {
                System.out.println("Error: Array Full!");
            }

            else
            {
                System.out.println("\nMovie Added!");
            }
        } 


    //Method that displays movie details
    public static void viewMovie()
    {
        //Set found to false
        found = false;

        System.out.println("\nHere are the available movies!\n");

        //Loop through array of movies
        for (int i = 0; i < movies.length; i++) 
        {
            //If there is a valid object, display the details
            if (movies[i] != null) 
            {

                //Set found to true
                found = true;

                //Print movie info
                System.out.println("Title: " + movies[i].getTitle());
                System.out.println("Description: " + movies[i].getDescription());
                System.out.println("Lead Actor: " + movies[i].getLeadActor());
                System.out.println("Lead Actress: " + movies[i].getLeadActress());
                System.out.println("Support Actor 1: " + movies[i].getSupportActor1());
                System.out.println("Support Actor 2: " + movies[i].getSupportActor2());
                System.out.println("Director: " + movies[i].getDirector());
                System.out.println("Producer: " + movies[i].getProducer());
                System.out.println("Studio: " + movies[i].getStudio());
                System.out.println("MPAA Rating: " + movies[i].getMpaa());
                System.out.println("Year: " + movies[i].getYear());
                System.out.println("Box Office: $" + movies[i].getBoxOffice());
                System.out.println("Average User Rating: " + movies[i].getAverageUserRating());
                System.out.println("Number of Ratings: " + movies[i].getNumRatings());

                // Display comments
                Comment[] comments = movies[i].getComments();
                if (comments != null && comments.length > 0) 
                {
                    System.out.println("Comments:");
                    for (int j = 0; j < comments.length; j++) 
                    {
                        Comment comment = comments[j];
                        if (comment != null) 
                        {
                            System.out.println("Comment " + (j + 1) + ": '" + comment.getText()
                            + "' Likes: " + comment.getNumLikes());
                        }
                    }
                } 

                else 
                {
                    System.out.println("No comments available for this movie.");
                }

                // Separate each movie with a line space
                System.out.println(); 
            }
        }

        //Display if no movies are found
        if (!found) 
        {
            System.out.println("No movies found.");
        }
    }


    //Method for updating a movie
    public static void updateMovie(Scanner input, List<MovieSearchResult> upSearchResults) {
        // Display search results
        if (!upSearchResults.isEmpty()) {
            System.out.println("\nSearch Results:");
    
            for (MovieSearchResult result : upSearchResults) {
                System.out.println(result.getMovieNumber() + " - " + result.getMovie().getTitle());
            }
    
            // Allow the user to pick a movie from the search results
            Movie selectedMovie = pickMovieFromSearchResults(input, upSearchResults);
    
            // Check if a movie was selected
            if (selectedMovie != null) 
            {
                // Display movie details for update
                displayMovieDetailsForUpdate(selectedMovie);
    
                // Get the attribute to update
                String updateAttribute;
                boolean validAttribute = false;
    
                // Loop until a valid attribute is provided
                do {
                    System.out.print("\nEnter the attribute to update: ");
                    updateAttribute = input.nextLine().toLowerCase();
    
                    // Validate the attribute
                    switch (updateAttribute) {
                        case "description":
                        case "lead actor":
                        case "lead actress":
                        case "support actor 1":
                        case "support actor 2":
                        case "director":
                        case "producer":
                        case "studio":
                        case "year":
                        case "mpaa rating":
                        case "box office":
                            validAttribute = true;
                            break;
                        default:
                            System.out.println("Invalid attribute. Please enter a valid attribute.");
                    }

                } while (!validAttribute);
    
                // Perform the update based on the chosen attribute
                switch (updateAttribute) 
                {
                    case "description":
                        System.out.print("Enter the new description: ");
                        selectedMovie.setDescription(validateFieldNotEmpty(input));
                        break;

                    case "lead actor":
                        System.out.print("Enter the new lead actor: ");
                        selectedMovie.setLeadActor(validateFieldNotEmpty(input));
                        break;

                    case "lead actress":
                        System.out.print("Enter the new lead actress: ");
                        selectedMovie.setLeadActress(validateFieldNotEmpty(input));
                        break;

                    case "support actor 1":
                        System.out.print("Enter the new support actor 1: ");
                        selectedMovie.setSupportActor1(validateFieldNotEmpty(input));
                        break;

                    case "support actor 2":
                        System.out.print("Enter the new support actor 2: ");
                        selectedMovie.setSupportActor2(validateFieldNotEmpty(input));
                        break;

                    case "director":
                        System.out.print("Enter the new director: ");
                        selectedMovie.setDirector(validateFieldNotEmpty(input));
                        break;

                    case "producer":
                        System.out.print("Enter the new producer: ");
                        selectedMovie.setProducer(validateFieldNotEmpty(input));
                        break;

                    case "studio":
                        System.out.print("Enter the new studio: ");
                        selectedMovie.setStudio(validateFieldNotEmpty(input));
                        break;

                    case "year":
                        System.out.print("Enter the new year: ");
                        selectedMovie.setYear(validateYear(input));

                        // Consume the newline character
                        input.nextLine();
                        break;

                    case "mpaa rating":
                        System.out.print("Enter the new MPAA rating: ");
                        selectedMovie.setMpaa(validateMPAA(input));
                        break;

                    case "box office":
                        System.out.print("Enter the new box office: ");
                        selectedMovie.setBoxOffice(validateBoxOffice(input));

                        // Consume the newline character
                        input.nextLine();
                        break;

                    default:
                        System.out.println("Invalid attribute."); 
                }
    
                System.out.println("Movie updated successfully!");
            } else {
                System.out.println("No movie selected for update.");
            }
        } else {
            System.out.println("No movies found matching the search text.");
        }
    }
    
    

    // Helper method to display movie details for update
    public static void displayMovieDetailsForUpdate(Movie movie) {
        System.out.println("\nMovie Details to Update:\n");
        System.out.println("Description: " + movie.getDescription());
        System.out.println("Lead Actor: " + movie.getLeadActor());
        System.out.println("Lead Actress: " + movie.getLeadActress());
        System.out.println("Support Actor 1: " + movie.getSupportActor1());
        System.out.println("Support Actor 2: " + movie.getSupportActor2());
        System.out.println("Director: " + movie.getDirector());
        System.out.println("Producer: " + movie.getProducer());
        System.out.println("Studio: " + movie.getStudio());
        System.out.println("Year: " + movie.getYear());
        System.out.println("MPAA Rating: " + movie.getMpaa());
        System.out.println("Box Office: $" + movie.getBoxOffice());

    }


    //Method for deleting a movie
    public static void deleteMovie(Scanner input, List<MovieSearchResult> delSearchResults) 
    {

        // Display search results
        if (!delSearchResults.isEmpty()) {
            System.out.println("\nSearch Results:");

            for (MovieSearchResult result : delSearchResults) {
                System.out.println(result.getMovieNumber() + " - " + result.getMovie().getTitle());
            }

            // Allow the user to pick a movie from the search results
            Movie selectedMovie = pickMovieFromSearchResults(input, delSearchResults);

            // Check if a movie was selected
            if (selectedMovie != null) 
            {
                // Delete the selected movie
                for (int i = 0; i < movies.length; i++) 
                {
                    if (movies[i] == selectedMovie) 
                    {
                        // Delete the movie by setting its array index to null
                        movies[i] = null;
                        System.out.println("\nMovie deleted successfully!");
                        return;
                    }
                }

                System.out.println("Error: Movie not found in the array.");
            } 
            
            else 
            {
                System.out.println("No movie selected for deletion.");
            }

        } 
        
        else 
        {
            System.out.println("No movies found matching the search text.");
        }
    }




    public static List<MovieSearchResult> searchMovie(Scanner input) 
    {
        // Get search text from the user
        System.out.print("Enter the text to search for in the title: ");
        userSearch = input.nextLine().toLowerCase();
    
        // Initialize a list to store search results
        List<MovieSearchResult> searchResults = new ArrayList<>();

        // Counter for sequential numbering
        int count = 1;

        //Search for movies containing the search text in the title
        for (int i = 0; i < movies.length; i++) 
        {
            if (movies[i] != null && movies[i].getTitle().toLowerCase().contains(userSearch)) 
            {
                searchResults.add(new MovieSearchResult(movies[i], count));
                count++;
            }
        }
    
        return searchResults;
    }
    
    
    public static Movie pickMovieFromSearchResults(Scanner input, List<MovieSearchResult> searchResults) 
    {
        int userSelection = 0;
    
        do {

            try {
                System.out.print("Select a movie number (0 to cancel): ");
                userSelection = input.nextInt();

                // Consume the newline character
                input.nextLine();
            
            } 
            
            catch (InputMismatchException e) {
                System.out.println("\nEnter a valid number!\n");

                // Clear the invalid input
                input.nextLine();
            }
    
            if (userSelection < 0 || userSelection > searchResults.size()) 
            {
                System.out.println("\nPlease choose a valid option!\n");

            } 
            
            else if (userSelection > 0) 
            {
                // Return the selected movie
                return searchResults.get(userSelection - 1).getMovie();
            }
    
        } while (userSelection != 0);
    
        // Return null if the user cancels
        return null;
    }
    
    
        // Method to display details of a movie add below ,Comment[] comments
        public static void displaySearchedMovie(Movie movie ) {
            System.out.println("\nMovie Details:\n");
            System.out.println("Title: " + movie.getTitle());
            System.out.println("Description: " + movie.getDescription());
            System.out.println("Lead Actor: " + movie.getLeadActor());
            System.out.println("Lead Actress: " + movie.getLeadActress());
            System.out.println("Support Actor 1: " + movie.getSupportActor1());
            System.out.println("Support Actor 2: " + movie.getSupportActor2());
            System.out.println("Director: " + movie.getDirector());
            System.out.println("Producer: " + movie.getProducer());
            System.out.println("Studio: " + movie.getStudio());
            System.out.println("MPAA Rating: " + movie.getMpaa());
            System.out.println("Year: " + movie.getYear());
            System.out.println("Box Office: $" + movie.getBoxOffice());

            System.out.println("Average Rating: " + movie.getAverageUserRating());
            System.out.println("Number of Ratings: " + movie.getNumRatings());

            // Display comments and likes
            //
            Comment[] comments = movie.getComments();
            if (comments != null) 
            {
                System.out.println("Comments:");

                for (int i = 0; i < comments.length; i++) 
                {
                    Comment comment = comments[i];

                    if (comment != null) 
                    {
                        System.out.println((i + 1) + ": '" + comment.getText()
                        + "' Likes: " + comment.getNumLikes());
                    }
                }
            }
        
        }


    
        // MovieSearchResult class to store search results
        public static class MovieSearchResult 
        {
            private Movie movie;
            private int movieNumber;

            public MovieSearchResult(Movie movie, int movieNumber) {
                this.movie = movie;
                this.movieNumber = movieNumber;
            }

            public Movie getMovie() {
                return movie;
            }

            public int getMovieNumber() {
                return movieNumber;
            }
        }




    //Create class movie
    public static class Movie
    {
        //Instance Variables
        private String title;
        private String description;
        private String leadActor;
        private String leadActress;
        private String supportActor1;
        private String supportActor2;
        private String directorName;
        private String producerName;
        private String studioName;
        private int year;
        private String mpaaRating;
        private int boxOffice;

        private int totalRatings;
        private int numRatings;
        private double avgRatings;

        

        //Array of comments
        private Comment[] comments = new Comment[10];


        //Constructor
        public Movie(String titleString, String descriptionString, String leadActorString, 
        String leadActressString, String supportActor1String, String supportActor2String, 
        String directorNameString, String producerNameString, String studioNameString, 
        String mpaaRatingString, int yearInt, int boxOfficeInt)
        {
    

            setTitle(titleString);
            setDescription(descriptionString);
            setLeadActor(leadActorString);
            setLeadActress(leadActressString);
            setSupportActor1(supportActor1String);
            setSupportActor2(supportActor2String);
            setDirector(directorNameString);
            setProducer(producerNameString);
            setStudio(studioNameString);
            setMpaa(mpaaRatingString);
            setYear(yearInt);
            setBoxOffice(boxOfficeInt);
        }

        //Set methods
        public void setTitle(String titleString)
        {
            if (titleString != null)
            {
                title = titleString;
            }

            else
            {
                System.out.println("Invalid Data");
            }
        }

        public void setDescription(String descriptionString)
        {
            if (descriptionString != null)
            {
                description = descriptionString;
            }

            else
            {
                System.out.println("Invalid Data");
            }
        }

        public void setLeadActor(String leadActorString)
        {
            if (leadActorString != null)
            {
                leadActor = leadActorString;
            }

            else
            {
                System.out.println("Invalid Data");
            }
        }

        public void setLeadActress(String leadActressString)
        {
            if (leadActressString != null)
            {
                leadActress = leadActressString;
            }

            else
            {
                System.out.println("Invalid Data");
            }
        }

        public void setSupportActor1(String supportActor1String)
        {
            if (supportActor1String != null)
            {
                supportActor1 = supportActor1String;
            }

            else
            {
                System.out.println("Invalid Data");
            }
        }

        public void setSupportActor2(String supportActor2String)
        {
            if (supportActor2String != null)
            {
                supportActor2 = supportActor2String;
            }

            else
            {
                System.out.println("Invalid Data");
            }
        }

        public void setDirector(String directorNameString)
        {
            if (directorNameString != null)
            {
                directorName = directorNameString;
            }

            else
            {
                System.out.println("Invalid Data");
            }
        }

        public void setProducer(String producerNameString)
        {
            if (producerNameString != null)
            {
                producerName = producerNameString;
            }

            else
            {
                System.out.println("Invalid Data");
            }
        }

        public void setStudio(String studioNameString)
        {
            if (studioNameString != null)
            {
                studioName = studioNameString;
            }

            else
            {
                System.out.println("Invalid Data");
            }
        }

        public void setMpaa(String mpaaRatingString)
        {
            if (mpaaRatingString != null)
            {
                mpaaRating = mpaaRatingString;
            }

            else
            {
                System.out.println("Invalid Data");
            }
        }

        public void setYear(int yearInt)
        {
            if (yearInt >= 1880)
            {
                year = yearInt;
            }

            else
            {
                System.out.println("Invalid Data");
            }
        }

        public void setBoxOffice(int boxOfficeInt)
        {
            if (boxOfficeInt >= 0)
            {
                boxOffice = boxOfficeInt;
            }

            else
            {
                System.out.println("Invalid Data");
            }
        }


        //Get methods
        public String getTitle()
        {
            return title;
        }

        public String getDescription()
        {
            return description;
        }

        public String getLeadActor()
        {
            return leadActor;
        }

        public String getLeadActress()
        {
            return leadActress;
        }

        public String getSupportActor1()
        {
            return supportActor1;
        }

        public String getSupportActor2()
        {
            return supportActor2;
        }

        public String getDirector()
        {
            return directorName;
        }

        public String getProducer()
        {
            return producerName;
        }

        public String getStudio()
        {
            return studioName;
        }

        public String getMpaa()
        {
            return mpaaRating;
        }

        public int getYear()
        {
            return year;
        }

        public int getBoxOffice()
        {
            return boxOffice;
        }
    
        public int getNumRatings()
        {
            return numRatings;
        }

        public int getTotalRatings()
        {
            return totalRatings;
        }

        public double getAverageUserRating()
        {
            return avgRatings;
        }

        public Comment[] getComments() 
        {
            return comments;
        }
        
        
        // Method to add a user rating
        public void addUserRating(int rating) 
        {
            if (rating >= 1 && rating <= 10) 
            {
                totalRatings += rating;
                numRatings++;
                avgRatings = calcAverageUserRating();
            } 
        }

        // Method to calculate and return the average user rating
        public double calcAverageUserRating() 
        {
            if (numRatings > 0) 
            {
                return (double) totalRatings / numRatings;
            } 
            
            else 
            {
                // Return 0 if no ratings yet
                return 0.0; 
            }
        }
    

    
        public static void rateMovie(Scanner input, Movie selectedMovie) 
        {
                // Check if a movie was selected
                if (selectedMovie != null) 
                {
                    boolean validInput = false;
        
                    // Loop until a valid rating is provided
                    while (!validInput) 
                    {
                        try {
                            // rate the movie
                            System.out.print("\nEnter your rating for the movie (1-10): ");
                            int rating = input.nextInt();
        
                            // Consume the newline character
                            input.nextLine();
        
                            // Check if the rating is within the valid range
                            if (rating >= 1 && rating <= 10) 
                            {
                                // Call the method to add user rating to the movie
                                selectedMovie.addUserRating(rating);
                                validInput = true;
                            } 
                            else 
                            {
                                System.out.println("\nInvalid rating. Ratings must be between 1 and 10. Please try again.");
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("\nInvalid input. Please enter a valid number.");
                            // Clear the invalid input
                            input.nextLine();
                        }
                    }
                }
        }
        
        
        // Method to add a comment
        public static void addComment(Scanner input, Movie selectedMovie) 
        {

                // Check if a movie was selected
                if (selectedMovie != null) 
                {
                    // Add a comment to the selected movie
                    System.out.print("Enter your comment: ");
                    String commentText = input.nextLine();

                    // Find the first available slot in the comments array
                    for (int i = 0; i < selectedMovie.comments.length; i++) {
                        if (selectedMovie.comments[i] == null) {
                            // Create a new Comment object and add it to the array
                            selectedMovie.comments[i] = new Comment(commentText, 0);
                            System.out.println("\nComment added successfully!");
                            return;
                        }
                    }

                    // If the array is full, print a message
                    System.out.println("Error: Comments array is full. Cannot add more comments.");
                }
        }



        // Method to add a like to a comment (user menu option)
        public static void commentLike(Scanner input, Movie selectedMovie) 
        {
            // Check if a movie was selected
            if (selectedMovie != null) 
            {
                // Get the comments for the selected movie
                Comment[] comments = selectedMovie.getComments();
                
                // Check if there are any comments and print list of comments
                if (comments != null) 
                {
                    System.out.println("\nComments for '" + selectedMovie.getTitle() + "': ");
                    for (int i = 0; i < comments.length; i++) 
                    {
                        Comment comment = comments[i];
                        if (comment != null) 
                        {
                            System.out.println((i + 1) + " - '" + comment.getText() + "' Likes: " + comment.getNumLikes());
                        }
                    }

                    System.out.print("Enter the number of the comment to like (0 to cancel): ");

                    // Loop until a valid comment number is provided
                    while (!input.hasNextInt()) 
                    {
                        System.out.println("\nInvalid input.");
                        System.out.print("\nPlease enter a valid comment number: ");
                        input.next();
                    }

                    int commentNumber = input.nextInt();

                    // Validate the comment number
                    if (commentNumber >= 1 && commentNumber <= comments.length) 
                    {
                        
                        Comment selectedComment = comments[commentNumber - 1];
                        if (selectedComment != null) 
                        {
                            selectedComment.likeComment();
                            System.out.println("\nComment liked successfully!");
                        } 
                        else 
                        {
                            System.out.println("\nInvalid number. Please enter a valid comment number next time.\n");
                        }
                    } 
                    else if (commentNumber == 0) 
                    {
                        // User canceled
                        return;
                    }
                } 
                else 
                {
                    System.out.println("\nNo comments available for this movie.");
                }
            }
        }


    }

    //Create seperate comment class  
    public static class Comment 
    {
        private String text;
        private int numLikes;
    
        public Comment(String text, int numLikes) 
        {
            this.text = text;
            this.numLikes = numLikes;
        }
    
        // Getter for text
        public String getText() 
        {
            return text;
        }
    
        // Getter for numLikes
        public int getNumLikes() 
        {
            return numLikes;
        }
    
        // Method to like the comment
        public void likeComment() 
        {
            numLikes++;
        }
    }

}