import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

class Process {
    int processID;
    int arrivalTime;
    int burstTime;
    int timeProcessed;
    int endTime;
    int turnaroundTime;
    int waitingTime;

    public Process(int processID, int arrivalTime, int burstTime, int endTime) {
        this.processID = processID;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.endTime = endTime;
    }
}

class GanttChartEntry {
    String processID;
    int timeProcessed;

    public GanttChartEntry(String processID, int timeProcessed) {
        this.processID = processID;
        this. timeProcessed= timeProcessed;
    }
}
public class PreEmpSJF {
    AtomicInteger currentTime = new AtomicInteger(0);
    ArrayList<GanttChartEntry> ganttChart = new ArrayList<>();
    ArrayList<GanttChartEntry> endTime = new ArrayList<>();
    private List<Process> processes;
    List<Integer> ListID = new ArrayList<>();
    List<Integer> ListArrival = new ArrayList<>();
    List<Integer> ListBurstTime = new ArrayList<>();
    List<Integer> ListTurnaroundTime = new ArrayList<>();
    List<Integer> ListWaitingTime = new ArrayList<>();
    int totalTurnaroundTime = 0;
    int totalWaitingTime = 0;

    public PreEmpSJF(List<Process> processes) {
        this.processes = processes;
        this.ganttChart = new ArrayList<>();
        this.endTime = new ArrayList<>(Collections.nCopies(processes.size(), null));
        this.ListID = new ArrayList<>();
        this.ListArrival = new ArrayList<>();
        this.ListBurstTime = new ArrayList<>();
         this.ListTurnaroundTime = new ArrayList<>(Collections.nCopies(processes.size(), 0));
        this.ListWaitingTime = new ArrayList<>(Collections.nCopies(processes.size(), 0));
        this.currentTime = new AtomicInteger();
    }

    public void runScheduler() {
        
        
        //Saves the processID, arrival time and burst time for gantt chart generation (processes will be empty by the time gantt chart can be printed)
        for (Process pro : processes) {
            int processID = pro.processID;
            int arrivalTime = pro.arrivalTime;
            int burstTime = pro.burstTime;

            ListID.add(processID);
            ListArrival.add(arrivalTime);
            ListBurstTime.add(burstTime);
        }
        
        while (!processes.isEmpty()) {
            //System.out.println("Processes: " + processes);what
            processes.sort((p1, p2) -> {
                // Check if both processes have arrived
                boolean p1Arrived = p1.arrivalTime <= currentTime.get();
                boolean p2Arrived = p2.arrivalTime <= currentTime.get();
                //System.out.println("p1Arrived : "+ p1Arrived + " p2Arrived : " + p2Arrived);

                // If burst times are not equal, compare burst times
                if (p1.burstTime != p2.burstTime && (p1Arrived == true && p2Arrived == true)) {
                    return Integer.compare(p1.burstTime, p2.burstTime);
                } else {
                    // If burst times are equal, prioritize the one that has arrived
                    if (p1Arrived && !p2Arrived) {
                        return -1; // Return a negative value to indicate p1 comes before p2
                    } else if (!p1Arrived && p2Arrived) {
                        return 1;  // Return a positive value to indicate p2 comes before p1
                    } else {
                        // Both have arrived or both haven't, compare arrival times
                        return Integer.compare(p1.arrivalTime, p2.arrivalTime);
                    }
                }
            });
        
            //Begin Processing
            Process currentProcess = processes.get(0);
            
            currentProcess.burstTime--;
        
            //System.out.println("Current Time: " + currentTime);
            //System.out.println("Executing Process: P" + currentProcess.processID);

            ganttChart.add(new GanttChartEntry("P" + currentProcess.processID, currentTime.get()));
        
            if (currentProcess.burstTime == 0) {
                currentProcess.timeProcessed = currentTime.get();
                GanttChartEntry entry = new GanttChartEntry("P" + currentProcess.processID, currentTime.get() + 1);
                endTime.set(currentProcess.processID - 1, entry);
                // Calculate turnaround time and waiting time
                currentProcess.turnaroundTime = (currentProcess.timeProcessed + 1)- currentProcess.arrivalTime;
                currentProcess.waitingTime = currentProcess.turnaroundTime - ListBurstTime.get(currentProcess.processID - 1);
 
                // Store the calculated values in the lists
                ListTurnaroundTime.set(currentProcess.processID - 1, currentProcess.turnaroundTime);
                ListWaitingTime.set(currentProcess.processID - 1, currentProcess.waitingTime);
                System.out.println("List turnaroundTime : " + ListTurnaroundTime);
                System.out.println("List waiting time : " + ListWaitingTime);
                System.out.println("List burst time : " + ListBurstTime);
 
                // Update total turnaround and waiting times
                totalTurnaroundTime += currentProcess.turnaroundTime;
                totalWaitingTime += currentProcess.waitingTime;
 
                processes.remove(currentProcess);
                
                
                //System.out.println("Process Completed. End Time: " + currentProcess.timeProcessed);
                
            }
            else {
                processes.remove(currentProcess);
                processes.add(currentProcess);
            }
            currentTime.incrementAndGet();
        }
        ganttChart.add(new GanttChartEntry("", currentTime.get()));
        
        printGanttChart();
        printTable();

    }

