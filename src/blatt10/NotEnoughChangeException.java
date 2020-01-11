package blatt10;

public class NotEnoughChangeException extends RuntimeException {

    NotEnoughChangeException(){

    }
    NotEnoughChangeException(String fehlermeldung){
        super(fehlermeldung);
    }
}
