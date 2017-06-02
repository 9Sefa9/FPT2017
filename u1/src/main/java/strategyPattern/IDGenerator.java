package strategyPattern;

public class IDGenerator {
    
    private static int id;
    public static long getNextID() throws IDOverFlowException{

        if(id >= 9999)
            throw new IDOverFlowException("ID: "+id+ "is not available");
        else
            return id+=1;

    }
}
