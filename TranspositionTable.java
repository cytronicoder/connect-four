import java.util.Arrays;

public class TranspositionTable {
    private static class Entry {
        public long key;
        public byte val;

        public Entry(long key, byte val) {
            this.key = key;
            this.val = val;
        }
    }

    private Entry[] entries;

    public TranspositionTable(int size) {
        entries = new Entry[size];
        Arrays.fill(entries, new Entry(0, (byte) 0));
    }

    public void reset() {
        Arrays.fill(entries, new Entry(0, (byte) 0));
    }

    public void put(long key, byte val) {
        int i = index(key);
        entries[i] = new Entry(key, val);
    }

    public byte get(long key) {
        int i = index(key);
        if (entries[i].key == key)
            return entries[i].val;
        else
            return 0;
    }

    private int index(long key) {
        return (int) (key % entries.length);
    }
}
