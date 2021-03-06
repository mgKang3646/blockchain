package model;

public class Mining {
	
	public String hashDifficulty = "0000";
	
	private BlockMaker blockMaker;
	private Block minedBlock;
	private boolean miningFlag;
	private boolean isMined;
	private int nonce;
	
	public Mining() {
		blockMaker = new BlockMaker();
	}
	
	public Block getMinedBlock() {
		return minedBlock;
	}
	
	public boolean isMined() {
		return isMined;
	}
	
	public void setIsMined(boolean value) {
		isMined = value;
	}
	
	public void setMiningFlag(boolean value) {
		miningFlag = value;
	}
	
	public void startMining() {
		Thread miningThread = new Thread() {
			public void run() {
				while(miningFlag) {
					setMinedBlock();
					printHashString();
					if(isBlockHash()) {
						setIsMined(true);
						Thread.yield();
						break;
					}
				}
			}
		};
		miningThread.start();
	}
	
	private void setMinedBlock(){
		this.minedBlock = blockMaker.makeMinedBlock(++nonce, Long.toString(System.currentTimeMillis()));
	}
	
	private void printHashString() {
		System.out.println(minedBlock.getHash());
	}
	
	private boolean isBlockHash() {
		return minedBlock.getHash().substring(0,hashDifficulty.length()).equals(hashDifficulty);
	}
}
