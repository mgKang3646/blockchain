package model;

import java.security.PrivateKey;
import java.security.PublicKey;

public class MyPeer {
	
	private PrivateKey privateKey;
	private PublicKey publicKey;
	private String userName;
	private String localhost;
	private boolean isLeader;
	
	public String getUserName() {
		return userName;
	}
	public String getLocalhost() {
		return localhost;
	}
	public PublicKey getPublicKey() {
		return publicKey;
	}
	public PrivateKey getPrivateKey() {
		return privateKey;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setLocalhost(String localhost) {
		this.localhost = localhost;
	}
	public void setPublicKey(PublicKey publicKey) {
		this.publicKey = publicKey;
	}
	public void setPrivateKey(PrivateKey privateKey) {
		this.privateKey = privateKey;
	}
	public boolean isLeader() {
		return isLeader;
	}
	public void setLeader(boolean isLeader) {
		this.isLeader = isLeader;
	}
}