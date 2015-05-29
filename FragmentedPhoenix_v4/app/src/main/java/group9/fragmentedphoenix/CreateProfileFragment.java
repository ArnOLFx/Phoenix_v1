package group9.fragmentedphoenix;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class CreateProfileFragment extends Fragment {
// Class that will facilitate the user to create a new profile for the application

    EditText username;                          // User can set their profile name in this EditText
    Button createProfileButton;                 // Button to proceed and create profile


    public CreateProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View createProfileView = inflater.inflate(R.layout.fragment_create_profile, container, false);
        username = (EditText) createProfileView.findViewById(R.id.username);
        createProfileButton = (Button) createProfileView.findViewById(R.id.createProfileButton);
        createProfileButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                MainActivity.db.execSQL("INSERT INTO profiles VALUES('" + username.getText() + "');");
                // SQL statement to create the profile in the database
                MainActivity.addNewItem(MainActivity.ets2Fragment);
                MainActivity.addNewItem(MainActivity.journeyFragment);
                MainActivity.mViewPager.setCurrentItem(1);
                /* Once the create profile button has been pressed the user will be taken to
                   the journey fragment so they can start their journey.
                */
                MainActivity.removeItem(0);
            }
        });



        return createProfileView;
    }
}
