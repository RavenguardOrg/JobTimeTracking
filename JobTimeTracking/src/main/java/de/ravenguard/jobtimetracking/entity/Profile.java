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
package de.ravenguard.jobtimetracking.entity;

import de.ravenguard.jobtimetracking.entity.Timetracking;
import de.ravenguard.jobtimetracking.control.PasswordConverter;
import java.io.Serializable;
import java.util.ArrayList;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Anika.Schmidt
 */
@Entity(name = "profile")
@NamedQueries({
  @NamedQuery(name = "Profile.login",
          query = "SELECT p FROM profile p WHERE p.username = :username")
})
@Table(name = "profile", schema = "jobtimetracking")
public class Profile implements Serializable {

  private static final long serialVersionUID = 1L;
  @Column(name = "company")
  private String company;
  @Column(name = "days_per_week", nullable = false)
  private double daysperweek;
  @Column(name = "department")
  private String department;
  @Column(name = "first_name", nullable = false)
  private String firstname;
  @Column(name = "hours_per_week", nullable = false)
  private double hoursperweek;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @Column(name = "password")
  @Convert(converter = PasswordConverter.class)
  private String password;
  @Column(name = "second_name")
  private String secondname;
  @Column(name = "surename", nullable = false)
  private String surename;
  @OneToMany(mappedBy = "profileFk", fetch = FetchType.EAGER, orphanRemoval = true,
          cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
  private ArrayList<Timetracking> tracking = new ArrayList<>();
  @Column(nullable = false, unique = true, name = "username")
  private String username;
  @Column(name = "vacation_days", nullable = false)
  private double vacationdays;

  public void addTracking(Timetracking tracking) {
    if (!this.tracking.contains(tracking)) {
      this.tracking.add(tracking);
      tracking.setProfileFk(this);
    }
  }

  public String getCompany() {
    return company;
  }

  public double getDaysPerWeek() {
    return daysperweek;
  }

  public String getDepartment() {
    return department;
  }

  public String getFirstname() {
    return firstname;
  }

  public double getHoursPerWeek() {
    return hoursperweek;
  }

  public Integer getId() {
    return id;
  }

  public String getPassword() {
    return password;
  }

  public String getSecondname() {
    return secondname;
  }

  public String getSurename() {
    return surename;
  }

  public ArrayList<Timetracking> getTracking() {
    return tracking;
  }

  public String getUsername() {
    return username;
  }

  public double getVacationDays() {
    return vacationdays;
  }

  public void setCompany(String company) {
    this.company = company;
  }

  public void setDaysPerWeek(double daysperweek) {
    this.daysperweek = daysperweek;
  }

  public void setDepartment(String department) {
    this.department = department;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public void setHoursPerWeek(double hoursperweek) {
    this.hoursperweek = hoursperweek;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setSecondname(String secondname) {
    this.secondname = secondname;
  }

  public void setSurename(String surename) {
    this.surename = surename;
  }

  public void setTracking(ArrayList<Timetracking> tracking) {
    this.tracking.clear();
    tracking.stream()
            .forEachOrdered((element) -> {
              addTracking(element);
            });
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setVacationDays(double vacationdays) {
    this.vacationdays = vacationdays;
  }
}
