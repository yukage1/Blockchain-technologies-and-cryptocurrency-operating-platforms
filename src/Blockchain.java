import java.util.ArrayList;
import java.util.List;

public class Blockchain {
    private List<Block> chain;

    public Blockchain() {
        this.chain = new ArrayList<>();
        chain.add(createGenesisBlock());
    }

    // Genesis block
    private Block createGenesisBlock() {
        return new Block(1, "0", 1);
    }

    public Block getLatestBlock() {
        return chain.get(chain.size() - 1);
    }

    public void addBlock(Block newBlock) {
        newBlock.setPrevHash(getLatestBlock().generateBlockHash());
        chain.add(newBlock);
    }

    public List<Block> getChain() {
        return chain;
    }
}
