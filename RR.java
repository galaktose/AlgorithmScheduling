import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

class ProcessRR {
    String processName;
    int arrivalTime;
    int burstTime;
    int remainingBurstTime;
    int turnaroundTime;
    int waitingTime;

    public ProcessRR(String processName, int arrivalTime, int burstTime) {
        this.processName = processName;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingBurstTime = burstTime;
        this.turnaroundTime = 0;
        this.waitingTime = 0;
    }
}

public class RR {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of processes (3-10): ");
        int numProcesses = scanner.nextInt();

        List<ProcessRR> processes = new ArrayList<>();

        for (int i = 0; i < numProcesses; i++) {
            System.out.println("\nEnter details for Process " + i);

            System.out.print("Arrival Time: ");
            int arrivalTime = scanner.nextInt();

            System.out.print("Burst Time: ");
            int burstTime = scanner.nextInt();

            processes.add(new ProcessRR("P" + i, arrivalTime, burstTime));
        }

        System.out.print("\nEnter Time Quantum for Round Robin: ");
        int timeQuantum = scanner.nextInt();

        roundRobin(processes, timeQuantum);

        for (ProcessRR process : processes) {
            System.out.println("Process " + process.processName +
                    " Turnaround Time: " + process.turnaroundTime +
                    " Waiting Time: " + process.waitingTime);
        }
    }

    private static void roundRobin(List<ProcessRR> processes, int timeQuantum) {
        int numProcesses = processes.size();
        boolean[] processCompleted = new boolean[numProcesses];
        int currentTime = 0;
    
        List<String> ganttChart = new ArrayList<>();
    
        while (!allProcessesCompleted(processCompleted)) {
            for (int i = 0; i < numProcesses; i++) {
                if (processes.get(i).arrivalTime <= currentTime && !processCompleted[i]) {
                    int executionTime;
    
                    if (timeQuantum < processes.get(i).remainingBurstTime) {
                        executionTime = timeQuantum;
                    } else {
                        executionTime = processes.get(i).remainingBurstTime;
                    }
    
                    processes.get(i).remainingBurstTime -= executionTime;
                    currentTime += executionTime;
    
                    ganttChart.add(processes.get(i).processName + ": " + currentTime);
    
                    updateProcessStatus(processes, processCompleted, i, currentTime);
                }
            }
        }
    
        System.out.println("\nGantt Chart:");
        for (String entry : ganttChart) {
            System.out.print(entry + " | ");
        }
        System.out.println();
    }
    
    private static boolean allProcessesCompleted(boolean[] processCompleted) {
        for (boolean completed : processCompleted) {
            if (!completed) {
                return false;
            }
        }
        return true;
    }

    private static void updateProcessStatus(List<ProcessRR> processes, boolean[] processCompleted, int index, int currentTime) {
        if (processes.get(index).remainingBurstTime <= 0) {
            processCompleted[index] = true;
            processes.get(index).turnaroundTime = currentTime - processes.get(index).arrivalTime;
            processes.get(index).waitingTime = processes.get(index).turnaroundTime - processes.get(index).burstTime;
        }
    }
}
