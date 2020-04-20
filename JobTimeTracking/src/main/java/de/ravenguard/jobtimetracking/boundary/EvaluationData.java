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
package de.ravenguard.jobtimetracking.boundary;

/**
 * Data bean for evaluation standard week
 *
 * @author Anika.Schmidt
 */
public class EvaluationData {

  private double balance;
  private double breaks;
  private double overtime;
  private double own;
  private double quota;

  public double getBalance() {
    return balance;
  }

  public double getBreaks() {
    return breaks;
  }

  public double getOvertime() {
    return overtime;
  }

  public double getOwn() {
    return own;
  }

  public double getQuota() {
    return quota;
  }

  public void setBalance(double balance) {
    this.balance = balance;
  }

  public void setBreaks(double breaks) {
    this.breaks = breaks;
  }

  public void setOvertime(double overtime) {
    this.overtime = overtime;
  }

  public void setOwn(double own) {
    this.own = own;
  }

  public void setQuota(double quota) {
    this.quota = quota;
  }
}
