package dev.anes.layout.Center.Deptor;

import dev.anes.App;
import dev.anes.core.model.Debtor;
import dev.anes.core.model.History;
import dev.anes.layout.items.HistoryDetailsitem;
import dev.anes.service.fx.observable.ObservableListMapper;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;


public class HistoryController {
    @FXML
    private Accordion historynumberaAccordion;
    private App app;
    private Debtor debtor;
    private FilteredList<History> historyFilteredList;
    private SortedList<History> historySortedList;
    private ObservableList<HistoryDetailsitem> obserloandetails;

    public void load(App app, Debtor debtor) {
        this.app = app;
        this.debtor = debtor;
        _load_fields();
        _load_bindings();
        _load_listeners();
    }

    private void _load_fields() {
        _history_number();
    }

    private void _load_bindings() {
    }

    private void _load_listeners() {
    }

    private void _history_number() {
        obserloandetails = FXCollections.observableArrayList();
        ObservableList<History> items = FXCollections.observableArrayList();

        app.getHistoryMasterList().forEach(history->{
   
            if(debtor.getDebtorID()==history.getDebtorID().getDebtorID()){
             
                items.add(history);
            }
        });
        historyFilteredList = new FilteredList<>(items);
        historySortedList = new SortedList<>(historyFilteredList);
        ObservableListMapper.mapContent(obserloandetails, historySortedList,
                History -> new HistoryDetailsitem(History, app));
        Bindings.bindContent(historynumberaAccordion.getPanes(), obserloandetails);
    }

}
