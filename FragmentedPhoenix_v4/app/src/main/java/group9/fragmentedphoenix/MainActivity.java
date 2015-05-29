package group9.fragmentedphoenix;

import java.util.ArrayList;
import java.util.Locale;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v13.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends Activity {

    static SectionsPagerAdapter mSectionsPagerAdapter;
    static ViewPager mViewPager;                     // Stores the fragments
    public static SQLiteDatabase db;                 // the database name

    String profileName = "Default Username";
    // Fragments
    static ETS2_Fragment ets2Fragment = new ETS2_Fragment();
    static JourneyFragment journeyFragment = new JourneyFragment();
    static CreateProfileFragment createProfileFragment = new CreateProfileFragment();
    static ArrayList<Fragment> fragments = new ArrayList<Fragment>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = openOrCreateDatabase("JourneyDB", Context.MODE_PRIVATE, null);
        // db.execSQL("CREATE TABLE IF NOT EXISTS profiles(Name TEXT);"); NO LONGER IN USE
        db.execSQL("CREATE TABLE IF NOT EXISTS errors(ErrorID INTEGER, JourneyID INTEGER, ErrorType TEXT);");
        // Creates the errors table in the database if it doesn't exist.
        // db.execSQL("CREATE TABLE IF NOT EXISTS journeydetails(JourneyID INTEGER, DateTime TEXT, Origin TEXT, Destination TEXT, TotalErrors INTEGER, Score Integer);");
        // Stores the details of previous journeys that have been taken.

        createViewPager();
        //launchApp();
        addNewItem(MainActivity.ets2Fragment);
        addNewItem(MainActivity.journeyFragment);
        getJourneyID();


    }

    public void createViewPager() {
        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
    }

    public static void addNewItem(Fragment fragment) {
        fragments.add(fragment);
        mSectionsPagerAdapter.notifyDataSetChanged();
    }

    public static void removeCurrentItem() {
        int position = mViewPager.getCurrentItem();
        fragments.remove(position);
        mSectionsPagerAdapter.notifyDataSetChanged();
    }

    public static void removeItem(int position) {
        fragments.remove(position);
        mSectionsPagerAdapter.notifyDataSetChanged();
    }

    //NOT IN USE
    public void launchApp(){
        // Check if a user profile has already been created, if not then the user will
        // automatically be taken to the create profile page.
        Cursor c = db.rawQuery("SELECT * FROM profiles", null);
        if(c.getCount() == 0){
                addNewItem(createProfileFragment);

        }
        else{
            c.moveToNext();
            profileName = (c.getString(c.getColumnIndex("Name")));
            addNewItem(MainActivity.ets2Fragment);
            addNewItem(MainActivity.journeyFragment);
            }
    }

    public static void getJourneyID(){
        // Method that will retrieve the next available Journey ID from the errors table.
        Cursor c = db.rawQuery("SELECT JourneyID FROM errors", null);
        if (c.getCount() == 0){
            ets2Fragment.JOURNEYID = 1;
        } else {
            while (c.moveToNext()) {
                ets2Fragment.JOURNEYID = (c.getInt(c.getColumnIndex("JourneyID")) + 1);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
            }

        @Override
        public int getItemPosition(Object object){
            return PagerAdapter.POSITION_NONE;
        }

        @Override
        public int getCount() {
            return fragments.size();
        }


        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section3).toUpperCase(l);
            }
            return null;
        }
    }

}
