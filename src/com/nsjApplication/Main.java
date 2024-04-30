package com.nsjApplication;

import com.nsjApplication.athlete.Athlete;
import com.nsjApplication.athlete.NewAthlete;

import java.io.*;
import java.util.*;

public class Main {
    static String FILENAME = "data.txt"; //file store the already existing trainees

    public static void main(String[] args) {
        // make arraylist from data.txt
        ArrayList<String> trainees = loadFromFile();
        System.out.println(trainees);
//      hash map trainingPlanMap is used for creating instances of Athlete and NewAthlete classes
        HashMap<String, Double> trainingPlanMap = new HashMap<>();
        trainingPlanMap.put("Beginner", 25.00);
        trainingPlanMap.put("Intermediate", 30.00);
        trainingPlanMap.put("Elite", 30.00);
//        hash map weightCategoryMap is used for creating instances of Athlete and NewAthlte classes
        HashMap<String, Double> weightCategoryMap = new HashMap<>();
        weightCategoryMap.put("Light-HeavyWeight", 100.00);
        weightCategoryMap.put("MiddleWeight", 90.00);
        weightCategoryMap.put("Light-MiddleWeight", 81.00);
        weightCategoryMap.put("LightWeight", 73.00);
        weightCategoryMap.put("FlyWeight", 66.00);
        Scanner input = new Scanner(System.in);
        String trainingPlan;
        double currentWeight;
        String weightCategory;
        int competitionsEntered;
        int hoursOfPrivateCoachingPerWeek;
        String athleteName;
        System.out.print("Enter athlete name: ");
        athleteName = input.nextLine();
        while (athleteName.isEmpty()){
            System.out.println("The name can't be empty! Enter again");
            System.out.print("Enter athlete name: ");
            athleteName = input.nextLine();
        }
        //true decides whether the athlete is old or new trainee
        Boolean trueValue = checkNameInTraineesList(athleteName.toLowerCase(), trainees);
        if (trueValue) {
            System.out.println(athleteName + " is a trainee of NSJ, continue to fill information to calculate total cost.");
        } else {
            System.out.println(athleteName + " is not a trainee of NSJ, register first.");
            System.out.print("Enter athlete name: ");
            athleteName = input.nextLine();
        }
        displayTrainingPlan();
        trainingPlan = askForTrainingPlan();
        System.out.printf("---------------------------------------------------------------------------%n");
        System.out.printf("                       Private Coaching - $9.00 per hour                   %n");
        System.out.printf("---------------------------------------------------------------------------%n");
        System.out.println("Enter 1 if Athlete takes private coaching or Enter 2 if he or she doesn't");
        int takesOrNot = input.nextInt();
        if (takesOrNot == 1) {
            hoursOfPrivateCoachingPerWeek = askForHoursOfPrivateCoachingPerWeek();
        } else {
            hoursOfPrivateCoachingPerWeek = 0;
        }
        currentWeight = askForCurrentWeight();
        //only when current weight is over 66kg, display weight categories and ask for weight category
        //else set weight category of the athlete as underweight
        if (currentWeight < 66.00) {
            weightCategory = "UnderWeight";
            System.out.println("Athlete " + athleteName + " is underweight.\n" +
                    "Doesn't have any weight category matches to enter the competition!");
        } else {
            displayWeightCategories();
            weightCategory = askForWeightCategory(currentWeight);
        }
        if (trueValue) {
            competitionsEntered = askForCompetitionsEntered(athleteName, trainingPlan, currentWeight);
            //make athlete object to make calculation
            Athlete athlete = new Athlete(athleteName, trainingPlan, currentWeight, weightCategory, competitionsEntered, hoursOfPrivateCoachingPerWeek, trainingPlanMap, weightCategoryMap);
            //display information
            athlete.printTable();
        } else {
            NewAthlete athlete = new NewAthlete(athleteName, trainingPlan, currentWeight, weightCategory, hoursOfPrivateCoachingPerWeek, trainingPlanMap, weightCategoryMap);
            trainees.add(athleteName);
            saveToFile(trainees);
            athlete.printTable();
        }
    }

