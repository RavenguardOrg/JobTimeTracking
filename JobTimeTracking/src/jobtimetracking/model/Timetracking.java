/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jobtimetracking.model;

import java.time.LocalDateTime;

/**
 *
 * @author Anika.Schmidt
 */
public class Timetracking {
    private TimeType type;
    private LocalDateTime begin;
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
