package strategyPattern;

public class IDOverFlowException extends Exception{

    private String message;

    public IDOverFlowException(String message){
        this.message = message;
    }

    @Override
    public String getMessage(){
        return this.message;
    }


}
