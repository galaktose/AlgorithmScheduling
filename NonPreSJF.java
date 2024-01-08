public class NonPreSJF extends Processes{
    public void Scheduler()
    {
        System.out.println("Input number of processes : ");
        processes = scanner.nextInt();
        int arrivalTime[] = new int[processes];
        int burstTime[] = new int[processes];
        System.out.println("Input process information: ");
        while (count < processes) {
            System.out.println("Process " + (1 + count) + " Arrival Time : ");
            arrivalTime[count] = scanner.nextInt();
            System.out.println("Process " + (1 + count) + " Burst Time : ");
            burstTime[count] = scanner.nextInt();
            count++;
        }

        System.out.println("Arrival Time: ");
        for (int i = 0; i < processes; i++) {
            System.out.print(arrivalTime[i] + " ");
        }
        System.out.println(" ");
        System.out.println("Burst Time: ");
        for (int i = 0; i < processes; i++) {
            System.out.print(burstTime[i] + " ");
        }
        System.out.println(" ");

        //Non pre emptive SJF Gantt chart generation

        //check total time
        int scheduleTime = 0;
        for (int i = 0; i < processes; i++) {
            scheduleTime =scheduleTime + burstTime[i];
        }

        System.out.println("Shortest Job First(Non Preemptive) Gantt Chart \n");
        //check arrival time
       
        System.out.print("|");
        for (int i = 0; i < scheduleTime - 1; i++) {
            System.out.print("----|");
        }
        System.out.print("\n");
        for (int i = 0; i < scheduleTime; i++) {
            System.out.print((i+1) + "    ");
        }

        //display arrival time
        for (int index = 0; index < processes; index++) {
            
        }

    }
}
 // int currentArrival = arrivalTime[0];
        // for (int i = 1; i < processes; i++) {
        //     if (arrivalTime[i] < currentArrival) {
        //         currentArrival = arrivalTime[i];
        //     }
        // }