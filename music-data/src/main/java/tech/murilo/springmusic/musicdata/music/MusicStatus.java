package tech.murilo.springmusic.musicdata.music;

public enum MusicStatus {
    WAIT_SAVE_PATH("WaitSavePath"),
    READY("Ready");

    private final String value;

    private MusicStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}