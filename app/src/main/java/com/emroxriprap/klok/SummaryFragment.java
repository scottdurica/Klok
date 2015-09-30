package com.emroxriprap.klok;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.emroxriprap.klok.data.KlokContract;

public class SummaryFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private float total;
    private CheckBox invoiced;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SummaryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SummaryFragment newInstance(String param1, String param2) {
        SummaryFragment fragment = new SummaryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public static SummaryFragment newInstance(Bundle bundle){
        SummaryFragment fragment = new SummaryFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public SummaryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Summary");
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_summary, container, false);
        Button startOver = (Button)rootView.findViewById(R.id.b_start_over);
        Button edit = (Button)rootView.findViewById(R.id.b_edit);
        Button addToDb = (Button)rootView.findViewById(R.id.b_save);
        startOver.setOnClickListener(this);
        edit.setOnClickListener(this);
        addToDb.setOnClickListener(this);

        total = getArguments().getFloat(MainActivity.ARG_LABOR_TOTAL) +
                getArguments().getFloat(MainActivity.ARG_MATERIAL_TOTAL);
        String totalString = String.format("%.2f",total);


        TextView jobName = (TextView)rootView.findViewById(R.id.tv_summary_name);
        TextView date = (TextView)rootView.findViewById(R.id.tv_summary_date);
        TextView laborTotal = (TextView)rootView.findViewById(R.id.tv_summary_labor_total);
        TextView materialTotal = (TextView)rootView.findViewById(R.id.tv_summary_material_total);
        TextView jobTotal = (TextView)rootView.findViewById(R.id.tv_summary_job_total);
        invoiced = (CheckBox)rootView.findViewById(R.id.cb_invoiced_status);

        jobName.setText(getArguments().get(MainActivity.ARG_LOCATION).toString());
        date.setText(getArguments().get(MainActivity.ARG_DATE_STRING).toString());
        laborTotal.setText(Utilities.formatToCurrencyString(getArguments().getFloat(MainActivity.ARG_LABOR_TOTAL)));
        materialTotal.setText(Utilities.formatToCurrencyString(getArguments().getFloat(MainActivity.ARG_MATERIAL_TOTAL)));
        jobTotal.setText(Utilities.formatToCurrencyString(total));

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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.b_save:
                int invoicedStatus = 0;
                if (invoiced.isChecked()){
                    invoicedStatus = 1;
                }
                ContentValues values = new ContentValues();
                Bundle b = getArguments();
                values.put(KlokContract.KlokEntry.COLUMN_LOCATION, b.getString(MainActivity.ARG_LOCATION));
                values.put(KlokContract.KlokEntry.COLUMN_ADDRESS,b.getString(MainActivity.ARG_ADDRESS));
                values.put(KlokContract.KlokEntry.COLUMN_DATE, b.getInt(MainActivity.ARG_DATE_INT));
                values.put(KlokContract.KlokEntry.COLUMN_DATE_STRING, b.getString(MainActivity.ARG_DATE_STRING));
                values.put(KlokContract.KlokEntry.COLUMN_HOURS, b.getFloat(MainActivity.ARG_HOURS));
                values.put(KlokContract.KlokEntry.COLUMN_RATE, b.getFloat(MainActivity.ARG_RATE));
                values.put(KlokContract.KlokEntry.COLUMN_TIME_DESC, b.getString(MainActivity.ARG_TIME_DESC));
                values.put(KlokContract.KlokEntry.COLUMN_LABOR_TOTAL, b.getFloat(MainActivity.ARG_LABOR_TOTAL));
                values.put(KlokContract.KlokEntry.COLUMN_MATERIALS, b.getFloat(MainActivity.ARG_MATERIALS));
                values.put(KlokContract.KlokEntry.COLUMN_MARKUP, b.getFloat(MainActivity.ARG_MARKUP));
                values.put(KlokContract.KlokEntry.COLUMN_MATERIAL_DESC, b.getString(MainActivity.ARG_MATERIAL_DESC));
                values.put(KlokContract.KlokEntry.COLUMN_MATERIAL_TOTAL, b.getFloat(MainActivity.ARG_MATERIAL_TOTAL));
                values.put(KlokContract.KlokEntry.COLUMN_TOTAL, total);
                values.put(KlokContract.KlokEntry.COLUMN_INVOICED, invoicedStatus);

                Uri uri = getActivity().getContentResolver().insert(KlokContract.KlokEntry.CONTENT_URI,values);
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
                    RecordsListFragment fragment = RecordsListFragment.newInstance(null);
                    FragmentTransaction transaction = getActivity().getFragmentManager().beginTransaction();
                    transaction.replace(R.id.container,fragment);
                    transaction.commit();
//                    getFragmentManager().popBackStackImmediate();
                }else{
                    Toast.makeText(getActivity(), "NOT SAVED!", Toast.LENGTH_SHORT).show();
                }

                break;
        }
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
