// package com.avertapest.core.service.json.masterlist;

// import java.io.File;
// import java.io.IOException;
// import java.util.List;

// import com.avertapest.App;
// import com.avertapest.core.service.app.Dir;
// import com.avertapest.core.service.json.JsonService;
// import com.fasterxml.jackson.databind.JsonNode;

// import javafx.collections.FXCollections;
// import javafx.collections.ObservableList;

// public class DiagramLabels {
//     private static File MASTER_FILE = new File(Dir.MASTERLIST_DIR + "diagram.masterlist.json");

//     public static ObservableList<String> masterlist() throws IOException {
//         String json = JsonService.readFromFile(MASTER_FILE);
//         if (json != null) {
//             JsonNode node = JsonService.parse(json);
//             List<String> list = JsonService.parseList(node, String.class);
//             return FXCollections.observableArrayList(list);
//         }
//         return FXCollections.observableArrayList();
//     }

//     public static void saveToFile(App app) throws IOException {
//         JsonService.saveToFile(app.diagramlabel_masterlist(), MASTER_FILE);
//     }
// }
