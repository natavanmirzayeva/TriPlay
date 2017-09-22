package com.ndp.triplay;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;




import java.util.Collections;
import java.util.List;

/**
 * Created by NatavanMirzayeva on 8/26/2017.
 */


//BURADA CHAT EKRANI ÜZERİNE GELECEK MESAJLARIN NE ŞEKİLDE GÖRÜNTÜLECEĞİ GÖSTERİLCEK
public class MessageAdapter extends ArrayAdapter<Chat>
{
    public MessageAdapter(Context context, int resource, List<Chat> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.item_message, parent, false);
        }


        ImageView photoImageView = (ImageView) convertView.findViewById(R.id.photoImageView);
        final  TextView  messageTextView = (TextView) convertView.findViewById(R.id.messageTextView);
        final  TextView nameTextView = (TextView) convertView.findViewById(R.id.nameTextView);
        final  TextView authorTextView = (TextView) convertView.findViewById(R.id.authorTextView);
        final TextView responseTextView = (TextView) convertView.findViewById(R.id.responseTextView);

        final Chat message = getItem(position);


        if(message.text != "") // üzerinde mesaj yazılı butona tıkladıysa ekrana o mesajı göstersin
        {
          //  messageTextView.setVisibility(View.VISIBLE);
          //  nameTextView.setVisibility(View.VISIBLE);


            nameTextView.setText(message.getName()); // KULLANICININ İSMİ
            messageTextView.setText(message.getText()); // KULLANICININ BUTONA TIKLADIĞI TEXT

        }

        photoImageView.setVisibility(View.GONE);

        new android.os.Handler().postDelayed(

                    new Runnable() {
                        @Override
                        public void run() {
                          //  responseTextView.setVisibility(View.VISIBLE);
                          //  authorTextView.setVisibility(View.VISIBLE);

                            authorTextView.setText(message.getAuthorName()); // KARŞIDAKİ KARAKTERİN İSMİ
                            responseTextView.setText(message.getResponse()); // KARŞIDAKİ KARAKTERİN CEVABI
                        }
                    },
                    1500);



        return convertView;
    }


}
