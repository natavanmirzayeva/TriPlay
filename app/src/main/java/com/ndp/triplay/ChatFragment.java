package com.ndp.triplay;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ChatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Intent intent;
    private ChildEventListener mChildEventListener;
    public DatabaseReference mMessagesDatabaseReference;
    FirebaseDatabase mFirebaseDatabase;
    private CharacterAdapter characterAdapter;
    ImageView img;





    private OnFragmentInteractionListener mListener;

    public ChatFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChatFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChatFragment newInstance(String param1, String param2) {
        ChatFragment fragment = new ChatFragment();
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
        View view = inflater.inflate(R.layout.fragment_chat, null);

        img = (ImageView) view.findViewById(R.id.photoImageView);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mMessagesDatabaseReference = mFirebaseDatabase.getReference().child("characters"); // karakterler noduna ulaşmak için böyle bi referans oluşturdum

        /*final Character character = new                                   // bu yorum satırını dikkate almayın hocam, key ile veritabanında oluşturmak için kod üzerinden ekliyorum
                Character("Mehmet Öz",R.drawable.deneme);
        mMessagesDatabaseReference.push().setValue(character);*/
        // img.setImageResource(character.getResim());


        List<Character> characters = new ArrayList<>();
        final ListView listemiz=(ListView) view.findViewById(R.id.messageListView);

        characterAdapter = new CharacterAdapter(this.getContext(), R.layout.item_message,  characters);
        listemiz.setAdapter(characterAdapter);



        listemiz.setOnItemClickListener(new AdapterView.OnItemClickListener() {  //karakter üzerine tıklandığında buraya girecek

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {


                Character char1 = (Character) listemiz.getItemAtPosition(position);
                Bundle data = new Bundle();
                data.putSerializable("name",char1);
                Fragment fragment = new MessageF();
                fragment.setArguments(data);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.content, fragment).commit();
              /*  transaction.replace(R.id.content, new MessageF());
                transaction.setTransition(transaction.TRANSIT_FRAGMENT_OPEN);
                transaction.commit();*/
               // intent = new Intent(ChatFragment.this,HomePage.class);
              //  intent.putExtra("Character",  char1);    //yeni ekran açıldığında o karakterin bilgisinide almak için kullandım
              //  startActivity(intent);


            }
        });


        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s)
            {
                Character character1 = dataSnapshot.getValue(Character.class);
                characterAdapter.add(character1);  //veritabanındaki karakterleri listeler

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mMessagesDatabaseReference.addChildEventListener(mChildEventListener);




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
}
