import java.util.Scanner;

//Group : TT8L
//Noor Hannan Bin Noor Hamsuruddin 1211104293
//Wan Muhammad Atif Bin Taram Satiraksa 1211103154
//Muhammad Syahmi Bin Mohd Azmi 121101975
//Syahmi Aufa Bin Usup 1221305485
//abstract
abstract class Processes{
        int processes;
        int count = 0;

        public abstract void Scheduler();
        Scanner scanner = new Scanner(System.in);
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
        System.out.println("2. Pre-emptive SJF");
        System.out.println("3. Non Pre-emptive Priority");
        System.out.println("4. Round Robin\n");
        
        //choose a process
        int choice;
        while (true) {
        System.out.print("Choice : ");
        choice = input.nextInt();
            if (choice == 1) {
                NonPreSJF SJF = new NonPreSJF();
                SJF.Scheduler();
                break;
            }
            else if (choice == 2) {
                PreEmpSJF scheduler = PreEmpSJF.requestProcess();
                scheduler.runScheduler();
                break;
            }
            else if (choice == 3) {
                PrePrioEMP Prio = new PrePrioEMP();
                Prio.Scheduler();
                break;
            }
            else if (choice == 4) {
                RR rrScheduler = new RR();
                rrScheduler.Scheduler();
                break;
            }
            else{
                System.out.println("Invalid input, please try again.");
                continue;
            }

        }
    }
}
