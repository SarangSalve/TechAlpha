package Projects;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class OnlineVotingSystem {

    private Map<String, Integer> candidates;

    public OnlineVotingSystem() {
    	
    	System.out.println("******WELCOME TO SHIRUR GENERAL ELECTION*******");
    	
        candidates = new HashMap<>();
      
        candidates.put("Amol Ramsing Kolhe NCP(SP)", 0);
        candidates.put("Shivajiroa Adhalarao Patil NCP", 0);
        candidates.put("Adv.Swapnil Shelar", 0);
    }

    public void displayCandidates() {
    	System.out.println();
        System.out.println("**** Candidates:- ****");
        for (String candidate : candidates.keySet()) {
            System.out.println(candidate);
        }
    }

    public void vote(String candidateName) {
        if (candidates.containsKey(candidateName)) {
            candidates.put(candidateName, candidates.get(candidateName) + 1);
            System.out.println();
            System.out.println("***** Thank you for voting! *****");
        } else {
            System.out.println("Invalid candidate name. Please vote for one of the listed candidates.");
        }
    }

    public void displayTotalVotes() {
        System.out.println("******Voting Results:*********");
        System.out.println();
        for (Map.Entry<String, Integer> entry : candidates.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " votes");
        }
    }
    
    public void displayWinner() {
        String winner = null;
        int maxVotes = -1;

        for (Map.Entry<String, Integer> entry : candidates.entrySet()) {
            if (entry.getValue() > maxVotes) {
                maxVotes = entry.getValue();
                winner = entry.getKey();
            }
        }

        if (winner != null) {
            System.out.println("****** WINNER OF GENERAL ELECTION *********");
            System.out.println(winner + " with " + maxVotes + " votes");
        } else {
            System.out.println("No votes have been cast.");
        }
    }

    public static void main(String[] args) {
        OnlineVotingSystem votingSystem = new OnlineVotingSystem();
        Scanner scanner = new Scanner(System.in);
        boolean votingActive = true;

        while (votingActive) {
            System.out.println("\n1. Display Candidates\n2. Vote\n3. Display Total Votes \n4. WINNER OF GENERAL ELECTION \n5.exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    votingSystem.displayCandidates();
                    break;
                case 2:
                    System.out.print("Enter the name of the candidate you want to vote for: ");
                    String candidateName = scanner.nextLine();
                    votingSystem.vote(candidateName);
                    break;
                case 3:
                    votingSystem.displayTotalVotes();
                    break;
                case 4:
                    votingSystem.displayWinner();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    votingActive = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 4.");
            }
        }

        scanner.close();
    }
}