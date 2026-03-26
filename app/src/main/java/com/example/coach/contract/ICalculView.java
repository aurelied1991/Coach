package com.example.coach.contract;

/**
 * Interface de la vue pour l'affichage des résultats d'IMG
 * Toute activité qui veut afficher un profil doit implémenter cette interface
 */
public interface ICalculView {
    /**
     * Affiche le résultat calculé par le presenter
     * @param image nom de l'image drawable à afficher
     * @param img valeur de l'IMG calculée
     * @param message message associé à l'IMG
     * @param normal true si le profil est dans la norme, false sinon
     */
    void afficherResultat(String image, double img, String message, boolean normal);
}
