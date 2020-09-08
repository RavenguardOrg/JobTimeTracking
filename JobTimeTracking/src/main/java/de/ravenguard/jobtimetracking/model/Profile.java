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
package de.ravenguard.jobtimetracking.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Anika.Schmidt
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Profile {

  private String surename;
  private String firstname;
  private String secondname;
  private String company;
  private String department;
  private String username;
  private String password;
  private double hoursperweek;
  private double daysperweek;
  private double vacationdays;
  @XmlElementWrapper(name = "timetrackings")
  @XmlElement(name = "timetracking")
  private ArrayList<Timetracking> tracking = new ArrayList<>();

  public double getHoursPerWeek() {
    return hoursperweek;
  }

  public void setHoursPerWeek(double hoursperweek) {
    this.hoursperweek = hoursperweek;
  }

  public double getDaysPerWeek() {
    return daysperweek;
  }

  public void setDaysPerWeek(double daysperweek) {
    this.daysperweek = daysperweek;
  }

  public double getVacationDays() {
    return vacationdays;
  }

  public void setVacationDays(double vacationdays) {
    this.vacationdays = vacationdays;
  }

  public String getSurename() {
    return surename;
  }

  public void setSurename(String surename) {
    this.surename = surename;
  }

  public String getFirstname() {
    return firstname;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public String getSecondname() {
    return secondname;
  }

  public void setSecondname(String secondname) {
    this.secondname = secondname;
  }

  public String getCompany() {
    return company;
  }

  public void setCompany(String company) {
    this.company = company;
  }

  public String getDepartment() {
    return department;
  }

  public void setDepartment(String department) {
    this.department = department;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public List<Timetracking> getTracking() {
    return tracking;
  }

  public void setTracking(List<Timetracking> tracking) {
    this.tracking.clear();
    if (tracking != null) {
      this.tracking.addAll(tracking);
    }
  }
}
