package com.emroxriprap.klok;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TimeDescriptionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TimeDescriptionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TimeDescriptionFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER





    private String timeDescription;
    private int hours;
    private int rate;

    private OnFragmentInteractionListener mListener;

    public static TimeDescriptionFragment newInstance(String dateString, int dateInt, String jobName) {
        TimeDescriptionFragment fragment = new TimeDescriptionFragment();
        Bundle args = new Bundle();
        args.putString(MainActivity.ARG_DATE_STRING, dateString);
        args.putInt(MainActivity.ARG_DATE_INT, dateInt);
        args.putString(MainActivity.ARG_LOCATION, jobName);
        fragment.setArguments(args);
        return fragment;
    }
    public static TimeDescriptionFragment newInstance(Bundle b){
        TimeDescriptionFragment fragment = new TimeDescriptionFragment();
        fragment.setArguments(b);
        return fragment;
    }

    public TimeDescriptionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Time");

        if (getArguments() != null) {
//            dateString = getArguments().getString(ARG_DATE_STRING);
//            dateInt = getArguments().getInt(ARG_DATE_INT);
//            location = getArguments().getString(ARG_LOCATION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_time_description, container, false);
        final EditText hours = (EditText)rootView.findViewById(R.id.et_hours);
        final EditText rate = (EditText)rootView.findViewById(R.id.et_rate);
        final EditText desc = (EditText)rootView.findViewById(R.id.et_description);

        FloatingActionButton fab = (FloatingActionButton)rootView.findViewById(R.id.fab_go_to_materials_screen);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Log.d("Float value is ", ""+s);
//
//                int i = (int)s * 100;
//                Log.d("Value is ", String.format("%.2f", s * r));

                List<EditText> list = new ArrayList<EditText>();
                list.add(hours);
                list.add(rate);
                list.add(desc);
                if (Utilities.validateEditTextFields(list)){
                    float s = Float.valueOf(hours.getText().toString());
                    float r = Float.valueOf(rate.getText().toString());
                    float tot = s*r;
                    getArguments().putString(MainActivity.ARG_TIME_DESC, desc.getText().toString());
                    getArguments().putFloat(MainActivity.ARG_HOURS, s);
                    getArguments().putFloat(MainActivity.ARG_RATE,r);
                    getArguments().putFloat(MainActivity.ARG_LABOR_TOTAL, tot);

                    MaterialDescriptionFragment matFrag = MaterialDescriptionFragment.newInstance(getArguments());
                    FragmentTransaction transaction = getActivity().getFragmentManager().beginTransaction();
                    transaction.replace(R.id.container,matFrag);
                    transaction.addToBackStack(null);
                    transaction.commit();

                }else{
                    Toast.makeText(getActivity(),"Fill out all fields.", Toast.LENGTH_SHORT).show();
                }

            }
        });

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
            throw new ClassCastException(activity.toString()
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
