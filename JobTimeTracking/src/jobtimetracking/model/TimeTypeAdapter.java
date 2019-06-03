/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jobtimetracking.model;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 *
 * @author Anika.Schmidt
 */
public class TimeTypeAdapter extends XmlAdapter<String, TimeType> {

    @Override
    public String marshal(TimeType v) throws Exception {
        if (v == null) {
            return null;
        }
        return v.toString();
    }

    @Override
    public TimeType unmarshal(String v) throws Exception {
        if (v == null) {
            return null;
        }
        return TimeType.valueOf(v);
    }
}

