package com.emroxriprap.klok;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


public class MainActivity extends AppCompatActivity implements RecordsListFragment.OnFragmentInteractionListener, JobNameFragment.OnFragmentInteractionListener,
DateFragment.OnFragmentInteractionListener, NewJobCreatorFragment.OnFragmentInteractionListener, TimeDescriptionFragment.OnFragmentInteractionListener,
        MaterialDescriptionFragment.OnFragmentInteractionListener, SummaryFragment.OnFragmentInteractionListener
{

    public static final String ARG_DATE_STRING = "date_string";
    public static final String ARG_DATE_INT = "date_int";
    public static final String ARG_LOCATION = "job_name";
    public static final String ARG_ADDRESS = "address";
    public static final String ARG_HOURS = "hours";
    public static final String ARG_RATE = "rate";
    public static final String ARG_TIME_DESC = "time_desc";
    public static final String ARG_LABOR_TOTAL = "labor_total";
    public static final String ARG_MATERIALS = "materials";
    public static final String ARG_MARKUP= "markup";
    public static final String ARG_MATERIAL_DESC = "material_desc";
    public static final String ARG_MATERIAL_TOTAL = "material_total";
    public static final String ARG_INVOICED = "invoiced";
    public static final String ARG_TOTAL = "total";

    @Override
    public void onBackPressed() {
        if(getFragmentManager().getBackStackEntryCount() == 1){
//            getFragmentManager().popBackStack();
//            setTitle("Klok");
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, new RecordsListFragment())
                    .commit();
        }
        else if(getFragmentManager().getBackStackEntryCount() == 0) {
            super.onBackPressed();
        }
        else{
            getFragmentManager().popBackStack();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new RecordsListFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.frag_job_name, container, false);
            return rootView;
        }
    }
}
