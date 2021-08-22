package json;

import java.io.BufferedReader;
import javax.json.Json;
import javax.json.JsonObject;
import factory.JsonFactory;
import model.Block;
import model.BlockChain;
import model.BlockMaker;
import model.BlockVerify;
import model.Peer;

public class PeerThreadReceive implements JsonReceive{
	private Peer peer;
	private BlockVerify blockVerify;
	private JsonObject jsonObject;
	private Block tmpBlock;
	private BlockChain blockchain;
	private BlockMaker blockMaker;
	

	public PeerThreadReceive(Peer peer) {
		this.peer = peer;
		this.blockVerify = peer.getBlockchain().getBlockVerify();
		this.blockMaker = new BlockMaker();
		this.blockchain = peer.getBlockchain();
	}
	
	public void read(BufferedReader bufferedReader) {
		setJsonObject(bufferedReader);
		processJsonQuery();
	}
	
	private void setJsonObject(BufferedReader bufferedReader) {
		jsonObject = Json.createReader(bufferedReader).readObject();
	}

	private void processJsonQuery() {
		String key = jsonObject.getString("identifier");
		System.out.println("identifier : " + key);
		
		switch(key) {
			case "minedBlock" : verifyBlock(); break;// 1. ���� 2. ������������� 3. �ٸ� Peer ������� Ȯ�� 4. ������ ������ �����Ϸ�.
			case "verifyResult" : handleVerifyResult(); break;
			default : break;
		}
	}
	
	private void verifyBlock() {
		tmpBlock = blockMaker.makeTmpBlock(jsonObject, blockchain.getPreviousHash());
		tmpBlock.verifyBlock(jsonObject.getString("hash"));
		blockVerify.setTmpBlock(tmpBlock);
		blockVerify.setIsMinedBlock(false);
		blockVerify.doPoll(true); // ä����
		blockVerify.doPoll(tmpBlock.isValid()); // ������
		blockVerify.waitOtherPeerPoll();
		blockVerify.broadCastingVerifiedResult();
	}
	
	private void handleVerifyResult() {
		blockVerify.doPoll(jsonObject.getBoolean("verifyResult"));
		blockVerify.waitOtherPeerPoll();
	}
}