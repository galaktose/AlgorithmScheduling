import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class ProcessRR {
    public int burstTime;
    public int arrivalTime;
    public int completionTime;
    public int remainingRunTime;

    // Initialize processes with an arrival time and burst time
    public ProcessRR(int arrivalTimeValue, int burstTimeValue) {
        burstTime = burstTimeValue;
        arrivalTime = arrivalTimeValue;
        completionTime = -1;
        remainingRunTime = burstTime;
    }
}

public class RR extends Processes{
    public void Scheduler() {
        int[] arrivalTimes = {0, 1, 5, 6, 7, 8};
        int[] burstTimes = {6, 4, 6, 6, 6, 6};
        int quantumTime = 3;

        float averageWaitTime = roundRobin(arrivalTimes, burstTimes, quantumTime);
        float averageTurnaroundTime = calculateAverageTurnaroundTime(averageWaitTime, burstTimes);

        System.out.println("Average Waiting Time: " + averageWaitTime);
        System.out.println("Average Turnaround Time: " + averageTurnaroundTime);
    }

    static public float roundRobin(int[] arrivalTimes, int[] burstTimes, int quantumTime) {
        // Avoid divide by zero
        if (arrivalTimes.length == 0)
            return 0;

        // processes can be either arriving, running, or finished
        List<ProcessRR> arrivingProcesses = new ArrayList<ProcessRR>();
        Queue<ProcessRR> runningProcesses = new LinkedList<ProcessRR>();
        List<ProcessRR> finishedProcesses = new ArrayList<ProcessRR>();

        // Create all processes in arriving
        for (int i = 0; i < arrivalTimes.length; i++) {
            arrivingProcesses.add(new ProcessRR(arrivalTimes[i], burstTimes[i]));
        }
        // I assume the arrays already list the processes based on priority.
        // If there is another way you want to choose priority then you should sort arrivingProcesses

        int currentTime = 0;

        // Simulate time until the processes are all finished
        while (!(arrivingProcesses.isEmpty() && runningProcesses.isEmpty())) {

            // First add any arriving processes to the queue
            for (int i = arrivingProcesses.size() - 1; i >= 0; i--) {
                if (arrivingProcesses.get(i).arrivalTime <= currentTime) {
                    runningProcesses.add(arrivingProcesses.get(i));
                    arrivingProcesses.remove(i);
                }
            }

            // Run the first item in the queue
            if (!runningProcesses.isEmpty())
                runningProcesses.peek().remainingRunTime--;

            currentTime++;

            // finish process if run time is 0
            if (runningProcesses.peek().remainingRunTime == 0) {
                runningProcesses.peek().completionTime = currentTime;
                finishedProcesses.add(runningProcesses.remove());
            }

            // if the quantum time is reached, put the process at the back
            if (currentTime % quantumTime == 0 && !runningProcesses.isEmpty()) {
                runningProcesses.add(runningProcesses.remove());
            }
        }

        // Calculate total waiting time
        float totalWaitTime = 0;

        for (ProcessRR checkProcess : finishedProcesses) {
            totalWaitTime += (checkProcess.completionTime - (checkProcess.arrivalTime + checkProcess.burstTime));
        }

        // return the average
        return totalWaitTime / arrivalTimes.length;
    }

    static public float calculateAverageTurnaroundTime(float averageWaitTime, int[] burstTimes) {
        // Calculate total turnaround time
        float totalTurnaroundTime = 0;

        for (int burstTime : burstTimes) {
            totalTurnaroundTime += burstTime;
        }

        // Add the waiting time to the total turnaround time
        totalTurnaroundTime += averageWaitTime * burstTimes.length;

        // return the average
        return totalTurnaroundTime / burstTimes.length;
    }
}
