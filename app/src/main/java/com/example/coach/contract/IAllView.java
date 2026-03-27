package com.example.coach.contract;

/**
 * Interface de base pour toutes les vues (Activities/Fragments) du projet Coach.
 * Définit les méthodes communes à toutes les vues.
 */
public interface IAllView {
    /**
     * Affiche un message à l'utilisateur (ex : Toast, AlertDialog, TextView...)
     * @param message texte à afficher
     */
    void afficherMessage(String message);
}
