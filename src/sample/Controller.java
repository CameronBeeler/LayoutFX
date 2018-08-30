package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;

public
class Controller
{
    @FXML
    private
    TextField nameField;

    @FXML
    private
    DatePicker selectedDate;

    @FXML
    private
    Button OKButton, CancelButton;

    @FXML
    private
    CheckBox checkBoxClear;

    @FXML
    private
    Label threadLabel;

    private static String tmp;

    @FXML
    public
    void initialize()
    {
//    Disabling buttons by default at the initialization of the UI
        OKButton.setDisable(true);
        CancelButton.setDisable(true);
        checkBoxClear.setDisable(true);
        selectedDate.setDisable(true);
        tmp  = threadLabel.getText();
    }

    @FXML
    public void onButtonClicked(ActionEvent e) // method name is assigned, so long as exact match between fxml file and here...
    {
        Runnable task = () ->
        {

            try
            {
                String s = Platform.isFxApplicationThread() ? "UI Thread" : "Background Thread";
                System.out.println("I'm going to sleep on the " + s);
                Platform.runLater(new Runnable()
                {
                    @Override
                    public
                    void run()
                    {
                        String s = Platform.isFxApplicationThread() ? "UI Thread" : "Background Thread";
                        System.out.println("I'm going to sleep on the " + s);

                        threadLabel.setText("Starting the Thread Now");
                    }
                });

                Thread.sleep(10000);
                Platform.runLater(new Runnable()
                {
                    @Override
                    public
                    void run()
                    {
                        String s = Platform.isFxApplicationThread() ? "UI Thread" : "Background Thread";
                        System.out.println("I'm waking up on the " + s);
                        threadLabel.setText("Finished with the Thread");
                    }
                });

            }
            catch(InterruptedException event)
            {
//                        nothing in here
            }
//                threadLabel.setText("Finished running the Thread");

        };
        new Thread(task).start();

        if (e.getSource().equals(nameField))
        {
            if(nameField.getText().trim().isEmpty())
            {
                handleKeyReleased();
            }



        }
        else if (e.getSource().equals(selectedDate))
        {
            System.out.println(selectedDate.getId());
         }
        else if (e.getSource().equals(OKButton))
        {
            System.out.println(OKButton.getId());

        }
        else if (e.getSource().equals(CancelButton))
        {
            System.out.println(CancelButton.getId());

        }
        else if (e.getSource().equals(checkBoxClear))
        {
            if(checkBoxClear.isSelected())
            {
                System.out.println("checkBoxClear is selected!");
                nameField.clear();
                handleKeyReleased();
                checkBoxClear.selectedProperty().set(false);
            }
            else
            {
                System.out.println("checkBoxClear is NOT selected!");
            }



        }
     }

     @FXML
     public
     void handleKeyReleased()
     {
         String text = nameField.getText();
         boolean disableButtons = text.isEmpty() || text.trim().isEmpty();

         OKButton.setDisable(disableButtons);
         CancelButton.setDisable(disableButtons);
         checkBoxClear.setDisable(disableButtons);
         selectedDate.setDisable(disableButtons);


         System.out.println("disable = " + disableButtons);
         if(disableButtons)
         {
             checkBoxClear.selectedProperty().set(false);
             selectedDate.setValue(null);
             threadLabel.setText(tmp);


         }
     }

 /*
     @FXML
    public void OnClick()
     {
         System.out.println("worth a try...onclick... is selected ->" + checkBoxClear.isSelected());
         if(checkBoxClear.isSelected())
         {
             System.out.println("checkBoxClear is selected!");
             nameField.clear();
             handleKeyReleased();
             checkBoxClear.selectedProperty().set(false);
         }
         else
         {
             System.out.println("checkBoxClear is NOT selected!");
         }

     }
     */
}
