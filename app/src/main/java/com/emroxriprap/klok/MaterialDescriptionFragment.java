package com.emroxriprap.klok;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.emroxriprap.klok.data.KlokContract;

import java.util.ArrayList;
import java.util.List;


public class MaterialDescriptionFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MaterialDescriptionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MaterialDescriptionFragment newInstance(String param1, String param2) {
        MaterialDescriptionFragment fragment = new MaterialDescriptionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public static MaterialDescriptionFragment newInstance(Bundle b) {
        MaterialDescriptionFragment fragment = new MaterialDescriptionFragment();
        fragment.setArguments(b);
        return fragment;
    }

    public MaterialDescriptionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        getActivity().setTitle("Materials");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_material_description, container, false);
        final EditText materials = (EditText)rootView.findViewById(R.id.et_mat_cost);
        final EditText rate = (EditText)rootView.findViewById(R.id.et_markup);
        final EditText desc = (EditText)rootView.findViewById(R.id.et_material_description);

        FloatingActionButton fab = (FloatingActionButton)rootView.findViewById(R.id.fab_go_to_summary_screen);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Log.d("Float value is ", ""+s);
//
//                int i = (int)s * 100;
//                Log.d("Value is ", String.format("%.2f", s * r));

                List<EditText> list = new ArrayList<EditText>();
                list.add(materials);
                list.add(rate);
                list.add(desc);
                if (Utilities.validateEditTextFields(list)){
                    float s = Float.valueOf(materials.getText().toString());
                    float r = Float.valueOf(rate.getText().toString());
                    float tot = s +(s*r);
                    getArguments().putString(MainActivity.ARG_MATERIAL_DESC, desc.getText().toString());
                    getArguments().putFloat(MainActivity.ARG_MATERIALS, s);
                    getArguments().putFloat(MainActivity.ARG_MARKUP,r);
                    getArguments().putFloat(MainActivity.ARG_MATERIAL_TOTAL, tot);

                    SummaryFragment sumFrag = SummaryFragment.newInstance(getArguments());
                    FragmentTransaction transaction = getActivity().getFragmentManager().beginTransaction();
                    transaction.replace(R.id.container,sumFrag);
                    transaction.addToBackStack(null);
                    transaction.commit();

                }else{
                    Toast.makeText(getActivity(), "Fill out all fields.", Toast.LENGTH_SHORT).show();
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
