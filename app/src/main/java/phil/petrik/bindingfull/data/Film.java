package phil.petrik.bindingfull.data;

import android.os.AsyncTask;
import android.text.method.HideReturnsTransformationMethod;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import phil.petrik.bindingfull.BR;

public class Film extends BaseObservable {
    @Expose(serialize = false)
    private Integer id;
    @Expose(serialize = true)
    private String cim;
    @Expose(serialize = true)
    private String kategoria;
    @Expose(serialize = true)
    private Integer hossz;
    @Expose(serialize = true)
    private Integer ertekels;//Az api félre van gépelve, szóval...

    public static Film emptyFilm(){
        return new Film(null,null,null,null,null);
    }

    public Film(Integer id, String cim, String kategoria, Integer hossz, Integer ertekels) {
        this.id = id;
        this.cim = cim;
        this.kategoria = kategoria;
        this.hossz = hossz;
        this.ertekels = ertekels;
    }

    @Bindable
    public Integer getId() {
        return id;
    }

    @Bindable
    public String getCim() {
        return cim;
    }

    @Bindable
    public String getKategoria() {
        return kategoria;
    }

    @Bindable
    public String getHosszString(){
        return hossz==null?"":hossz.toString();
    }

    public Integer getHossz() {
        return hossz;
    }

    @Bindable
    public String getErtekelsString(){
        return ertekels==null?"":ertekels.toString();
    }

    public Integer getErtekels() {
        return ertekels;
    }

    public void setId(Integer id) {
        this.id = id;
        notifyPropertyChanged(BR.id);
    }

    public void setCim(String cim) {
        this.cim = cim;
        notifyPropertyChanged(BR.cim);
    }

    public void setKategoria(String kategoria) {
        this.kategoria = kategoria;
        notifyPropertyChanged(BR.kategoria);
    }

    public void setHosszString(String hossz) {
        try {
            this.hossz = Integer.parseInt(hossz);
        }
            catch (Exception e){
            this.hossz = 0;
        }
        notifyPropertyChanged(BR.hosszString);
    }

    public void setHossz(int hossz) {
        this.hossz = hossz;
    }

    public void setErtekelsString(String ertekels) {
        try {
            this.ertekels = Integer.parseInt(ertekels);
        }
        catch (Exception e){
            this.ertekels = 0;
        }
        notifyPropertyChanged(BR.ertekelsString);
    }

    public void setErtekels(int ertekels) {
        this.ertekels = ertekels;
    }

    @NonNull
    @Override
    public String toString() {
        return "id:" + id + ", cim:" + cim + ", kategoria:" + kategoria
                + ", hossz:" + hossz + ", ertekeles:" + ertekels;
    }

    public String toJson() {
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        return gson.toJson(this);
    }
}
