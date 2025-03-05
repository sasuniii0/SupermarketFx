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
    public SuperBO getBO(BOTypes boTypes){
        switch (boTypes){
            case CUSTOMER:
                return new CustomerBOImpl();
            case ITEM:
                return new ItemBOImpl();
            case ORDER:
                return new PlacePlaceOrderBOImpl();
            default:
                return null;
        }
    }
}
