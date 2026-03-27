package com.example.coach.view;
import android.graphics.Color;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.coach.R;
import com.example.coach.contract.ICalculView;
import com.example.coach.model.Profil;
import com.example.coach.presenter.CalculPresenter;

public class CalculActivity extends AppCompatActivity implements ICalculView {
    // Champs de saisie utilisateur
    private EditText txtPoids, txtTaille, txtAge;
    // Boutons radio pour le sexe
    private RadioButton rdHomme, rdFemme;
    // Affichage du résultat
    private TextView lblIMG;
    private ImageView imgSmiley;
    // Bouton de calcul
    private Button btnCalc;
    // Lien avec la logique métier (Presenter)
    private CalculPresenter presenter;

    /**
     * Méthode appelée à la création de l'activité
     * Initialise l'interface utilisateur et configure l'affichage en plein écran
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Active l'affichage plein écran moderne
        EdgeToEdge.enable(this);
        // Charge le layout XML
        setContentView(R.layout.activity_calcul);
        // Gestion des marges système (barre statut/navigation)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Initialisation globale
        init();
    }

    /**
     * Initialise les composants et les comportements
     */
    private void init(){
        chargeObjetsGraphiques(); // Récupération des éléments UI
        presenter = new CalculPresenter(this); // Création du presenter
        btnCalc.setOnClickListener(v -> btnCalc_clic()); // Association du bouton avec son action
        recupProfil();
    }

    /**
     * Associe les variables Java aux composants du layout XML
     */
    private void chargeObjetsGraphiques(){
        txtPoids = findViewById(R.id.txtPoids);
        txtTaille = findViewById(R.id.txtTaille);
        txtAge = findViewById(R.id.txtAge);
        rdHomme = findViewById(R.id.rdHomme);
        rdFemme = findViewById(R.id.rdFemme);
        lblIMG = findViewById(R.id.lblIMG);
        imgSmiley = findViewById(R.id.imgSmiley);
        btnCalc = findViewById(R.id.btnCalc);
    }

    /**
     * Méthode appelée lors du clic sur le bouton calcul
     * Récupère les données, les valide puis lance le calcul
     */
    private void btnCalc_clic(){
        Integer poids = 0, taille = 0, age = 0, sexe = 0;
        try {
            // Conversion des champs texte en entiers
            poids = Integer.parseInt(txtPoids.getText().toString());
            taille = Integer.parseInt(txtTaille.getText().toString());
            age = Integer.parseInt(txtAge.getText().toString());
        } catch (Exception ignored) {
            // Si erreur de conversion (champ vide ou invalide), on ignore
        }

        // Détermination du sexe (1 = homme, 0 = femme)
        if (rdHomme.isChecked()) {
            sexe = 1;
        }

        // Vérification des champs obligatoires
        if (poids == 0 || taille == 0 || age == 0) {
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
        } else {
            // Envoie les données au Presenter pour traitement
            presenter.creerProfil(poids, taille, age, sexe);
        }
    }

    /**
     * Récupère l'éventuel profil envoyé pat une autre activity
     */
    private void recupProfil(){
        Profil profil = (Profil) getIntent().getSerializableExtra("profil");
        if (profil != null) {
            // Appel de ta méthode remplirChamps
            remplirChamps(profil.getPoids(), profil.getTaille(), profil.getAge(), profil.getSexe());
        }else{
            // récupère le dernier profil enregistré dans la BDD
            presenter.chargerDernierProfil();
        }
    }

    /**
     * Affiche le résultat du calcul (image + texte) en fonction des données retournées par le presenter
     * @param image nom de l'image drawable à afficher
     * @param img valeur de l'IMG calculée
     * @param message message associé à l'IMG
     * @param normal true si le profil est dans la norme, false sinon
     */
    @Override
    public void afficherResultat(String image, double img, String message, boolean normal) {
        // Récupère l'identifiant de l'image dans les ressources
        int imageId = getResources().getIdentifier(image, "drawable", getPackageName());
        // Si l'image existe → on l'affiche
        if (imageId != 0) {
            imgSmiley.setImageResource(imageId);
        } else {
            // Sinon image par défaut
            imgSmiley.setImageResource(R.drawable.normal); // Image par défaut
        }
        // Formatage du texte affiché
        String texte = String.format("%.01f", img) + " : IMG " + message;
        // Affichage du résultat
        lblIMG.setText(texte);
        // Couleur du texte selon le résultat
        lblIMG.setTextColor(normal ? Color.GREEN : Color.RED);
    }

    /**
     * Remplit les champs avec un profil existant (appelé par le Presenter)
     * @param poids poids de la personne
     * @param taille taille de la personne
     * @param age âge de la personne
     * @param sexe (1 = homme, 0 = femme)
     */
    @Override
    public void remplirChamps(Integer poids, Integer taille, Integer age, Integer sexe) {
        // Remplissage des champs texte
        txtPoids.setText(poids.toString());
        txtTaille.setText(taille.toString());
        txtAge.setText(age.toString());
        // Sélection du bon bouton radio
        if (sexe == 1) {
            rdHomme.setChecked(true);
        }else{
            rdFemme.setChecked(true);
        }
    }

    /**
     * Affiche un message toast à l'utilisateur
     * Appelé par le Presenter
     * @param message texte à afficher
     */
    @Override
    public void afficherMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}