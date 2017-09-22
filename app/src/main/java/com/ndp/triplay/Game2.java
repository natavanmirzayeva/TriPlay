package com.ndp.triplay;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Game2.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Game2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Game2 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Button control;
    TextView question;
    EditText answer;
    SharedPreferences.Editor editor;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Game2() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Game2.
     */
    // TODO: Rename and change types and number of parameters
    public static Game2 newInstance(String param1, String param2) {
        Game2 fragment = new Game2();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_game2, null);



        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        editor = sharedPrefs.edit();

        control = (Button) view.findViewById(R.id.control);
        question = (TextView) view.findViewById(R.id.question);
        answer = (EditText) view.findViewById(R.id.answer);
        question.setText("İstanbul Hangi Yıl Fethedilmiştir?");

        control.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        String text = answer.getText().toString().trim();
                        if(text.equalsIgnoreCase("1453")){
                            Toast.makeText(getContext(), "Tebrikler! İstanbulun Kurtuluşunu Doğru Bildiniz:)", Toast.LENGTH_SHORT).show();
                            editor.putString("level", "1");

                        }else {
                            Toast.makeText(getContext(), "Maalesef Bilemediniz:(", Toast.LENGTH_SHORT).show();
                            editor.putString("level", "0");
                        }


                    }
                });




        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Game2.OnFragmentInteractionListener) {
            mListener = (Game2.OnFragmentInteractionListener) context;
        } else {

        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
