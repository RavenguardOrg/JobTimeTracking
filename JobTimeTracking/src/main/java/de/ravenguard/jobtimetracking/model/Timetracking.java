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

import de.ravenguard.jobtimetracking.repository.LocalDateTimeAdapter;
import de.ravenguard.jobtimetracking.repository.TimeTypeAdapter;
import java.time.LocalDateTime;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Record for time tracking, containing begin, end and type of time
 *
 * @author Anika.Schmidt
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Timetracking {

  @XmlJavaTypeAdapter(value = TimeTypeAdapter.class)
  private TimeType type;
  @XmlJavaTypeAdapter(value = LocalDateTimeAdapter.class)
  private LocalDateTime begin;
  @XmlJavaTypeAdapter(value = LocalDateTimeAdapter.class)
  private LocalDateTime ende;

  public TimeType getType() {
    return type;
  }

  public void setType(TimeType type) {
    this.type = type;
  }

  public LocalDateTime getBegin() {
    return begin;
  }

  public void setBegin(LocalDateTime begin) {
    this.begin = begin;
  }

  public LocalDateTime getEnde() {
    return ende;
  }

  public void setEnde(LocalDateTime ende) {
    this.ende = ende;
  }
}
