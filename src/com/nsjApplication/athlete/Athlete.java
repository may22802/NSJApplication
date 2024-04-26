package com.nsjApplication.athlete;

import java.util.HashMap;

/** athlete class used to store athlete information
 *
 */
public class Athlete {
    private String athleteName;
    private String trainingPlan;
    private double currentWeight;
    private String weightCategory;
    private int competitionsEntered;
    private int hoursOfPrivateCoachingPerWeek;
    HashMap<String, Double> trainingPlanMap = new HashMap<>();
    HashMap<String, Double> weightCategoryMap = new HashMap<>();

    public void setTrainingPlanMap(HashMap<String, Double> trainingPlanMap) {
        this.trainingPlanMap = trainingPlanMap;
    }

    public HashMap<String, Double> getWeightCategoryMap() {
        return weightCategoryMap;
    }

    public void setWeightCategoryMap(HashMap<String, Double> weightCategoryMap) {
        this.weightCategoryMap = weightCategoryMap;
    }


    public HashMap<String, Double> getTrainingPlanMap() {
        return trainingPlanMap;
    }

    public String getAthleteName() {
        return athleteName;
    }

    public void setAthleteName(String athleteName) {
        this.athleteName = athleteName;
    }

    public String getTrainingPlan() {
        return trainingPlan;
    }

    public void setTrainingPlan(String trainingPlan) {
        this.trainingPlan = trainingPlan;
    }

    public double getCurrentWeight() {
        return currentWeight;
    }

    public void setCurrentWeight(double currentWeight) {
        this.currentWeight = currentWeight;
    }

    public String getWeightCategory() {
        return weightCategory;
    }

    public void setWeightCategory(String weightCategory) {
        this.weightCategory = weightCategory;
    }

    public int getCompetitionsEntered() {
        return competitionsEntered;
    }

    public void setCompetitionsEntered(int competitionsEntered) {
        this.competitionsEntered = competitionsEntered;
    }

    public int getHoursOfPrivateCoachingPerWeek() {
        return hoursOfPrivateCoachingPerWeek;
    }

    public void setHoursOfPrivateCoachingPerWeek(int hoursOfPrivateCoachingPerWeek) {
        this.hoursOfPrivateCoachingPerWeek = hoursOfPrivateCoachingPerWeek;
    }

    /**calculate cost of training per month
     * multiply training plan cost per week and 4
     * @return double
     */
    public double calculateTrainingCost() {
        return trainingPlanMap.get(trainingPlan) * 4;
    }

    /**calculate cost of private training per month
     * multiply private coaching hours and 9.00 (cost per hours) and 4
     * @return double
     */
    public double calculatePrivateTrainingCost() {
        return hoursOfPrivateCoachingPerWeek * 9.00 * 4;
    }

    /**calculate total cost per month
     * addition of training plan cost per month and public training cost per month
     * @return double
     */
    public double calculateTotalCost() {
        return calculateTrainingCost() + calculatePrivateTrainingCost();
    }

    /** calculate competitions entry fee
     * multiply competitions entered and 22.00 (competition entry fee per competition)
     * @return double
     */
    public double calculateCompetitionEntryFee() {
        return competitionsEntered * 22.00;
    }

    /** compare current weight to the weight category chosen
     * display --kg heavier or lesser
     * @return String
     */
    public String currentWeightComparedToWeightCategory() {
        if (weightCategory.equals("UnderWeight")) {
            return "Athlete is under weight!" + (weightCategoryMap.get("FlyWeight") - currentWeight) + "kg lower than FlyWeight";
        }
        if (weightCategory.equals("HeavyWeight")) {
            return "HeavyWeight(Over 100kg)|CW is over 100kg.";
        }
        return currentWeight - weightCategoryMap.get(weightCategory) > 0 ?
                String.format("%.2f",currentWeight - weightCategoryMap.get(weightCategory)) + " kg heavier."
                : String.format("%.2f",weightCategoryMap.get(weightCategory) - currentWeight) + " kg lower.";
    }

    public Athlete(String athleteName, String trainingPlan, double currentWeight, String weightCategory, int competitionsEntered, int hoursOfPrivateCoachingPerWeek, HashMap<String, Double> trainingPlanMap, HashMap<String, Double> weightCategoryMap) {
        this.athleteName = athleteName;
        this.trainingPlan = trainingPlan;
        this.currentWeight = currentWeight;
        this.weightCategory = weightCategory;
        this.competitionsEntered = competitionsEntered;
        this.hoursOfPrivateCoachingPerWeek = hoursOfPrivateCoachingPerWeek;
        this.trainingPlanMap = trainingPlanMap;
        this.weightCategoryMap = weightCategoryMap;
    }

    /**display athlete information
     * along with user requirements costs and current weight compare to weight category
     */
    public void printTable() {
        System.out.printf("------------------------------------------------------------------------------------------------%n");
        System.out.printf("                                       Athlete Information                                      %n");
        System.out.printf("------------------------------------------------------------------------------------------------%n");
        System.out.printf("|%-43s|%-50s|%n", "AthleteName", athleteName);
        System.out.printf("|%-43s|%-50s|%n", "TrainingPlan", trainingPlan);
        System.out.printf("|%-43s|%-50s|%n", "CurrentWeight", currentWeight + "kg");
        System.out.printf("|%-43s|%-50s|%n", "WeightCategory", weightCategory);
        System.out.printf("|%-43s|%-50s|%n", "CompetitionsEntered", competitionsEntered);
        System.out.printf("|%-43s|%-50s|%n", "HoursOfPrivateTrainingPerWeek", hoursOfPrivateCoachingPerWeek);
        System.out.printf("|%-43s|%-50s|%n", "TrainingCost", "$" + String.format("%.2f",calculateTrainingCost()));
        System.out.printf("|%-43s|%-50s|%n", "PrivateTrainingCost", "$" + String.format("%.2f",calculatePrivateTrainingCost()));
        System.out.printf("|%-43s|%-50s|%n", "TotalCost", "$" + String.format("%.2f",calculateTotalCost()));
        System.out.printf("|%-43s|%-50s|%n", "CompetitionEntryFee", "$" + String.format("%.2f",calculateCompetitionEntryFee()));
        System.out.printf("|%-43s|%-50s|%n", "CurrentWeightCompareToWeightCategory", currentWeightComparedToWeightCategory());
        System.out.printf("------------------------------------------------------------------------------------------------%n");
    }

}