    /**ask for current weight
     * ask user input again if the input is invalid
     * @return double 66.89, 72.00, etc
     */
    private static double askForCurrentWeight() {
        Scanner input = new Scanner(System.in);
        double currentWeight;
        System.out.print("Enter current weight in kg: ");
        if (!input.hasNextDouble()){
            System.out.println("Invalid Input! Please Enter Number");
            return askForCurrentWeight();
        }
        currentWeight = input.nextDouble();
        return currentWeight;
    }

    /**
     * ask for hours of private coaching per week and validate the input
     * if the input is greater or less than 1
     * display error message
     * recall the function until the input is between 5 and 1 inclusive
     *
     * @return int 1, 2, 3, 4, 5
     */
    private static int askForHoursOfPrivateCoachingPerWeek() {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter hours of private coaching per week (Max is 5 | Min is 1) : ");
        if (!input.hasNextInt()) {
            System.out.println("Invalid input! Please enter number between 5 and 1 inclusive");
            return askForHoursOfPrivateCoachingPerWeek();
        }
        int hours = input.nextInt();
        if (hours > 5 || hours < 1) {
            System.out.println("Maximum is 5 hours and Minimum is 1 hour. Please enter again");
            return askForHoursOfPrivateCoachingPerWeek();
        }
        return hours;
    }

    /**
     * ask for competitions entered and validate the input
     * only athlete at intermediate and elite and current weight > 66 can enter competitions
     * if not display why athlete is disqualified and return 0
     *
     * @param athleteName to display when is unqualified
     * @param trainingPlan to know if the athlete is in which training plan
     * @param currentWeight to know if the athlete is underweight or not
     * @return the number of competitions entered or 0 if the athlete is unqualifiede
     */
    private static int askForCompetitionsEntered(String athleteName, String trainingPlan, double currentWeight) {
        int competitionsEntered;
        Scanner input = new Scanner(System.in);
        if (!(trainingPlan.equals("Beginner") || currentWeight < 66.00)) {
            System.out.printf("-----------------------------------------------------------%n");
            System.out.printf("               CompetitionEntryFee - $22.00                %n");
            System.out.printf("-----------------------------------------------------------%n");
            System.out.print("Enter the number of competitions entered: ");
            if (!input.hasNextInt()){
                System.out.println("Invalid Input! Please enter the number!");
                return askForCompetitionsEntered(athleteName,trainingPlan,currentWeight);
            }
            competitionsEntered = input.nextInt();
        } else {
            System.out.println("Athlete " + athleteName + " is not qualified to enter competition.\n" +
                    "Athlete must be in Elite or Intermediate Level and current weight not less than 66.00kg.");
            System.out.printf("-------------------------------------------------%n");
            System.out.printf("|%-15s|%-15s|%-15s|%n", "Athlete", "Training Plan", "Current Weight");
            System.out.printf("-------------------------------------------------%n");
            System.out.printf("|%-15s|%-15s|%-15s|%n", athleteName, trainingPlan, currentWeight);
            System.out.printf("-------------------------------------------------%n");
            competitionsEntered = 0;
        }

        return competitionsEntered;
    }


