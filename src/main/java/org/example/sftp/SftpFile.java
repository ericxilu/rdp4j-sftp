package org.example.sftp;


import com.github.drapostolos.rdp4j.spi.FileElement;
import com.jcraft.jsch.ChannelSftp.LsEntry;

public class SftpFile implements FileElement, Comparable<Object>
{
    private final LsEntry file;

    public SftpFile(LsEntry file) {
        this.file = file;
    }

    //    @Override
    public long lastModified()
    {
        return file.getAttrs().getMTime();
    }

    //  @Override
    public boolean isDirectory() {
        return false;
    }

    //  @Override
    public String getName() {
        return file.getFilename();
    }

    //  @Override
    public String toString() {
        return getName();
    }

    @Override
    public int compareTo(Object o) {
        return this.getName().compareTo(((SftpFile)o).getName());
    }
}
