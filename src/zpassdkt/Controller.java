package zpassdkt;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.text.Text;

import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import com.sun.org.apache.xml.internal.security.utils.Base64;
import javafx.stage.Stage;


public class Controller implements Initializable {
    @FXML private Text actiontarget;
    @FXML private TextField saltField = new TextField();
    @FXML private TextField uriField;
    @FXML private PasswordField keywordField;

    final Clipboard clipboard = Clipboard.getSystemClipboard();

    @FXML protected void handleOkButtonAction(ActionEvent event) {
        ClipboardContent cb = new ClipboardContent();
        cb.putString(genPass(saltField.getText(), uriField.getText(), keywordField.getText()));
        clipboard.setContent(cb);
        actiontarget.setText("Ready to \"paste\"!");
    }

    @FXML protected void handleClearButtonAction(ActionEvent event) {
        // Clear URI field
        uriField.setText("");
        // Clear clipboard
        ClipboardContent cb = new ClipboardContent();
        cb.putString("");
        clipboard.setContent(cb);

        actiontarget.setText("All clear.");
    }

    @FXML protected void handleMenuSettingsSaltAction(ActionEvent event) throws Exception {
        uriField.setText("settings");
        Parent settingsParent = FXMLLoader.load(getClass().getResource("settings_layout.fxml"));
        Stage settingsStage = new Stage();
        settingsStage.setTitle("Settings");
        settingsStage.setScene(new Scene(settingsParent, 300, 130));
        settingsStage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Preferences preferences = Preferences.userRoot().node(this.getClass().getName());
        saltField.setText(preferences.get("salt", ""));
    }

    private String genPass(String input, String pass, String salt) {
        final String res = "%s@%s:%s";

        MessageDigest hash = null;
        try {
            hash = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] theDigest = hash.digest(String.format(res, salt, input, pass).getBytes());
        // Convert the hash to base62 so that it becomes a shorter string
        String base64 = Base64.encode(theDigest);
        // Destructively convert base64 to base62.
        // This is ok since we don't care about reverting back to the original string.
        return base64.replaceAll("\\+", "Z").replaceAll("/", "z").replaceAll("=", "");
    }

    @FXML public void handleInputChangeAction(ActionEvent event) {
        actiontarget.setText("");
    }

    public void handleApplySettingsButton(ActionEvent event) {
        Preferences preferences = Preferences.userRoot().node(this.getClass().getName());
        preferences.put("salt", saltField.getText());
        ((Node) event.getSource()).getScene().getWindow().hide();
    }
}
