package com.ndp.triplay;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class MessageF extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    FirebaseAuth mauth;
    Button buttonSignOut;
    private GoogleApiClient mGoogleApiClient;
    FirebaseUser fuser;
    FirebaseAuth firebaseAuth;

    private  String userID;
    FirebaseDatabase mFirebaseDatabase;
    private static final String TAG = "HomePage";
    public static final int DEFAULT_MSG_LENGTH_LIMIT = 1000;
    private ListView mMessageListView;
    private MessageAdapter mMessageAdapter;
    private ImageButton mPhotoPickerButton;
    private EditText mMessageEditText;
    private Button mSendButton, mSendButton1;
    private ChildEventListener mChildEventListener;
    public DatabaseReference mMessagesDatabaseReference;
    private String mUsername;
    Character character;
    int number = 0;
    int number1 = 0;
    private   String result;
    boolean control = false; // oyun fragmentine ne zaman gececegini belirleyen degisken
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MessageF() {

    }


    public static MessageF newInstance(String param1, String param2) {
        MessageF fragment = new MessageF();
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
        View view = inflater.inflate(R.layout.fragment_message, null);


        //buttonSignOut = (Button) view.findViewById(R.id.buttonSignOut);
        mauth = FirebaseAuth.getInstance();
        fuser = mauth.getCurrentUser();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mMessagesDatabaseReference = mFirebaseDatabase.getReference().child("messages"); //messages alanına erişmek için referans oluşturuldu




        mMessageListView = (ListView) view.findViewById(R.id.messageListView);  //mesajların görüntüleneceği listview
        //   mPhotoPickerButton = (ImageButton) findViewById(R.id.photoPickerButton);
        // mMessageEditText = (EditText) findViewById(R.id.messageEditText);
        mSendButton = (Button) view.findViewById(R.id.sendButton);     //soldaki buton
        mSendButton1 = (Button) view.findViewById(R.id.sendButton1); // sagdaki buton



        List<Chat> chatList = new ArrayList<>();   // mesaj düğümlerinin tutulacağı liste
        mMessageAdapter = new MessageAdapter(this.getContext(), R.layout.item_message, chatList);
        mMessageListView.setAdapter(mMessageAdapter);



        mSendButton.setEnabled(true);
        mSendButton1.setEnabled(true);



        mMessageAdapter.clear();

       // control(mSendButton);
        getMessageText(mSendButton,1);  //fonksiyondaki ikinci parametre veritabanındaki orderNo yu temsil ediyor(soldaki butonun orderNo su 1 den 10a kadar geçerli)


        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                save(mSendButton);

                //AŞAĞIDA DA VERİTABANINA BİŞİ YAZDIRMIYO SADECE OKUYUP BU KOD ÜZERİNDE HESAPLAYARAK Bİ SONRAKİ BUTON TEXTLERİNŞ GETİRİYO
                changeContent(mSendButton, mSendButton1);//icerik degisikligi icin fonsiyon cagirildi

                //orderno ya göre mesajı al butona yazdır

               /* Chat friendlyMessage = new
                        Chat("Oyun Başlasıın.",
                        "",9,"Oyun Başlasıın.","Gizli Kullanıcı");
                mMessagesDatabaseReference
                        .push().setValue(friendlyMessage);


               final String ID = mMessagesDatabaseReference.push().getKey();  */
           /*     FirebaseUser user = mauth.getCurrentUser();
                mMessagesDatabaseReference.child(ID).child("name").setValue(user.getEmail());
                mMessagesDatabaseReference.child(ID).child("text").setValue("iyi");*/
                //  mMessageEditText.setText("");
            }
        });


       // control(mSendButton1);
        getMessageText(mSendButton1,10);

        mSendButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save(mSendButton1);   // isminin save olduğuna kanmayın bişi kaydetmiyo :)
               // control(mSendButton1);
                changeContent(mSendButton1,mSendButton);



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
        if (context instanceof MessageF.OnFragmentInteractionListener) {
            mListener = (MessageF.OnFragmentInteractionListener) context;
        } else {

        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }

    public void save(final Button button)  // parametre olarak gelen butonun üzerine yazı yazmak için kullanıldı; hem sol hem sağ buton için geçerli
    {
        mMessagesDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String txt = button.getText().toString();
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    Chat chat1 = ds.getValue(Chat.class);
                    if(chat1.text.equals(txt))
                    {
                        FirebaseUser user = mauth.getCurrentUser();

                        chat1.text = txt; // burada da o mesajı adaptera eklemek için kullanıldı, veritabanına ekleme yok burada da
                        chat1.setName(user.getEmail()); // o anki kullanıcının ismini burdan yazdırıyorum bu adaptera gidiyo veritabanına kaydolmuyo

                        /*final MessageRelationship mRelationship = new
                                MessageRelationship(user.getEmail(), chat1.getAuthorName(),chat1.getOrderNo());
                        mFirebaseDatabase.getReference().child("messageRelationship").push().setValue(mRelationship);
                        */
                        mMessageAdapter.add(chat1);



                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



    public void getMessageText(final Button button,final int number)
    {

        mMessagesDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Character character = (Character) getArguments().getSerializable("name");
                String txt = character.getCharacterName();

                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    Chat chat1 = ds.getValue(Chat.class);
                    chat1.getOrderNo();
                    if(chat1.getAuthorName().equals(txt) && chat1.getOrderNo() == number) // mesajın order no suna ve karakter ismine göre mesaj textini aldım
                    {
                        result = chat1.getText(); // result global değişkenine ekleyip parametre olarak gelen butonun üzerine yazdırdım
                        button.setText(result);   // örneğin geliyormusuna tıkladığında o zaman başlayalım gelmesi için kullanıldı
                        //İLK AŞAMADA SAYFA İLK AÇILDIĞINDA BUTONUN ÜZERİNDEKİ YAZILAR BURDAN GELİYOR
                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });


    }

    public void control(final Button mButon)
    {
        mMessagesDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String txt = mButon.getText().toString();
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    Chat chat1 = ds.getValue(Chat.class);
                    if(chat1.text.equals(txt))
                    {
                        if(chat1.getOrderNo() > 8)
                        {
                            Toast toast = Toast.makeText(getContext(), "Oyun Vaktii", Toast.LENGTH_SHORT); // deneme amaçlı toast mesajı yazdırıldı
                            toast.show();
                            new android.os.Handler().postDelayed(

                                    new Runnable() {
                                        @Override
                                        public void run() {
                                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                            FragmentTransaction transaction = fragmentManager.beginTransaction();
                                            transaction.replace(R.id.content, new GameFragment()).commit();
                                        }
                                    },
                                    1500);

                        }
                        }
                    }


                }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void changeContent(final Button buton,final Button buton1)
    {
        //AŞAĞIDA DA VERİTABANINA BİŞİ YAZDIRMIYO SADECE OKUYUP BU KOD ÜZERİNDE HESAPLAYARAK Bİ SONRAKİ BUTON TEXTLERİNŞ GETİRİYO
        mMessagesDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String txt = buton.getText().toString();
                String txt1 = buton1.getText().toString();
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    Chat chat1 = ds.getValue(Chat.class);
                    Chat chat2 = ds.getValue(Chat.class);
                    if(chat1.text.equals(txt))
                    {
                        chat1.increase(chat1.getOrderNo());  //SONRAKİ AŞAMALAR İÇİN BUTONUN ÜZERİNE YAZILARI OrderNo larını arttırarak yazıdırılıyor.

                        if(chat1.getOrderNo() == 9 || chat1.getOrderNo() == 18)
                        {
                            control = true;
                        }
                        number = chat1.getOrderNo();

                    }
                    else if(chat2.getText().equals(txt1))
                    {
                       chat2.increase(chat2.getOrderNo());
                        if(chat2.getOrderNo() == 9 || chat2.getOrderNo() == 18)
                        {
                            control = true;
                        }
                        number1 = chat2.getOrderNo();
                    }

                }

                mMessagesDatabaseReference.orderByChild("orderNo").equalTo(number).addChildEventListener(new ChildEventListener() { // BURDA DA ORDERNO SU KAÇ HESAPLANDI İSE ONA GÖRE GETİRİCEK
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        Chat chat1 = dataSnapshot.getValue(Chat.class);
                        buton.setText(chat1.getText());
                        if(control == true)
                        {

                            new android.os.Handler().postDelayed(

                                    new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast toast = Toast.makeText(getContext(), "Oyun Alanina Gecelim", Toast.LENGTH_SHORT); // deneme amaçlı toast mesajı yazdırıldı
                                            toast.show();

                                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                            FragmentTransaction transaction = fragmentManager.beginTransaction();
                                            transaction.replace(R.id.content, new GameFragment()).commit();
                                        }
                                    },
                                    5000);

                        }


                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                        Chat chat1 = dataSnapshot.getValue(Chat.class);
                        buton.setText(chat1.getText());
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
                });


                mMessagesDatabaseReference.orderByChild("orderNo").equalTo(number1).addChildEventListener(new ChildEventListener() { // BURDA DA ORDERNO SU KAÇ HESAPLANDI İSE ONA GÖRE GETİRİCEK
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        Chat chat1 = dataSnapshot.getValue(Chat.class);
                        buton1.setText(chat1.getText());
                        if(control == true)
                        {

                            new android.os.Handler().postDelayed(

                                    new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast toast = Toast.makeText(getContext(), "Oyun Alanina Gecelim", Toast.LENGTH_SHORT); // deneme amaçlı toast mesajı yazdırıldı
                                            toast.show();

                                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                            FragmentTransaction transaction = fragmentManager.beginTransaction();
                                            transaction.replace(R.id.content, new GameFragment()).commit();
                                        }
                                    },
                                    5000);

                        }


                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                        Chat chat1 = dataSnapshot.getValue(Chat.class);
                        buton1.setText(chat1.getText());
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
                });



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }




        });

    }

     @Override
    public void onStart() {

        Character character = (Character) getArguments().getSerializable("name");
        Context context = getContext();

        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, character.getCharacterName(), duration); // deneme amaçlı toast mesajı yazdırıldı
        toast.show();
        Chat chat = new Chat();
        chat.setAuthorName(character.getCharacterName());
        chat.setResponse("Orada kimse var mı?");
        mMessageAdapter.add(chat);
       //  chat.setResponse("Lütfen cevap verin...");
       //  mMessageAdapter.add(chat);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this.getContext())
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mGoogleApiClient.connect();
        super.onStart();
    }

}
