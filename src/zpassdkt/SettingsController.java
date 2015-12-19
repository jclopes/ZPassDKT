package zpassdkt;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

/**
 * Created by joao on 2015/10/17.
 */
public class SettingsController implements Initializable {
  @FXML
  private TextField saltField;
  private StringProperty salt = new SimpleStringProperty(this, "salt");

  public String getSalt() {
    return salt.get();
  }

  public StringProperty saltProperty() {
    return salt;
  }

  @FXML
  public void handleApplySettingsButton(ActionEvent event) {
    Preferences preferences = Preferences.userRoot().node(Controller.class.getName());
    preferences.put("salt", saltField.getText());
    salt.set(saltField.getText());
    ((Node) event.getSource()).getScene().getWindow().hide();
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    Preferences preferences = Preferences.userRoot().node(Controller.class.getName());
    saltField.setText(preferences.get("salt", ""));
  }
}
