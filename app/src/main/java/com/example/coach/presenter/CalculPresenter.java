package com.example.coach.presenter;
import android.content.Context;
import com.example.coach.contract.ICalculView;
import com.example.coach.data.ProfilDAO;
import com.example.coach.model.Profil;

import java.util.Date;

/**
 * Presenter du calcul d'IMG
 * Sert d'intermédiaire entre la vue et le modèle Profil
 */
public class CalculPresenter {
    private ICalculView vue; // Interface pour afficher les résultats
    private Profil profil; // Modèle contenant les données du profil
    private ProfilDAO profilDAO;

    /**
     * Constructeur du presenter
     * @param vue vue associée pour afficher les résultats
     */
    public CalculPresenter(ICalculView vue, Context context) {
        this.vue = vue;
        this.profilDAO = new ProfilDAO(context);
    }

    /**
     * Crée un profil avec les données fournies et envoie le résultat à la vue
     * @param poids
     * @param taille
     * @param age
     * @param sexe
     */
    public void creerProfil(Integer poids, Integer taille, Integer age, Integer sexe) {
        profil = new Profil(poids, taille, age, sexe, new Date());
        profilDAO.insertProfil(profil);

        // On pousse les résultats vers la vue
        vue.afficherResultat(
                profil.getImage(), // Nom de l'image à afficher
                profil.getImg(), // Valeur IMG calculée
                profil.getMessage(), // Message associé (ex: normal, surpoids)
                profil.normal() // Indique si le profil est normal (true/false)
        );
    }

    public void chargerDernierProfil() {
        Profil profil = profilDAO.getLastProfil();
        if (profil != null) {
            vue.remplirChamps(
                    profil.getPoids(),
                    profil.getTaille(),
                    profil.getAge(),
                    profil.getSexe()
            );
        }
    }
}
