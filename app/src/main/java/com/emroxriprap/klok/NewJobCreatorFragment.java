package com.emroxriprap.klok;

import android.app.Activity;
import android.app.Fragment;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.emroxriprap.klok.data.KlokContract;

import java.util.ArrayList;
import java.util.List;

public class NewJobCreatorFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Button add,clear;
    EditText name,address;

    private OnFragmentInteractionListener mListener;

    public static NewJobCreatorFragment newInstance(String param1, String param2) {
        NewJobCreatorFragment fragment = new NewJobCreatorFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public NewJobCreatorFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_new_job_creator, container, false);
        final ViewGroup viewGroup = (ViewGroup)rootView;
        name = (EditText)rootView.findViewById(R.id.et_njc_location);
        address = (EditText)rootView.findViewById(R.id.et_njc_address);
//        unitApt = (EditText)rootView.findViewById(R.id.et_unit_apt);
//        city = (EditText)rootView.findViewById(R.id.et_city);
//        state = (EditText)rootView.findViewById(R.id.et_state);

        clear = (Button)rootView.findViewById(R.id.b_clear_fields);
        add = (Button)rootView.findViewById(R.id.b_add_job);

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name.setText("");
                address.setText("");
//                unitApt.setText("");
//                city.setText("");
//                state.setText("");
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<EditText> etList = new ArrayList<EditText>();
                etList.add(name);
                etList.add(address);
//                etList.add(unitApt);
//                etList.add(city);
//                etList.add(state);
                if(Utilities.validateEditTextFields(etList)){
                    // check for duplicates in db..if none, add to db
                    ContentValues values = new ContentValues();
                    values.put(KlokContract.JobEntry.COLUMN_LOCATION, name.getText().toString());
                    values.put(KlokContract.JobEntry.COLUMN_ADDRESS,address.getText().toString());
//                    values.put(KlokContract.JobEntry.COLUMN_ADDRESS_TWO,unitApt.getText().toString());
//                    values.put(KlokContract.JobEntry.COLUMN_CITY,city.getText().toString());
//                    values.put(KlokContract.JobEntry.COLUMN_STATE,state.getText().toString());

                    Uri uri = getActivity().getContentResolver().insert(KlokContract.JobEntry.CONTENT_URI,values);
                    if (uri != null) {
//                        Toast.makeText(getActivity(), "Saved to DB", Toast.LENGTH_SHORT).show();
//                        getActivity().getContentResolver().notifyChange(KlokContract.JobEntry.CONTENT_URI,null);
                        InputMethodManager inputMethodManager = (InputMethodManager)getActivity().getSystemService(
                                Activity.INPUT_METHOD_SERVICE);
                        try
                        {
                            inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                        }
                        catch (Exception e){}

                          getFragmentManager().popBackStackImmediate();
                    }else{
                        Toast.makeText(getActivity(), "NOT SAVED!", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getActivity(),"Fill out all fields", Toast.LENGTH_SHORT).show();;

                }
            }
        });

        return rootView;
    }
//    private boolean validateFields(){
//
//
//            if (name.getText().toString().trim().length()==0 ||
//                    address.getText().toString().trim().length()==0 ||
//                    unitApt.getText().toString().trim().length()==0 ||
//                    city.getText().toString().trim().length()==0 ||
//                    state.getText().toString().trim().length()==0){
//
//                    return false;
//            }
//
//
//        return true;
//    }

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
