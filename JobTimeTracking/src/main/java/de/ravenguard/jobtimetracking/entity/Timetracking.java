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

import de.ravenguard.jobtimetracking.entity.Profile;
import de.ravenguard.jobtimetracking.entity.TimeType;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Record for time tracking, containing begin, end and type of time
 *
 * @author Anika.Schmidt
 */
@Entity(name = "TimeTracking")
@Table(name = "time_tracking", schema = "jobtimetracking")
public class Timetracking implements Serializable {

  private static final long serialVersionUID = 1L;

  @Column(name = "begin", nullable = false, columnDefinition = "TIMESTAMP")
  private LocalDateTime begin;
  @Column(name = "end", nullable = false, columnDefinition = "TIMESTAMP")
  private LocalDateTime ende;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false, unique = true)
  private Integer id;
  @ManyToOne(fetch = FetchType.EAGER,
          cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH})
  @JoinColumn(name = "profile_id", nullable = false)
  private Profile profileFk;
  @Enumerated(EnumType.STRING)
  @Column(name = "type")
  private TimeType type;

  public LocalDateTime getBegin() {
    return begin;
  }

  public LocalDateTime getEnde() {
    return ende;
  }

  public Integer getId() {
    return id;
  }

  public Profile getProfileFk() {
    return profileFk;
  }

  public TimeType getType() {
    return type;
  }

  public void setBegin(LocalDateTime begin) {
    this.begin = begin;
  }

  public void setEnde(LocalDateTime ende) {
    this.ende = ende;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public void setProfileFk(Profile profileFk) {
    this.profileFk = profileFk;
    if (!profileFk.getTracking().contains(this)) {
      profileFk.getTracking().add(this);
    }
  }

  public void setType(TimeType type) {
    this.type = type;
  }
}
