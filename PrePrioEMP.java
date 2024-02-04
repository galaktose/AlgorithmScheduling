import java.util.Scanner;

public class PrePrioEMP extends Processes {

    private int processes; 
    private int[] burst; 
    private int[] arrive; 
    private int[] priveledged; 
    public int[] wait; 
    public int[] turnarounf; 
    private String[] id; 
    private Scanner scanner; 

    
    public PrePrioEMP() {
        scanner = new Scanner(System.in);
    }

    // how many processse
    public void Scheduler() {
        System.out.println("Please input the number of processes:");
        processes = scanner.nextInt();

        burst = new int[processes];
        arrive = new int[processes];
        priveledged = new int[processes];
        id = new String[processes];

        // details
        for (int i = 0; i < processes; i++) {
            System.out.println("Enter the arrival time of process " + (i + 1) + ": ");
            arrive[i] = scanner.nextInt();
            System.out.println("Enter the burst time of process " + (i + 1) + ": ");
            burst[i] = scanner.nextInt();
            System.out.println("Enter the priority level of process " + (i + 1) + ": ");
            priveledged[i] = scanner.nextInt();
            id[i] = "P" + (i + 1);
        }
    }

    // sorting
    void sort(int[] At, int[] Bt, int[] prio, String[] process) {
        for (int i = 0; i < processes - 1; i++) {
            for (int j = 0; j < processes - i - 1; j++) {

                if (At[j] > At[j + 1]) {
                    swap(At, Bt, prio, process, j, j + 1);
                } else if (At[j] == At[j + 1]) {
                    if (prio[j] < prio[j + 1]) {
                        swap(At, Bt, prio, process, j, j + 1);
                    }
                }
            }
        }
    }

    
    void swap(int[] At, int[] Bt, int[] prio, String[] process, int i, int j) {
        
        int temp = At[i];
        At[i] = At[j];
        At[j] = temp;

        temp = Bt[i];
        Bt[i] = Bt[j];
        Bt[j] = temp;

        temp = prio[i];
        prio[i] = prio[j];
        prio[j] = temp;

        String tempStr = process[i];
        process[i] = process[j];
        process[j] = tempStr;
    }

    public void prioralgo() {
        int finish[] = new int[processes]; 
        int Bt[] = burst.clone(); 
        int At[] = arrive.clone();
        int prio[] = priveledged.clone(); 
        String process[] = id.clone(); 
        int wait[] = new int[processes]; 
        int turnarounf[] = new int[processes]; 

        // Sort the processes
        sort(At, Bt, prio, process);

        // Calculate 
        finish[0] = At[0] + Bt[0]; 
        turnarounf[0] = finish[0] - At[0]; 
        wait[0] = turnarounf[0] - Bt[0]; 

        for (int i = 1; i < processes; i++) {
            finish[i] = Bt[i] + finish[i - 1]; 
            turnarounf[i] = finish[i] - At[i];
            wait[i] = turnarounf[i] - Bt[i]; 
        }

        float sum = 0; 
        for (int n : wait) {
            sum += n; 
        }
        float avgWaitTime = sum / processes; 
        

        sum = 0; 
        for (int n : turnarounf) {
            sum += n; 
        }
        float avgTurnarounf = sum / processes; 

        // Display zaresults
        System.out.println("Average Waiting Time: " + avgWaitTime);
        System.out.println("Average Turnaround Time: " + avgTurnarounf);
    }

    // outputting(real)
    public void outputProcesses() {

        sort(arrive, burst, priveledged, id);
        //header
        System.out.println("Process\tArrival\tBurst\tPriority");
        for (int i = 0; i < processes; i++) {
            System.out.println(id[i] + "\t" + arrive[i] + "\t" + burst[i] + "\t" + priveledged[i] + "\t\t" /*+ wait + "\t" + turnarounf*/);
        }
        
    }
    
}