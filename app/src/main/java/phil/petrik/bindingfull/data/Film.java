package phil.petrik.bindingfull.data;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Film {
    private Integer id;
    private String cim;
    private String kategoria;
    private int hossz;
    private int ertekeles;

    public Film(Integer id, String cim, String kategoria, int hossz, int ertekeles) {
        this.id = id;
        this.cim = cim;
        this.kategoria = kategoria;
        this.hossz = hossz;
        this.ertekeles = ertekeles;
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

    public int getErtekeles() {
        return ertekeles;
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

    public void setErtekeles(int ertekeles) {
        this.ertekeles = ertekeles;
    }

    @Override
    public String toString() {
        return "" + id + cim + kategoria + hossz + ertekeles;
    }

    public static Film getFilm(int id) throws IOException {
        final String[] content = new String[1];
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(WebDoctor.getCall("film").second));
                    StringBuilder boby = new StringBuilder();
                    String line = null;
                    line = bufferedReader.readLine();
                    while (line != null){
                        boby.append(line);
                        line = bufferedReader.readLine();
                    }
                    content[0] = boby.toString();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        Gson gson = new Gson();
        return gson.fromJson(content[0],Film.class);
    }

    public static Film[] getFilms() throws IOException {
        final String[] content = new String[1];
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(WebDoctor.getCall("film").second));
                    StringBuilder boby = new StringBuilder();
                    String line = null;
                    line = bufferedReader.readLine();
                    while (line != null){
                        boby.append(line);
                        line = bufferedReader.readLine();
                    }
                    content[0] = boby.toString();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        Gson gson = new Gson();
        return gson.fromJson(content[0],Film[].class);
    }
}
