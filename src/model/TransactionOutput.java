package model;

import java.security.PublicKey;

import org.apache.commons.codec.digest.DigestUtils;
import org.bouncycastle.util.encoders.Base64;

import util.Encoding;

public class TransactionOutput {
	PublicKey recipient;
	String miner;
	String utxoHash;
	String transactionHash;
	float value;

	public void generateUtxoHash() {
		double nonce = Math.random();
		String recipientEncoding = Encoding.encodeKey(recipient);
		utxoHash = DigestUtils.sha256Hex(miner+recipientEncoding+value+nonce);
	}
	
	public float getValue() {
		return value;
	}
	
	public String getMiner() {
		return miner;
	}

	public String getUtxoHash() {
		return utxoHash;
	}

	public PublicKey getRecipient() {
		return recipient;
	}
	
	public String getTransactionHash() {
		return transactionHash;
	}
	
	public void setValue(float value) {
		this.value = value;
	}

	public void setMiner(String miner) {
		this.miner = miner;
	}

	public void setRecipient(PublicKey recipient) {
		this.recipient = recipient;
	}
	
	public void setTransactionHash(String transactionHash) {
		this.transactionHash = transactionHash;
	}
	
	public void print() {
		System.out.println("UTXO 정보 \n");
		System.out.println("UTXO 소유주 : " + getRecipient().getEncoded().toString());
		System.out.println("Minner : " +getMiner());
		System.out.println("금액 : " + getValue());
	}
}
