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
package de.ravenguard.jobtimetracking.control;

import de.ravenguard.jobtimetracking.entity.Profile;
import de.ravenguard.jobtimetracking.control.LoginException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * @author Anika.Schmidt
 */
public class ProfileDao {

  private EntityManager em;
  private EntityManagerFactory emf = Persistence.createEntityManagerFactory("job-time-tracking");

  public ProfileDao() {
    em = emf.createEntityManager();
  }

  public void close() {
    em.close();
    emf.close();
  }

  /**
   * Deletes the profile.
   *
   * @param profile profile to delete
   */
  public void delete(Profile profile) {
    EntityTransaction tx = em.getTransaction();
    tx.begin();
    em.remove(profile);
    tx.commit();
  }

  /**
   * Load the profile
   *
   * @param userName
   * @param password
   * @return Profile instance
   * @throws LoginException
   */
  public Profile loadProfile(String userName, String password)
          throws LoginException, NoResultException {
    TypedQuery<Profile> query = em.createNamedQuery("Profile.login", Profile.class);
    query.setParameter("username", userName);
    Profile profile = query.getSingleResult();
    if (profile.getPassword().equalsIgnoreCase(password)) {
      return profile;
    } else {
      throw new LoginException();
    }
  }

  /**
   * Saves the Profile of the User
   *
   * @param profile
   */
  public void save(Profile profile) {
    EntityTransaction tx = em.getTransaction();
    tx.begin();
    if (profile.getId() == null) {
      em.persist(profile);
    } else {
      em.merge(profile);
    }
    tx.commit();
  }
}
