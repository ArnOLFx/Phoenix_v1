package group9.fragmentedphoenix;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;


import group9.fragmentedphoenix.dummy.DummyContent;

// Class that creates the fragment to display error information once the journey has finished.
public class JourneyFragment extends Fragment {

    // TextViews for number of gear, brake, speed and total combined errors.
    TextView gearErrorsResult;
    TextView brakeErrorsResult;
    TextView speedErrorsResult;
    TextView totalErrorsResult;


    public JourneyFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View journeyview = inflater.inflate(R.layout.fragment_journey, container, false);
        gearErrorsResult = (TextView) journeyview.findViewById(R.id.GearErrorsResult);
        brakeErrorsResult = (TextView) journeyview.findViewById(R.id.BrakeErrorsResult);
        speedErrorsResult = (TextView) journeyview.findViewById(R.id.SpeedErrorsResult);
        totalErrorsResult = (TextView) journeyview.findViewById(R.id.TotalErrorsResult);
        listErrors();           // Method that will retrieve the error counts from the database.
        return journeyview;
    }

    public void listErrors() {
        Cursor gearSQL = MainActivity.db.rawQuery("SELECT COUNT(*) FROM errors WHERE ErrorType = 'Gear up error' AND JourneyID = '" + ETS2_Fragment.JOURNEYID + "'", null);
        // Query to count the numbers of gear errors from the finished journey.
        gearSQL.moveToFirst();
        int gearCount = gearSQL.getInt(0);      // An int is initialised with the value of gear errors.
        gearSQL.close();                        // The cursor is closed.

        Cursor brakeSQL = MainActivity.db.rawQuery("SELECT COUNT(*) FROM errors WHERE ErrorType = 'brakeError' AND JourneyID = '" + ETS2_Fragment.JOURNEYID + "'", null);
        // Query to count the numbers of brake errors from the finished journey.
        brakeSQL.moveToFirst();
        int brakeCount = brakeSQL.getInt(0);    // An int is initialised with the value of brake errors.
        brakeSQL.close();                       // The cursor is closed.

        Cursor speedSQL = MainActivity.db.rawQuery("SELECT COUNT(*) FROM errors WHERE ErrorType = 'speedError' AND JourneyID = '" + ETS2_Fragment.JOURNEYID + "'", null);
        // Query to count the numbers of speed errors from the finished journey.
        speedSQL.moveToFirst();
        int speedCount = speedSQL.getInt(0);    // An int is initialised with the value of speed errors.
        speedSQL.close();                       // The cursor is closed.

        // Textviews are assigned the values of the error counts.
        // Total errors count is a sum of all error type counts.
        gearErrorsResult.setText(String.valueOf(gearCount));
        brakeErrorsResult.setText(String.valueOf(brakeCount));
        speedErrorsResult.setText(String.valueOf(speedCount));
        totalErrorsResult.setText(String.valueOf(gearCount + brakeCount + speedCount));



    }
}
