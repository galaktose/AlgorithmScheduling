import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Process {
    int processId;
    int arrivalTime;
    int burstTime;

    public Process(int processId, int arrivalTime, int burstTime) {
        this.processId = processId;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
    }
}

public class PreEmpSJF {
    private List<Process> processes;
    private List<String> ganttChart;

    public PreEmpSJF(List<Process> processes) {
        this.processes = processes;
        this.ganttChart = new ArrayList<>();
    }

    public void runScheduler() {
        int currentTime = 0;
        int totalProcesses = processes.size();

        while (!processes.isEmpty()) {
            // Sort the processes based on burst time and arrival time
            processes.sort((p1, p2) -> {
                if (p1.burstTime == p2.burstTime) {
                    return Integer.compare(p1.arrivalTime, p2.arrivalTime);
                }
                return Integer.compare(p1.burstTime, p2.burstTime);
            });

            Process currentProcess = processes.get(0);

            // Execute the process for one time unit
            currentTime++;
            currentProcess.burstTime--;

            // Update Gantt Chart
            ganttChart.add("P" + currentProcess.processId);

            // Remove the process if it is completed
            if (currentProcess.burstTime == 0) {
                processes.remove(currentProcess);
            }
        }

        printGanttChart();
    }

    private void printGanttChart() {
        System.out.println("Gantt Chart:");
        for (String process : ganttChart) {
            System.out.print(process + " ");
        }
        System.out.println();
    }

    public static PreEmpSJF createSchedulerWithUserInput() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of processes: ");
        int numProcesses = scanner.nextInt();

        List<Process> processes = new ArrayList<>();

        for (int i = 0; i <= numProcesses; i++) {
            System.out.print("Enter arrival time for Process P" + i + ": ");
            int arrivalTime = scanner.nextInt();

            System.out.print("Enter burst time for Process P" + i + ": ");
            int burstTime = scanner.nextInt();

            processes.add(new Process(i, arrivalTime, burstTime));
        }

        return new PreEmpSJF(processes);
    }
}

