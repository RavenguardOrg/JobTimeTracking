/*
 * Copyright (C) 2019 Anika.Schmidt
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
package de.ravenguard.jobtimetracking.logic;

import de.ravenguard.jobtimetracking.model.Profile;
import de.ravenguard.jobtimetracking.model.TimeType;
import de.ravenguard.jobtimetracking.model.Timetracking;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Anika.Schmidt
 */
public class EvaluationService {

  /**
   * getStandardWeek
   *
   * @param profile
   * @return
   */
  public EvaluationData getStandardWeek(Profile profile) {
    LocalDateTime firstDayOfWeek = LocalDate.now().with(DayOfWeek.MONDAY).atStartOfDay();

    int weekOfYear = LocalDate.now().get(WeekFields.ISO.weekOfYear());
    // using week based year (for example: 30.12.2019 - 2020 Week 1)
    int currentYear = LocalDate.now().get(WeekFields.ISO.weekBasedYear());
    // filter List for data before current week
    List<Timetracking> beforeList = profile.getTracking().stream()
            .filter(element
                    -> element.getBegin().isBefore(firstDayOfWeek))
            .collect(Collectors.toList());
    // get current week
    List<Timetracking> currentWeek = profile.getTracking().stream()
            .filter(element
                    -> element.getBegin().get(WeekFields.ISO.weekOfYear()) == weekOfYear
            && element.getBegin().get(WeekFields.ISO.weekBasedYear()) == currentYear)
            .collect(Collectors.toList());

    // evaluate both lists
    EvaluationData before = calculateValues(beforeList, profile);
    EvaluationData current = calculateValues(currentWeek, profile);

    // calculate total overtime
    current.setOvertime(current.getOvertime() + before.getOvertime());

    return current;
  }

  /**
   * calculateValues
   *
   * @param profile
   * @return
   */
  private EvaluationData calculateValues(List<Timetracking> currentWeek, Profile profile) {
    // calculate hours per day
    double hoursPerDay = profile.getHoursPerWeek() / profile.getDaysPerWeek();
    Duration durationPerDay = Duration.of((long) (hoursPerDay * 60), ChronoUnit.MINUTES);

    // count Weeks
    long countWeeks = currentWeek.stream()
            // map to identifier for week and year
            .map(element -> element.getBegin().get(WeekFields.ISO.weekOfYear()) + "_" + element.getBegin().get(WeekFields.ISO.weekBasedYear()))
            .distinct()
            .count();

    // count all holidays
    long countHolidays = currentWeek.stream()
            .filter(element -> element.getType() == TimeType.HOLIDAY)
            .count();

    // calculate time to work
    double timeToWork = countWeeks * profile.getHoursPerWeek() - countHolidays * hoursPerDay;
    // sum up all break times
    double sumBreaks = currentWeek.stream()
            // filter: Only Break
            .filter(element -> element.getType() == TimeType.BREAK)
            // map: Duration
            .map(element -> Duration.between(element.getBegin(), element.getEnde()))
            // sum: Duration to double hours and sum up
            .collect(Collectors.summingDouble(element -> {
              long hours = element.toHours();
              double fraction = (element.toMinutes() - (hours * 60)) / 60.0;
              return hours + fraction;
            }));

    // sum up all work time
    double sumWorkTime = currentWeek.stream()
            // filter: Only Work Times
            .filter(element -> element.getType() != TimeType.BREAK
            && element.getType() != TimeType.WORK_LIFE_BALANCE
            && element.getType() != TimeType.HOLIDAY)
            // map: difference Duration or hours per Day
            .map(element -> {
              if (!element.getType().isCompleteDay()) {
                return Duration.between(element.getBegin(), element.getEnde());
              } else {
                return durationPerDay;
              }
            })
            // sum: Duration to double hours and sum up
            .collect(Collectors.summingDouble(element -> {
              long hours = element.toHours();
              double fraction = (element.toMinutes() - (hours * 60)) / 60.0;
              return hours + fraction;
            }));

    // Set return values
    EvaluationData data = new EvaluationData();
    data.setQuota(timeToWork);
    data.setBreaks(sumBreaks);
    data.setOwn(sumWorkTime);
    data.setBalance(timeToWork - sumWorkTime);
    if (sumWorkTime > timeToWork) {
      data.setOvertime(sumWorkTime - timeToWork);
    }

    return data;
  }

  /**
   * getEvaluationMonth
   *
   * @param profile
   * @return
   */
  public EvaluationData getEvaluationMonth(Profile profile) {
    LocalDateTime firstDayOfMonth = LocalDate.now().with(ChronoField.DAY_OF_MONTH, 1).atStartOfDay();

    Month month = LocalDate.now().getMonth();
    int currentYear = LocalDate.now().getYear();
    // filter List for data before current month
    List<Timetracking> beforeList = profile.getTracking().stream()
            .filter(element
                    -> element.getBegin().isBefore(firstDayOfMonth))
            .collect(Collectors.toList());
    // get current month
    List<Timetracking> currentWeek = profile.getTracking().stream()
            .filter(element
                    -> element.getBegin().getMonth() == month
            && element.getBegin().getYear() == currentYear)
            .collect(Collectors.toList());

    // evaluate both lists
    EvaluationData before = calculateValues(beforeList, profile);
    EvaluationData current = calculateValues(currentWeek, profile);

    // calculate total overtime
    current.setOvertime(current.getOvertime() + before.getOvertime());

    return current;
  }

  /**
   * getEvaluationYear
   *
   * @param profile return
   */
  public EvaluationData getEvaluationYear(Profile profile) {
    LocalDateTime firstDayOfYear = LocalDate.now()
            .with(ChronoField.DAY_OF_MONTH, 1)
            .with(ChronoField.MONTH_OF_YEAR, 1)
            .atStartOfDay();

    int currentYear = LocalDate.now().getYear();
    // filter List for data before current month
    List<Timetracking> beforeList = profile.getTracking().stream()
            .filter(element
                    -> element.getBegin().isBefore(firstDayOfYear))
            .collect(Collectors.toList());
    // get current month
    List<Timetracking> currentWeek = profile.getTracking().stream()
            .filter(element
                    -> element.getBegin().getYear() == currentYear)
            .collect(Collectors.toList());

    // evaluate both lists
    EvaluationData before = calculateValues(beforeList, profile);
    EvaluationData current = calculateValues(currentWeek, profile);

    // calculate total overtime
    current.setOvertime(current.getOvertime() + before.getOvertime());

    return current;
  }
}
