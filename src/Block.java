import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class Block {
    private int version;         // Protocol version
    private String prevHash;     // Хеш попереднього блоку
    private long timestamp;      // Timestamp
    private int difficulty;      // Difficulty of mining
    private int nonce;           // A random value
    private String merkleRoot;   // The root of the Merkle tree
    private List<Transaction> transactions; // List of transactions in the block

    // Конструктор
    public Block(int version, String prevHash, int difficulty) {
        this.version = version;
        this.prevHash = prevHash;
        this.timestamp = System.currentTimeMillis();
        this.difficulty = difficulty;
        this.nonce = 0;
        this.transactions = new ArrayList<>();
        this.merkleRoot = calculateMerkleRoot();
    }

    public void addTransaction(Transaction tx) {
        transactions.add(tx);
        this.merkleRoot = calculateMerkleRoot();
    }

    private String calculateMerkleRoot() {
        if (transactions.isEmpty()) return "";
        return transactions.get(0).getTxHash();
    }

    // Generate block header hash
    public String generateBlockHash() {
        String data = prevHash + timestamp + difficulty + nonce + merkleRoot;
        return applySHA256(data);
    }
    
    // Method for hashing using SHA-256
    private static String applySHA256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    
    public boolean verifyBlock() {
        String calculatedHash = generateBlockHash();
        return calculatedHash.equals(applySHA256(prevHash + timestamp + difficulty + nonce + merkleRoot));
    }

    public int getVersion() { return version; }
    public void setVersion(int version) { this.version = version; }

    public String getPrevHash() { return prevHash; }
    public void setPrevHash(String prevHash) { this.prevHash = prevHash; }

    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }

    public int getDifficulty() { return difficulty; }
    public void setDifficulty(int difficulty) { this.difficulty = difficulty; }

    public int getNonce() { return nonce; }
    public void setNonce(int nonce) { this.nonce = nonce; }

    public String getMerkleRoot() { return merkleRoot; }

    public List<Transaction> getTransactions() { return transactions; }

    @Override
    public String toString() {
        return "Block{" +
                "version=" + version +
                ", prevHash='" + prevHash + '\'' +
                ", timestamp=" + timestamp +
                ", difficulty=" + difficulty +
                ", nonce=" + nonce +
                ", merkleRoot='" + merkleRoot + '\'' +
                ", transactions=" + transactions +
                '}';
    }
}
