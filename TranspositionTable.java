public class TranspositionTable {
    private class Entry {
        private long key;
        private byte value;

        public Entry(long key, byte value) {
            this.key = key;
            this.value = value;
        }
    }

    private Entry[] table;

    private long index(long key) {
        return key % table.length;
    }

    public TranspositionTable(long size) {
        table = new Entry[(int) size];
    }

    public void reset() {
        for (int i = 0; i < table.length; i++) {
            table[i] = new Entry((long) 0, (byte) 0);
        }
    }

    public void put(long key, byte value) {
        int index = (int) index(key);
        table[index].key = key;
        table[index].value = value;
    }

    public byte get(long key) {
        int index = (int) index(key);
        if (table[index].key == key) {
            return table[index].value;
        } else {
            return 0;
        }
    }
}
