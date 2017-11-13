package Main;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class practiceAssignmentController {

    @FXML
    private Label pAssgnTitle;
    @FXML
    private Label pMarkLabel;
    @FXML
    private VBox pAssgnVbox;
    @FXML
    private Label viewPAssgnLabel;
    @FXML
    private Button checkAnsButton;
    @FXML
    private Button pAssgnToPList;
    @FXML
    private Button newPracticeButton;

    private Assignment assignment;
    private ArrayList<Question> questionList = new ArrayList<Question>();
    private ArrayList<ToggleGroup> toggleList = new ArrayList<>();
    private ArrayList<String> questionOrder = new ArrayList<>();
    private ArrayList<String> ansIndex = new ArrayList<>();

    private ArrayList<Label> solnList = new ArrayList<>();

    public void initAssignment(Assignment assgn) {
        assignment = assgn;
        pAssgnTitle.setText(assignment.getAssignmentName() + " Practice");

        loadQuestions();
    }

    public void loadQuestions() {
        // Gets the questions
        questionList = assignment.getRandomQuestionList();

        // Displays all the questions
        for(int i = 0; i < questionList.size(); i++) {
            Label questionLabel = new Label();
            questionLabel.setText(i+1 + ". " + questionList.get(i).getTheQuestion());
            ToggleGroup mcAnsToggle = new ToggleGroup();
            toggleList.add(mcAnsToggle);
            pAssgnVbox.getChildren().add(questionLabel);
            for(int j = 0; j < questionList.get(i).getMcAnswers().size(); j++) {
                RadioButton mcAnsOption = new RadioButton(questionList.get(i).getMcAnswers().get(j));
                mcAnsOption.setUserData(String.valueOf(j));
                mcAnsOption.setToggleGroup(mcAnsToggle);
                pAssgnVbox.getChildren().add(mcAnsOption);
            }
            Separator sep = new Separator();
            sep.setVisible(false);
            pAssgnVbox.getChildren().add(sep);

            Label solnLabel = new Label();
            solnLabel.setManaged(false);
            solnList.add(solnLabel);
            pAssgnVbox.getChildren().add(solnLabel);

            Separator sep1 = new Separator();
            sep1.setVisible(false);
            pAssgnVbox.getChildren().add(sep1);
        }
    }

    public void checkAnswers() {
        questionOrder.clear();
        ansIndex.clear();
        int numCorrect = 0;

        for(int i = 0; i < questionList.size(); i++) {
            Label lb = solnList.get(i);
            lb.setManaged(true);

            Question q = questionList.get(i);
            questionOrder.add(q.getTheQuestion());

            ToggleGroup tg = toggleList.get(i);
            if(tg.getSelectedToggle() != null) {
                String selectedAns = tg.getSelectedToggle().getUserData().toString();
                ansIndex.add(selectedAns);

                // Checking the answer
                if(selectedAns.equals(q.getSolnIndStr())) {
                    numCorrect++;
                    lb.setStyle("-fx-text-fill: green;");
                    lb.setText("Correct!");
                } else {
                    lb.setStyle("-fx-text-fill: red;");
                    lb.setText("Incorrect. Correct answer is: " + q.getMcAnswers().get(q.getSolnInd()));
                }
            } else {
                lb.setStyle("-fx-text-fill: red;");
                lb.setText("Incorrect. Correct answer is: " + q.getMcAnswers().get(q.getSolnInd()));
            }
        }

        // Calc and show mark
        pMarkLabel.setManaged(true);
        Double mark = (double) numCorrect / questionOrder.size() * 100;
        pMarkLabel.setText("You got: " + numCorrect + " out of " + questionOrder.size() + ", " + mark + "%!");

        toggleList.clear();
        checkAnsButton.setManaged(false);
        newPracticeButton.setManaged(true);
    }

    public void newPracticeSet() {
        checkAnsButton.setManaged(true);
        newPracticeButton.setManaged(false);
        ///// here
    }

    public void backToPList() {

    }

}
