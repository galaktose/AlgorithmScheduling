public class NonPreSJF extends Processes{

    public void Scheduler()
    {
        System.out.println("Input number of processes : ");
        processes = scanner.nextInt();
        int arrivalTime[] = new int[processes];
        int burstTime[] = new int[processes];
        int processNumber[] = new int[processes];

        System.out.println("Input process information: ");
        while (count < processes) {
            System.out.println("Process " + (1 + count) + " Arrival Time : ");
            arrivalTime[count] = scanner.nextInt();
            System.out.println("Process " + (1 + count) + " Burst Time : ");
            burstTime[count] = scanner.nextInt();
            processNumber[count] = count + 1;
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
        System.out.println("Process Numbers: ");
        for (int i = 0; i < processes; i++) {
            System.out.print(processNumber[i] + " ");
        }
        System.out.println(" ");

        //sort arrival times according to their length, with burst time and process number following suit
        int tempInt = 0;
        for (int index = 0; index < processes - 1; index++) {
            if (arrivalTime[index] > arrivalTime[index+1]) {
                tempInt = arrivalTime[index];
                arrivalTime[index] = arrivalTime[index+1];
                arrivalTime[index+1] = tempInt;

                tempInt = burstTime[index];
                burstTime[index] = burstTime[index+1];
                burstTime[index+1] = tempInt;

                tempInt = processNumber[index];
                processNumber[index] = processNumber[index+1];
                processNumber[index+1] = tempInt;
            }
        }

        //Non pre emptive SJF Gantt chart generation

        //check total time
        int scheduleTime = 0;
        for (int i = 0; i < processes; i++) {
            scheduleTime =scheduleTime + burstTime[i];
        }

        //create process arrival time line
        String processArrival = "Process Arrival |";
        count = 0;
        boolean newProcess = false;
        for (int currentTime = 0; currentTime < scheduleTime; currentTime++) {
        newProcess = false;
            for (int index = 0; index < processes; index++) {
                if (arrivalTime[index] == currentTime) {
                    processArrival = processArrival + "P" + processNumber[index] + "("+burstTime[index]+")" + "  ";
                    newProcess = true;
                    break;
                }
            }
            if (!newProcess) {
                switch (currentTime) {
                    case 0:
                    processArrival = processArrival + "      ";
                        break;
                
                    default:
                    processArrival = processArrival + "       ";
                        break;
                }
            }
        }

        System.out.println("Shortest Job First(Non Preemptive) Gantt Chart \n");
        //check arrival time

        System.out.println(processDisplay(arrivalTime,burstTime,processNumber,processes, scheduleTime));
       
        //print gantt chart lines
        System.out.print("Chart Line      |");
        for (int i = 0; i < scheduleTime + 1; i++) {
            System.out.print("------|");
        }
        //print numbers
        System.out.print("\n                ");
        for (int i = 0; i < scheduleTime + 2; i++) {
            if (i < 10) {
                System.out.print((i) + "      ");
            } 
            else {
                System.out.print((i) + "     ");
            }
            
        }
        System.out.println("\n");

        
        System.out.println(processArrival + "\n");

    }

    //fix this
    private String processDisplay(int[] sortedArrival, int[] sortedBurst, int[] processTurns,int processes,int time)
    {
        StringBuilder taskDisplay = new StringBuilder();
        taskDisplay.append("Process Ends    |");

        for (int currentTime = 0; currentTime < time; currentTime++) {
            boolean processArrived = false;
    
            for (int index = 0; index < processes; index++) {
                if (sortedArrival[index] == currentTime && sortedBurst[index] > 0) {
                    processArrived = true;
    
                    // Add spaces before the process based on the remaining burst time
                    int remainingBurst = sortedBurst[index];
                    for (int j = 0; j < remainingBurst; j++) {
                        taskDisplay.append("       ");
                    }
    
                    taskDisplay.append("P").append(processTurns[index]).append("(0)  ");
                    sortedBurst[index]--;
    
                    break;
                }
            }
    
            if (!processArrived) {
                switch (currentTime) {
                    case 0:
                    taskDisplay.append(" idle ");
                        break;
                    default:
                    taskDisplay.append(" idle  ");
                        break;
                }
            }
        }
        return taskDisplay.toString();
    }
}
