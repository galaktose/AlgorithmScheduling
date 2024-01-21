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
        // int tempInt = 0;
        // for (int index = 0; index < processes - 1; index++) {
        //     if (arrivalTime[index] > arrivalTime[index+1]) {
        //         tempInt = arrivalTime[index];
        //         arrivalTime[index] = arrivalTime[index+1];
        //         arrivalTime[index+1] = tempInt;

        //         tempInt = burstTime[index];
        //         burstTime[index] = burstTime[index+1];
        //         burstTime[index+1] = tempInt;

        //         tempInt = processNumber[index];
        //         processNumber[index] = processNumber[index+1];
        //         processNumber[index+1] = tempInt;
        //     }
        // }

        //Non pre emptive SJF Gantt chart generation

        //check total time
        int scheduleTime = 0;
        for (int i = 0; i < processes; i++) {
            scheduleTime =scheduleTime + burstTime[i];
        }
        scheduleTime += processes;

        //create process arrival time line
        String processArrival = "Process Arrival|";
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
        System.out.print("Chart Line     ||");
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
    private String processDisplay(int[] Arrival, int[] Burst, int[] processTurns,int processes,int time)
    {
        StringBuilder taskDisplay = new StringBuilder();
        int[] queue = new int[processes];
        int[] queueTurns= new int[processes];
        int queueNumber = 0;
        int arrivedProcesses = 0;
        int tempInt;
        boolean processSearch = true;
        int queueLength = 0;
        taskDisplay.append("Process Ends   |");

        for (int currentTime = 0; currentTime < time; currentTime++) {
            //check if there are any processes left in the queue
            if (arrivedProcesses < processes  && processSearch) {
                for (int index = 0; index < processes; index++) {
                    if (Arrival[index] == currentTime && Burst[index] > 0) {
                        //add it into the queue
                        queue[queueNumber] = Burst[index];
                        queueTurns[queueNumber] = processTurns[index];
                        queueNumber++;
                        arrivedProcesses++;
                        queueLength++;
                        break;
                    }
                }
            }
            else{
                processSearch = false;
            }

            // Check if there is an active process in the queue
            if (arrivedProcesses > 0) {
                // Execute the process at the front of the queue
                if (queue[0] > 0) {
                    queue[0] = queue[0] - 1;
                    taskDisplay.append("       ");
                }
                else{
                    taskDisplay.append("P").append(queueTurns[0]).append("(0)  ");
                    //sort the queue every time a process is finished in increasing order, but move all the 0s to the back
                    for (int i = 0; i < queueLength - 1; i++) {
                        for (int index = 0; index < queueLength - i - 1; index++) {
                            if (queue[index] > queue[index+1] || queue[index] == 0) {
                                tempInt = queue[index];
                                queue[index] = queue[index+1];
                                queue[index+1] = tempInt;

                                tempInt = queueTurns[index];
                                queueTurns[index] = queueTurns[index+1];
                                queueTurns[index+1] = tempInt;
                            }
                        }
                    }
                    //remove 1 from the relevant integers
                    queue[0]--;
                    arrivedProcesses -= 1;
                    queueLength--;
                }    
            }
            else{
                //if no process is active, display "idle"
                taskDisplay.append("idle   ");
            }
    
        }
        return taskDisplay.toString();
    }
}
