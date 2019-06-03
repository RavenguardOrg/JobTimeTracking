/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jobtimetracking.model;

import java.time.LocalDateTime;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 *
 * @author Anika.Schmidt
 */
public class LocalDateTimeAdapter extends XmlAdapter<String, LocalDateTime> {

    @Override
    public String marshal(LocalDateTime v) throws Exception {
        if (v == null) {
            return null;
        }
        return v.toString();
    }

    @Override
    public LocalDateTime unmarshal(String v) throws Exception {
        if (v == null) {
            return null;
        }
        return LocalDateTime.parse(v);
    }
}
