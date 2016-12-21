package com.gramazski.seaport.actioner.uploader;

import com.gramazski.seaport.entity.port.building.Berth;

/**
 * Created by gs on 21.12.2016.
 */
public class BerthUploader extends Thread {
    private Berth berth;

    @Override
    public void run(){

    }

    public BerthUploader(Berth berth){
        this.berth = berth;
    }

    public Berth getBerth() {
        return berth;
    }

    public void setBerth(Berth berth) {
        this.berth = berth;
    }

    public void startUpload(){

    }
}
