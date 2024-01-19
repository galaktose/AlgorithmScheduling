import java.util.Scanner;

public class RR {
    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter number of processes: ");
        int num = scanner.nextInt();

        int B[] = new int[num];
        int A[] = new int[num]; // Arrival time array

        for (int i = 0; i < num; i++) {
            System.out.println("Enter the arrival time for p" + (i + 1));
            A[i] = scanner.nextInt();

            System.out.println("Enter the burst time for p" + (i + 1));
            B[i] = scanner.nextInt();
        }

        System.out.println("Enter quantum time: ");
        int q = scanner.nextInt();

        int wait[] = new int[num];
        int remainingBurst[] = new int[num];

        for (int i = 0; i < num; i++) {
            remainingBurst[i] = B[i];
        }

        int currentTime = 0;

        do {
            for (int i = 0; i < num; i++) {
                if (remainingBurst[i] > 0 && A[i] <= currentTime) {
                    if (remainingBurst[i] > q) {
                        for (int j = 0; j < num; j++) {
                            if (j != i && remainingBurst[j] > 0 && A[j] <= currentTime) {
                                wait[j] += q;
                            }
                        }
                        remainingBurst[i] -= q;
                        currentTime += q;
                    } else {
                        for (int j = 0; j < num; j++) {
                            if (j != i && remainingBurst[j] > 0 && A[j] <= currentTime) {
                                wait[j] += remainingBurst[i];
                            }
                        }
                        currentTime += remainingBurst[i];
                        remainingBurst[i] = 0;
                    }
                }
            }

        } while (!allProcessesCompleted(remainingBurst));

        System.out.println("Process\t\t\twaiting time");

        float totalWait = 0;

        for (int i = 0; i < num; i++) {
            System.out.println("p" + (i + 1) + "\t\t\t" + wait[i]);
            totalWait += wait[i];
        }

        System.out.println("Average waiting time is: " + (totalWait / num));
    }

    private static boolean allProcessesCompleted(int[] remainingBurst) {
        for (int value : remainingBurst) {
            if (value > 0) {
                return false;
            }
        }
        return true;
    }
}
