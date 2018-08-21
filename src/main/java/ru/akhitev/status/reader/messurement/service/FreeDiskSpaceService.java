package ru.akhitev.status.reader.messurement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.akhitev.status.reader.messurement.vo.Status;
import ru.akhitev.status.reader.reporter.ReportMaker;

import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class FreeDiskSpaceService implements MessureService {
    private final static  String MESSUREMENT = "Free Disk Space";
    private final static long THRESHOLD_VALUE = 1000L;

    @Autowired
    private ReportMaker reportMaker;

    @Override
    public void prepareValue() {
        Status status;
        try {
            long freeSpaceInMegaBytes = freeSpaceInMegaBytes();
            reportMaker.addStatus(
                    new Status(MESSUREMENT, convertToString(freeSpaceInMegaBytes), isNormalValue(freeSpaceInMegaBytes)));
        } catch (IOException e) {
            e.printStackTrace();
            reportMaker.addStatus(new Status(MESSUREMENT, "error", Boolean.FALSE));
        }
    }

    private long freeSpaceInMegaBytes() throws IOException {
        final long threeOrdersOfNumber = 1024;
        return freeSpaceInBites() / threeOrdersOfNumber / threeOrdersOfNumber;
    }

    private long freeSpaceInBites() throws IOException {
        FileStore store = Files.getFileStore(Paths.get("/"));
        return store.getUsableSpace();
    }

    private boolean isNormalValue(long freeSpaceInMegaBytes) {
        return freeSpaceInMegaBytes > THRESHOLD_VALUE;
    }

    private String convertToString(long freeSpaceInMegaBytes) throws IOException {
        return String.format("%dMB", freeSpaceInMegaBytes());
    }
}
