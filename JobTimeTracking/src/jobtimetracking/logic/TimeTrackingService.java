/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
