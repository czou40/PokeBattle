import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.application.Platform;

/**
 * The main UI component.
 *
 * @author Chunhao Zou
 * @version 1.0
 */
public class PokeBattle extends Application implements PokemonUtility {
    private static MyGridPane root;
    private static MyGridPane myPane;
    private static MyGridPane enemyPane;
    private static Label myName;
    private static ImageView myImage;
    private static MyGridPane myInfoGridPane;
    private static Label myInfo;
    private static ProgressBar myHP;
    private static MyGridPane myMoveButtonsPane;
    private static Button[] myMoveButtons;
    private static MyGridPane userOperationsButtonsPane;
    private static Button fightButton;
    private static Button bagButton;
    private static Button pokemonButton;
    private static Button runButton;


    private static Label enemyName;
    private static ImageView enemyImage;
    private static MyGridPane enemyInfoGridPane;
    private static Label enemyInfo;
    private static ProgressBar enemyHP;
    private static TextArea battleDetail;

    private static Image moveImage;
    private static ImageView moveOnMeImage;
    private static ImageView moveOnEnemyImage;

    private Pokemon me;
    private Pokemon enemy;
    private static String difficulty;

    private Stage primary;

    /**
     * Start the program.
     *
     * @param primaryStage Primary stage.
     * @throws Exception Exception.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primary = primaryStage;
        //Set up and show primary stage, adjust size.
        root = new MyGridPane(FULL_SPAN, PokemonUtility.getSpan(2));
        Scene scene = new Scene(root, STAGE_WIDTH, STAGE_HEIGHT);
        scene.getStylesheets().add("src/style.css");
        primaryStage.setTitle("Pokemon");
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        primaryStage.show();
        initUI();

    }

    /**
     * Load the UI.
     */
    private void initUI() {
        //General layout
        myPane = new MyGridPane(CUSTOM_ROW_SPAN, FULL_SPAN);
        enemyPane = new MyGridPane(CUSTOM_ROW_SPAN, FULL_SPAN);
        root.addRow(0, myPane, enemyPane);

        //Layout in myPane
        myName = new Label();
        myName.setId("name");
        myImage = new ImageView();
        myImage.setPreserveRatio(true);
        myImage.fitWidthProperty().bind(root.widthProperty().divide(2).subtract(MARGIN));
        myImage.fitHeightProperty().bind(root.heightProperty().multiply(CUSTOM_ROW_SPAN[1] / 100));
        myInfoGridPane = new MyGridPane(PokemonUtility.getSpan(2), FULL_SPAN);
        myInfo = new Label();
        myInfo.setId("info");
        myHP = new ProgressBar();
        myHP.setProgress(0);
        myHP.prefWidthProperty().bind(root.widthProperty().divide(2).subtract(MARGIN));
        myInfoGridPane.addColumn(0, myInfo, myHP);
        myMoveButtonsPane = new MyGridPane();
        //myMoveButtonsPane.setVisible(false);
        userOperationsButtonsPane = new MyGridPane(FULL_SPAN, PokemonUtility.getSpan(4));
        myPane.addColumn(0, myName, myImage, myInfoGridPane, userOperationsButtonsPane);

        //Layout in enemyPane
        enemyName = new Label();
        enemyName.setId("name");
        enemyImage = new ImageView();
        enemyImage.setPreserveRatio(true);
        enemyImage.fitWidthProperty().bind(root.widthProperty().divide(2).subtract(MARGIN));
        enemyImage.fitHeightProperty().bind(root.heightProperty().multiply(CUSTOM_ROW_SPAN[1] / 100));
        enemyInfoGridPane = new MyGridPane(PokemonUtility.getSpan(2), FULL_SPAN);
        enemyInfo = new Label();
        enemyInfo.setId("info");
        enemyHP = new ProgressBar();
        enemyHP.setProgress(0);
        enemyHP.prefWidthProperty().bind(root.widthProperty().divide(2).subtract(MARGIN));
        enemyInfoGridPane.addColumn(0, enemyInfo, enemyHP);
        battleDetail = new TextArea();
        battleDetail.setEditable(false);
        battleDetail.setWrapText(true);
        enemyPane.addColumn(0, enemyName, enemyImage, enemyInfoGridPane, battleDetail);
        initGame(new YellowJacket(5), new BullDogs(1));
    }

