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
package de.ravenguard.jobtimetracking.repository;

import de.ravenguard.jobtimetracking.model.TimeType;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 *
 * @author Anika.Schmidt
 */
public class TimeTypeAdapter extends XmlAdapter<String, TimeType> {

  /**
   * marshal String
   *
   * @param v
   * @return
   * @throws Exception
   */
  @Override
  public String marshal(TimeType v) throws Exception {
    if (v == null) {
      return null;
    }
    return v.toString();
  }

  /**
   * unmarshal TimeType
   *
   * @param v
   * @return
   * @throws Exception
   */
  @Override
  public TimeType unmarshal(String v) throws Exception {
    if (v == null) {
      return null;
    }
    return TimeType.valueOf(v);
  }
}
