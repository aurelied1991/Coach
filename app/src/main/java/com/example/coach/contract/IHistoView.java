package com.example.coach.contract;

import com.example.coach.model.Profil;

import java.util.List;

/**
 * Interface pour la vue historique (liste de profils).
 * Étend IAllView, donc hérite de afficherMessage().
 */
public interface IHistoView extends IAllView{
    /**
     * Affiche une liste de profils dans l'UI (ex : ListView, RecyclerView)
     * @param profils liste de profils à afficher
     */
    void afficherListe(List profils);

    /**
     * Transfert un profil sélectionné à une autre activité ou fragment
     * @param profil profil à transférer
     */
    void transfertProfil(Profil profil);
}