    /**
     * ask for weight category and check if it matches the current weight
     * if weight category chosen doesn't match the current weight, the function is called again and again
     * until the weight category is matched with the current weight
     *
     * @param currentWeight
     * @return String ("HeavyWeight") ("Light-HeavyWeight")
     */
    private static String askForWeightCategory(double currentWeight) {
        String weightCategoryReturn = "";
        HashMap<String, Double> weightCategoriesValues = new HashMap<>();
        weightCategoriesValues.put("Light-HeavyWeight", 100.00);
        weightCategoriesValues.put("MiddleWeight", 90.00);
        weightCategoriesValues.put("Light-MiddleWeight", 81.00);
        weightCategoriesValues.put("LightWeight", 73.00);
        weightCategoriesValues.put("FlyWeight", 66.00);
        HashMap<Integer, String> weightCategories = new HashMap<>();
        weightCategories.put(1, "HeavyWeight");
        weightCategories.put(2, "Light-HeavyWeight");
        weightCategories.put(3, "MiddleWeight");
        weightCategories.put(4, "Light-MiddleWeight");
        weightCategories.put(5, "LightWeight");
        weightCategories.put(6, "FlyWeight");
        Scanner input = new Scanner(System.in);
        System.out.println("Enter 1 for HeavyWeight\n" +
                "Enter 2 for Light-HeavyWeight\n" +
                "Enter 3 for MiddleWeight\n" +
                "Enter 4 for Light-MiddleWeight\n" +
                "Enter 5 for LightWeight\n" +
                "Enter 6 for FlyWeight: ");
        if (!input.hasNextInt()){
            System.out.println("Invalid input! Please Enter 1 - 6");
            return askForWeightCategory(currentWeight);
        }
        int inputNum = input.nextInt();
        /* if current weight > 100 and weight category chosen is "HeavyWeight"
         * or current weight == 100 and weight category chosen is "Light-HeavyWeight"
         * @ return "HeavyWeight" or "Light-HeavyWeight"
         */
        if ((currentWeight > 100.00 && inputNum == 1) || (currentWeight == 100.00 && inputNum == 2)) {
            weightCategoryReturn = weightCategories.get(inputNum);
        }
        /* if current weight >= 66 and >73 and weight category chosen is "FlyWeight"
         * @return "FlyWeight
         */
        else if (currentWeight >= 66.00 && currentWeight < 73 && inputNum == 6) {
            weightCategoryReturn = weightCategories.get(inputNum);
        } else {
            /* if weight category chosen is "HeavyWeight" or"Light-HeavyWeight"
             * but current weight is < 100
             * display error message
             * recall the function to choose the weight category that matches current weight
             */
            if (inputNum == 1 || inputNum == 2) {
                System.out.println(inputNum == 1 ? "Athlete current weight should be over 100kg to be in heavy weight category! Please Choose again" :
                        "Athlete current weight should be 100kg to be in Light heavy weight category! Please choose again!");
                return askForWeightCategory(currentWeight);
            } else {
                double upperWeight = weightCategoriesValues.get(weightCategories.get(inputNum - 1));
                double currentWeightCategory = weightCategoriesValues.get(weightCategories.get(inputNum));
                /* if current weight is greater than or equal to weight category chosen
                 * and current weight is less than upper weight category
                 * @return String "MiddleWeight" "Light-MiddleWeight" "LightWeight"
                 */
                if (currentWeight >= currentWeightCategory && currentWeight < upperWeight) {
                    weightCategoryReturn = weightCategories.get(inputNum);
                }
                /* current weight is greater than or equal to upper weight category
                 * or lesser than weight category chosen
                 * display error message
                 * recall the function to choose the weight category that matches current weight
                 */
                else {
                    System.out.println(currentWeight - currentWeightCategory > 0 ?
                            "Athlete current weight is " + String.format("%.2f", currentWeight - currentWeightCategory) + " kg heavier than " + weightCategories.get(inputNum) + " weight category. Please Choose Again."
                            : "Athlete current weight is " + String.format("%.2f", currentWeightCategory - currentWeight) + " kg lower than " + weightCategories.get(inputNum) + " weight category. Please choose again!");
                    return askForWeightCategory(currentWeight);
                }
            }
        }
        return weightCategoryReturn;
    }

