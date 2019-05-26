/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jobtimetracking.model;

/**
 *
 * @author Anika.Schmidt
 */
public enum TimeType {
    WORK(false, false, "Work time"),
    BREAK(false, true, "Break"),
    MEETING_OFFICE(false, false, "Meeting"),
    VACATION(true, false, "Vacation"),
    ILLNESS(true, false, "Illness"),
    TRAINING(false, false, "Training"),
    BUSINESS_TRIP(false, false, "Business Trip"),
    BUSINESS_DINNER(false, false, "Business Dinner"),
    SCHOOL_UNIVERSITY(true, false, "School or University"),
    HOMEOFFICE(false, false, "Home Office"),
    WORK_LIFE_BALANCE(false, false, "Work Life Balance");
    
    private boolean completeDay;
    private boolean freeTime;
    private String label;

    private TimeType(boolean completeDay, boolean freeTime, String label) {
        this.completeDay = completeDay;
        this.freeTime = freeTime;
        this.label = label;
    }

    public boolean isCompleteDay() {
        return completeDay;
    }

    public boolean isFreeTime() {
        return freeTime;
    }

    public String getLabel() {
        return label;
    }
}
