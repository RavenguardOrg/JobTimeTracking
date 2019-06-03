/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jobtimetracking.repository;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
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
     * Load the profile
     *
     * @param path Path of XML file
     * @param password
     * @return Profile instance
     * @throws JAXBException Parse error
     * @throws java.io.IOException
     * @throws javax.crypto.NoSuchPaddingException
     * @throws java.security.InvalidKeyException
     * @throws java.security.NoSuchAlgorithmException
     * @throws java.security.spec.InvalidKeySpecException
     */
    public static Profile getFromPath(Path path, String password) throws JAXBException, IOException, NoSuchPaddingException, InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException {
        if (path == null) {
            throw new NullPointerException("Path cannot be null.");
        }

        final JAXBContext jc = JAXBContext.newInstance(Profile.class);
        final Unmarshaller u = jc.createUnmarshaller();
        InputStream is = Files.newInputStream(path, StandardOpenOption.READ);
        CipherInputStream cis = new CipherInputStream(is, createSecretKey(password));
        return (Profile) u.unmarshal(cis);
    }

    /**
     * Saves the Profile of the User
     *
     * @param profile
     * @throws JAXBException parse error
     * @throws IOException I/O error
     * @throws java.security.NoSuchAlgorithmException
     * @throws java.security.spec.InvalidKeySpecException
     * @throws javax.crypto.NoSuchPaddingException
     * @throws java.security.InvalidKeyException
     */
    public static void saveToPath(Profile profile) throws JAXBException, IOException,
            NoSuchAlgorithmException, InvalidKeySpecException,
            NoSuchPaddingException, InvalidKeyException {
        if (profile == null) {
            throw new IllegalArgumentException("profile cannot be null.");
        }

        Path path = Paths.get(profile.getUsername() + ".xml");
        final JAXBContext jc = JAXBContext.newInstance(Profile.class);
        final Marshaller m = jc.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        m.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        OutputStream os = Files.newOutputStream(path, StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
        CipherOutputStream cos = new CipherOutputStream(os, createSecretKey(profile.getPassword()));
        m.marshal(profile, cos);
    }

    /**
     *
     * @param password
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     */
    private static Cipher createSecretKey(String password) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException {
        char[] pwd = password.toCharArray();
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
        PBEKeySpec keySpec = new PBEKeySpec(pwd, "TiniSagtMiau".getBytes(), 40000, 128);
        SecretKey keyTmp = keyFactory.generateSecret(keySpec);
        Key key = new SecretKeySpec(keyTmp.getEncoded(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);

        return cipher;
    }
}
