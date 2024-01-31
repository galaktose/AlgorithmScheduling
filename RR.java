import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Scanner;

public class RR extends Processes{
    public void Scheduler() {
        Scanner scanner = new Scanner(System.in);
        int num = 0;

        while (num < 3 || num > 10) {
            try {
                System.out.print("Enter number of process between 3 and 10 = ");
                num = scanner.nextInt();

            } catch (InputMismatchException e) {
                scanner.next();
            }
        }

        int[] processId = new int[num];
        int[] arrivalTime = new int[num];
        int[] burstTime = new int[num];
        int[] burstTime2 = new int[num];
        int[] arrivalTime2 = new int[num];
        int quantumTime = 0;

        while (quantumTime <= 0) {
            try {
                System.out.print("Enter quantum time = ");
                quantumTime = scanner.nextInt();

            } catch (InputMismatchException e) {
                scanner.next();
            }
        }

        for (int i = 0; i < num; i++) {
            boolean validInput = false;

            while (!validInput) {
                try {
                    System.out.print("Please enter P" + i + " Arrival Time = ");
                    arrivalTime[i] = scanner.nextInt();
                    arrivalTime2[i] = arrivalTime[i];

                    System.out.print("Please enter P" + i + " Burst Time = ");
                    burstTime[i] = scanner.nextInt();
                    burstTime2[i] = burstTime[i];

                    processId[i] = i;

                    validInput = true;

                } catch (InputMismatchException e) {
                    System.out.println("Please enter valid integers for Arrival Time and Burst Time.");
                    scanner.next(); 
                }
            }
        }

        // Storing Temp Value
		int tempArrivalTime; 
		int tempProcessId; 
		int tempBurstTime;
		int tempArrivalTime2;
		int tempBurstTime2;

		for (int i = 0; i < num; i++) {
            for (int j = i + 1; j < num; j++) { 
                if (arrivalTime[i] > arrivalTime[j]){
                    tempArrivalTime = arrivalTime[i];
                    arrivalTime[i] = arrivalTime[j];
                    arrivalTime[j] = tempArrivalTime;
					
					tempProcessId = processId[i];
					processId[i] = processId[j];
                    processId[j] = tempProcessId;
					
					tempBurstTime = burstTime[i];
					burstTime[i] = burstTime[j];
                    burstTime[j] = tempBurstTime;
					
					tempArrivalTime2 = arrivalTime2[i];
					arrivalTime2[i] = arrivalTime2[j];
                    arrivalTime2[j] = tempArrivalTime2;
					
					tempBurstTime2 = burstTime2[i];
					burstTime2[i] = burstTime2[j];
                    burstTime2[j] = tempBurstTime2;
                }
            }
        }

		// Round Robin Calculation
        boolean[] isReady = new boolean[processId.length];
		LinkedList<Integer> ready = new LinkedList<Integer>();
		
		int limit = 0;
		int totalBurstTime = 0;
		
		ArrayList<Integer> time = new ArrayList<Integer>();
		ArrayList<Integer> addTime = new ArrayList<Integer>();
		ArrayList<Integer> processTime = new ArrayList<Integer>();
		
		int temp = 0;
		
		if(arrivalTime[temp] <= limit) {
			if(burstTime[temp] > quantumTime) {
				burstTime[temp] = burstTime[temp] - quantumTime;
				time.add(quantumTime);
				processTime.add(processId[temp]);
				totalBurstTime += quantumTime;
				addTime.add(totalBurstTime);
				limit = limit + quantumTime;
			} 

			else {
				time.add(burstTime[temp]);
				processTime.add(processId[temp]);
				totalBurstTime += burstTime[temp];
				addTime.add(totalBurstTime);
				limit = limit + burstTime[temp];
				burstTime[temp] = 0;
			}
		}
	
		for (int i = 1; i < processId.length; i++) {
			if (arrivalTime[i] <= limit && isReady[processId[i]] == false) {
				ready.addLast(processId[i]);
				isReady[processId[i]] = true;
			}
		}
		
		while(!ready.isEmpty()) {
			if(burstTime[temp] != 0) {
				ready.addLast(processId[temp]);
			}

			int temp2 = ready.removeFirst();

			for(int i = 0; i < processId.length; i++) {
				if(processId[i] == temp2) {
					temp = i;
				}
			}

			if(arrivalTime[temp] <= limit) {
				if(burstTime[temp] > quantumTime) {
					burstTime[temp] = burstTime[temp] - quantumTime;
					time.add(quantumTime);
					processTime.add(processId[temp]);
					totalBurstTime += quantumTime;
					addTime.add(totalBurstTime);
					limit = limit + quantumTime;

				} 

				else {
					time.add(burstTime[temp]);
					processTime.add(processId[temp]);
					totalBurstTime += burstTime[temp];
					addTime.add(totalBurstTime);
					limit = limit + burstTime[temp];
					burstTime[temp] = 0;
				}
			}

			for (int i = 1; i < processId.length; i++){
				if (arrivalTime[i] <= limit && isReady[processId[i]] == false){
					ready.addLast(processId[i]);
					isReady[processId[i]] = true;
				}
			}
		}

        //Calculate Total TurnAround & Waiting Time
		int[] currentTime = new int[processId.length];
		int[] waitingTime = new int[processId.length];
		int[] turnAroundTime = new int[processId.length];

		for(int i = 0; i < currentTime.length; i++) {
			int index = 0;
			for (int j = 0; j < processTime.size(); j++) {
				if (i == processTime.get(j)){
					index = j+1;
				}
			}

			int s = 0;
			for (int k = 0; k < index; k++) {
				s = s + time.get(k);
			}

			currentTime[i] = s;
		}
		
		for(int i = 0; i < turnAroundTime.length; i++) {
			int index = 0;
			for(int j = 0; j < processId.length; j++) {
				if (i == processId[j]) {
					index = j;
				}
			}

			turnAroundTime[i] = currentTime[i] - arrivalTime2[index];
		}
		
		for(int i = 0; i < waitingTime.length; i++) {
			int index = 0;
			for(int j = 0; j < processId.length; j++) {
				if (i == processId[j]) {
					index = j;
				}
			}

			waitingTime[i] = turnAroundTime[i] - burstTime2[index];
		}
		
		double averageTurnaround = 0;
		int totalTurnaround = 0;

		for (int i = 0; i < turnAroundTime.length; i++) {
			totalTurnaround += turnAroundTime[i];

		}
		
        averageTurnaround = totalTurnaround / processId.length;
		
        double averageWaitingTime = 0;
		int totalWaitingTime = 0;

		for (int i = 0; i < waitingTime.length; i++) {
			totalWaitingTime += waitingTime[i];

		}

		averageWaitingTime = totalWaitingTime / processId.length;
        
        // Display Gantt Chart
        System.out.println("\nGantt Chart : \n");
		for (int i = 0; i < processTime.size(); i++){
			System.out.format("| P" + "%s" + " : " + "%2d" + "  ", processTime.get(i), addTime.get(i));
		}

		System.out.print("|");
		System.out.print("\n\n");

        // Display Result
		System.out.println("--------------------------------------------------------------------------------------");
		System.out.println("| Process | Arrival Time | Burst Time | Finish Time | Turnaround Time | Waiting Time |");
		System.out.println("--------------------------------------------------------------------------------------");
		
        for (int i = 0; i < turnAroundTime.length; i++){
				System.out.format("| P" + "%s" + "      |" 
                                    + "%2d" + "            |" 
                                    + "%2d" + "          |" 
                                    + "%2d" + "           |" 
                                    + "%2d" + "               |" 
                                    + "%2d" + "            |", 
                                    i, arrivalTime[i], burstTime2[i], addTime.get(i), turnAroundTime[i], waitingTime[i]);
				
                System.out.print("\n");
				System.out.println("--------------------------------------------------------------------------------------");
		}

        System.out.println("\nAverage Turnaround Time : " + averageTurnaround);
		System.out.println("Total Turnaround Time : " + totalTurnaround);

        System.out.println("\nAverage Waiting Time : " + averageWaitingTime);
		System.out.println("Total Waiting Time : " + totalWaitingTime);

        System.out.println("\n");
    }
}
