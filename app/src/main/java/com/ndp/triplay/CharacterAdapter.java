package com.ndp.triplay;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.*;
import java.util.List;

/**
 * Created by NatavanMirzayeva on 9/2/2017.
 */

public class CharacterAdapter extends ArrayAdapter<Character>
{

    public CharacterAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Character> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.item_message, parent, false);
        }


        ImageView photoImageView = (ImageView) convertView.findViewById(R.id.photoImageView);
        final TextView messageTextView = (TextView) convertView.findViewById(R.id.messageTextView);


        final  Character character = getItem(position);




            photoImageView.setImageResource(character.getResim()); // listelenen karakterlerin ismi ve fotoğrafı gözüküyor ilk sayfa açıldığında
            messageTextView.setText(character.getCharacterName());



        return convertView;
    }

}
