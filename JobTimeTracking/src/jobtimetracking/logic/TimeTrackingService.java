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
package jobtimetracking.logic;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import javax.crypto.NoSuchPaddingException;
import javax.xml.bind.JAXBException;
import jobtimetracking.model.Profile;
import jobtimetracking.repository.ProfileDao;

/**
 *
 * @author Anika.Schmidt
 */
public class TimeTrackingService {

    private Profile profile;

    public List<String> login(String username, String password) {
        List<String> errors = new ArrayList<>();
        Path userFile = Paths.get(username + ".xml");
        if (Files.exists(userFile)) {
            try {
                profile = ProfileDao.getFromPath(userFile, password);
            } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException ex) {
                errors.add("Your passsword is incorrect!");
            } catch (JAXBException ex) {
                errors.add("Wrong file format!");
            } catch (IOException ex) {
                errors.add("Error reading file!");
            }
        } else {
            errors.add("Username unknown or no file found!");
        }

        return errors;
    }

    public List<String> register(String username, String password, String company, String department, String surename, String firstname, String secondname,
            double hoursperweek, double daysperweek, double vacationdays) {

        List<String> errors = new ArrayList<>();
        profile = new Profile();
        profile.setCompany(company);
        profile.setDepartment(department);
        profile.setDaysperweek(daysperweek);
        profile.setFirstname(firstname);
        profile.setHoursperweek(hoursperweek);
        profile.setPassword(password);
        profile.setSecondname(secondname);
        profile.setSurename(surename);
        profile.setUsername(username);
        profile.setVacationdays(vacationdays);
        try {
            ProfileDao.saveToPath(profile);
        } catch (JAXBException ex) {
            errors.add("Error saving your profile!");
        } catch (IOException ex) {
            errors.add("Can't write file!");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException ex) {
            errors.add("Can't encrypt file please choose another password!");
            ex.printStackTrace();
        }
        return errors;
    }
}
