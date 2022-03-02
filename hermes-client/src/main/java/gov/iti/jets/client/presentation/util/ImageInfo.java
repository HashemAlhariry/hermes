package gov.iti.jets.client.presentation.util;

public class ImageInfo {
    public byte [] bytes;
    public String extension;

    public ImageInfo(byte[] bytes, String extension) {
        this.bytes = bytes;
        this.extension = extension;
    }

    public ImageInfo() {
    }
}
