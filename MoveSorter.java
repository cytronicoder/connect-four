public class MoveSorter {
    private int size;
    
    // Contains size moves with their score ordered by score
    private Move[] entries = new Move[Position.WIDTH];

    private class Move {
        private long move;
        private int score;
    }

    public void add(long move, int score) {
        int pos = size++;
        // shift all entries to the right to make room for the new entry
        for (int i = size - 1; i > pos; i--) {
            entries[i] = entries[i - 1];
        }
        entries[pos] = new Move();
        entries[pos].move = move;
        entries[pos].score = score;
    }

    public long getNext() {
        if (size != 0) {
            return entries[--size].move;
        } else {
            return 0;
        }
    }
}
