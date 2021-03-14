package com.epam.esm.reader.util;

public class Counter {

    private static int validFilesCount;
    private static int inValidFilesCount;

    public static int getValidFilesCount() {
        return validFilesCount;
    }

    public static int getInValidFilesCount() {
        return inValidFilesCount;
    }

    public static synchronized void increaseValidFilesCount() {
        validFilesCount++;
    }

    public static synchronized void increaseInValidFilesCount() {
        inValidFilesCount++;
    }
}
