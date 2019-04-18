package com.jimscosmos.opengl;

import android.content.Context;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;
import android.view.MotionEvent;

import com.jimcosmos.opengl.R;
import com.jimscosmos.opengl.pojo.earthquakes.EarthQuakeCoordinate;
import com.jimscosmos.opengl.pojo.earthquakes.Earthquake;
import com.jimscosmos.opengl.pojo.earthquakes.Geometry;
import com.jimscosmos.opengl.pojo.meteors.Coordinate;
import com.jimscosmos.opengl.pojo.meteors.Geolocation;
import com.jimscosmos.opengl.pojo.meteors.Meteor;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


class GlRenderer implements Renderer {
    /** Factors to rotate sphere. */
    private static final float FACTOR_ROTATION_LONGITUDE = 150.0f;
    private static final float FACTOR_ROTATION_LATITUDE = 150.0f;

    /** Factors to scale sphere. */
    private static final float FACTOR_HYPOT = 500.0f;
    private static final float SPHERE_SCALE_MAX = 10.0f;
    private static final float SPHERE_SCALE_MIN = 0.5f;

    /** Object distance on the screen. */
    private static final float OBJECT_DISTANCE = -5.0f;

    /** Clear colour, alpha component. */
    private static final float CLEAR_RED = 0.0f;

    /** Clear colour, alpha component. */
    private static final float CLEAR_GREEN = 0.0f;

    /** Clear colour, alpha component. */
    private static final float CLEAR_BLUE = 0.0f;

    /** Clear colour, alpha component. */
    private static final float CLEAR_ALPHA = 0.5f;

    /** Perspective setup, field of view component. */
    private static final float FIELD_OF_VIEW_Y = 100.0f;

    /** Perspective setup, near component. */
    private static final float Z_NEAR = 0.1f;

    /** Perspective setup, far component. */
    private static final float Z_FAR = 100.0f;

    /** The earth's sphere. */
    private final Sphere mEarth;

    private final List<Sphere> spheres = new ArrayList<>();

    /** The context. */
    private final Context mContext;

    /** The spheres rotation angle. */
    private float mRotationAngle;

    /** The spheres tilt angle. */
    private float mAxialTiltAngle;

    /** Scaling of the sphere. */
    private float mSphereScale = 1.0f,lastSphereScale = 0.0f;

    private int nOfNewSpheres = 0;
    /**
     * Change the rotation angles for the sphere.
     *
     * @param pointA Start point.
     * @param pointB End point.
     */
    public void setMoveDelta(MotionEvent.PointerCoords pointA, MotionEvent.PointerCoords pointB) {
        mRotationAngle -= ((pointA.x - pointB.x) / FACTOR_ROTATION_LONGITUDE);
        mAxialTiltAngle -=((pointA.y - pointB.y) / FACTOR_ROTATION_LATITUDE);

    }

    /**
     * Change the rotation angles for the sphere and its position (zoom).
     *
     * @param coordinate0 The first finger coordinate.
     * @param coordinate1 The second finger coordinate.
     */
    public void setZoom(MotionEvent.PointerCoords coordinate0, MotionEvent.PointerCoords coordinate1) {
        float hypotenuse = (float) Maths.hypotenuse(coordinate0, coordinate1);

        if (hypotenuse > 0.0f) {
            mSphereScale = lastSphereScale + hypotenuse/ FACTOR_HYPOT;

            if (mSphereScale > SPHERE_SCALE_MAX) {
                mSphereScale = SPHERE_SCALE_MAX;
            } else if (mSphereScale < SPHERE_SCALE_MIN) {
                mSphereScale = SPHERE_SCALE_MIN;
            }
        }
    }


    /**
     * Constructor to set the handed over context.
     * @param context The context.
     */
    public GlRenderer(final Context context) {
        this.mContext = context;
        this.mEarth = new Sphere(6, 2f,0,0,0);

          //  this.spheres.add(randomMeteor(2f,0,0,0));

        this.mRotationAngle = 0.0f;
        this.mAxialTiltAngle = 0.0f;
    }

    private Sphere randomMeteor(float radius,float x0,float y0,float z0) {

        // meteoriti grossi in base alla massa del meteorite

        double u = Math.random();
        double v = Math.random();
        double theta = 2 * Math.PI * u;
        double phi = Math.acos(2 * v - 1);
        float x = (float) (x0 + (radius * Math.sin(phi) * Math.cos(theta)));
        float y = (float) (y0 + (radius * Math.sin(phi) * Math.sin(theta)));
        float z = (float) (z0 + (radius * Math.cos(phi)));

        return new Sphere(3, (float) (Math.random()*0.05f),x ,y ,z );
    }

