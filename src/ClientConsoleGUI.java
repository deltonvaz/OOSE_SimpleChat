import common.ChatIF;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import client.ChatClient;
/**
 * This class constructs the GUI for a chat client.  It implements the
 * chat interface in order to activate the display() method.
 * @author Delton Vaz (edition for OOSE)
 * @author William D'amico (edition for OOSE)
 * @version November 2018
 */
public class ClientConsoleGUI extends Application implements ChatIF {
	ChatClient chatClient;
	ClientConsoleGUI client;
	
	private final GridPane grid = new GridPane();
	
	public TextArea messageTextArea;
	
    public static void main(String[] args) {
        launch(args);
       
    }
    
    @Override
    public void start(Stage primaryStage) {    
    	messageTextArea = new TextArea();
    	createLoginScene(primaryStage);
    	
    	primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
            	//chat.quit();
                Platform.exit();
                System.exit(0);

            }
        });
    }
    
    public void createLoginScene(Stage primaryStage) {
    	primaryStage.setTitle("Simple Chat");
    	client = this;
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Scene scene = new Scene(grid, 300, 275);
        primaryStage.setScene(scene);
        
        
        Text scenetitle = new Text("Simple Chat - Login");
        
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        //
        Label userName = new Label("User Name:");
        grid.add(userName, 0, 1);

        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 1);
        
        //
        Label port = new Label("Port:");
        grid.add(port, 0, 2);

        TextField portTextField = new TextField();
        portTextField.setPromptText("5555");
        grid.add(portTextField, 1, 2);
        
        //
        Label host = new Label("Host:");
        grid.add(host, 0, 3);

        TextField hostTextField = new TextField();
        hostTextField.setPromptText("localhost");
        grid.add(hostTextField, 1, 3);

        Button btn = new Button("Sign in");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 4);
        
        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 6);
        
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	String host, port, username = "";
                
                host = hostTextField.getText();
                port = portTextField.getText();
                username = userTextField.getText();
                
                actiontarget.setFill(Color.FIREBRICK);
            	
            	if (host.equals(""))
            		host = "localhost";
            	
            	if (port.equals(""))
        			port = "5555";
            	
            	if (username.equals("")) {
            		actiontarget.setText("You must insert an username");
            	}else {
            		primaryStage.setScene(chatWindow(host, port, username));
            	}    	
            }
        });
        
        userTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ENTER)  {
                	String host, port, username = "";
                    
                    host = hostTextField.getText();
                    port = portTextField.getText();
                    username = userTextField.getText();
                    
                    actiontarget.setFill(Color.FIREBRICK);
                	
                	if (host.equals(""))
                		host = "localhost";
                	
                	if (port.equals(""))
            			port = "5555";
                	
                	if (username.equals("")) {
                		actiontarget.setText("You must insert an username");
                	}else {
                		primaryStage.setScene(chatWindow(host, port, username));
                	}    
                }
            }
        });
        
        primaryStage.show();
 
    }
    
    public Scene chatWindow(String host, String port, String username) {
        ChatClient chatClient = new ChatClient(host, Integer.valueOf(port), client, username);
    	
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Scene scene = new Scene(grid, 400, 375);
    	
        Text scenetitle = new Text("Simple Chat - Chat");
        
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        //
        Label messageLabel = new Label("Messages");
        grid.add(messageLabel, 0, 1);

        this.messageTextArea.setEditable(false);
        this.messageTextArea.setText("");
        grid.add(this.messageTextArea, 0, 2);
        
        
        //Label
        Label insertTextLabel = new Label("Tap here");
        grid.add(insertTextLabel, 0, 3);

        // Text area for message
        TextArea message = new TextArea();
        grid.add(message, 0, 4);
        message.requestFocus();
        
        // To contain the buttons
        HBox buttonBar = new HBox();
 
        // Button to Append text
        Button buttonAppend = new Button("Send message");
 
        // Action event.
        buttonAppend.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String text = message.getText();
                Thread thread = new Thread(() -> {
                	chatClient.handleMessageFromClientUI(text);
                	message.clear();
                });
                thread.start();
            }
        });
        
        //Grid buttonbar
        grid.add(buttonBar, 0, 5);
        grid.add(buttonAppend, 0, 6);
        
        message.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ENTER)  {
                    String text = message.getText();
                    Thread thread = new Thread(() -> {
                    	chatClient.handleMessageFromClientUI(text);
                    	message.clear();
                    });
                    thread.start();
                }
            }
        });
        
		return scene;
    }

    /**
     * This method overrides the method in the ChatIF interface.  It
     * displays a message onto the screen.
     *
     * @param message The string to be displayed.
     */
    public void display(String message) 
    {
        if (Platform.isFxApplicationThread()) {
        	this.messageTextArea.appendText(message);
        } else {
            Platform.runLater(() -> this.messageTextArea.appendText(message+"\n"));
        }
    	System.out.println("Log: > " + message);
    }

    

}