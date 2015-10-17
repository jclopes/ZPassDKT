package zpassdkt;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;


public class Controller implements Initializable {
  @FXML
  private Text actionTarget;
  @FXML
  private TextField uriField;
  @FXML
  private PasswordField keywordField;

  private final ZPassModel model = new ZPassModel();
  final Clipboard clipboard = Clipboard.getSystemClipboard();

  @FXML
  protected void handleOkButtonAction(ActionEvent event) {
    ClipboardContent cb = new ClipboardContent();
    Preferences preferences = Preferences.userRoot().node(this.getClass().getName());
    model.setKeyword(keywordField.getText());
    model.setUrl(uriField.getText());

    cb.putString(model.getPass());
    clipboard.setContent(cb);
    actionTarget.setText("Ready to \"paste\"!");
  }

  @FXML
  protected void handleClearButtonAction(ActionEvent event) {
    // Clear URI field
    model.setUrl("");
    uriField.setText("");

    // Clear clipboard
    ClipboardContent cb = new ClipboardContent();
    cb.putString("");
    clipboard.setContent(cb);

    actionTarget.setText("All clear.");
  }

  @FXML
  protected void handleMenuSettingsSaltAction(ActionEvent event) throws Exception {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("settings_layout.fxml"));
    Parent settingsParent = loader.load();
    SettingsController settingsController = (SettingsController) loader.getController();
    settingsController.saltProperty().addListener(
        new ChangeListener<String>() {
          @Override
          public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            model.setSalt(newValue);
          }
        }
    );
    Stage settingsStage = new Stage();
    settingsStage.setTitle("Settings");
    settingsStage.setScene(new Scene(settingsParent, 300, 130));
    settingsStage.show();
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    Preferences preferences = Preferences.userRoot().node(this.getClass().getName());
    model.setSalt(preferences.get("salt", ""));
  }
}
