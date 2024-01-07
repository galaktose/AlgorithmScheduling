import java.util.Scanner;

//abstract
abstract class Processes{
        protected int processes;
        protected int[] arrivalTime;
        protected int[] burstTime;
    }

public class Main {
    
    public static void main(String[] args) {
    Scanner input = new Scanner(System.in);
        System.out.print(" ");
        for (int i = 0; i < 22; i++) {
            System.out.print("-");
        }
        System.out.println("-");
        System.out.println("| CPU Process Scheduler |");
        System.out.print(" ");
        for (int i = 0; i < 23; i++) {
            System.out.print("-");
        }
        System.out.println(" ");

        System.out.println("Select a process scheduling method :");
        System.out.println("1. Shortest Job First(SJF) , Non Pre-emptive");
        System.out.println("2. Non Pre-emptive Priority");
        System.out.println("3. Pre-emptive Priority");
        System.out.println("4. Round Robin\n");
        
        //choose a process
        int choice;
        while (true) {
        System.out.print("Choice : ");
        choice = input.nextInt();
            if (choice == 1) {
                
                break;
            }
            else if (choice == 2) {
                
                break;
            }
            else if (choice == 3) {
                
                break;
            }
            else if (choice == 4) {

                break;
            }
            else if (choice == 5) {

                break;
            }
            else{
                System.out.println("Invalid input, please try again.");
                continue;
            }

        }
    }
}
