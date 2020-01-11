package blatt10;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class Register {
    Map<Coin, Integer> register = new HashMap<>();

    public Register() {
        register.put(Coin.ONE, 0);
        register.put(Coin.TWO, 0);
        register.put(Coin.FIVE, 0);
        register.put(Coin.TEN, 0);
        register.put(Coin.TWENTY, 0);
        register.put(Coin.FIFTY, 0);
        register.put(Coin.ONE_HUNDRED, 0);
        register.put(Coin.TWO_HUNDRED, 0);
    }



    public static final int getCentValue(Map<Coin, Integer> coins){
        int result=0;
        for(Coin key: coins.keySet()){
            result += key.getValue() * coins.get(key);
        }
        return result;
    }

    // Für die Ausgabe hilfreich
    public static final String asString(Map<Coin, Integer> coins) {
        StringBuilder sb = new StringBuilder();
        for (Coin c : Coin.values())
            sb.append("Coin " + c.getValue() + ": " + coins.get(c) + "\n");
        return sb.toString();
    }



    public void addCoins(int amount, Coin coin){
        if(amount<=0)
            throw new IllegalArgumentException("must be positive");
        else{
           amount+= register.get(coin);
           register.put(coin, amount);
        }
    }

    // Verwendung der bereits vorhandenen Methode getCentValue
    public int getCurrentValue(){
        return getCentValue(register);
    }

    public Map<Coin, Integer> getChange(Map<Coin, Integer> given, int price){
        // Verwendung eines Registers für die Rückgabe erspart die Initialisierung
        Register result = new Register();

        if(given == null)
            throw new IllegalArgumentException();
        if(price<=0)
            throw new IllegalArgumentException("price must be positive");
        if(getCentValue(given)<price)
            throw new NotEnoughChangeException("");

        // Von der größten zur kleinsten Münze
        for (Coin c : Coin.values()) {
            // Falls die Münze vorhanden und der Preis groß genug
            while((given.get(c)>0) && (price >= c.getValue())){
                price -= c.getValue(); // Preis reduzieren
                result.addCoins(1, c); // Münze in das Rückgaberegister
                given.put(c, given.get(c)-1); // Münze aus Kasse entfernen
            }
        }
        return result.register;
    }

    public static void main(String[] args) {
        Register tabea = new Register();
        tabea.addCoins(10,Coin.ONE);
        tabea.addCoins(20,Coin.TWO);
        tabea.addCoins(12,Coin.FIVE);
        tabea.addCoins(15,Coin.TEN);
        tabea.addCoins(3,Coin.TWENTY);
        tabea.addCoins(2,Coin.FIFTY);
        tabea.addCoins(1,Coin.ONE_HUNDRED);
        // tabea.addCoins(2,Coin.TWO_HUNDRED);
        System.out.println(tabea.getCurrentValue() + " Cent");

        Map<Coin, Integer> change = tabea.getChange(tabea.register, 365 );
        System.out.println(asString(change));
    }
}

