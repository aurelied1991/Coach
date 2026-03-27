package com.example.coach.model;

import java.util.Date;

/**
 * Classe représentant un profil utilisateur pour le calcul de l'IMG
 */
public class Profil {
    // Valeurs minimales et maximales de l'IMG selon le sexe
    private static final int minFemme = 25;
    private static final int maxFemme = 30;
    private static final int minHomme = 15;
    private static final int maxHomme = 20;

    // Message et image correspondant au résultat de l'IMG
    private static final String[] message = {"trop faible", "normal", "trop élevé"};
    private static final String[] image = {"maigre", "normal", "graisse"};

    // Données personnelles de l'utilisateur
    private Integer poids;
    private Integer taille;
    private Integer age;
    private Integer sexe;
    private Date dateMesure;

    // Résultats calculés
    private double img;
    private int indice;

    /**
     * Constructeur du profil et calcul automatique de l'IMG et de l'indice
     * @param poids
     * @param taille
     * @param age
     * @param sexe
     */
    public Profil(Integer poids, Integer taille, Integer age, Integer sexe, Date dateMesure) {
        this.poids = poids;
        this.taille = taille;
        this.age = age;
        this.sexe = sexe;
        this.dateMesure = dateMesure;
        this.img = calculImg();
        this.indice = calculIndice();
    }

    public Integer getPoids(){
        return poids;
    }

    public Integer getTaille(){
        return taille;
    }

    public Integer getAge(){
        return age;
    }

    public Integer getSexe(){
        return sexe;
    }

    public Date getDateMesure() {
        return dateMesure;
    }

    /**
     * Retourne l'Indice de Masse Grasse (IMG) calculé pour le profil
     * @return
     */
    public double getImg() {
        return img;
    }

    /**
     * Retourne le message correspondant à l'IMG du profil
     * @return
     */
    public String getMessage() {
        return message[indice];
    }

    /**
     * Retourne le nom de l'image associée à l'état de l'IMG.
     * @return
     */
    public String getImage(){
        return image[indice];
    }

    /**
     * Indique si l'IMG du profil est dans la norme
     * @return
     */
    public boolean normal(){
        return indice == 1;
    }

    /**
     * Calcul l'IMG selon la formule standard
     * @return
     */
    private double calculImg(){
        double tailleMetres = taille / 100.0;
        return (1.2 * poids / (tailleMetres * tailleMetres))
                + (0.23 * age)
                - (10.83 * sexe)
                - 5.4;
    }

    /**
     * Détermine l'indice de l'IMG
     * @return
     */
    private int calculIndice(){
        int min = (sexe == 0) ? minFemme : minHomme;
        int max = (sexe == 0) ? maxFemme : maxHomme;
        if (img > max) {
            return 2; // au-dessus de la norme
        }
        if (img >= min) {
            return 1; // dans la norme
        }
        return 0; // en dessous
    }
}
