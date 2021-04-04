
package org.openjfx.models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "timetable")
@XmlAccessorType (XmlAccessType.FIELD)
public class TimeTable {
    
    /******************************************/
    private String  name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    /******************************************/
    
    /******************************************/
    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public TimeTable() {
    }
    
    public TimeTable(String name, String data) {
        this.name = name;
        this.data = data;
    }
    /******************************************/
    
    
}