    /**
     * Initiate the game with the two pokemons specified in the parameter.
     *
     * @param m Me.
     * @param e Enemy.
     */
    private void initGame(Pokemon m, Pokemon e) {
        this.me = m;
        this.enemy = e;
        loadGame();
    }

    /**
     * Load UI that are related to the two pokemons.
     */
    private void loadGame() {
        myName.setText(String.format(MY_NAME, me.getName()));
        enemyName.setText(String.format(ENEMY_NAME, enemy.getName()));
        myImage.setImage(new Image(me.getPicture()));
        enemyImage.setImage(new Image(enemy.getPicture()));
        int myMoveNumbers = me.getMoves().length;
        int rowNumber = (int) Math.ceil(1.0 * myMoveNumbers / MOVE_BUTTON_EACH_ROW);
        myMoveButtonsPane.setConstraints(PokemonUtility.getSpan(rowNumber),
                PokemonUtility.getSpan(MOVE_BUTTON_EACH_ROW));
        myMoveButtons = new Button[myMoveNumbers];
        MyMoveButtonOnActon myMoveButtonOnActon = new MyMoveButtonOnActon();
        for (int i = 0; i < myMoveNumbers; i++) {
            myMoveButtons[i] = new Button();
            myMoveButtons[i].setText(me.getMoves()[i].getName());
            myMoveButtons[i].setId(String.valueOf(i));
            myMoveButtons[i].prefWidthProperty().bind(root.widthProperty().
                    divide(2 * MOVE_BUTTON_EACH_ROW).subtract(MARGIN));
            myMoveButtons[i].setOnAction(myMoveButtonOnActon);
            Tooltip tooltip = new Tooltip(PokemonUtility.getDescription(me.getMoves()[i].getType()));
            tooltip.setShowDelay(Duration.ZERO);
            myMoveButtons[i].setTooltip(tooltip);
        }
        for (int i = 0; i < (int) Math.ceil(1.0 * myMoveNumbers / MOVE_BUTTON_EACH_ROW); i++) {
            for (int j = 0; j < Math.min(MOVE_BUTTON_EACH_ROW, myMoveNumbers - i * MOVE_BUTTON_EACH_ROW); j++) {
                myMoveButtonsPane.add(myMoveButtons[MOVE_BUTTON_EACH_ROW * i + j], j, i);
            }
        }

        fightButton = new Button("Fight!");
        bagButton = new Button("Bag!");
        pokemonButton = new Button("Pokemon!");
        runButton = new Button("Run!");
        fightButton.prefWidthProperty().bind(root.widthProperty().
                divide(8).subtract(MARGIN));
        bagButton.prefWidthProperty().bind(root.widthProperty().
                divide(8).subtract(MARGIN));
        pokemonButton.prefWidthProperty().bind(root.widthProperty().
                divide(8).subtract(MARGIN));
        runButton.prefWidthProperty().bind(root.widthProperty().
                divide(8).subtract(MARGIN));
        fightButton.setOnAction(actionEvent -> {
                myPane.getChildren().remove(userOperationsButtonsPane.getParent());
                myPane.add(myMoveButtonsPane, 0, 3);
            });

        runButton.setOnAction(actionEvent -> {
                System.exit(0);
            });
        userOperationsButtonsPane.getChildren().setAll();
        userOperationsButtonsPane.addRow(0, fightButton, bagButton, pokemonButton, runButton);
        battleDetail.setText("A new battle has begun!\n");
        updateInfo();
    }

