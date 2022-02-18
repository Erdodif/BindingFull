package phil.petrik.bindingfull;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;

import java.io.IOException;

import phil.petrik.bindingfull.data.Film;
import phil.petrik.bindingfull.data.RequestTask;
import phil.petrik.bindingfull.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    public Film film;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        film = new Film(null, "Menő cím", "Kategória123", 100, 1);
        //setFilms();
        binding.buttonSync.setOnClickListener(($) -> {
            setFilms();
        });
        binding.buttonClose.setOnClickListener(($) -> {
            binding.layoutFilmInspector.setVisibility(View.GONE);
        });
        binding.buttonCloseEditor.setOnClickListener(($) -> {
            binding.layoutFilmEditor.setVisibility(View.GONE);
        });
        binding.buttonSync.callOnClick();
        binding.buttonAlter.setOnClickListener(($) -> {
            binding.textInputCim.getEditText().setText(Editable.Factory.getInstance().newEditable(film.getCim()));
            binding.textInputKategoria.getEditText().setText(Editable.Factory.getInstance().newEditable(film.getKategoria()));
            binding.textInputHossz.getEditText().setText(Editable.Factory.getInstance().newEditable(film.getHossz().toString()));
            binding.textInputErtekeles.getEditText().setText(Editable.Factory.getInstance().newEditable(film.getErtekels().toString()));
            binding.layoutFilmEditor.setVisibility(View.VISIBLE);
            binding.layoutFilmInspector.setVisibility(View.GONE);
        });
        binding.buttonSend.setOnClickListener(($) -> {
            film.setCim(binding.textInputCim.getEditText().getText().toString());
            film.setKategoria(binding.textInputKategoria.getEditText().getText().toString());
            film.setHossz(binding.textInputHossz.getEditText().getText().toString());
            film.setErtekels(binding.textInputErtekeles.getEditText().getText().toString());
            sendFilm(binding.getFilm());
        });
    }

    private void sendFilm(Film film) {
        if (film.getId() == null) {
            try {
                RequestTask requestTask = new RequestTask("/film", "POST", film.toJson());
                requestTask.setLastTask(new Runnable() {
                    @Override
                    public void run() {
                        if (requestTask.getResponse().getCode() < 300) {
                            Toast.makeText(MainActivity.this, "Sikeres felvétel!", Toast.LENGTH_SHORT).show();
                            binding.layoutFilmEditor.setVisibility(View.GONE);
                            return;
                        }
                        Log.d("Hívás / " + requestTask.getResponse().getCode(), requestTask.getResponse().getContent());
                        Toast.makeText(MainActivity.this, "Sikertelen feltöltés!", Toast.LENGTH_SHORT).show();
                    }
                });
                requestTask.execute();
                setFilms();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Módosítás");
        alertDialog.setMessage("Elvégzi a módosításokat?");
        alertDialog.setPositiveButton("Igen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    RequestTask requestTask = new RequestTask("/film/" + film.getId(), "PATCH", film.toJson());
                    requestTask.setLastTask(new Runnable() {
                        @Override
                        public void run() {
                            if (requestTask.getResponse().getCode() < 300) {
                                Toast.makeText(MainActivity.this, "Sikeresen módosítva!", Toast.LENGTH_SHORT).show();
                                binding.layoutFilmEditor.setVisibility(View.GONE);
                                return;
                            }
                            Log.d("Hívás / " + requestTask.getResponse().getCode(), requestTask.getResponse().getContent());
                            Toast.makeText(MainActivity.this, "Sikertelen módosítás!", Toast.LENGTH_SHORT).show();
                        }
                    });
                    requestTask.execute();
                    setFilms();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        alertDialog.setNegativeButton("Nem", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                binding.layoutFilmEditor.setVisibility(View.GONE);
                binding.setFilm(Film.emptyFilm());
            }
        });
        alertDialog.show();
    }

    private void setFilm(int id) {
        binding.layoutFilmInspector.setVisibility(View.VISIBLE);
        binding.layoutFilmEditor.setVisibility(View.GONE);
        try {
            RequestTask requestTask = new RequestTask("/film/" + id, "GET");
            requestTask.setLastTask(new Runnable() {
                @Override
                public void run() {
                    Gson gson = new Gson();
                    String content = requestTask.getResponse().getContent();
                    Log.d("Hívás / " + requestTask.getResponse().getCode(), content);
                    film =gson.fromJson(content, Film.class);
                    binding.setFilm(film);
                }
            });
            requestTask.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteFilm(int id) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Törlés");
        alertDialog.setMessage("Biztos törölni szeretné?");
        alertDialog.setPositiveButton("Igen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    RequestTask requestTask = new RequestTask("/film/" + id, "DELETE");
                    requestTask.setLastTask(new Runnable() {
                        @Override
                        public void run() {
                            if (requestTask.getResponse().getCode() < 300) {
                                Toast.makeText(MainActivity.this, "Sikeresen törölve!", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            Log.d("Hívás / " + requestTask.getResponse().getCode(), requestTask.getResponse().getContent());
                            Toast.makeText(MainActivity.this, "Sikertelen törlés!", Toast.LENGTH_SHORT).show();
                        }
                    });
                    requestTask.execute();
                    setFilms();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        alertDialog.setNegativeButton("Nem", null);
        alertDialog.show();
    }

    private void setFilms() {
        try {
            RequestTask requestTask = new RequestTask("/film", "GET");
            requestTask.setLastTask(new Runnable() {
                @Override
                public void run() {
                    binding.layoutFilms.removeAllViews();
                    Gson gson = new Gson();
                    String content = requestTask.getResponse().getContent();
                    Film[] filmek = (gson.fromJson(content, Film[].class));
                    Log.d("Hívás / " + requestTask.getResponse().getCode(), "FilmCount: " + filmek.length);
                    for (Film film : filmek) {
                        binding.layoutFilms.addView(createFilmButton(film));
                    }
                }
            });
            requestTask.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private MaterialButton createFilmButton(Film film) {
        MaterialButton buttonFilm = new MaterialButton(MainActivity.this);
        buttonFilm.setText(film.getCim());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
        );
        buttonFilm.setLayoutParams(lp);
        buttonFilm.setOnClickListener(($) -> {
            setFilm(film.getId());
        });
        buttonFilm.setOnLongClickListener(($) -> {
            deleteFilm(film.getId());
            return true;
        });
        return buttonFilm;
    }

}