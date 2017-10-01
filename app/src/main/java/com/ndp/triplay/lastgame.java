package com.ndp.sonoyun;

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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link lastgame.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link lastgame#newInstance} factory method to
 * create an instance of this fragment.
 */
public class lastgame extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Button option1;
    Button option2;
    Button option3;
    Button option4;
    ImageView imageView;
    TextView textView;

    SharedPreferences.Editor editor;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public lastgame() {
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
    public static lastgame newInstance(String param1, String param2) {
        lastgame fragment = new lastgame();
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
        View view = inflater.inflate(R.layout.fragment_lastgame, null);

        option1 = (Button) view.findViewById(R.id.option1);
        option2 = (Button) view.findViewById(R.id.option2);
        option3 = (Button) view.findViewById(R.id.option3);
        option4 = (Button) view.findViewById(R.id.option4);
        imageView = (ImageView) view.findViewById(R.id.image);
        textView = (TextView) view.findViewById(R.id.question);


        imageView.setImageResource(R.drawable.soru1);
        textView.setText("Dünyanın en eski alış-veriş merkezi ve içinden günde yarım milyon insanın geçtiği yer neresidir?");
        option1.setText("KAPALIÇARŞI");
        option2.setText("KERVANSARAY");
        option3.setText("PERA PALAS OTELİ");
        option4.setText("ALMAN ÇEŞMESİ");
        option1.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        Toast.makeText(getContext(), "Doğru Bildiniz :)", Toast.LENGTH_SHORT).show();

                        imageView.setImageResource(R.drawable.soru2);
                        textView.setText("Ünlü İngiliz yazar Agatha Cristie, Doğu Ekspresi’nde Cinayet adlı romanını İstanbul’da nerede yazmıştır?");
                        option1.setText("KAPALIÇARŞI");
                        option2.setText("KERVANSARAY");
                        option3.setText("PERA PALAS OTELİ");
                        option4.setText("ALMAN ÇEŞMESİ");
                        option1.setOnClickListener(
                                new View.OnClickListener() {
                                    public void onClick(View view) {
                                        Toast.makeText(getContext(), "Yanlış Cevap", Toast.LENGTH_SHORT).show();

                                    }
                                });
                        option2.setOnClickListener(
                                new View.OnClickListener() {
                                    public void onClick(View view) {
                                        Toast.makeText(getContext(), "Yanlış Cevap", Toast.LENGTH_SHORT).show();

                                    }
                                });
                        option3.setOnClickListener(
                                new View.OnClickListener() {
                                    public void onClick(View view) {
                                        Toast.makeText(getContext(), "Doğru Bildiniz :)", Toast.LENGTH_SHORT).show();

                                        imageView.setImageResource(R.drawable.soru3);
                                        textView.setText("Mermerleri ile değerli taşları Almanya’da işlenmiş ve parçalar halinde gemi ile İstanbul’a getirilmiş olan çeşme hangisidir?");
                                        option1.setText("KAPALIÇARŞI");
                                        option2.setText("PERA PALAS OTELİ");
                                        option3.setText("ANADOLU HİSARI");
                                        option4.setText("ALMAN ÇEŞMESİ");

                                        option1.setOnClickListener(
                                                new View.OnClickListener() {
                                                    public void onClick(View view) {
                                                        Toast.makeText(getContext(), "Yanlış Cevap", Toast.LENGTH_SHORT).show();

                                                    }
                                                });
                                        option2.setOnClickListener(
                                                new View.OnClickListener() {
                                                    public void onClick(View view) {
                                                        Toast.makeText(getContext(), "Yanlış Cevap", Toast.LENGTH_SHORT).show();

                                                    }
                                                });
                                        option3.setOnClickListener(
                                                new View.OnClickListener() {
                                                    public void onClick(View view) {
                                                        Toast.makeText(getContext(), "Yanlış Cevap", Toast.LENGTH_SHORT).show();

                                                    }
                                                });
                                        option4.setOnClickListener(
                                                new View.OnClickListener() {
                                                    public void onClick(View view) {
                                                        Toast.makeText(getContext(), "Doğru Bildiniz", Toast.LENGTH_SHORT).show();

                                                    }
                                                });


                                    }
                                });
                        option4.setOnClickListener(
                                new View.OnClickListener() {
                                    public void onClick(View view) {
                                        Toast.makeText(getContext(), "Yanlış Cevap", Toast.LENGTH_SHORT).show();

                                    }
                                });

                    }
                });
        option2.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        Toast.makeText(getContext(), "Yanlış Cevap", Toast.LENGTH_SHORT).show();

                    }
                });
        option3.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        Toast.makeText(getContext(), "Yanlış Cevap", Toast.LENGTH_SHORT).show();

                    }
                });
        option4.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        Toast.makeText(getContext(), "Yanlış Cevap", Toast.LENGTH_SHORT).show();

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
        if (context instanceof lastgame.OnFragmentInteractionListener) {
            mListener = (lastgame.OnFragmentInteractionListener) context;
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
