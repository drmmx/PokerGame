
package com.drmmx.devmax.pokergame.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TextData {

    @SerializedName("TextData")
    private List<TextDatum> textData = null;

    public List<TextDatum> getTextData() {
        return textData;
    }

    public void setTextData(List<TextDatum> textData) {
        this.textData = textData;
    }

}
