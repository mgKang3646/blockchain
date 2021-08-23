package application;

import java.io.IOException;
import java.security.Security;

import javafx.application.Application;
import javafx.stage.Stage;
import newview.FxmlStage;
import newview.NewView;
import newview.ViewURL;
//스테이지 Controller 말고 미리 저장시켜 놓기
// 검증시 다른 Peer Button 불기능 ( 싱글톤 ??? )
//+ Upgrade 기능 DB 블록저장 + 채굴 후 다시 채굴할 때 버튼을 두 번 클릭해야 함
public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) throws IOException {
		FxmlStage.setPrimaryStage(primaryStage);
		NewView newView = new NewView();
		newView.openView(ViewURL.loginURL);
	}
	
	public static void main(String[] args) {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider()); // privateKey와 publicKey 생성을 위한 provider 추가
		launch(args);
	}
}
