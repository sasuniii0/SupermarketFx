package lk.ijse.gdse71.supermarketfx.dao;

import lk.ijse.gdse71.supermarketfx.entity.Customer;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CrudDAO<T> extends SuperDAO {

/*
    Optional<T> get(String id)> --> return type deddi optional walin null kyla return wena eka nawattanwa..
*/
    List<T> getAll() throws SQLException;
    boolean save(T dto) throws SQLException;
    String getLastId() throws SQLException ;
    boolean delete(String id) throws SQLException ;
    boolean update(T dto) throws SQLException ;
}
