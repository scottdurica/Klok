package com.emroxriprap.klok;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.emroxriprap.klok.data.KlokContract;


public class TestFragment extends android.support.v4.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    EditText jobName,date,addressOne,addressTwo,city,state,hours;
    Button enterData,deleteRecords;
    TextView dbTotal;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private int dbCount;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TestFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TestFragment newInstance(String param1, String param2) {
        TestFragment fragment = new TestFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public TestFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        Cursor cursor = getActivity().getContentResolver().query(KlokContract.KlokEntry.CONTENT_URI,
                null,
                null,
                null,
                null);
        dbCount = cursor.getCount();

    }
    private void initViews(View rootView){

        jobName = (EditText)rootView.findViewById(R.id.et_job_name);
        date = (EditText)rootView.findViewById(R.id.et_date);
        addressOne = (EditText)rootView.findViewById(R.id.et_address1);
        addressTwo = (EditText)rootView.findViewById(R.id.et_address2);
        city = (EditText)rootView.findViewById(R.id.et_city);
        state = (EditText)rootView.findViewById(R.id.et_state);
        hours = (EditText)rootView.findViewById(R.id.et_hours);

        dbTotal = (TextView)rootView.findViewById(R.id.tv_total);
        dbTotal.setText(dbCount + "");

        enterData = (Button)rootView.findViewById(R.id.b_enter_data);
        enterData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();

                values.put(KlokContract.KlokEntry.COLUMN_JOB_NAME,jobName.getText().toString());
                values.put(KlokContract.KlokEntry.COLUMN_DATE,date.getText().toString());
                values.put(KlokContract.KlokEntry.COLUMN_ADDRESS_ONE,addressOne.getText().toString());
                values.put(KlokContract.KlokEntry.COLUMN_ADDRESS_TWO,addressTwo.getText().toString());
                values.put(KlokContract.KlokEntry.COLUMN_CITY,city.getText().toString());
                values.put(KlokContract.KlokEntry.COLUMN_STATE,state.getText().toString());
                values.put(KlokContract.KlokEntry.COLUMN_HOURS,hours.getText().toString());

                Uri uri = getActivity().getContentResolver().insert(KlokContract.KlokEntry.CONTENT_URI,values);
            }
        });
        deleteRecords = (Button)rootView.findViewById(R.id.b_delete_records);
        deleteRecords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        TextView textView = new TextView(getActivity());
//        textView.setText(R.string.hello_blank_fragment);
//        return textView;
          View rootView = inflater.inflate(R.layout.fragment_main, container, false);
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