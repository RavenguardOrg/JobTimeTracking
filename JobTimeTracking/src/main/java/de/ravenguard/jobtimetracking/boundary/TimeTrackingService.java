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
package de.ravenguard.jobtimetracking.boundary;

import de.ravenguard.jobtimetracking.control.LoginException;
import de.ravenguard.jobtimetracking.control.ProfileDao;
import de.ravenguard.jobtimetracking.entity.Profile;
import de.ravenguard.jobtimetracking.entity.TimeType;
import de.ravenguard.jobtimetracking.entity.Timetracking;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

/**
 *
 * @author Anika.Schmidt
 */
public class TimeTrackingService {

  private ProfileDao dao = new ProfileDao();
  private boolean isBreak;
  private LocalDateTime lastTimeStamp;
  private Profile profile;

  /**
   *
   * @param element
   * @return
   */
  public List<String> addTimeTracking(Timetracking element) {
    List<String> errors = new ArrayList<>();

    List<Timetracking> elements = profile.getTracking();
    for (Timetracking tracking : elements) {
      if (element.getBegin().isAfter(tracking.getBegin())
              && element.getBegin().isBefore(tracking.getEnd())) {
        errors.add("No overlaps in time entries allowed!");
        return errors;
      }
      if (element.getEnd().isAfter(tracking.getBegin())
              && element.getEnd().isBefore(tracking.getEnd())) {
        errors.add("No overlaps in time entries allowed!");
        return errors;
      }
      if (element.getBegin().isBefore(tracking.getBegin())
              && element.getEnd().isAfter(tracking.getEnd())) {
        errors.add("No overlaps in time entries allowed!");
        return errors;
      }
    }
    profile.addTracking(element);
    dao.save(profile);
    return errors;
  }

  public void close() {
    dao.close();
  }

  /**
   *
   */
  public void createTimeTrackingRecord() {
    Timetracking record = new Timetracking();
    record.setBegin(lastTimeStamp);
    record.setEnd(LocalDateTime.now());
    if (isBreak) {
      record.setType(TimeType.BREAK);
    } else {
      record.setType(TimeType.WORK);
    }
    lastTimeStamp = LocalDateTime.now();
    isBreak = !isBreak;
    profile.addTracking(record);
    dao.save(profile);
  }

  /**
   *
   */
  public void endAutomaticTimeTracking() {
    Timetracking record = new Timetracking();
    record.setBegin(lastTimeStamp);
    record.setEnd(LocalDateTime.now());
    if (isBreak) {
      record.setType(TimeType.BREAK);
    } else {
      record.setType(TimeType.WORK);
    }
    profile.addTracking(record);
    dao.save(profile);
  }

  public EvaluationData getEvaluationMonth() {
    EvaluationService evaluationService = new EvaluationService();
    return evaluationService.getEvaluationMonth(profile);
  }

  public EvaluationData getEvaluationYear() {
    EvaluationService evaluationService = new EvaluationService();
    return evaluationService.getEvaluationYear(profile);
  }

  public Profile getProfile() {
    return profile;
  }

  public List<Timetracking> getTrackings() {
    return profile.getTracking();
  }

  public EvaluationData getWeekData() {
    EvaluationService evaluationService = new EvaluationService();
    return evaluationService.getStandardWeek(profile);
  }

  /**
   *
   * param username param password
   *
   * @param username
   * @param password
   * @throws LoginException
   */
  public void login(String username, String password) throws NoResultException, LoginException {
    profile = dao.loadProfile(username, password);
  }

  public void deleteTimeTracking(Timetracking timetracking) throws PersistenceException {
    profile.getTracking().remove(timetracking);
    dao.save(profile);
  }

  public List<String> updateTimeTracking(Timetracking element) {
    List<String> errors = new ArrayList<>();
    int index = profile.getTracking().indexOf(element);
    if (index > -1) {
      List<Timetracking> elements = profile.getTracking();
      for (Timetracking tracking : elements) {
        if (tracking.equals(element)) {
          // Don't compare yourself
          continue;
        }
        if (element.getBegin().isAfter(tracking.getBegin())
                && element.getBegin().isBefore(tracking.getEnd())) {
          errors.add("No overlaps in time entries allowed!");
          return errors;
        }
        if (element.getEnd().isAfter(tracking.getBegin())
                && element.getEnd().isBefore(tracking.getEnd())) {
          errors.add("No overlaps in time entries allowed!");
          return errors;
        }
        if (element.getBegin().isBefore(tracking.getBegin())
                && element.getEnd().isAfter(tracking.getEnd())) {
          errors.add("No overlaps in time entries allowed!");
          return errors;
        }
      }
      Timetracking update = profile.getTracking().get(index);
      update.setBegin(element.getBegin());
      update.setEnd(element.getEnd());
      update.setProfileFk(element.getProfileFk());
      update.setType(element.getType());
      dao.save(profile);
      return new ArrayList<>();
    } else {
      errors.add("No entry in database found");
    }
    return errors;
  }

  /**
   *
   * @param username
   * @param password
   * @param company
   * @param department
   * @param surename
   * @param firstname
   * @param secondname
   * @param hoursperweek
   * @param daysperweek
   * @param vacationdays
   */
  public void register(String username, String password, String company, String department, String surename, String firstname, String secondname,
          double hoursperweek, double daysperweek, double vacationdays) {

    profile = new Profile();
    profile.setCompany(company);
    profile.setDepartment(department);
    profile.setDaysPerWeek(daysperweek);
    profile.setFirstname(firstname);
    profile.setHoursPerWeek(hoursperweek);
    profile.setPassword(password);
    profile.setSecondname(secondname);
    profile.setSurename(surename);
    profile.setUsername(username);
    profile.setVacationDays(vacationdays);
    dao.save(profile);
  }

  public void startAutomaticTimeTracking() {
    lastTimeStamp = LocalDateTime.now();
    isBreak = false;
  }

  /**
   *
   * @param username
   * @param password
   * @param company
   * @param department
   * @param surename
   * @param hoursPerWeek
   * @param daysPerWeek
   * @param vacationDays
   */
  public void updateProfile(String username, String password, String company, String department, String surename, double hoursPerWeek, double daysPerWeek,
          double vacationDays) {
    profile.setCompany(company);
    profile.setDaysPerWeek(daysPerWeek);
    profile.setDepartment(department);
    profile.setHoursPerWeek(hoursPerWeek);
    profile.setPassword(password);
    profile.setSurename(surename);
    profile.setUsername(username);
    profile.setVacationDays(vacationDays);
    dao.save(profile);
  }
}
