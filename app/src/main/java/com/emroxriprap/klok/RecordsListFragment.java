package com.emroxriprap.klok;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.emroxriprap.klok.data.KlokContract;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RecordsListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RecordsListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecordsListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";

    String [] mTableColNames = {
            KlokContract.KlokEntry.COLUMN_JOB_NAME,
            KlokContract.KlokEntry.COLUMN_DATE_STRING
//            ,KlokContract.KlokEntry.COLUMN_ADDRESS_ONE,
//            KlokContract.KlokEntry.COLUMN_ADDRESS_TWO,
//            KlokContract.KlokEntry.COLUMN_CITY,
//            KlokContract.KlokEntry.COLUMN_STATE,
//            KlokContract.KlokEntry.COLUMN_HOURS
    };
//    int [] listItemIdViews;
    Cursor cursor;
    ListView listView;
    SimpleCursorAdapter simpleCursorAdapter;
    FloatingActionButton fab;

    // TODO: Rename and change types of parameters
    private String dateString;
//    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment RecordsListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecordsListFragment newInstance(String param1) {
        RecordsListFragment fragment = new RecordsListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public RecordsListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);

        }
        cursor = getActivity().getContentResolver().query(KlokContract.KlokEntry.CONTENT_URI,
                null,
                null,
                null,
                null);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.frag_records_list, container, false);
        listView = (ListView)rootView.findViewById(R.id.lv_records);
        int [] listItemIdViews = {R.id.tv_list_item_job_name,R.id.tv_list_item_date};
        simpleCursorAdapter = new SimpleCursorAdapter(
                getActivity(),
                R.layout.record_list_item_layout,
                cursor,
                mTableColNames,
                listItemIdViews,
                0
        );
        listView.setAdapter(simpleCursorAdapter);
        fab = (FloatingActionButton)rootView.findViewById(R.id.fab_add_record);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment dateFrag = new DateFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.container,dateFrag);
                transaction.addToBackStack(null);

                transaction.commit();

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
