package com.company;

public class Main {
    /*Класс, отведенный под решение 1 лабораторной работы курса ООП */

    public static void main(String[] args) {
        System.out.println("Простые числа в диапазоне от 2 до 100:\n");
        for (int number = 2; number < 100; number++){
            if (isPrime(number)){
                System.out.println(number);
            }
        }
    }

    public static boolean isPrime(int number){
        boolean res;
        if (number < 2){ //Если число меньше двух, то оно не является ни простым, ни составным
            res = false;
        } else {
            res = true;
            for (int i = 2; i <= Math.sqrt(number); i++){ //Поиск до корня с целью оптимизации быстродействия
                if (number % i == 0) { //Если есть делитель нацело
                    res = false;
                    break;
                }
            }
        }
        return res;
    }
}
