package com.ndp.triplay;

import java.io.Serializable;

/**
 * Created by NatavanMirzayeva on 9/2/2017.
 */

public class Character implements Serializable {
    private String characterName;
    private int resim;
    private String url;



    public int getResim() {
        return resim;
    }

    public String getUrl() {
        return url;
    }

    public void setResim(int resim) {
        this.resim = resim;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

     public Character(){}

    public Character(String characterName,int resim)
    {
        this.resim = resim;
        this.characterName = characterName;

    }
}
