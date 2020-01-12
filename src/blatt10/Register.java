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

    public Map<Coin, Integer> getChange(Map<Coin, Integer> given, int price) {
        if (isValidPayment(given, price)) {
            addMapOfCoins(given); // Zubuchen der Zahlung
            int change = getCentValue(given) - price;
            return removeMinimalCoinMap(change);
        }
        return null;
    }

    private Map<Coin, Integer> removeMinimalCoinMap(int change) {
        Map<Coin, Integer> result = new HashMap<>();
        // Von der größten zur kleinsten Münze
        for (Coin c : Coin.values()) {
            if (register.get(c) == null) continue;
            // Falls die Münze vorhanden und der Restbetrag groß genug
            while((register.get(c)>0) && (change >= c.getValue())){
                change -= c.getValue(); // Rest reduzieren
                addCoinToMap(result, c); // Münze in das Rückgaberegister
                removeCoinFromMap(this.register, c); // Münze aus Kasse entfernen
            }
        }
        return result;
    }

    private static void addCoinToMap(Map<Coin, Integer> map,  Coin c) {
        if (map.containsKey(c))
            map.put(c, map.get(c)+1);
        else
            map.put(c, 1);
    }

    private static void removeCoinFromMap(Map<Coin, Integer> map,  Coin c) {
        if (map.containsKey(c)) {
            map.put(c, map.get(c) - 1);
            if (map.get(c) <= 0)
                map.remove(c);
        }
        else
            throw new IllegalArgumentException("Removing not existing Coin");
    }


    private void addMapOfCoins(Map<Coin, Integer> given) {
        for (Coin c : given.keySet())
            if (given.get(c)>0)
                this.addCoins(given.get(c), c);
    }

    private boolean isValidPayment(Map<Coin, Integer> given, int price) {
        if(given == null)
            throw new IllegalArgumentException();
        if(price<=0)
            throw new IllegalArgumentException("price must be positive");
        if(getCentValue(given)<price)
            throw new NotEnoughChangeException("");
        return true;
    }


    public static void main(String[] args) {
        Register kasse = new Register();
        kasse.addCoins(10,Coin.ONE);
        kasse.addCoins(20,Coin.TWO);
        kasse.addCoins(12,Coin.FIVE);
        kasse.addCoins(15,Coin.TEN);
        kasse.addCoins(3,Coin.TWENTY);
        kasse.addCoins(2,Coin.FIFTY);
        kasse.addCoins(1,Coin.ONE_HUNDRED);
        kasse.addCoins(2,Coin.TWO_HUNDRED);

        Map<Coin, Integer> zahlung = new HashMap<>();
        zahlung.put(Coin.TWO_HUNDRED, 10);

        Map<Coin, Integer> change = kasse.getChange(zahlung, 1599 );
        System.out.println(asString(change));
    }
}

