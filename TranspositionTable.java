public class TranspositionTable {
    /** An entry in the transposition table.
     * Each entry has a 56 bit key and an 8 bit score.
     */
    private class Entry {
        private long key;
        private byte value;

        public Entry(long key, byte value) {
            this.key = key;
            this.value = value;
        }
    }
    
    private Entry[] table;

    /** Get the index of the entry with the given key.
     * @param key The key of the entry.
     * @return The index of the entry with the given key.
     */
    private long index(long key) {
        return key % table.length;
    }

    /** Constructor */
    public TranspositionTable(long size) {
        table = new Entry[(int) size];
    }

    /** Reset the transposition table. */
    public void reset() {
        for (int i = 0; i < table.length; i++) {
            table[i] = new Entry((long) 0, (byte) 0);
        }
    }

    /** Insert an entry into the transposition table.
     * @param key The key of the entry.
     * @param value The value of the entry.
     */
    public void put(long key, byte value) {
        int index = (int) index(key);
        table[index].key = key;
        table[index].value = value;
    }

    /** Get the value of the entry with the given key.
     * @param key The key of the entry.
     * @return The value of the entry with the given key.
     */
    public byte get(long key) {
        int index = (int) index(key);
        if (table[index].key == key) {
            return table[index].value;
        } else {
            return 0;
        }
    }
}
