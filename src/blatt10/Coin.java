package blatt10;

public enum Coin {

    // Reihenfolge von der größten zur kleinsten macht die Iteration einfacher
    TWO_HUNDRED(200),
    ONE_HUNDRED(100),
    FIFTY(50),
    TWENTY(20),
    TEN(10),
    FIVE(5),
    TWO(2),
    ONE(1);

    private final int value;

    Coin(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