    @Override
    public void onDrawFrame(final GL10 gl) {
        gl.glClearColor(0f/255f, 0f/255f, 0f/255F, 0.0f);
        //rgb(10, 61, 98)
        //rgb(34, 47, 62)
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();
        gl.glTranslatef(0.0f, 0.0f, OBJECT_DISTANCE);
        gl.glScalef(mSphereScale, mSphereScale, mSphereScale);
        gl.glRotatef(this.mAxialTiltAngle, 1, 0, 0);
        gl.glRotatef(this.mRotationAngle, 0, 1, 0);

        this.mEarth.draw(gl);
//nOfNewSpheres
        for (; nOfNewSpheres >0; nOfNewSpheres--){
             spheres.get(spheres.size()- nOfNewSpheres).loadGLTexture(gl,this.mContext,R.drawable.icon1);
        }

        for (int i = 0; i< spheres.size(); i++){
            spheres.get(i).draw(gl);
        }


    }

    @Override
    public void onSurfaceChanged(final GL10 gl, final int width, final int height) {
        final float aspectRatio = (float) width / (float) (height == 0 ? 1 : height);

        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        GLU.gluPerspective(gl, FIELD_OF_VIEW_Y, aspectRatio, Z_NEAR, Z_FAR);
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    @Override
    public void onSurfaceCreated(final GL10 gl, final EGLConfig config) {
        this.mEarth.loadGLTexture(gl, this.mContext, R.drawable.earth_8k);

        for (int i = 0; i< spheres.size(); i++){
            spheres.get(i).loadGLTexture(gl,this.mContext,R.drawable.icon1);
        }

        gl.glEnable(GL10.GL_TEXTURE_2D);
        gl.glShadeModel(GL10.GL_SMOOTH);
        gl.glClearColor(CLEAR_RED, CLEAR_GREEN, CLEAR_BLUE, CLEAR_ALPHA);
        gl.glClearDepthf(1.0f);
        gl.glEnable(GL10.GL_DEPTH_TEST);
        gl.glDepthFunc(GL10.GL_LEQUAL);
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
    }

    public void addMeteor(Meteor meteor,double max,double min) {
        Sphere s = genSphereByMeteor(meteor,2f,max,min);
        if(s!=null){
            spheres.add(s);
            nOfNewSpheres++;
        }
    }

    public void addEarthQuake(Earthquake earthquake) {
        System.out.println("easy 1");
        Sphere s = genSPhereByEarthQuake(earthquake,2f);
        if(s!=null){
            System.out.println("easy ");
            spheres.add(s);
            nOfNewSpheres++;
        }
    }

    private Sphere genSphereByMeteor(Meteor meteor,float radius,double maxMass,double minMass) {

        Geolocation g = meteor.getGeolocation();
        if(g==null)return null;
        List<Coordinate> c = g.getCoordinates();
        if(c==null || c.size()!=2)return null;

        String strLat = c.get(0).get$numberDouble();
        String strLon = c.get(1).get$numberDouble();

        String mass = meteor.getMass();

        if(strLat!=null && strLon!=null && mass!=null)
        if(strLat.matches("-?\\d+(\\.\\d+)?") && strLon.matches("-?\\d+(\\.\\d+)?")
        && mass.matches("-?\\d+(\\.\\d+)?")){
            float lat = Float.parseFloat(strLat);
            float lon = Float.parseFloat(strLon);
            float mass_ = Float.parseFloat(mass);

            float x = (float) (radius * Math.cos(lat) * Math.cos(lon));
            float y = (float) (radius * Math.cos(lat) * Math.sin(lon));
            float z = (float) (radius * Math.sin(lat));

            // 23000000 = .5 : mass = ris

            return new Sphere(1, (float) (.025f + mass_*.2f / (maxMass-minMass)),x ,y ,z);
        }

        return null;
    }

    private Sphere genSPhereByEarthQuake(Earthquake earthquake,float radius) {

        Geometry g = earthquake.getGeometry();
        if(g==null)return null;
        List<EarthQuakeCoordinate> c  = g.getCoordinates();
        if(c==null || c.size()!=3)return null;

        String strLat = c.get(0).get$numberDouble();
        String strLon = c.get(1).get$numberDouble();
        String strAlt = c.get(2).get$numberInt();

        String magnitudo = earthquake.getProperties().getMag().get$numberDouble();

        if(strLat!=null && strLon!=null && magnitudo!=null)
            if(strLat.matches("-?\\d+(\\.\\d+)?") && strLon.matches("-?\\d+(\\.\\d+)?")
                    && magnitudo.matches("-?\\d+(\\.\\d+)?")){
                float lat = Float.parseFloat(strLat);
                float lon = Float.parseFloat(strLon);
                float mag = Float.parseFloat(magnitudo);

                float myRay = (mag / 20f) + 1;

                float x = (float) ( (radius - myRay) * Math.cos(lat) * Math.cos(lon));
                float y = (float) ((radius - myRay) * Math.cos(lat) * Math.sin(lon));
                float z = (float) ((radius - myRay) * Math.sin(lat));

                // 23000000 = .5 : mass = ris

                return new Sphere(3,myRay,x ,y ,z);
            }

        return null;
    }


}