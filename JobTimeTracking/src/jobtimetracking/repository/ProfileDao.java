/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jobtimetracking.repository;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import jobtimetracking.model.Profile;

/**
 *
 * @author Anika.Schmidt
 */
public class ProfileDao {

    /**
     * Deletes the file at the path.
     *
     * @param path path to file
     * @throws IOException I/O error
     */
    public static void deletePath(Path path) throws IOException {
        if (path == null) {
            throw new NullPointerException("Path cannot be null.");
        }

        Files.deleteIfExists(path);
    }

    /**
     * Loads the Collection of legal holidays.
     *
     * @param path Path of XML file
     * @return Trainee instance
     * @throws JAXBException Parse error
     */
    public static Profile getFromPath(Path path) throws JAXBException {
        if (path == null) {
            throw new NullPointerException("Path cannot be null.");
        }

        final JAXBContext jc = JAXBContext.newInstance(Profile.class);
        final Unmarshaller u = jc.createUnmarshaller();
        return (Profile) u.unmarshal(path.toFile());
    }

    /**
     * Saves a Collection of legal holidays to the path.
     *
     * @param profile
     * @throws JAXBException parse error
     * @throws IOException I/O error
     */
    public static void saveToPath(Profile profile) throws JAXBException, IOException {
        if (profile == null) {
            throw new IllegalArgumentException("profile cannot be null.");
        }

        Path path = Paths.get(profile.getUsername()+ ".xml");
        final JAXBContext jc = JAXBContext.newInstance(Profile.class);
        final Marshaller m = jc.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        m.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        final OutputStream os = Files.newOutputStream(path, StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);

        m.marshal(profile, os);
    }
}
