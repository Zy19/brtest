package com.br;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

class WordGeneratorStreamer {

    private final String filename;
    private final List<String> lines;
    private final Stream<String> stream;

    public WordGeneratorStreamer(final String filename) {
        this.filename = filename;
        this.lines = null;
        this.stream = null;
    }

    public WordGeneratorStreamer(final List<String> lines) {
        this.filename = null;
        this.lines = lines;
        this.stream = null;
    }

    public WordGeneratorStreamer(final Stream<String> stream) {
        this.filename = null;
        this.lines = null;
        this.stream = stream;
    }

    public Stream<String> getStream() throws IOException {

        if(stream != null){
            return stream;
        }

        if (lines != null) {
            return lines.stream();
        }
        return Files.lines(Paths.get(filename));
    }
}
