package com.brandshoutcom.utils;

/**
 * Created by LANReHNiCs on 9/17/2016.
 */
public class ImageResizer {
    private int allowedFileSizeInByte;
    private String sourcePath;
    private String destinationPath;

    public ImageResizer(int allowedSize, String sourcePath, String destinationPath)
    {
        allowedFileSizeInByte = allowedSize;
        this.sourcePath = sourcePath;
        this.destinationPath = destinationPath;
    }
}
