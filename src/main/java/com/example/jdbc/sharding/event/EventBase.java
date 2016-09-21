package com.example.jdbc.sharding.event;

import java.io.Serializable;
import java.util.Date;

/**
 * Event Base
 * Created by fangtao on 16/9/5.
 */
public abstract class EventBase implements Serializable {
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
