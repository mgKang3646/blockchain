package model;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.json.JsonObject;

public class BlockMaker {
	
	Block block;

	public Block makeGenesisBlock() {
		block = new Block();
		block.setPreviousBlockHash("0000000000000000000000000000000000000000000000000000000000000000");
		block.setNonce(0);
		block.setTimestamp("00000000");
		block.setNum(0);
		block.generateHash();
		
		return block;
	}
	
	public Block makeTmpBlock(JsonObject jsonObject) {
		block = new Block();
		block.setNum(jsonObject.getInt("blockNum"));
		block.setNonce(jsonObject.getInt("nonce"));
		block.setTimestamp(jsonObject.getString("timestamp"));
		block.setTxData(jsonObject.getString("txData"));
		block.setPreviousBlockHash(BlockChain.getBlocklist().getPreviousHash()); // 본인이 갖고 있는 마지막블록의 이전해쉬여야함.
		block.generateHash();
		
		return block;
	}
	
	public Block makeMinedBlock(int nonce, String timestamp) {
		block = new Block();
		block.setNum(BlockChain.getBlocklist().getBlockNum());
		block.setPreviousBlockHash(BlockChain.getBlocklist().getPreviousHash());
		block.setNonce(nonce);
		block.setTxData(Wallet.txList.getTxData());
		block.setTimestamp(timestamp);
		block.generateHash();
		
		return block;
	}
	
	//DB txData 추가는 나중에 하기
	public Block makeDBBlock(ResultSet rs) {
		block = new Block();
		try {
			block.setNum(Integer.valueOf(rs.getString("num")));
			block.setPreviousBlockHash(rs.getString("previoushash"));
			block.setNonce(Integer.valueOf(rs.getString("nonce")));
			block.setTxData(rs.getString("transaction"));
			block.setTimestamp(rs.getString("timestamp"));
			block.setHash(rs.getString("hash"));
		} catch (NumberFormatException | SQLException e) {
			e.printStackTrace();
		}
		return block;
	}
	
	public Block makeLeaderBlock(JsonObject jsonObject) {
		block = new Block();
		block.setNum(jsonObject.getInt("blockNum"));
		block.setNonce(jsonObject.getInt("nonce"));
		block.setTimestamp(jsonObject.getString("timestamp"));
		block.setTxData(jsonObject.getString("txData"));
		block.setPreviousBlockHash(jsonObject.getString("previousHash")); // 본인이 갖고 있는 마지막블록의 이전해쉬여야함.
		block.setHash(jsonObject.getString("hash"));
		
		return block;
	}

}
