package com.emroxriprap.klok;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.emroxriprap.klok.data.KlokContract;


public class JobNameFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String KEY_DATE_STRING = "date_string";
    private static final String KEY_DATE_INT = "date_int";

    ListView jobNamelistView;

    private String [] mTableColumnNames = {
            KlokContract.JobEntry.COLUMN_JOB_NAME,

    };


    private String dateString;
    private int dateInt;
    private int dbCount;
    private Cursor cursor;


    private OnFragmentInteractionListener mListener;



    public static JobNameFragment newInstance(String dateString, int dateInt) {
        JobNameFragment fragment = new JobNameFragment();
        Bundle args = new Bundle();
        args.putString(KEY_DATE_STRING, dateString);
        args.putInt(KEY_DATE_INT, dateInt);
        fragment.setArguments(args);
        return fragment;
    }

    public JobNameFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            dateString = getArguments().getString(KEY_DATE_STRING);
            dateInt = getArguments().getInt(KEY_DATE_INT);
            getActivity().setTitle("Select Job");
        }
        cursor = getActivity().getContentResolver().query(KlokContract.JobEntry.CONTENT_URI,
                null,
                null,
                null,
                null);
        dbCount = cursor.getCount();

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.job_name_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_new_job_menu_item: {
                NewJobCreatorFragment newFrag = new NewJobCreatorFragment();
                FragmentTransaction transaction = getActivity().getFragmentManager().beginTransaction();
                transaction.replace(R.id.container,newFrag);
                transaction.addToBackStack(null);
                transaction.commit();
            }
            break;
        }
        return true;
    }

    private void initViews(View rootView){


        jobNamelistView = (ListView)rootView.findViewById(R.id.lv_job_names);
        int [] to = new int []{android.R.id.text1};
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                getActivity(),
                android.R.layout.simple_spinner_item,
                cursor,
                mTableColumnNames,
                to,
                0
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        jobNamelistView.setAdapter(adapter);
//        addFakeData();
//        enterData.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ContentValues values = new ContentValues();
//
//
//
//
//                values.put(KlokContract.KlokEntry.COLUMN_JOB_NAME,jobName.getText().toString());
//                values.put(KlokContract.KlokEntry.COLUMN_DATE,dateInt);
//                values.put(KlokContract.KlokEntry.COLUMN_DATE_STRING,dateString);
////                values.put(KlokContract.KlokEntry.COLUMN_DATE,date.getText().toString());
//                values.put(KlokContract.KlokEntry.COLUMN_ADDRESS_ONE,addressOne.getText().toString());
//                values.put(KlokContract.KlokEntry.COLUMN_ADDRESS_TWO,addressTwo.getText().toString());
//                values.put(KlokContract.KlokEntry.COLUMN_CITY,city.getText().toString());
//                values.put(KlokContract.KlokEntry.COLUMN_STATE,state.getText().toString());
//                values.put(KlokContract.KlokEntry.COLUMN_HOURS,hours.getText().toString());
//
//                Uri uri = getActivity().getContentResolver().insert(KlokContract.KlokEntry.CONTENT_URI,values);
//                if(uri != null){
//                    RecordsListFragment recordsFrag = new RecordsListFragment();
//                    FragmentTransaction fm = getFragmentManager().beginTransaction().replace(
//                            R.id.container,recordsFrag
//                    ).addToBackStack(null);
//                    fm.commit();
//                }
//            }
//        });


    }

    private void addFakeData() {

        ContentValues values = new ContentValues();
        values.put(KlokContract.JobEntry.COLUMN_JOB_NAME,"Village Falls");
        values.put(KlokContract.JobEntry.COLUMN_ADDRESS_ONE,"115 DW Highway");
        values.put(KlokContract.JobEntry.COLUMN_ADDRESS_TWO,"General");
        values.put(KlokContract.JobEntry.COLUMN_CITY,"Merrimack");
        values.put(KlokContract.JobEntry.COLUMN_STATE,"New Hampshire");

        Uri uri = getActivity().getContentResolver().insert(KlokContract.JobEntry.CONTENT_URI,values);
        if (uri != null)
            Toast.makeText(getActivity(),"Value added to DB", Toast.LENGTH_LONG).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        TextView textView = new TextView(getActivity());
//        textView.setText(R.string.hello_blank_fragment);
//        return textView;
          View rootView = inflater.inflate(R.layout.frag_job_name, container, false);
        initViews(rootView);
        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Hey stupid, " + activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
