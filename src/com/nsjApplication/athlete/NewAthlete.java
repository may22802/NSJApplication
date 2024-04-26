package com.nsjApplication.athlete;

import java.util.HashMap;

public class NewAthlete extends Athlete {

    public NewAthlete(String athleteName, String trainingPlan, double currentWeight, String weightCategory, int hoursOfPrivateCoachingPerWeek, HashMap<String, Double> trainingPlanMap, HashMap<String, Double> weightCategoryMap) {
        super(athleteName, trainingPlan, currentWeight, weightCategory, 0, hoursOfPrivateCoachingPerWeek, trainingPlanMap, weightCategoryMap);
    }

    @Override
    public void printTable() { //s for formating string data type '-' to justify String + Double becomes string
        System.out.printf("------------------------------------------------------------------------------------------------%n");
        System.out.printf("                                       Athlete Information                                      %n");
        System.out.printf("------------------------------------------------------------------------------------------------%n");
        System.out.printf("|%-43s|%-50s|%n", "AthleteName", this.getAthleteName());
        System.out.printf("|%-43s|%-50s|%n", "TrainingPlan", this.getTrainingPlan());
        System.out.printf("|%-43s|%-50s|%n", "CurrentWeight", this.getCurrentWeight() + "kg");
        System.out.printf("|%-43s|%-50s|%n", "WeightCategory", this.getWeightCategory());
        System.out.printf("|%-43s|%-50s|%n", "HoursOfPrivateTrainingPerWeek", this.getHoursOfPrivateCoachingPerWeek());
        System.out.printf("|%-43s|%-50s|%n", "CurrentWeightCompareToWeightCategory", currentWeightComparedToWeightCategory());
        System.out.printf("------------------------------------------------------------------------------------------------%n");
    }
}
