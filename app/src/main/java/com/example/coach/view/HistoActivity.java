package com.example.coach.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coach.R;
import com.example.coach.contract.IHistoView;
import com.example.coach.model.Profil;
import com.example.coach.presenter.HistoPresenter;

import java.util.List;

public class HistoActivity extends AppCompatActivity implements IHistoView {

    private HistoPresenter presenter; // Presenter qui gère la logique historique

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Mode plein écran
        EdgeToEdge.enable(this);
        // Charge le layout
        setContentView(R.layout.activity_histo);
        // Ajuste les marges selon les barres système
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Initialisation globale
        init();
    }

    /**
     * Initialise le Presenter et charge la liste des profils
     */
    private void init() {
        presenter = new HistoPresenter(this);
        presenter.chargerProfils(); // Appel API pour récupérer tous les profils
    }

    /**
     * Affiche la liste des profils dans un RecyclerView
     * @param profils liste de profils à afficher
     */
    @Override
    public void afficherListe(List profils) {
        if (profils != null){
            RecyclerView lstHisto = (RecyclerView) findViewById(R.id.lstHisto);
            // Adaptateur du RecyclerView (affichage des profils)
            HistoListAdapter adapter = new HistoListAdapter(profils, HistoActivity.this);
            lstHisto.setAdapter(adapter);
            lstHisto.setLayoutManager(new LinearLayoutManager(HistoActivity.this));
        }
    }

    /**
     * Méthode permettant le transfert du profil vers une activity
     * Appelé quand l'utilisateur clique sur un profil dans la liste
     * @param profil
     */
    @Override
    public void transfertProfil(Profil profil) {
        Intent intent = new Intent(HistoActivity.this, CalculActivity.class);
        intent.putExtra("profil", profil); // passe le profil à CalculActivity
        startActivity(intent);
    }

    /**
     * Affiche un message Toast
     * @param message texte à afficher
     */
    @Override
    public void afficherMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}