public class Transactions {

    private static class TransactionNode {
        Transaction data;
        TransactionNode next;

        TransactionNode(Transaction d) {
            data = d;
        }
    }

    private TransactionNode head;
    private TransactionNode iterCurrent;

    public Transactions() {
        head = null;
        iterCurrent = null;
    }

    public void add(Transaction tran) {
        TransactionNode n = new TransactionNode(tran);
        n.next = head;
        head = n;
    }

    
    public void reset() {
        iterCurrent = head;
    }

    public boolean hasNext() {
        return iterCurrent != null;
    }

    public Transaction getNext() {
        Transaction t = iterCurrent.data;
        iterCurrent = iterCurrent.next;
        return t;
    }
}
