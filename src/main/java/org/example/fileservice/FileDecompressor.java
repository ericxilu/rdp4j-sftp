package org.example.fileservice;

import java.io.IOException;
import java.nio.file.Path;

public interface FileDecompressor {
    void decompress(Path source, Path target) throws IOException;
}
