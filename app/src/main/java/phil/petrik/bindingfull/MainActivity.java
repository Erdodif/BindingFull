package phil.petrik.bindingfull;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import phil.petrik.bindingfull.data.Film;
import phil.petrik.bindingfull.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    public Film film;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        film = new Film(null, "Menő cím", "Kategória123", 100, 1);
       try {
            binding.setFilm(Film.getFilm(1));
            //binding.setFilm(film);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}