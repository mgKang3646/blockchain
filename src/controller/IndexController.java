package controller;

import java.net.URL;
import java.util.ResourceBundle;

import factory.NewPageFactory;
import factory.UtilFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.Peer;

public class IndexController implements Controller  {
	
	@FXML private Button miningButton;
	@FXML private HBox content;
	@FXML private Button blockchainButton;
	@FXML private TextField idText;
	//@FXML private Button wireButton;
	@FXML private Button upgradeButton;
	@FXML private Button goMyPageButton;
	@FXML private Button stateConnectionButton;
	@FXML private ImageView upgradeButtonImageView;
	@FXML private AnchorPane EasyCoin;	
	
	private Stage stage;
	private Peer peer;
	private String childPage;
	private NewPageFactory newPageFactory;
	private Image upgradeButtonImage;

	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		newPageFactory = new NewPageFactory();
	}	
	@Override
	public void setStage(Stage stage) {
		this.stage = stage;
	}
	@Override
	public void setPeer(Peer peer) {
		this.peer = peer;
	}
	@Override
	public void setObject(Object object) {
		childPage = (String)object;
	}
	@Override
	public void execute() {
		newPageFactory.setStage(stage);
		setUserName();
		switchContent();
		setButtonAction(blockchainButton,"blockchain");
		setButtonAction(miningButton,"mining");
		setButtonAction(stateConnectionButton,"stateConnection");
		//setButtonAction(wireButton,"wire");
		//업그레이드 버튼 추가되어야 함
	}

	private void switchContent() {
		switch(childPage) {
			case "blockchain" : blockchainHandler(); break;
			case "mining" : miningHandler(); break;
			case "stateConnection" : stateConnectionHandler(); break;
			case "wire" : wireHandler(); break;
			default : break;
		}
	}

	public void setChildPage(String childPage) {
		this.childPage = childPage;
	}

	private void setButtonAction(Button button, String name) {
		button.setOnAction(ActionEvent->{
				childPage = name;
				switchContent();
		});
	}
	
	private void setUserName() {
		idText.setText(peer.getUserName());
	}
	
	private void miningHandler() {
		newPageFactory.addMiningPage(content, peer);
	}
	
	private void blockchainHandler()  {
		newPageFactory.addBlockChainPage(content, peer);
	}
	
	private void stateConnectionHandler()  {
		System.out.println("연결관계 행들러 실행");
	}
	
	private void wireHandler() {
		System.out.println("송금 핸들러 실행");
	}

	private void doUpgrade(){
	}
}
