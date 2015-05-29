package group9.fragmentedphoenix;

import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.swedspot.automotiveapi.AutomotiveSignalId;
import android.swedspot.scs.data.SCSFloat;
import android.swedspot.scs.data.SCSShort;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import group9.fragmentedphoenix.observer.AbstractSignalListener;
import group9.fragmentedphoenix.observer.AutomotiveManagerWrapper;

import static group9.fragmentedphoenix.MainActivity.addNewItem;
import static group9.fragmentedphoenix.MainActivity.removeItem;


public class ETS2_Fragment extends Fragment {

    /**
     * ***** LISTENER ******
     */
    private SpeedSignalListener speedSignalListener;
    private GearSignalListener gearSignalListener;
    private RpmSignalListener rpmSignalListener;
    private BrakeSignalListener brakeSignalListener;
    private avgFuelSignalListener fuelSignalListener;

    /**
     * ******** TextViews ********
     */
    private TextView txvSpeed;
    private TextView txvGear;
    private TextView txvRpm;
    //private TextView txvBrake;
    //private TextView txvFuel;

    /**
     * ******** ErrorViews ********
     */
    private TextView txvErrorSpeed;
    private TextView txvErrorRpm;
    private TextView txvErrorGear;
    private TextView txvErrorBrake;
    private TextView txvErrorFuel;

    /**
     * ******** ErrorTables ********
     */
    private LinearLayout errorTable;
    private LinearLayout errorTable2;
    private LinearLayout errorTable3;
    private LinearLayout errorTable4;
    private LinearLayout errorTable5;

    /**
     * ******** GameValues ********
     */
    int engineSpeed;
    int currentGear;
    int speed;
    float brake;
    float avgFuel;

    /**
     * ******** PerformanceValuesForDatabase ********
     */
    int perfectGear = 0;    //If you shift gear within 1000-1500 rpm
    int speedPenalty = 0;   //If you go over the speed limit
    int gearPenalty = 0;    //If you change gear over 1500 rpm
    int brakePenalty = 0;   //If you brake at 1.0 (0.0-1.0)
    int fuelPenalty = 0;    //If you use to much fuel

    int tempGear;
    boolean isBraking = false;
    boolean isSpeeding = false;
    String errorType = "";

    static int JOURNEYID;
    int errorid = 0;

    /**
     * Buttons
     */
    Button start, stop;

