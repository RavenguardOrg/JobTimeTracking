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

import de.ravenguard.jobtimetracking.boundary.EvaluationData;
import de.ravenguard.jobtimetracking.boundary.EvaluationService;
import de.ravenguard.jobtimetracking.entity.Profile;
import de.ravenguard.jobtimetracking.entity.TimeType;
import de.ravenguard.jobtimetracking.entity.Timetracking;
import de.ravenguard.jobtimetracking.control.LoginException;
import de.ravenguard.jobtimetracking.control.ProfileDao;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.NoResultException;

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
              && element.getBegin().isBefore(tracking.getEnde())) {
        errors.add("No overlaps in time entries allowed!");
        return errors;
      }
      if (element.getEnde().isAfter(tracking.getBegin())
              && element.getEnde().isBefore(tracking.getEnde())) {
        errors.add("No overlaps in time entries allowed!");
        return errors;
      }
      if (element.getBegin().isBefore(tracking.getBegin())
              && element.getEnde().isAfter(tracking.getEnde())) {
        errors.add("No overlaps in time entries allowed!");
        return errors;
      }
    }
    profile.getTracking().add(element);
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
    record.setEnde(LocalDateTime.now());
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
    record.setEnde(LocalDateTime.now());
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
    Profile returnValue = new Profile();
    returnValue.setCompany(profile.getCompany());
    returnValue.setDaysPerWeek(profile.getDaysPerWeek());
    returnValue.setDepartment(profile.getDepartment());
    returnValue.setFirstname(profile.getFirstname());
    returnValue.setHoursPerWeek(profile.getHoursPerWeek());
    returnValue.setPassword("");
    returnValue.setSecondname(profile.getSecondname());
    returnValue.setSurename(profile.getSurename());
    returnValue.setUsername(profile.getUsername());
    returnValue.setVacationDays(profile.getVacationDays());

    return returnValue;
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
    lastTimeStamp = LocalDateTime.now();
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
    lastTimeStamp = LocalDateTime.now();
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