    /**
     * display weight category with detail information
     * used linked hash map to display in order of put method
     * format string to display data in a table
     */
    private static void displayWeightCategories() {
        LinkedHashMap<String, String> weightCategories = new LinkedHashMap<>();
        weightCategories.put("HeavyWeight", "Unlimited(over 100)");
        weightCategories.put("Light-HeavyWeight", "100");
        weightCategories.put("MiddleWeight", "90");
        weightCategories.put("Light-MiddleWeight", "81");
        weightCategories.put("LightWeight", "73");
        weightCategories.put("FlyWeight", "66");
        System.out.printf("-------------------------------------------%n");
        System.out.printf("              weight Categories            %n");
        System.out.printf("-------------------------------------------%n");
        for (Map.Entry<String, String> weightCategory : weightCategories.entrySet()) {
            System.out.printf("|%-20s|%-20s|%n", weightCategory.getKey(), weightCategory.getValue());
        }
        System.out.printf("-------------------------------------------%n");
    }

    /**
     * display training plan with detail information
     * used linked hash map to display in order of put method
     * format string to display data in a table
     */
    private static void displayTrainingPlan() {
        LinkedHashMap<String, String> displayTrainingPlan = new LinkedHashMap<>();
        displayTrainingPlan.put("Beginner(2 sessions per week) - weekly fee", "$25.00");
        displayTrainingPlan.put("Intermediate(3 sessions per week) - weekly fee", "$30.00");
        displayTrainingPlan.put("Elite(5 sessions per week) - weekly fee", "$35.00");
        //looping through to display in table format
        System.out.printf("-----------------------------------------------------------%n");
        System.out.printf("                    Training Plan                         %n");
        System.out.printf("-----------------------------------------------------------%n");
        for (Map.Entry<String, String> trainingPlan : displayTrainingPlan.entrySet()) {
            System.out.printf("|%-50s|%-6s|%n", trainingPlan.getKey(), trainingPlan.getValue());
        }
        System.out.printf("-----------------------------------------------------------%n");
    }

    /**
     * ask for training plan and validate the input
     * if the input is not a number, recall function
     * if input is not 1 or 2 or 3, ask for input again
     * @return String ("Beginner") ("Intermediate")
     */
    private static String askForTrainingPlan() {
        HashMap<Integer, String> trainingPlan = new HashMap<>();
        trainingPlan.put(1, "Beginner");
        trainingPlan.put(2, "Intermediate");
        trainingPlan.put(3, "Elite");
        Scanner input = new Scanner(System.in);
        int inputNum;
        do {
            System.out.println("Enter 1 for Beginner\n" +
                    "Enter 2 for Intermediate\n" +
                    "Enter 3 for Elite");
            if (!input.hasNextInt()) {
                System.out.println("Invalid input! Please enter 1 or 2 or 3");
                return askForTrainingPlan();
            }
            inputNum = input.nextInt();
        } while (inputNum != 1 && inputNum != 2 && inputNum != 3);
        return trainingPlan.get(inputNum);
    }

    /**
     * check the name is in trainees list
     *
     * @param athleteName
     * @param trainees
     * @return Boolean
     */
    private static Boolean checkNameInTraineesList(String athleteName, ArrayList<String> trainees) {
        return trainees.contains(athleteName);
    }

    /**
     * load data from data.txt file
     * read data from file line by line and add each to array list
     *
     * @return ArrayList
     */
    private static ArrayList<String> loadFromFile() {
        ArrayList<String> trainnes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {
            String name;
            while ((name = reader.readLine()) != null) {
                trainnes.add(name);
            }
        } catch (IOException e) {
            System.err.println("Error loading from file: " + e.getMessage());
        }
        return trainnes;
    }

    /**
     * write down items of array list of a file
     * write down each item of array list into a file line by line
     *
     * @param trainnes
     */
    private static void saveToFile(ArrayList<String> trainnes) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILENAME))) {
            for (String name : trainnes) {
                writer.write(name);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving to file: " + e.getMessage());
        }
    }
}
