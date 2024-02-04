import java.util.Arrays;
import java.util.Scanner;

public class PrePrioEMP extends Processes {
    public void Scheduler()
    {
        System.out.println("Please input the number of processes :");
        Scanner scanner = new Scanner(System.in);
        int NoP = scanner.nextInt();
        int Bt [] = new int[processes];
        int At [] = new int[processes];
        int prio [] = new int[processes];
        String process[] = new String[NoP];
        int p = 1;
        for (int i = 0; i < NoP; i++) {
            process[i] = "P" + p;
            p++;
        }

        System.out.println(Arrays.toString(process));

    }


}
