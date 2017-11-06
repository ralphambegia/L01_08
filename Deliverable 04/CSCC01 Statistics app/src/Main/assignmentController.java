package Main;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class assignmentController {

    @FXML
    private TextField aID;
    @FXML
    private TextField aName;
    @FXML
    private Label dueDateLabel;
    @FXML
    private CheckBox aNoDueDate;
    @FXML
    private Label assignmentLabel;
    @FXML
    private DatePicker aDatePicker;
    @FXML
    private Button createAssgnButton;

    public static final String INVALID_DATE = "INVALIDDATE";

    public void createAssgnHandler() {
        if (aName.getText().trim().length() == 0 || aName.getText().trim().length() > 25) {
            assignmentLabel.setText("Please enter an assignment name between 1-25 characters.");
            return;
        }
        if (isNameUsed()) {
            return;
        }

        if (aDatePicker.getValue() == null) {
            assignmentLabel.setText("Please pick a valid due date");
            return;
        }

        if (aNoDueDate.isSelected()) {
            Admin.createAssignment(aName.getText());
            System.out.println("Assignment: " + aName.getText() + " created");
        } else {
            String dueDateStr = validDueDate();
            if (dueDateStr.equals(INVALID_DATE)) {
                return;
            } else {
                Admin.createAssignment(aName.getText(), dueDateStr);
                assignmentLabel.setText("");
                System.out.println("Assignment: " + aName.getText() + " created");
            }
        }
    }

    public void noDeadlineHandler() {
        if (aNoDueDate.isSelected()) {
            dueDateLabel.setVisible(false);
            aDatePicker.setVisible(false);
            assignmentLabel.setText("");
        } else {
            dueDateLabel.setVisible(true);
            aDatePicker.setVisible(true);
        }
    }

    /*
    If the due date is after today, returns the duedate in yyyy/MM/dd format.
    Otherwise returns INVALID_DATE
     */
    private String validDueDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

        LocalDate ld = aDatePicker.getValue();
        Calendar cal = Calendar.getInstance();
        cal.set(ld.getYear(), ld.getMonthValue(), ld.getDayOfMonth());

        Date todaysDate = new Date();
        Date dueDate = Date.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant());

        if (dueDate.before(todaysDate)) {
            assignmentLabel.setText("Please enter a date after today.");
            return INVALID_DATE;
        } else {
            assignmentLabel.setText("");
        }

        String dueDateStr = dateFormat.format(dueDate);
        return dueDateStr;
    }

    /**
     * @return TRUE if the assignment name is already in use
     */
    private Boolean isNameUsed() {
        for (Assignment curr : Data.assignmentList) {
            if (aName.getText().equals(curr.getAssignmentName())) {
                assignmentLabel.setText("Assignment name already in use.");
                return true;
            }
        }
        return false;
    }
}
