package lk.ijse.gdse71.supermarketfx.bo;

import lk.ijse.gdse71.supermarketfx.bo.custom.impl.CustomerBOImpl;
import lk.ijse.gdse71.supermarketfx.bo.custom.impl.ItemBOImpl;
import lk.ijse.gdse71.supermarketfx.bo.custom.impl.PlacePlaceOrderBOImpl;

public class BOFactory {
    private static BOFactory boFactory;
    private BOFactory(){

    }
    public static BOFactory getBoFactory(){
        return (boFactory==null)?(boFactory=new BOFactory()):boFactory;
    }
    public enum BOTypes{
        CUSTOMER,ITEM,ORDER
    }
    @SuppressWarnings("unchecked")
    public <T> T getBO(BOTypes boTypes){
        switch (boTypes){
            case CUSTOMER:
                return(T) new CustomerBOImpl();
            case ITEM:
                return(T) new ItemBOImpl();
            case ORDER:
                return(T) new PlacePlaceOrderBOImpl();
            default:
                return null;
        }
    }
}
