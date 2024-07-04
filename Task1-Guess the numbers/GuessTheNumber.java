import java.util.Scanner;
import java.util.Random;

public class GuessTheNumber {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        int lowerNo = 1; 
        int upperNo = 100; 
        int randomNumber = random.nextInt(upperNo - lowerNo + 1) + lowerNo;
        int maxLimit = 7;
        int attempts = 0; 

        System.out.println("Welcome to Guess the Number Game!");
        System.out.println("Guess a number between " + lowerNo + " and " + upperNo);

        while (attempts < maxLimit) {
            System.out.print("Enter your guess: ");
            int guess = scanner.nextInt();
            attempts++;

            if (guess == randomNumber) {
                System.out.println("Congratulations! You guessed the number in " + attempts + " attempts.");
                break;
            } else if (guess < randomNumber) {
                System.out.println("The number is higher.");
            } else {
                System.out.println("The number is lower.");
            }
        }

        if (attempts == maxLimit) {
            System.out.println("Oops! You've run out of attempts. The number was: " + randomNumber);
        }

        scanner.close();
    }
}
