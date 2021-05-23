package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import database.DAO;
import database.DTO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.BlockchainModel;
import model.PeerModel;
import model.Pem;
import model.WalletModel;
import model.produceKey;

public class JoinController implements Initializable {

	@FXML private Button join_linkButton;
	@FXML private TextField userNameText;
	@FXML private Button goToIndexButton;
	@FXML private Label privateKeyLabel;
	@FXML private ImageView join_linkButtonImageView; 
	
	
	Image join_linkButtonImage;
	DTO userDTO;
	DAO dao;
	produceKey produceKey;
	PeerModel peerModel;
	WalletModel walletModel;
	BlockchainModel blockchainModel;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		userDTO = new DTO();
		dao = new DAO();
		produceKey = new produceKey(); //privateKey�� publicKey ������ ���� Model
		peerModel = new PeerModel(); // P2P ��� ��
		walletModel = new WalletModel(); // ���� ����
		blockchainModel = new BlockchainModel(); // ����ü�� ����

		
		join_linkButton.setOnMouseEntered(e->{
			join_linkButtonImage = new Image("/image/join_linkEntered.png");
			join_linkButtonImageView.setImage(join_linkButtonImage);
		});
			
		join_linkButton.setOnMouseExited(e->{
			join_linkButtonImage = new Image("/image/join_link.png");
			join_linkButtonImageView.setImage(join_linkButtonImage);
		});
		
		
	}

	
	public void join() throws Exception {
		
		// ID �ߺ�üũ
		
		String username = userNameText.getText(); 
		int registerResult = dao.registerCheck(username);
		
		//DB�� �̹� HOST �ּҰ� ��ϵǾ� �ִ� ���
		if(registerResult == 1) {
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/registercheck.fxml"));
			Parent root = loader.load();
			RegisterCheckController rcc = loader.getController();
			rcc.setPrimaryStage((Stage)join_linkButton.getScene().getWindow());
			
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
		}
		
		// DB�� �ּҰ� ��ϵǾ� ���� ���� ���
		else {
		
			produceKey.generateKeyPair(); // privateKey, publicKey ����
			
			//PrivateKey ��, PublicKey�� Pem ���� �����
			Pem pemFileForPrivate = new Pem(produceKey.getPrivateKey(),username); 
			pemFileForPrivate.write(username+"privatekey.pem");
			System.out.println(String.format("EC ��ȣŰ %s������ �����½��ϴ�.",username+"privatekey.pem"));

			Pem pemFileForPublic = new Pem(produceKey.getPublicKey(),username); 
			pemFileForPublic.write(username+"publickey.pem");
			System.out.println(String.format("EC ��ȣŰ %s������ �����½��ϴ�.",username+"publickey.pem"));

			//�ּ� ���� �����ϱ�
			String localhost = "localhost:"+ (5500 + (int)(Math.random()*100)); // �ּ� ���� ����
			
			//DB ����
			if(dao.join(localhost, username)>0) { // join()�� return���� 0 �̻��̸� DB���� ��������ó��
					
					try {
						//���� �ʱ�ȭ
						walletModel.setPrivateKey(produceKey.getPrivateKey());
						walletModel.setPublicKey(produceKey.getPublicKey());
						walletModel.setUserLocalHost(localhost);
						walletModel.setUsername(username);
						
						// P2P ���� �����Ű��
						FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/netprogress.fxml"));
						Parent root = loader.load();
						
						Scene scene = new Scene(root);
						Stage stage = new Stage();
						stage.setScene(scene);
						stage.setX(join_linkButton.getScene().getWindow().getX()+65);
						stage.setY(join_linkButton.getScene().getWindow().getY()+50);
						stage.setResizable(false);
						stage.show();
						
						NetProgressController npc = loader.getController(); 
						npc.setPrimaryStage((Stage)join_linkButton.getScene().getWindow());
						npc.doProgress(peerModel, walletModel, blockchainModel,"join");
						npc.setPrivateKeyPath(pemFileForPrivate.getPath());

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
				}	
			} 
		}
	}
}