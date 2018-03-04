package wilsonranking.api.service;

import java.io.File;

/**
 * Created by chunwyc on 2/3/2018.
 */
public interface FileService {
    void startFileSystemWatcher();
    void stopFileSystemWatcher();
    void submitFile(File file);
}
