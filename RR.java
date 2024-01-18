import java.util.Scanner;

class ProcessRR {
    String processName;
    int arrivalTime;
    int burstTime;
    int remainingBurstTime;

    public ProcessRR(String processName, int arrivalTime, int burstTime) {
        this.processName = processName;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingBurstTime = burstTime;
    }
}

public class RR {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of processes (3-10): ");
        int numProcesses = scanner.nextInt();

        if (numProcesses < 3 || numProcesses > 10) {
            System.out.println("Number of processes should be between 3 and 10.");
            return;
        }

        ProcessRR[] processes = new ProcessRR[numProcesses];

        // Get details for each process
        for (int i = 0; i < numProcesses; i++) {
            System.out.println("\nEnter details for Process " + i);

            System.out.print("Arrival Time: ");
            int arrivalTime = scanner.nextInt();

            System.out.print("Burst Time: ");
            int burstTime = scanner.nextInt();

            processes[i] = new ProcessRR("P" + i, arrivalTime, burstTime);
        }

        // Get the time quantum for Round Robin
        System.out.print("\nEnter Time Quantum for Round Robin: ");
        int timeQuantum = scanner.nextInt();

        return;
    }
}