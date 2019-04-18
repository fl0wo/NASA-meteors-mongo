package com.jimscosmos.opengl.pojo;

import android.widget.Toast;

import com.jimscosmos.opengl.MainApp;
import com.jimscosmos.opengl.pojo.meteors.Meteor;

import java.util.List;

public class ResultMeteorHandler implements ResultHandler<Meteor> {

    private int N_INTERESTED_METEORS = 100;
    private MainApp main;

    public ResultMeteorHandler(MainApp mainApp) {
        this.main = mainApp;
    }

    public void handleResults(List<Meteor> meteorList) {
        if (meteorList != null && meteorList.size() != 0) {

            double max = -1;
            double min = Integer.MAX_VALUE;

            int tmp = 0;

            for(int i=0;i<Math.min(meteorList.size(),this.N_INTERESTED_METEORS);i++) {
                System.out.println("meteorite : " +meteorList.get(i));

                String s = meteorList.get(i).getMass();
                if(s==null)continue;
                if(!s.matches("-?\\d+(\\.\\d+)?"))continue;

                double value = Double.parseDouble(s);

                if(max<value){max = value;tmp = i;}
                if(value<min)min = value;

            }

            System.out.println("massimo :" +meteorList.get(tmp));


            for(int i=0;i<Math.min(meteorList.size(),this.N_INTERESTED_METEORS);i++) {
                main.addMeteor(meteorList.get(i),max,min);
            }

        }
    }


    public void handleError(Throwable t) {
        String m = t.getMessage();
        main.toastError(m);
    }

    @Override
    public int getNInterestedObject() {
        return this.N_INTERESTED_METEORS;
    }
}
