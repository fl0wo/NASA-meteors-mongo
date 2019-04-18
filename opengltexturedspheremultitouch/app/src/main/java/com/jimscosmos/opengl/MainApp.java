package com.jimscosmos.opengl;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jimscosmos.opengl.pojo.PojoService;
import com.jimscosmos.opengl.pojo.ResultEarthQuakeHandler;
import com.jimscosmos.opengl.pojo.ResultHandler;
import com.jimscosmos.opengl.pojo.ResultMeteorHandler;
import com.jimscosmos.opengl.pojo.earthquakes.Earthquake;
import com.jimscosmos.opengl.pojo.meteors.Meteor;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.jimscosmos.opengl.pojo.PojoService.BASE_URL;

public class MainApp extends Activity{

    private GLSurfaceView mGlSurfaceView;

    private MotionEvent.PointerCoords point0 = new MotionEvent.PointerCoords();
    private MotionEvent.PointerCoords point1 = new MotionEvent.PointerCoords();

    private Retrofit retrofit;

    private GlRenderer renderer;

    private ResultHandler<Meteor> meteorsHandler;
    private ResultHandler<Earthquake> earthQuakesHandler;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.mGlSurfaceView = new GLSurfaceView(this);
        this.mGlSurfaceView.setEGLConfigChooser(8, 8, 8, 8, 16, 0);

        meteorsHandler = new ResultMeteorHandler(this);
        earthQuakesHandler = new ResultEarthQuakeHandler(this);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        PojoService pojoService = retrofit.create(PojoService.class);

        Observable<List<Meteor>> meteorsObservable = pojoService
                .getMeteorsByLimit(meteorsHandler.getNInterestedObject());

        Observable<List<Earthquake>> earthQuakes = pojoService
                .getAllEarthquakes();

        doSubscribeMongo(meteorsObservable,meteorsHandler);
        //doSubscribeMongo(earthQuakes,earthQuakesHandler);

        renderer = new GlRenderer(this);

        this.mGlSurfaceView.setRenderer(renderer);
        this.setContentView(this.mGlSurfaceView);

        // Handle touch events.
        this.mGlSurfaceView.setOnTouchListener(touchListener(renderer));
    }

    private <L> void doSubscribeMongo(Observable<List<L>> observable, ResultHandler<L> handler) {
        observable
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(handler::handleResults, handler::handleError);
    }

    @NonNull
    private View.OnTouchListener touchListener(final GlRenderer renderer) {
        return (v, event) -> {
            switch (event.getPointerCount()) {
                case 1:
                    switch (event.getActionMasked()) {
                        case MotionEvent.ACTION_DOWN:
                            event.getPointerCoords(0, point0);
                            break;
                        case MotionEvent.ACTION_UP:
                        //    renderer.setUppedZoom();

                       //     renderer.setMovedDelta();
                        case MotionEvent.ACTION_MOVE:
                            event.getPointerCoords(0, point1);
                            renderer.setMoveDelta(point0, point1);
                            break;
                    }
                    break;
                case 2:
                    switch (event.getActionMasked()) {
                        case MotionEvent.ACTION_DOWN:
                            event.getPointerCoords(0, point0);
                            event.getPointerCoords(1, point1);
                           // renderer.startingSetUppingZoom(point0,point1);
                        case MotionEvent.ACTION_UP:
                        case MotionEvent.ACTION_MOVE:
                            event.getPointerCoords(0, point0);
                            event.getPointerCoords(1, point1);
                            renderer.setZoom(point0, point1);
                            break;
                    }
                    break;
            }

            return true;
        };
    }

    /**
     * Remember to resume the glSurface.
     */
    @Override
    protected void onResume() {
        super.onResume();
        this.mGlSurfaceView.onResume();
    }

    /**
     * Also pause the glSurface.
     */
    @Override
    protected void onPause() {
        this.mGlSurfaceView.onPause();
        super.onPause();
    }

    public void addMeteor(Meteor meteor, double max, double min) {
        renderer.addMeteor(meteor,max,min);
    }

    public void toastError(String m) {
        Toast.makeText(this, "ERROR IN FETCHING API RESPONSE. Try again ["+m+"]", Toast.LENGTH_LONG).show();
    }

    public void addEarthQuake(Earthquake earthquake) {
        renderer.addEarthQuake(earthquake);
    }
}
