package model;

import java.util.ArrayList;

public class PeerList {
	private ArrayList<OtherPeer> peerList;
	
	public PeerList() {
		peerList = new ArrayList<OtherPeer>();
	}
	
	public ArrayList<OtherPeer> getPeerList(){
		return peerList;
	}
	
	public void add(OtherPeer otherPeer) {
		peerList.add(otherPeer);
	}
	
	public void delete(PeerThread peerThread) {
		for(OtherPeer otherPeer : peerList) {
			if(otherPeer.getPeerThread() == peerThread) {
				peerList.remove(otherPeer);
				break;
			}
		}
	}
	
	public int getSize() {
		return peerList.size();
	}
	
	public OtherPeer searchOtherPeer(String userName) {
		for(OtherPeer otherPeer : getPeerList()) {
			if(otherPeer.getUserName().equals(userName)) return otherPeer;
		}
		return null;
	}
}
