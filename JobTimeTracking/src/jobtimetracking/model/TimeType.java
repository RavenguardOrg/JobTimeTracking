/*
 * Copyright (C) 2019 Anika Schmidt
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
    ILLNESS(false, false, "Illness"),
    HOLIDAY(true, false, "Holiday"),
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
