package com.ndp.triplay;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GameFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Button bttn1,bttn2,bttn3,bttn4;
    ImageView flag;
    List<CountryItem> list;
    Random r;
    int turn =1;
    private OnFragmentInteractionListener mListener;

    FragmentManager fragmentManager;
    FragmentTransaction transaction;

    SharedPreferences sharedPrefs;

    SharedPreferences.Editor editor;


    public GameFragment() {

    }


    public static GameFragment newInstance(String param1, String param2) {
        GameFragment fragment = new GameFragment();
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
        View view = inflater.inflate(R.layout.fragment_game, null);

        flag = (ImageView) view.findViewById(R.id.flag);
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        editor = sharedPrefs.edit();



        bttn1 = (Button) view.findViewById(R.id.bttn1);
        bttn2 = (Button) view.findViewById(R.id.bttn2);
        bttn3 = (Button) view.findViewById(R.id.bttn3);
        bttn4 = (Button) view.findViewById(R.id.bttn4);

        list = new ArrayList<>();

        r = new Random();
        for(int i = 0; i< new Database().answers.length; i++){

            list.add(new CountryItem(new Database().answers[i],new Database().flags[i]));

        }

        Collections.shuffle(list);
        newQuestion(turn);

        fragmentManager = getActivity().getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();

        bttn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(bttn1.getText().toString().equalsIgnoreCase(list.get(turn - 1).getName())){
                    editor.putString("level", "2");
                    editor.apply();
                    Toast.makeText(getContext(), "Doğru Cevap :)", Toast.LENGTH_SHORT).show();
                    transaction.replace(R.id.content, new Game2()).commit();
                }else {
                    editor.putString("level", "0");
                    editor.apply();
                    Toast.makeText(getContext(), "Yanlış Cevap :(", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getContext(), "Yeni Soru İçin Sohbet Ekranına Dön", Toast.LENGTH_SHORT).show();

                }
            }
        });
        bttn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(bttn2.getText().toString().equalsIgnoreCase(list.get(turn - 1).getName())){
                    editor.putString("level", "2");
                    editor.apply();
                    Toast.makeText(getContext(), "Doğru Cevap :)", Toast.LENGTH_SHORT).show();
                    transaction.replace(R.id.content, new Game2()).commit();
                }else {
                    editor.putString("level", "0");
                    editor.apply();
                    Toast.makeText(getContext(), "Yanlış Cevap :(", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getContext(), "Yeni Soru İçin Sohbet Ekranına Dön", Toast.LENGTH_SHORT).show();

                }

            }

        });
        bttn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(bttn3.getText().toString().equalsIgnoreCase(list.get(turn - 1).getName())){
                    editor.putString("level", "2");
                    editor.apply();
                    Toast.makeText(getContext(), "Doğru Cevap :)", Toast.LENGTH_SHORT).show();
                    transaction.replace(R.id.content, new Game2()).commit();
                }else {
                    editor.putString("level", "0");
                    editor.apply();
                    Toast.makeText(getContext(), "Yanlış Cevap :(", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getContext(), "Yeni Soru İçin Sohbet Ekranına Dön", Toast.LENGTH_SHORT).show();

                }

            }

        });
        bttn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(bttn4.getText().toString().equalsIgnoreCase(list.get(turn - 1).getName())){
                    editor.putString("level", "2");
                    editor.apply();
                    Toast.makeText(getContext(), "Doğru Cevap :)", Toast.LENGTH_SHORT).show();
                    transaction.replace(R.id.content, new Game2()).commit();
                }else {
                    editor.putString("level", "0");
                    editor.apply();
                    Toast.makeText(getContext(), "Yanlış Cevap :(", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getContext(), "Yeni Soru İçin Sohbet Ekranına Dön", Toast.LENGTH_SHORT).show();

                }

            }

        });

        return view;
    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
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
    private void newQuestion(int number){

        flag.setImageResource(list.get(number-1).getImage());
        int correct_answer = r.nextInt(4)+1;
        int firstButton = number-1;
        int secondButton;
        int thirdButton;
        int fourtButton;

        switch (correct_answer){
            case 1:
                bttn1.setText(list.get(firstButton).getName());
                do {
                    secondButton = r.nextInt(list.size());
                }while(secondButton == firstButton);

                do {
                    thirdButton = r.nextInt(list.size());
                }while(thirdButton == firstButton || thirdButton == secondButton);

                do {
                    fourtButton = r.nextInt(list.size());
                }while(fourtButton == firstButton || fourtButton == secondButton || fourtButton == thirdButton);
                bttn2.setText(list.get(secondButton).getName());
                bttn3.setText(list.get(thirdButton).getName());
                bttn4.setText(list.get(fourtButton).getName());

                break;
            case 2:
                bttn2.setText(list.get(firstButton).getName());
                do {
                    secondButton = r.nextInt(list.size());
                }while(secondButton == firstButton);

                do {
                    thirdButton = r.nextInt(list.size());
                }while(thirdButton == firstButton || thirdButton == secondButton);

                do {
                    fourtButton = r.nextInt(list.size());
                }while(fourtButton == firstButton || fourtButton == secondButton || fourtButton == thirdButton);
                bttn1.setText(list.get(secondButton).getName());
                bttn3.setText(list.get(thirdButton).getName());
                bttn4.setText(list.get(fourtButton).getName());


                break;
            case 3:
                bttn3.setText(list.get(firstButton).getName());
                do {
                    secondButton = r.nextInt(list.size());
                }while(secondButton == firstButton);

                do {
                    thirdButton = r.nextInt(list.size());
                }while(thirdButton == firstButton || thirdButton == secondButton);

                do {
                    fourtButton = r.nextInt(list.size());
                }while(fourtButton == firstButton || fourtButton == secondButton || fourtButton == thirdButton);
                bttn2.setText(list.get(secondButton).getName());
                bttn1.setText(list.get(thirdButton).getName());
                bttn4.setText(list.get(fourtButton).getName());
                break;
            case 4:

                bttn4.setText(list.get(firstButton).getName());
                do {
                    secondButton = r.nextInt(list.size());
                }while(secondButton == firstButton);

                do {
                    thirdButton = r.nextInt(list.size());
                }while(thirdButton == firstButton || thirdButton == secondButton);

                do {
                    fourtButton = r.nextInt(list.size());
                }while(fourtButton == firstButton || fourtButton == secondButton || fourtButton == thirdButton);
                bttn2.setText(list.get(secondButton).getName());
                bttn3.setText(list.get(thirdButton).getName());
                bttn1.setText(list.get(fourtButton).getName());
                break;
        }
    }

}