    /**
     * Let the enemy and me attack each other for one round. All info will be updated
     * and the animations will be displayed.
     *
     * @param move My move.
     */
    private void proceedOneRound(Move move) {
        myMoveButtonsPane.setDisable(true);
        Effect myMoveEffect = me.attack(enemy, move);
        battleDetail.appendText(myMoveEffect.getInfo() + "\n");
        displayMove(move, false);
        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(event -> {
                updateInfo();
                removeMoveImage();
                if (move.getType().equals(FL)) {
                    proceedOneRound(me.autoChooseMove(enemy));
                    return;
                }
                PauseTransition pause2 = new PauseTransition(Duration.seconds(2));
                // * enemy.getMoves().length)]);
                pause2.setOnFinished(event2 -> {
                        if (checkLost().equals(NEXT)) {
                            myPane.getChildren().remove(myMoveButtonsPane.getParent());
                            myPane.add(userOperationsButtonsPane, 0, 3);
                            myMoveButtonsPane.setDisable(false);
                            return;
                        }
                        Move enemyMove = enemy.autoChooseMove(me);
                        //Effect enemyMoveEffect = enemy.attack(me, enemy.getMoves()[(int) (Math.random()
                        Effect enemyMoveEffect = enemy.attack(me, enemyMove);
                        battleDetail.appendText(enemyMoveEffect.getInfo() + "\n");
                        displayMove(enemyMove, true);
                        PauseTransition pause3 = new PauseTransition(Duration.seconds(1));
                        pause3.setOnFinished(event3 -> {
                                updateInfo();
                                removeMoveImage();
                                PauseTransition pause4 = new PauseTransition(Duration.seconds(2));
                                pause4.setOnFinished(event4 -> {
                                        checkLost();
                                        myPane.getChildren().remove(myMoveButtonsPane.getParent());
                                        myPane.add(userOperationsButtonsPane, 0, 3);
                                        myMoveButtonsPane.setDisable(false);
                                    });
                                pause4.play();
                            });
                        pause3.play();
                    });
                pause2.play();
            });
        pause.play();
    }

    /**
     * Check if I am lost. If lost, return a String and show the message.
     *
     * @return A String telling my status.
     */
    private String checkLost() {
        if (me.isLost()) {
            showMessageStage(LOST, 5);
            return LOST;
        }
        if (enemy.isLost()) {
            showMessageStage(NEXT, 5);
            initGame(new YellowJacket(me.getLevel() + 1),
                    new BullDogs(enemy.getLevel() + 3));
            return NEXT;
        }
        return "";
    }

    /**
     * Display animations of a move.
     *
     * @param move    Move.
     * @param reverse If true, DISPLAY_ME_ONLY will display the picture on the enemy, and vice versa.
     */
    private void displayMove(Move move, boolean reverse) {
        moveImage = new Image(move.getPicture());
        moveOnMeImage = new ImageView(moveImage);
        moveOnEnemyImage = new ImageView(moveImage);
        moveOnMeImage.fitWidthProperty().bind(root.widthProperty().divide(2).subtract(MARGIN));
        moveOnMeImage.fitHeightProperty().bind(root.heightProperty().multiply(CUSTOM_ROW_SPAN[1] / 100));
        moveOnEnemyImage.fitWidthProperty().bind(root.widthProperty().divide(2).subtract(MARGIN));
        moveOnEnemyImage.fitHeightProperty().bind(root.heightProperty().multiply(CUSTOM_ROW_SPAN[1] / 100));
        int mode = move.getPictureDisplayMode();
        if (mode == DISPLAY_BOTH) {
            ((StackPane) myImage.getParent()).getChildren().add(moveOnMeImage);
            ((StackPane) enemyImage.getParent()).getChildren().add(moveOnEnemyImage);
        } else if (mode == DISPLAY_ENEMY_ONLY && !reverse || mode == DISPLAY_ME_ONLY && reverse) {
            ((StackPane) enemyImage.getParent()).getChildren().add(moveOnEnemyImage);
        } else if (mode == DISPLAY_ENEMY_ONLY && reverse || mode == DISPLAY_ME_ONLY && !reverse) {
            ((StackPane) myImage.getParent()).getChildren().add(moveOnMeImage);
        }
    }

