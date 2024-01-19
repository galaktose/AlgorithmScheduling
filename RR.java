import java.util.Scanner;

public class RR {
    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter number of processes: ");
        int num = scanner.nextInt();

        int B[] = new int[num];
        int A[] = new int[num];

        for (int i = 0; i < num; i++) {
            System.out.println("Enter the arrival time for p" + (i + 1));
            A[i] = scanner.nextInt();

            System.out.println("Enter the burst time for p" + (i + 1));
            B[i] = scanner.nextInt();
        }

        // Sort processes based on arrival time
        sortProcesses(A, B);

        System.out.println("Enter quantum time: ");
        int q = scanner.nextInt();

        int wait[] = new int[num];
        int turnaround[] = new int[num];
        int remainingBurst[] = new int[num];

        for (int i = 0; i < num; i++) {
            remainingBurst[i] = B[i];
        }

        int currentTime = 0;
        int currentProcess = 0; // Variable to keep track of the current process index

        StringBuilder ganttChart = new StringBuilder("Gantt Chart: ");

        do {
            if (remainingBurst[currentProcess] > 0 && A[currentProcess] <= currentTime) {
                ganttChart.append(" | p").append(currentProcess + 1).append(" ");

                for (int i = 0; i < num; i++) {
                    if (A[i] > currentTime && A[i] <= currentTime + q) {
                        A[i] = currentTime + q;
                    }  
                } 

                if (remainingBurst[currentProcess] > q) {
                    for (int j = 0; j < num; j++) {
                        if (j != currentProcess && remainingBurst[j] > 0 && A[j] <= currentTime) {
                            wait[j] += q;
                        }
                    }
                    remainingBurst[currentProcess] -= q;
                    currentTime += q;
                } else {
                    for (int j = 0; j < num; j++) {
                        if (j != currentProcess && remainingBurst[j] > 0 && A[j] <= currentTime) {
                            wait[j] += remainingBurst[currentProcess];
                        }
                    }
                    currentTime += remainingBurst[currentProcess];
                    turnaround[currentProcess] = currentTime - A[currentProcess];
                    remainingBurst[currentProcess] = 0;
                }
            }     

            currentProcess = getNextProcess(A, remainingBurst, currentTime, num);

        } while (!allProcessesCompleted(remainingBurst));

        System.out.println(ganttChart.toString());

        System.out.println("Process\t\t\tWaiting Time\tTurnaround Time");

        float totalWait = 0;
        float totalTurnaround = 0;

        for (int i = 0; i < num; i++) {
            System.out.println("p" + (i + 1) + "\t\t\t" + wait[i] + "\t\t\t" + turnaround[i]);
            totalWait += wait[i];
            totalTurnaround += turnaround[i];
        }

        System.out.println("Average waiting time is: " + (totalWait / num));
        System.out.println("Average turnaround time is: " + (totalTurnaround / num));
    }

    private static boolean allProcessesCompleted(int[] remainingBurst) {
        for (int value : remainingBurst) {
            if (value > 0) {
                return false;
            }
        }
        return true;
    }

    private static void sortProcesses(int[] arrivalTimes, int[] burstTimes) {
        int n = arrivalTimes.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arrivalTimes[j] > arrivalTimes[j + 1]) {
                    // Swap arrival times
                    int tempArrival = arrivalTimes[j];
                    arrivalTimes[j] = arrivalTimes[j + 1];
                    arrivalTimes[j + 1] = tempArrival;

                    // Swap burst times accordingly
                    int tempBurst = burstTimes[j];
                    burstTimes[j] = burstTimes[j + 1];
                    burstTimes[j + 1] = tempBurst;
                }
            }
        }
    }

    private static int getNextProcess(int[] arrivalTimes, int[] remainingBurst, int currentTime, int num) {
        int nextProcess = -1;
        for (int i = 0; i < num; i++) {
            if (remainingBurst[i] > 0 && arrivalTimes[i] <= currentTime) {
                nextProcess = i;
                break;
            }
        }
        return nextProcess;
    }
}
