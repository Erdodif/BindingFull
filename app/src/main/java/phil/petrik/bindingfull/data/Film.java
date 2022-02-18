package phil.petrik.bindingfull.data;

import android.os.AsyncTask;
import android.text.method.HideReturnsTransformationMethod;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Film {
    private Integer id;
    private String cim;
    private String kategoria;
    private Integer hossz;
    private Integer ertekels;//Az api félre van gépelve, szóval...

    public static Film emptyFilm(){
        return new Film(null,null,null,null,null);
    }

    public Film(Integer id, String cim, String kategoria, Integer hossz, Integer ertekeles) {
        this.id = id;
        this.cim = cim;
        this.kategoria = kategoria;
        this.hossz = hossz;
        this.ertekels = ertekeles;
    }

    public Integer getId() {
        return id;
    }

    public String getCim() {
        return cim;
    }

    public String getKategoria() {
        return kategoria;
    }

    public Integer getHossz() {
        return hossz;
    }

    public Integer getErtekels() {
        return ertekels;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCim(String cim) {
        this.cim = cim;
    }

    public void setKategoria(String kategoria) {
        this.kategoria = kategoria;
    }

    public void setHossz(String hossz) {
        this.hossz = Integer.parseInt(hossz);
    }

    public void setHossz(int hossz) {
        this.hossz = hossz;
    }

    public void setErtekels(String ertekels) {
        this.ertekels = Integer.parseInt(ertekels);
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
        return "{ \"cim\":\"" + cim + "\", \"kategoria\":\"" + kategoria
                + "\", \"hossz\":" + hossz + ", \"ertekeles\":" + ertekels +"}";
    }
}
