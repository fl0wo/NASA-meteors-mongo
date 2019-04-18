package com.jimscosmos.opengl.pojo;

import com.jimscosmos.opengl.MainApp;
import com.jimscosmos.opengl.pojo.earthquakes.Earthquake;

import java.util.List;

public class ResultEarthQuakeHandler implements ResultHandler<Earthquake> {
    private MainApp main;

    private final int N_INTERESTED_OBJECTS = 100;

    public ResultEarthQuakeHandler(MainApp mainApp) {
        this.main = mainApp;
    }

    @Override
    public void handleResults(List<Earthquake> meteorList) {
        for (Earthquake earthquake : meteorList) {
            main.addEarthQuake(earthquake);
            System.out.println(" c : " + earthquake.getProperties().getTitle());
        }
    }

    @Override
    public void handleError(Throwable t) {
        System.err.println(t.getMessage());
    }

    @Override
    public int getNInterestedObject() {
        return N_INTERESTED_OBJECTS;
    }
}
