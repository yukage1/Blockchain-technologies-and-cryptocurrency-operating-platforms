public class Main {
    public static void main(String[] args) throws Exception {

          Blockchain blockchain = new Blockchain();

        // Create blocks and add them to the blockchain
        for (int i = 1; i <= 5; i++) {
            Block block = new Block(1, blockchain.getLatestBlock().generateBlockHash(), 1);


        // Add several transactions to the block
            for (int j = 0; j < 3; j++) {
                Transaction tx = new Transaction("address" + i, new String[]{"address" + (i + 1)}, i * 10 + j, System.currentTimeMillis());
                block.addTransaction(tx);
            }

            blockchain.addBlock(block);

            System.out.println("Block #" + i + " added to the blockchain:");
            System.out.println(block);
            System.out.println();
        }


        // Verification of each block in the chain
        for (Block block : blockchain.getChain()) {
            System.out.println("Block verification result: " + block.verifyBlock());
        }
    }
}
