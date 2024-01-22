import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

class Process {
    int processId;
    int arrivalTime;
    int burstTime;
    int timeProcessed;

    public Process(int processId, int arrivalTime, int burstTime) {
        this.processId = processId;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.timeProcessed = timeProcessed;
    }
}

class GanttChartEntry {
    String processId;
    int timeProcessed;

    public GanttChartEntry(String processId, int timeProcessed) {
        this.processId = processId;
        this. timeProcessed= timeProcessed;
    }
}
public class PreEmpSJF {
    ArrayList<GanttChartEntry> ganttChart = new ArrayList<>();
    private List<Process> processes;

    public PreEmpSJF(List<Process> processes) {
        this.processes = processes;
        this.ganttChart = new ArrayList<>();
    }

    public void runScheduler() {
        AtomicInteger currentTime = new AtomicInteger(0);
        while (!processes.isEmpty()) {
            System.out.println("Processes: " + processes);
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
        
            System.out.println("Current Time: " + currentTime);
            //System.out.println("Executing Process: P" + currentProcess.processId);

            ganttChart.add(new GanttChartEntry("P" + currentProcess.processId, currentTime.get()));
        
            if (currentProcess.burstTime == 0) {
                currentProcess.timeProcessed = currentTime.get();
                processes.remove(currentProcess);
                System.out.println("Process Completed. End Time: " + currentProcess.timeProcessed);
                
            }
            else {
                processes.remove(currentProcess);
                processes.add(currentProcess);
            }
            currentTime.incrementAndGet();
        }
        ganttChart.add(new GanttChartEntry("", currentTime.get()));
        printGanttChart();

    }

    public void printGanttChart() {
        System.out.println("Gantt Chart:");
    
        for (GanttChartEntry entry0 : ganttChart) {
            System.out.print(" | " + entry0.processId);
        }
        System.out.println(); // Move to the next line for better readability
        for (GanttChartEntry entry1 : ganttChart) {
            if (entry1 == ganttChart.get(10)) {
                break;
            }
            System.out.print(entry1.timeProcessed + "    ");
            
        }
        for (int i = 10; i < ganttChart.size(); i++) {
            GanttChartEntry entry2 = ganttChart.get(i);
            System.out.print(entry2.timeProcessed +  "   ");
        }
        
    }

    public static PreEmpSJF requestProcess() {
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

