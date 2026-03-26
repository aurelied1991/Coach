package com.example.coach.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class ProfilTest {

    private Profil profilMaigre = new Profil (45, 170, 20, 0);
    private Profil profilNormal = new Profil (70, 180, 40, 1);
    private Profil profilGras = new Profil (67, 165, 35, 0);

    @Test
    public void getImg() {
        assertEquals(17.9, profilMaigre.getImg(), 0.1);
        assertEquals(18.9, profilNormal.getImg(), 0.1);
        assertEquals(32.2, profilGras.getImg(), 0.1);
    }

    @Test
    public void getMessage() {
        assertEquals("trop faible", profilMaigre.getMessage());
        assertEquals("normal", profilNormal.getMessage());
        assertEquals("trop élevé", profilGras.getMessage());
    }

    @Test
    public void getImage() {
        assertEquals("maigre", profilMaigre.getImage());
        assertEquals("normal", profilNormal.getImage());
        assertEquals("graisse", profilGras.getImage());
    }

    @Test
    public void normal() {
        assertFalse(profilMaigre.normal());
        assertTrue(profilNormal.normal());
        assertFalse(profilGras.normal());
    }
}