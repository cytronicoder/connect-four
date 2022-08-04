public class TranspositionTable {
    private static class Entry {
        private long key;
        private byte value;
    }

    private static Entry[] entries;

    private static long index(long key) {
        return key % entries.length;
    }

    public TranspositionTable(int size) {
        entries = new Entry[size];
    }

    public void reset() {
        // fill everything with 0
        for (int i = 0; i < entries.length; i++) {
            entries[i] = new Entry();
            entries[i].key = 0;
            entries[i].value = 0;
        }
    }

    public void put(long key, byte value) {
        int index = (int) index(key);
        entries[index].key = key;
        entries[index].value = value;
    }

    public byte get(long key) {
        int index = (int) index(key);
        if (entries[index].key == key) {
            return entries[index].value;
        }
        return 0;
    }
}