    public ETS2_Fragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_current_trip, container, false);

        start = (Button) rootView.findViewById(R.id.start);
        stop = (Button) rootView.findViewById(R.id.stop);

        stop.setVisibility(View.INVISIBLE);         // The stop button is initially invisible

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onResume();
                MainActivity.getJourneyID();
                start.setVisibility(View.INVISIBLE);    // The start button will vanish when pressed.
                stop.setVisibility(View.VISIBLE);       // The stop button will then appear.
                if (MainActivity.fragments.contains(MainActivity.journeyFragment)) {
                    removeItem(MainActivity.fragments.size() - 1);
                }
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPause();
                start.setVisibility(View.VISIBLE);         // When stopped, the start button will re-appear
                stop.setVisibility(View.INVISIBLE);        // The stop button will then vanish.
                addNewItem(MainActivity.journeyFragment);
                MainActivity.mViewPager.setCurrentItem(MainActivity.fragments.size());
            }
        });
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeFragmentViews();
    }

    @Override
    public void onResume() {        // The signals will re-added when a journey is restarted
        super.onStart();
        addSignalListeners();
    }

    @Override
    public void onPause() {         // The signals are stopped when the journey is ended.
        super.onPause();
        removeSignalListeners();
    }

    private void initializeFragmentViews() {
        txvSpeed = (TextView) getActivity().findViewById(R.id.speedValue);
        txvGear = (TextView) getActivity().findViewById(R.id.gearValue);
        txvRpm = (TextView) getActivity().findViewById(R.id.rpmValue);
        //txvBrake = (TextView) getActivity().findViewById(R.id.brakeValue);
        //txvFuel = (TextView) getActivity().findViewById(R.id.avgFuelValue);

        txvErrorRpm = (TextView) getActivity().findViewById(R.id.rpmError);
        txvErrorSpeed = (TextView) getActivity().findViewById(R.id.speedError);
        txvErrorGear = (TextView) getActivity().findViewById(R.id.gearError);
        txvErrorBrake = (TextView) getActivity().findViewById(R.id.brakeError);
      //  txvErrorFuel = (TextView) getActivity().findViewById(R.id.fuelError);

        errorTable = (LinearLayout) getActivity().findViewById(R.id.errorTable);
        errorTable2 = (LinearLayout) getActivity().findViewById(R.id.errorTable2);
        errorTable3 = (LinearLayout) getActivity().findViewById(R.id.errorTable3);
        errorTable4 = (LinearLayout) getActivity().findViewById(R.id.errorTable4);
    //    errorTable5 = (LinearLayout) getActivity().findViewById(R.id.errorTable5);
    }

    private void initSignalListeners() {
        speedSignalListener = new SpeedSignalListener();
        gearSignalListener = new GearSignalListener();
        rpmSignalListener = new RpmSignalListener();
        brakeSignalListener = new BrakeSignalListener();
        fuelSignalListener = new avgFuelSignalListener();
    }

    private void addSignalListeners() {
        final AutomotiveManagerWrapper automotiveManagerWrapper = AutomotiveManagerWrapper.getInstance(this.getActivity().getApplicationContext());
        initSignalListeners();
        automotiveManagerWrapper.addListener(speedSignalListener);
        automotiveManagerWrapper.addListener(gearSignalListener);
        automotiveManagerWrapper.addListener(rpmSignalListener);
        automotiveManagerWrapper.addListener(brakeSignalListener);
        automotiveManagerWrapper.addListener(fuelSignalListener);

    }

    private void removeSignalListeners() {
        final AutomotiveManagerWrapper automotiveManagerWrapper = AutomotiveManagerWrapper.getInstance(this.getActivity().getApplicationContext());
        automotiveManagerWrapper.removeListener(speedSignalListener);
        automotiveManagerWrapper.removeListener(gearSignalListener);
        automotiveManagerWrapper.removeListener(rpmSignalListener);
        automotiveManagerWrapper.removeListener(brakeSignalListener);
        automotiveManagerWrapper.removeListener(fuelSignalListener);
    }

    private void onSpeedChanged(SCSFloat data) {
        speed = (int) data.getFloatValue();
        txvSpeed.setText(String.valueOf(speed));
        speedErrorSQL();
        speedErrorGUI();
    }

    private void onGearChanged(SCSShort data) {
        currentGear = (int) data.getShortValue();
        txvGear.setText(String.valueOf(currentGear));
        gearErrorSQL();
        gearErrorGUI();
    }

    private void onRpmChanged(SCSFloat data) {
        engineSpeed = (int) data.getFloatValue();
        txvRpm.setText(String.valueOf(engineSpeed));

    }

    private void onBrakeChanged(SCSFloat data) {
        brake = data.getFloatValue();
        brakeErrorSQL();
        brakeErrorGUI();
    }

    private void onFuelChanged(SCSFloat data) {
        avgFuel =  data.getFloatValue();


    }

    /**
     * **************** WARNINGS *************************
     */



    private void speedErrorSQL() {
        // An error is only added once per speeding offence (isSpeeding boolean acts as a cooldown)
        if (speed > 80 && !isSpeeding) {
            isSpeeding = true;
            speedPenalty++;
            errorType = "speedError";
            errorid++;
            addError();
        } else if (speed < 80) {    // The boolean is reset when the speed drops below 80
            isSpeeding = false;
        }
    }

    private void speedErrorGUI() {
        // A warning message appears if the player is speeding, and vanishes upon speed <80
        if (speed > 80) {
            txvErrorSpeed.setText("Speed too high!");
            errorTable2.setBackgroundColor(new Color().parseColor("#FFFF3333"));
        } else {
            txvErrorSpeed.setText("");
            errorTable2.setBackgroundColor(new Color().parseColor("#00000000"));
        }
    }

    private void gearErrorSQL() {
        // If there is high RPM upon gear change then an error is added.
        if (engineSpeed > 1500 && currentGear != tempGear && currentGear < 11) {
            errorid++;
            errorType = "Gear up error";
            addError();
            tempGear = currentGear;
        }
    }

    private void gearErrorGUI() {
        // Warning messages for high/low RPM
        if (engineSpeed > 1500 && currentGear < 11) {
            txvErrorGear.setText("Change gear up!");
            errorTable3.setBackgroundColor(new Color().parseColor("#FFFF3333"));
        } else if (engineSpeed < 850) {
            txvErrorGear.setText("Change gear down");
            errorTable3.setBackgroundColor(new Color().parseColor("#FF0000FF"));
        } else {
            txvErrorGear.setText("");
            errorTable3.setBackgroundColor(new Color().parseColor("#00000000"));
        }
    }

    private void brakeErrorSQL() {
        // If user brakes too hard then an error is perfomed and added to the error database.
        if (brake > 0.8 && isBraking == false) {
            isBraking = true;
            brakePenalty++;
            errorType = "brakeError";
            errorid++;
            addError();
        } else if (brake < 0.8) {
            isBraking = false;
        }
    }

    private void brakeErrorGUI() {
        // Warning message is displayed when user is braking too harshly.
        if (brake > 0.8) {
            txvErrorBrake.setText("Braking too harsh!");
            errorTable4.setBackgroundColor(new Color().parseColor("#FFFF3333"));
        } else {
            txvErrorBrake.setText("");
            errorTable4.setBackgroundColor(new Color().parseColor("#00000000"));
        }
    }

    private void fuelError() {
        // Fuel consumption method - no longer in use as the journey duration wasn't long enough
        // to make it a worthwhile feature.
        if (avgFuel > 0.47) {
            txvErrorFuel.setText("Using to much fuel!");
            errorTable5.setBackgroundColor(new Color().parseColor("#FFFF3333"));
            fuelPenalty++;
            errorType = "fuelError";
            errorid = fuelPenalty;
            addError();
        } else {
            txvErrorFuel.setText("");
            errorTable5.setBackgroundColor(new Color().parseColor("#00000000"));
        }
    }

    void addError(){
        // An error is added to the table each time an error is performed.
        MainActivity.db.execSQL("INSERT INTO errors VALUES('" + errorid + "', '" + JOURNEYID + "', '" + errorType + "');");
    }



    /**
     * ***************** Signal Listeners ******************
     */
    class SpeedSignalListener extends AbstractSignalListener<SCSFloat> {

        public SpeedSignalListener() {
            super(ETS2_Fragment.this);
        }

        @Override
        public int getSignalId() {
            return AutomotiveSignalId.FMS_WHEEL_BASED_SPEED;
        }

        @Override
        public void onSignalDeliveredOnUiThread(SCSFloat data) {
            onSpeedChanged(data);
        }
    }

    class GearSignalListener extends AbstractSignalListener<SCSShort> {

        public GearSignalListener() {
            super(ETS2_Fragment.this);
        }

        @Override
        public int getSignalId() {
            return AutomotiveSignalId.FMS_CURRENT_GEAR;
        }

        @Override
        public void onSignalDeliveredOnUiThread(SCSShort data) {
            onGearChanged(data);
        }
    }

    class RpmSignalListener extends AbstractSignalListener<SCSFloat> {

        public RpmSignalListener() {
            super(ETS2_Fragment.this);
        }

        @Override
        public int getSignalId() {
            return AutomotiveSignalId.FMS_ENGINE_SPEED;
        }

        @Override
        public void onSignalDeliveredOnUiThread(SCSFloat data) {
            onRpmChanged(data);
        }
    }

    //CHANGED BRAKE TO FUEL RATE
    class BrakeSignalListener extends AbstractSignalListener<SCSFloat> {

        public BrakeSignalListener() { super(ETS2_Fragment.this); }

        @Override
        public int getSignalId() { return AutomotiveSignalId.FMS_TACHOGRAPH_VEHICLE_SPEED; }

        @Override
        public void onSignalDeliveredOnUiThread(SCSFloat data) { onBrakeChanged(data); }
    }

    class avgFuelSignalListener extends AbstractSignalListener<SCSFloat> {

        public avgFuelSignalListener() { super(ETS2_Fragment.this); }

        @Override
        public int getSignalId() { return AutomotiveSignalId.FMS_FUEL_RATE; }

        @Override
        public void onSignalDeliveredOnUiThread(SCSFloat data) { onFuelChanged(data); }
    }

}
