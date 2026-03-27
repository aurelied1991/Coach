package com.example.coach.contract;

/**
 * Interface de la vue pour l'affichage des résultats d'IMG
 * Elle définit les actions que le Presenter peut demander à l'interface graphique
 * Toute activité (ex : CalculActivity) doit implémenter cette interface.
 */
public interface ICalculView extends IAllView{
    /**
     * Affiche le résultat calculé par le presenter
     * @param image nom de l'image drawable à afficher
     * @param img valeur de l'IMG calculée
     * @param message message associé à l'IMG
     * @param normal true si le profil est dans la norme, false sinon
     */
    void afficherResultat(String image, double img, String message, boolean normal);

    /**
     * Toute activité (ex : CalculActivity) doit implémenter cette interface.
     * @param poids poids de la personne
     * @param taille taille de la personne
     * @param age âge de la personne
     * @param sexe (1 = homme, 0 = femme)
     */
    void remplirChamps(Integer poids, Integer taille, Integer age, Integer sexe);
}
