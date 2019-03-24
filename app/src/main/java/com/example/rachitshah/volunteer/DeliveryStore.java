package com.example.rachitshah.volunteer;

class DeliveryStore {
    String key,latitude,longitude,delplace,rname,vname,deldate,delstatus,delrid;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getVname() {
        return vname;
    }

    public void setVname(String vname) {
        this.vname = vname;
    }

    public String getRname() {
        return rname;
    }

    public void setRname(String rname) {
        this.rname = rname;
    }

    public String getDelplace() {
        return delplace;
    }

    public void setDelplace(String delplace) {
        this.delplace = delplace;
    }

    public String getLatitude() {
        return  latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return  longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getDeldate(){return deldate;}

    public void setDeldate(String deldate){ this.deldate=deldate;}

    public String getDelstatus() {
        return delstatus;
    }

    public void setDelstatus(String delstatus) {
        this.delstatus = delstatus;
    }

    public String getDelrid() {
        return delrid;
    }

    public void setDelrid(String delrid) {
        this.delrid = delrid;
    }


}
