package lk.ijse.gdse71.supermarketfx.dao;

import lk.ijse.gdse71.supermarketfx.dao.custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;
    private DAOFactory(){

    }
    public static DAOFactory getDaoFactory(){
        return (daoFactory==null)?(daoFactory=new DAOFactory()):daoFactory;
    }
    public enum DAOTypes{
        CUSTOMER,ITEM,ORDER,ORDER_DETAIL,QUERY
    }

    // superdao eken inherit una class withrai use krnn puluwn
    //warning nathi krnna me annotation eka use krnwa...
    @SuppressWarnings("unchecked")
    public <T extends SuperDAO>T getDao(DAOTypes daoTypes){
        // casting
        switch (daoTypes){
            case CUSTOMER:
                return (T) new CustomerDAOImpl();
            case ITEM:
                return (T) new ItemDAOImpl();
            case ORDER:
                return (T) new OrderDAOImpl();
            case ORDER_DETAIL:
                return (T) new OrderDetailsDAOImpl();
            case QUERY:
                return (T) new QueryDAOImpl();
            default:
                return null;
        }
    }
}
