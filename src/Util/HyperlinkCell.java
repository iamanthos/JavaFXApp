package Util;

import Models.Product;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class HyperlinkCell implements  Callback<TableColumn<Product, Hyperlink>, TableCell<Product, Hyperlink>> {
 
    @Override
    public TableCell<Product, Hyperlink> call(TableColumn<Product, Hyperlink> arg) {
        TableCell<Product, Hyperlink> cell = new TableCell<Product, Hyperlink>() {
            @Override
            protected void updateItem(Hyperlink Product, boolean empty) {
                setGraphic(Product);
            }
        };
        return cell;
    }
}
