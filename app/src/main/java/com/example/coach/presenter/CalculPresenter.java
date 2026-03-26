package com.example.coach.presenter;
import com.example.coach.contract.ICalculView;
import com.example.coach.model.Profil;


public class CalculPresenter {
    private ICalculView vue;
    private Profil profil;

    public CalculPresenter(ICalculView vue) {
        this.vue = vue;
    }
    public void creerProfil(Integer poids, Integer taille, Integer age, Integer sexe) {
        profil = new Profil(poids, taille, age, sexe);

        // On pousse les résultats vers la vue
        vue.afficherResultat(
                profil.getImage(),
                profil.getImg(),
                profil.getMessage(),
                profil.normal()
        );
    }
}
