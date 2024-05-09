// package com.avertapest.core.service.json.masterlist;

// import java.io.File;
// import java.io.IOException;
// import java.util.List;

// import com.avertapest.App;
// import com.avertapest.core.model.report.verbiage.Verbiage;
// import com.avertapest.core.service.app.Dir;
// import com.avertapest.core.service.json.JsonModule;
// import com.fasterxml.jackson.databind.JsonNode;

// import dev.sol.service.JsonService;
// import javafx.collections.FXCollections;
// import javafx.collections.ObservableList;

// public class Verbiages {

//     private static File MASTER_FILE = new File(Dir.MASTERLIST_DIR + "verbiage.masterlist.json");

//     private static ObservableList<Verbiage> _list(File file) throws IOException {
//         String json = JsonService.readFromFile(file);

//         if (json != null) {
//             JsonNode node = JsonService.parse(json, JsonModule.MAPPER);
//             List<Verbiage> list = JsonService.parseList(node, Verbiage.class, JsonModule.MAPPER);
//             return FXCollections.observableArrayList(list);
//         }
//         return FXCollections.observableArrayList();
//     }

//     public static ObservableList<Verbiage> masterlist() throws Exception {
//         return _list(MASTER_FILE);
//     }

//     public static void saveToFile(App app) throws IOException {
//         JsonService.saveToFile(Dir.TEMP_DIR, app.verbiage_masterlist(), MASTER_FILE, JsonModule.MAPPER);
//     }
// }
