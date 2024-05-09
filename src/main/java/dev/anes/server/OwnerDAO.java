package dev.anes.server;

import java.sql.SQLException;
import java.sql.Types;

import javax.sql.rowset.CachedRowSet;

import dev.anes.App;
import dev.anes.core.model.Owner;
import dev.anes.service.db.DBCommand;
import dev.anes.service.db.DBParam;
import dev.anes.service.db.DBService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class OwnerDAO {

    private static final String TABLE = "owner";
    private static final String DATABASE = "lending";

    public static int getLastId() {
        String sql = "SELECT MAX(ownerID) AS last_id FROM " + TABLE;
        CachedRowSet crs = DBService.QUERY(DATABASE, TABLE, sql, new DBParam[] {});

        try {
            if (crs.next())
                return crs.getInt("last_id");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;

    }

    public static ObservableList<Owner> getList(App app) throws SQLException {
        ObservableList<Owner> list = FXCollections.observableArrayList();
        CachedRowSet crs = DBCommand.READ(DATABASE, TABLE, new DBParam[] {});

        while (crs.next()) {
            Owner owner = _data(crs, app);
            if (owner != null) {
                list.add(owner);
            }
        }

        return list;
    }

    public static void insert(Owner owner) {
        DBCommand.CREATE(DATABASE, TABLE, _params(owner).toArray(new DBParam[] {}));
    }

    public static void remove(Owner owner) {
        DBCommand.DELETE(DATABASE, TABLE, new DBParam(Types.INTEGER, "ownerID", owner.getOwnerID()));
    }

    public static void update(Owner owner) {
        DBCommand.UPDATE(DATABASE, TABLE, new DBParam(Types.INTEGER, "ownerID", owner.getOwnerID()),
                _params(owner).toArray(new DBParam[] {}));
    }

    private static Owner _data(CachedRowSet crs, App app) throws SQLException {
        int ownerID = crs.getInt("ownerID");
        String userName = crs.getString("userName");
        String passWord = crs.getString("password");
        String firstName = crs.getString("firstName");
        String lastName = crs.getString("lastName");
        int gender = crs.getInt("gender");
        String email = crs.getString("email");
        String phoneNumber = crs.getString("phoneNumber");

        return new Owner(ownerID, userName, passWord, firstName, lastName, gender, email, phoneNumber);

    }

    private static ObservableList<DBParam> _params(Owner owner) {
        ObservableList<DBParam> parameters = FXCollections.observableArrayList();
        parameters.add(new DBParam(Types.INTEGER, "ownerID", owner.getOwnerID()));
        parameters.add(new DBParam(Types.VARCHAR, "userName", owner.getUsername()));
        parameters.add(new DBParam(Types.VARCHAR, "password", owner.getPassword()));
        parameters.add(new DBParam(Types.VARCHAR, "firstName", owner.getFirstname()));
        parameters.add(new DBParam(Types.VARCHAR, "lastName", owner.getLastname()));
        parameters.add(new DBParam(Types.INTEGER, "gender", owner.getGender()));
        parameters.add(new DBParam(Types.VARCHAR, "email", owner.getEmail()));
        parameters.add(new DBParam(Types.VARCHAR, "phoneNumber", owner.getPhoneNumber()));

        return parameters;

    }

}
