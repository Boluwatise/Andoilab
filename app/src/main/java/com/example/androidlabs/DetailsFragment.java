package com.example.androidlabs;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "name";
    private static final String ARG_PARAM2 = "height";

  private static final String ARG_PARAM3 = "mass";


  TextView nameField;
  TextView heightField;
  TextView massField;




  public DetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param name Parameter 1.
     * @param height Parameter 2.
     * @param mass Parameter 3.
     * @return A new instance of fragment DetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailsFragment newInstance(String name, String height,String mass) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, name);
        args.putString(ARG_PARAM2, height);
      args.putString(ARG_PARAM3, mass);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      View v = inflater.inflate(R.layout.fragment_details, container, false);

      Bundle bundle = getArguments();

      nameField = (TextView) v.findViewById(R.id.name) ;
      heightField =(TextView) v.findViewById(R.id.height)  ;
      massField= (TextView) v.findViewById(R.id.mass) ;


        nameField.setText(bundle.getString(ARG_PARAM1));
        heightField.setText(bundle.getString(ARG_PARAM2));
        massField.setText(bundle.getString(ARG_PARAM3));

        // Inflate the layout for this fragment
        return v;
    }
}
