package phil.petrik.bindingfull.data;

import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Film {
    private Integer id;
    private String cim;
    private String kategoria;
    private int hossz;
    private int ertekels;//Az api félre van gépelve, szóval...

    public Film(Integer id, String cim, String kategoria, int hossz, int ertekeles) {
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

    public int getHossz() {
        return hossz;
    }

    public int getErtekels() {
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

    public void setHossz(int hossz) {
        this.hossz = hossz;
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

    public static Film getFilm(int id) throws IOException{
        //TODO
        final String[] content = new String[1];
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(WebDoctor.getCall("/film/"+id).second));
                    StringBuilder boby = new StringBuilder();
                    String line = bufferedReader.readLine();
                    while (line != null) {
                        boby.append(line);
                        line = bufferedReader.readLine();
                    }
                    bufferedReader.close();
                    content[0] = boby.toString();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        Gson gson = new Gson();
        return gson.fromJson(content[0], Film.class);
    }

    public static Film[] getFilms() throws IOException {
        //TODO
        final String[] content = new String[1];
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(WebDoctor.getCall("/film").second));
                    StringBuilder boby = new StringBuilder();
                    String line = null;
                    line = bufferedReader.readLine();
                    while (line != null) {
                        boby.append(line);
                        line = bufferedReader.readLine();
                    }
                    content[0] = boby.toString();
                } catch (IOException e) {
                    e.printStackTrace(System.err);
                }
            }
        });
        Gson gson = new Gson();
        return gson.fromJson(content[0], Film[].class);
    }

}
