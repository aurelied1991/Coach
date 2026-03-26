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
import com.example.coach.presenter.CalculPresenter;

public class MainActivity extends AppCompatActivity implements ICalculView {
    private EditText txtPoids, txtTaille, txtAge;
    private RadioButton rdHomme;
    private TextView lblIMG;
    private ImageView imgSmiley;
    private Button btnCalc;
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
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();
    }

    /**
     * Initialise les composants graphiques, le presenter et le listener du bouton de calcul
     */
    private void init(){
        chargeObjetsGraphiques();
        presenter = new CalculPresenter(this);
        btnCalc.setOnClickListener(v -> btnCalc_clic());
    }

    /**
     * Récupère les éléments de l'interface graphique à partir du layout XML
     */
    private void chargeObjetsGraphiques(){
        txtPoids = findViewById(R.id.txtPoids);
        txtTaille = findViewById(R.id.txtTaille);
        txtAge = findViewById(R.id.txtAge);
        rdHomme = findViewById(R.id.rdHomme);
        lblIMG = findViewById(R.id.lblIMG);
        imgSmiley = findViewById(R.id.imgSmiley);
        btnCalc = findViewById(R.id.btnCalc);
    }

    /**
     * Récupère les données saisies par l'utilisateur, vérifie leur validité puis lance le calcul via le presenter
     */
    private void btnCalc_clic(){
        Integer poids = 0, taille = 0, age = 0, sexe = 0;
        try {
            // Conversion des champs texte en entiers
            poids = Integer.parseInt(txtPoids.getText().toString());
            taille = Integer.parseInt(txtTaille.getText().toString());
            age = Integer.parseInt(txtAge.getText().toString());
        } catch (Exception ignored) {}

        // Détermination du sexe
        if (rdHomme.isChecked()) {
            sexe = 1;
        }

        // Vérification des champs
        if (poids == 0 || taille == 0 || age == 0) {
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
        } else {
            // Appel au presenter pour effectuer le calcul
            presenter.creerProfil(poids, taille, age, sexe);
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
        // Récupération de l'image correspondante
        int imageId = getResources().getIdentifier(image, "drawable", getPackageName());
        if (imageId != 0) {
            imgSmiley.setImageResource(imageId);
        } else {
            imgSmiley.setImageResource(R.drawable.normal); // Image par défaut
        }
        // Construction et affichage du texte
        String texte = String.format("%.01f", img) + " : IMG " + message;
        lblIMG.setText(texte);
        // Couleur du texte selon le résultat
        lblIMG.setTextColor(normal ? Color.GREEN : Color.RED);
    }
}