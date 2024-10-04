import java.security.*;
import java.util.Arrays;

public class Transaction {
    private String input;       // Sender's address
    private String[] outputs;   // Recipients' addresses
    private double amount;      // Transaction amount
    private long txTimestamp;   // Timestamp
    private String txHash;      // Transaction hash
    private byte[] signature;   // Digital signature

    public Transaction(String input, String[] outputs, double amount, long txTimestamp) {
        this.input = input;
        this.outputs = outputs;
        this.amount = amount;
        this.txTimestamp = txTimestamp;
        this.txHash = generateHash();
    }

    // Generate the transaction hash
    private String generateHash() {
        String data = input + Arrays.toString(outputs) + amount + txTimestamp;
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

    // Sign the transaction with a private key
    public void signTransaction(PrivateKey privateKey) throws Exception {
        Signature rsa = Signature.getInstance("SHA256withRSA");
        rsa.initSign(privateKey);
        rsa.update(txHash.getBytes());
        this.signature = rsa.sign();
    }

    // Digital signature verification
    public boolean verifySignature(PublicKey publicKey) throws Exception {
        Signature rsa = Signature.getInstance("SHA256withRSA");
        rsa.initVerify(publicKey);
        rsa.update(txHash.getBytes());
        return rsa.verify(this.signature);
    }

    public String getInput() { return input; }
    public void setInput(String input) { this.input = input; }

    public String[] getOutputs() { return outputs; }
    public void setOutputs(String[] outputs) { this.outputs = outputs; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public long getTxTimestamp() { return txTimestamp; }
    public void setTxTimestamp(long txTimestamp) { this.txTimestamp = txTimestamp; }

    public String getTxHash() { return txHash; }
    public byte[] getSignature() { return signature; }

    @Override
    public String toString() {
        return "Transaction{" +
                "input='" + input + '\'' +
                ", outputs=" + Arrays.toString(outputs) +
                ", amount=" + amount +
                ", txTimestamp=" + txTimestamp +
                ", txHash='" + txHash + '\'' +
                ", signature=" + Arrays.toString(signature) +
                '}';
    }
}