    /**
     * Remove move animations.
     */
    private void removeMoveImage() {
        ((StackPane) myImage.getParent()).getChildren().remove(moveOnMeImage);
        ((StackPane) enemyImage.getParent()).getChildren().remove(moveOnEnemyImage);
    }

    /**
     * Show customized message window.
     *
     * @param message Message.
     * @param second  How many seconds it will be displayed.
     */
    private void showMessageStage(String message, int second) {
        Scene scene;
        Stage stage;
        MyGridPane pane = new MyGridPane(FULL_SPAN, FULL_SPAN);
        pane.getStylesheets().add("src/style.css");
        Label label = new Label();
        label.setText(String.format(message, second, second == 1 ? S_S : S_P));
        pane.add(label, 0, 0);
        scene = new Scene(pane);
        stage = new Stage();
        stage.setScene(scene);
        stage.setAlwaysOnTop(true);
        stage.setFullScreen(true);
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        stage.show();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                int countdown = second;
                while (countdown > 0) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    countdown--;
                    int finalCountdown = countdown;
                    Platform.runLater(() -> label.setText(String.format(message, finalCountdown,
                            finalCountdown == 1 ? S_S : S_P)));
                }
                if (message.equals(LOST)) {
                    System.exit(0);
                } else {
                    Platform.runLater(() -> {
                            primary.setFullScreen(true);
                            stage.close();
                        });
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    /**
     * Show the newest status of me and my enemy on screen.
     */
    private void updateInfo() {
        int[] temp = PokemonUtility.getProgressBarColor(me.getCurrentHP() / me.getMaxHP());
        myHP.setStyle(String.format(PROGRESS_BAR_COLOR, temp[0], temp[1], temp[2]));
        temp = PokemonUtility.getProgressBarColor(enemy.getCurrentHP() / enemy.getMaxHP());
        enemyHP.setStyle(String.format(PROGRESS_BAR_COLOR, temp[0], temp[1], temp[2]));
//        PauseTransition pause = new PauseTransition(Duration.seconds(1));
//        pause.setOnFinished(event -> {
        Timeline timeline = new Timeline();
        KeyValue myKeyValue = new KeyValue(myHP.progressProperty(), me.getCurrentHP() / me.getMaxHP());
        KeyFrame myKeyFrame = new KeyFrame(new Duration(1000), myKeyValue);
        KeyValue enemyKeyValue = new KeyValue(enemyHP.progressProperty(), enemy.getCurrentHP() / enemy.getMaxHP());
        KeyFrame enemyKeyFrame = new KeyFrame(new Duration(1000), enemyKeyValue);
        timeline.getKeyFrames().addAll(myKeyFrame, enemyKeyFrame);
        timeline.play();
        myInfo.setText(String.format(INFO, me.getLevel(), me.getCurrentHP(), me.getMaxHP(), me.getAtk()));
        enemyInfo.setText(String.format(INFO, enemy.getLevel(), enemy.getCurrentHP(),
                enemy.getMaxHP(), enemy.getAtk()));
//            PauseTransition pause2 = new PauseTransition(Duration.seconds(1));
//            pause2.setOnFinished(event2 -> {
//            });
//            pause2.play();
//        });
//        pause.play();
    }

    /**
     * Main.
     *
     * @param args Args.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Customized Listener.
     */
    private class MyMoveButtonOnActon implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {
            proceedOneRound(me.getMoves()[Integer.parseInt(((Button) actionEvent.getSource()).getId())]);
        }
    }
}
