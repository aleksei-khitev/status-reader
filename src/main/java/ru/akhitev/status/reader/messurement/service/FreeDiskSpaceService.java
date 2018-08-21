package ru.akhitev.status.reader.messurement.service;

import ru.akhitev.status.reader.messurement.vo.Status;

import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FreeDiskSpaceService implements MessureService {
    @Override
    public Status prepareValue() {
        final String messurement = "Free Disk Space";
        Status status;
        try {

            status = new Status(messurement, String.valueOf(freeSpaceInMegaBytes()));
        } catch (IOException e) {
            e.printStackTrace();
            status = new Status(messurement, "error");
        }
        return status;
    }

    private long freeSpaceInMegaBytes() throws IOException {
        return freeSpaceInBites() / 1024 / 1024;
    }

    private long freeSpaceInBites() throws IOException {
        FileStore store = Files.getFileStore(Paths.get("/"));
        return store.getUsableSpace();
    }
}
