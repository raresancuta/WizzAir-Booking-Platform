module ubb.scs.map.zboruri_practic {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens ubb.scs.map.zboruri_practic.Controller to javafx.fxml;
    opens ubb.scs.map.zboruri_practic to javafx.fxml;

    exports ubb.scs.map.zboruri_practic;
    exports ubb.scs.map.zboruri_practic.Controller;
    exports ubb.scs.map.zboruri_practic.Service;
    exports ubb.scs.map.zboruri_practic.Domain;
    exports ubb.scs.map.zboruri_practic.Repository;
    exports ubb.scs.map.zboruri_practic.Utils;
}