package com.emroxriprap.klok;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.emroxriprap.klok.data.KlokContract;


public class JobNameFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private static final int JOB_NAMES_LOADER = 1;

//    ListView jobNamelistView;
    TextView location,address;
//    TextView unitApt;
//    private String [] mTableColumnNames = {
//            KlokContract.JobEntry.COLUMN_JOB_NAME,
//            KlokContract.JobEntry.COLUMN_ADDRESS_TWO
//    };
    private String [] mTableColumnNames = {
            KlokContract.KlokEntry.COLUMN_LOCATION,
            KlokContract.KlokEntry.COLUMN_ADDRESS
    };

    private View mRootView;
    private String dateString;
    private int dateInt;
//    private int dbCount;
//    private Cursor cursor;
    SimpleCursorAdapter adapter;

    private OnFragmentInteractionListener mListener;



    public static JobNameFragment newInstance(String dateString, int dateInt) {
        JobNameFragment fragment = new JobNameFragment();
        Bundle args = new Bundle();
        args.putString(MainActivity.ARG_DATE_STRING, dateString);
        args.putInt(MainActivity.ARG_DATE_INT, dateInt);
        fragment.setArguments(args);
        return fragment;
    }
    public static JobNameFragment newInstance(Bundle b) {
        JobNameFragment fragment = new JobNameFragment();

        fragment.setArguments(b);
        return fragment;
    }

    public JobNameFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        getActivity().setTitle("Select Job Name");
        if (getArguments() != null) {
            dateString = getArguments().getString(MainActivity.ARG_DATE_STRING);
            dateInt = getArguments().getInt(MainActivity.ARG_DATE_INT);
        }
//        cursor = getActivity().getContentResolver().query(KlokContract.JobEntry.CONTENT_URI,
//                null,
//                null,
//                null,
//                null);
//        dbCount = cursor.getCount();
        getLoaderManager().initLoader(JOB_NAMES_LOADER,null,this);
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


        int [] to = new int []{R.id.tv_job_list_item_job_name,R.id.tv_job_list_item_address_two};
        adapter = new SimpleCursorAdapter(
                getActivity(),
                R.layout.job_name_list_item_layout,
                null,
                mTableColumnNames,
                to,
                0
        );
//        jobNamelistView = (ListView)rootView.findViewById(android.R.id.list);
//        location = (TextView)rootView.findViewById(R.id.tv_job_list_item_job_name);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        setListAdapter(adapter);

//        addFakeData();



    }

//    private void addFakeData() {
//
//        ContentValues values = new ContentValues();
//        values.put(KlokContract.JobEntry.COLUMN_JOB_NAME,"Village Falls");
//        values.put(KlokContract.JobEntry.COLUMN_ADDRESS_ONE,"115 DW Highway");
//        values.put(KlokContract.JobEntry.COLUMN_ADDRESS_TWO,"General");
//        values.put(KlokContract.JobEntry.COLUMN_CITY,"Merrimack");
//        values.put(KlokContract.JobEntry.COLUMN_STATE,"New Hampshire");
//
//        Uri uri = getActivity().getContentResolver().insert(KlokContract.JobEntry.CONTENT_URI,values);
//        if (uri != null)
//            Toast.makeText(getActivity(),"Value added to DB", Toast.LENGTH_LONG).show();
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        TextView textView = new TextView(getActivity());
//        textView.setText(R.string.hello_blank_fragment);
//        return textView;
        View rootView = inflater.inflate(R.layout.frag_job_name, container, false);
        initViews(rootView);
        mRootView = rootView;
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                location = (TextView)view.findViewById(R.id.tv_job_list_item_job_name);
                address = (TextView)view.findViewById(R.id.tv_job_list_item_address_two);
                getArguments().putString(MainActivity.ARG_LOCATION,location.getText().toString());
                getArguments().putString(MainActivity.ARG_ADDRESS,address.getText().toString());
//                Fragment timeFrag = TimeDescriptionFragment.newInstance(dateString, dateInt, jobNameSelected);
                Fragment timeFrag = TimeDescriptionFragment.newInstance(getArguments());
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.container,timeFrag);
                transaction.addToBackStack(null);

                transaction.commit();

            }
        });
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

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader loader = new CursorLoader(
                getActivity(),
                KlokContract.JobEntry.CONTENT_URI,
                        null,
                        null,
                        null,
                        null
        );

        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        ((SimpleCursorAdapter)this.getListAdapter()).swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        ((SimpleCursorAdapter)this.getListAdapter()).swapCursor(null);
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
