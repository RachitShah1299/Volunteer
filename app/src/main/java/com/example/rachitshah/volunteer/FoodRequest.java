package com.example.rachitshah.volunteer;

class FoodRequest {

    String key,rname,desc,fddate,loc;

/*
    public FoodRequest(String f,String f2,String f3,String f4, String f5 )
    {
        key = f;
        rname = f2;
        desc = f3;
        fddate = f4;
        loc = f5;
    }
*/


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getRname() {
        return rname;
    }

    public void setRname(String rname) {
        this.rname = rname;
    }

    public String getdesc(){return desc;}

    public void setdesc(String desc){ this.desc=desc;}

    public String getFddate(){return fddate;}

    public void setFddate(String fddate){ this.fddate=fddate;}

    public String getAddress(){return loc;}

    public void setAddress(String loc){ this.loc=loc;}

}
