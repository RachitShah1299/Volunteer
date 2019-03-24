package com.example.rachitshah.volunteer.Recent_Listview;

public class Rec_DataModel {

    String images;
    int image;
    String locs;
    String reqids;
    String dons;
    String dlocs;

    public Rec_DataModel(String image, int i,String loc,String reqid,String don,String dloc)
    {
        this.images = image;
        this.image=i;
        this.locs=loc;
        this.reqids=reqid;
        this.dons=don;
        this.dlocs=dloc;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getLocs() {
        return locs;
    }

    public void setlocs(String locs) {
        this.locs = locs;
    }

    public String getReqids() {
        return reqids;
    }

    public void setReqids(String reqids) {
        this.reqids = reqids;
    }

    public String getDons() {
        return dons;
    }

    public void setDons(String dons) {
        this.dons = dons;
    }

    public String getDlocs() {
        return dlocs;
    }

    public void setDlocs(String dlocs) {
        this.dlocs = dlocs;
    }

}