    public void printGanttChart() {
        System.out.println("Gantt Chart:");
        
        for (int i = 0; i < currentTime.get(); i++) {
            boolean found = false;
            for (int j = 0; j < endTime.size(); j++) {
                if (endTime.get(j).timeProcessed == i) {
                    // Print the process ID with the end time
                    System.out.print("|" + endTime.get(j).processID + "(0)");
                    found = true;
                    break;
                }
            }
            if (!found) {
                // If no process ended at time i, print a blank space
                System.out.print(" ____");
            }
        }
        System.out.println();
        for (GanttChartEntry entry0 : ganttChart) {
            System.out.print(" | " + entry0.processID);
        }

        System.out.println(); // Move to the next line for better readability

        //prints the time up until 9 (for readability)
        for (GanttChartEntry entry1 : ganttChart) {
            if (entry1 == ganttChart.get(10)) {
                break;
            }
            System.out.print(" " + entry1.timeProcessed + "   ");
        }

        //prints the time from 10 until currentTime (for readability)
        for (int i = 10; i < ganttChart.size(); i++) {
            GanttChartEntry entry2 = ganttChart.get(i);
            System.out.print(entry2.timeProcessed +  "   ");
        }
        System.out.println();

        //print arrival time in Pn(m) format where n is processID and m is arrivalTime
        System.out.println("Arrival Time : ");
        for (int i = 0; i < ListID.size(); i++) {
            System.out.print("P" + ListID.get(i) + "(" + ListArrival.get(i) + ") ");
        }
        
    }
    
    public void printTable() {
        System.out.println("\nTable:");

        System.out.printf("%-10s%-15s%-15s%-20s%-18s%-15s\n", "ProcessID", "Arrival Time", "Burst Time",
                "Finishing Time", "Turnaround Time", "Waiting Time");

        int n = ListID.size();
        for (int i = 0; i < n; i++) {
            int processID = ListID.get(i);
            int arrivalTime = ListArrival.get(i);
            int burstTime = ListBurstTime.get(i);
            int finishingTime = endTime.get(i).timeProcessed;
            int turnaroundTime = ListTurnaroundTime.get(i);
            int waitingTime = ListWaitingTime.get(i);

            System.out.printf("%-10d%-15d%-15d%-20d%-18d%-15d\n", processID, arrivalTime, burstTime, finishingTime,
                    turnaroundTime, waitingTime);
        }

        int totalTurnaround = 0;
        int totalWaiting = 0;

        for (int i = 0; i < n; i++) {
            totalTurnaround += ListTurnaroundTime.get(i);
            totalWaiting += ListWaitingTime.get(i);
        }

        double averageTurnaround = (double) totalTurnaround / n;
        double averageWaiting = (double) totalWaiting / n;

        System.out.println("\nTotal Turnaround Time: " + totalTurnaround);
        System.out.println("Average Turnaround Time: " + averageTurnaround);
        System.out.println("Total Waiting Time: " + totalWaiting);
        System.out.println("Average Waiting Time: " + averageWaiting);
    }

    public static PreEmpSJF requestProcess() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of processes: ");
        int numProcesses = scanner.nextInt();

        List<Process> processes = new ArrayList<>();

        for (int i = 1; i <= numProcesses; i++) {
            System.out.print("Enter arrival time for Process P" + i + ": ");
            int arrivalTime = scanner.nextInt();

            System.out.print("Enter burst time for Process P" + i + ": ");
            int burstTime = scanner.nextInt();

            processes.add(new Process(i, arrivalTime, burstTime,0));
            
        }

        return new PreEmpSJF(processes);
    }
}

