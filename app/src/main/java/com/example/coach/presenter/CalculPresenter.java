package com.example.coach.presenter;
import com.example.coach.contract.ICalculView;
import com.example.coach.model.Profil;

import java.util.Date;

/**
 * Presenter du calcul d'IMG
 * Sert d'intermédiaire entre la vue et le modèle Profil
 */
public class CalculPresenter {
    private ICalculView vue; // Interface pour afficher les résultats
    private Profil profil; // Modèle contenant les données du profil

    /**
     * Constructeur du presenter
     * @param vue vue associée pour afficher les résultats
     */
    public CalculPresenter(ICalculView vue) {
        this.vue = vue;
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

        // On pousse les résultats vers la vue
        vue.afficherResultat(
                profil.getImage(), // Nom de l'image à afficher
                profil.getImg(), // Valeur IMG calculée
                profil.getMessage(), // Message associé (ex: normal, surpoids)
                profil.normal() // Indique si le profil est normal (true/false)
        );
    }
}
