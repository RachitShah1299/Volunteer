package com.example.rachitshah.volunteer.Vol_Home;

public class Vol_home_datamodel {
    String images;
    int image;
    String locs;
    String reqids;

    public Vol_home_datamodel(String image, int i, String s, String s1) {

        this.images = image;
        this.image=i;
        this.locs=s;
        this.reqids=s1;

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

}
