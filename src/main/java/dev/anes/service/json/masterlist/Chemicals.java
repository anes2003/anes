// package dev.ozz.service.json.masterlist;



// import java.io.File;
// import java.io.IOException;
// import java.util.List;


// import com.fasterxml.jackson.databind.JsonNode;

// import javafx.collections.FXCollections;
// import javafx.collections.ObservableList;

// public class Chemicals {

//     private static File MASTER_FILE = new File(Dir.MASTERLIST_DIR + "chemical.masterlist.json");

//     public static ObservableList<Chemical> masterlist() throws IOException {
//         String json = JsonService.readFromFile(MASTER_FILE);
//         if (json != null) {
//             JsonNode node = JsonService.parse(json);
//             List<Chemical> list = JsonService.parseList(node, Chemical.class);
//             return FXCollections.observableArrayList(list);
//         }
//         return FXCollections.observableArrayList();
//     }

//     public static void saveToFile(App app) throws IOException {
//         JsonService.saveToFile(app.chemical_masterlist(), MASTER_FILE);
//     }

// }
