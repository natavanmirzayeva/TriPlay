package com.ndp.triplay;

/**
 * Created by Murat Kaçmaz on 5.09.2017.
 */

public class CountryItem {
    String name;
    int image;

    public CountryItem(String name, int image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public int getImage() {
        return image;
    }
}
