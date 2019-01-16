
package com.drmmx.devmax.pokergame.model;

import com.google.gson.annotations.SerializedName;

public class TextDatum {

    @SerializedName("Open")
    private String open;
    @SerializedName("Openlink")
    private String openLink;

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getOpenLink() {
        return openLink;
    }

    public void setOpenLink(String openLink) {
        this.openLink = openLink;
    }

}
