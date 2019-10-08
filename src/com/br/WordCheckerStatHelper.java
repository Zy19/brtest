package com.br;

/**
 * Helper which is used for getting stat info in multicore mode
 */
@Deprecated
class WordCheckerStatHelper {

    private int attempt;
    private long elapsedTime;
    private int chunk;
    private int delay;
    private int increaseSize;
    private int maxCoreInUse;
    private long elapsedTimePerWord;

    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder();

        builder.append("Attempt: ");
        builder.append(attempt);
        builder.append("; ");

        builder.append("elapsedTime: ");
        builder.append(elapsedTime);
        builder.append("; ");

        builder.append("elapsedTimePerWord: ");
        builder.append(elapsedTimePerWord);
        builder.append("; ");

        builder.append("chunk: ");
        builder.append(chunk);
        builder.append("; ");

        builder.append("delay: ");
        builder.append(delay);
        builder.append("; ");

        builder.append("increaseSize: ");
        builder.append(increaseSize);
        builder.append("; ");

        builder.append("maxCoreInUse: ");
        builder.append(maxCoreInUse);
        builder.append("; ");

        return builder.toString();
    }

    public int getAttempt() {
        return attempt;
    }

    public void setAttempt(int attempt) {
        this.attempt = attempt;
    }

    public long getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(long elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public int getChunk() {
        return chunk;
    }

    public void setChunk(int chunk) {
        this.chunk = chunk;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public int getIncreaseSize() {
        return increaseSize;
    }

    public void setIncreaseSize(int increaseSize) {
        this.increaseSize = increaseSize;
    }

    public int getMaxCoreInUse() {
        return maxCoreInUse;
    }

    public void setMaxCoreInUse(int maxCoreInUse) {
        this.maxCoreInUse = maxCoreInUse;
    }

    public long getElapsedTimePerWord() {
        return elapsedTimePerWord;
    }

    public void setElapsedTimePerWord(long elapsedTimePerWord) {
        this.elapsedTimePerWord = elapsedTimePerWord;
    }
}
