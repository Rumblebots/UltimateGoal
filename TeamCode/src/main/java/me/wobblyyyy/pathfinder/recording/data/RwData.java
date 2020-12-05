package me.wobblyyyy.pathfinder.recording.data;

/**
 * No way to natively write on Android!
 *
 * <p>
 * You need to use the Pathfinder-ftc library (or write
 * your own implementation) in order to save information
 * locally.
 * </p>
 *
 * @author Colin Robertson
 */
public interface RwData {
    /**
     * Read data.
     *
     * @return data that's been read.
     */
    String read(String path, String file);

    /**
     * Save data.
     *
     * @param data data that's to be written.
     */
    void save(String path, String file, String data);
}
