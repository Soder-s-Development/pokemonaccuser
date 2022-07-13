package com.juliano.app.servie;

import java.util.Random;

public interface GerarCod {

    Random rand = new Random();

    public static Integer getRandonInt(int max){
        return rand.nextInt(max);
    }
}
