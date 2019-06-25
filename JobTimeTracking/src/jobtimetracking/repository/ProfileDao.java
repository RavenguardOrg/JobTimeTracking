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
package jobtimetracking.repository;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
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
     * @throws java.io.UnsupportedEncodingException
     * @throws java.security.NoSuchAlgorithmException
     */
    public static Profile getFromPath(Path path, String password) throws JAXBException, IOException, NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException, InvalidKeyException {
        if (path == null) {
            throw new NullPointerException("Path cannot be null.");
        }

        final JAXBContext jc = JAXBContext.newInstance(Profile.class);
        final Unmarshaller u = jc.createUnmarshaller();
        InputStream is = Files.newInputStream(path, StandardOpenOption.READ);
        try (CipherInputStream cis = new CipherInputStream(is, createDecryptCipher(password))) {
            return (Profile) u.unmarshal(cis);
        }
    }

    /**
     * Saves the Profile of the User
     *
     * @param profile
     * @throws JAXBException parse error
     * @throws IOException I/O error
     * @throws java.security.NoSuchAlgorithmException
     * @throws javax.crypto.NoSuchPaddingException
     * @throws java.io.UnsupportedEncodingException
     * @throws java.security.InvalidKeyException
     */
    public static void saveToPath(Profile profile) throws JAXBException, IOException, NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException, InvalidKeyException {
        if (profile == null) {
            throw new IllegalArgumentException("profile cannot be null.");
        }

        Path path = Paths.get(profile.getUsername()+ ".xml");
        final JAXBContext jc = JAXBContext.newInstance(Profile.class);
        final Marshaller m = jc.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        m.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        OutputStream os = Files.newOutputStream(path, StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
        try (CipherOutputStream cos = new CipherOutputStream(os, createEncryptCipher(profile.getPassword()))) {
            m.marshal(profile, cos);
        }
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
    private static Cipher createEncryptCipher(String password) throws NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException, InvalidKeyException {
        Cipher c = Cipher.getInstance("AES/ECB/PKCS5Padding");
        Key k = createKey(password);
        c.init(Cipher.ENCRYPT_MODE, k);

        return c;
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
    private static Cipher createDecryptCipher(String password) throws NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException, InvalidKeyException {
        Cipher c = Cipher.getInstance("AES/ECB/PKCS5Padding");
        Key k = createKey(password);
        c.init(Cipher.DECRYPT_MODE, k);

        return c;
    }

    /**
     *
     * @param password
     * @return
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    private static Key createKey(String password) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        byte[] key = password.getBytes("UTF-8");
        MessageDigest sha = MessageDigest.getInstance("SHA-1");
        key = sha.digest(key);
        key = Arrays.copyOf(key, 16);
        return new SecretKeySpec(key, "AES");
    }
}
