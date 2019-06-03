/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jobtimetracking.model;

import java.util.ArrayList;
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

    public double getHoursperweek() {
        return hoursperweek;
    }

    public void setHoursperweek(double hoursperweek) {
        this.hoursperweek = hoursperweek;
    }

    public double getDaysperweek() {
        return daysperweek;
    }

    public void setDaysperweek(double daysperweek) {
        this.daysperweek = daysperweek;
    }

    public double getVacationdays() {
        return vacationdays;
    }

    public void setVacationdays(double vacationdays) {
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

    public ArrayList<Timetracking> getTracking() {
        return tracking;
    }

    public void setTracking(ArrayList<Timetracking> tracking) {
        this.tracking = tracking;
    }
}
