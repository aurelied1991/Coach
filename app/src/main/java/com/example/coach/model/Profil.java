package com.example.coach.model;

public class Profil {
    private static final int minFemme = 25;
    private static final int maxFemme = 30;
    private static final int minHomme = 15;
    private static final int maxHomme = 20;

    private static final String[] message = {"trop faible", "normal", "trop élevé"};
    private static final String[] image = {"maigre", "normal", "graisse"};
    private Integer poids;
    private Integer taille;
    private Integer age;
    private Integer sexe;
    private double img;
    private int indice;

    public Profil(Integer poids, Integer taille, Integer age, Integer sexe) {
        this.poids = poids;
        this.taille = taille;
        this.age = age;
        this.sexe = sexe;
        this.img = calculImg();
        this.indice = calculIndice();
    }

    public double getImg() {
        return img;
    }
    public String getMessage() {
        return message[indice];
    }

    public String getImage(){
        return image[indice];
    }

    public boolean normal(){
        return indice == 1;
    }

    private double calculImg(){
        double tailleMetres = taille / 100.0;
        return (1.2 * poids / (tailleMetres * tailleMetres))
                + (0.23 * age)
                - (10.83 * sexe)
                - 5.4;
    }

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
