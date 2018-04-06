package com.betwin.contract;

import org.apache.commons.codec.Charsets;
import org.apache.commons.io.IOUtils;


import java.io.*;


public class SoliditySource {
    private final String source;

    public SoliditySource(String source) {
        this.source = source;
    }

    public String getSource() {
        return source;
    }

    public static SoliditySource from(File file) {
        try {
            return from(new FileInputStream(file));
        } catch (IOException e) {
            throw new IOError(e);
        }
    }

    public static SoliditySource from(InputStream file) {
        try {
            return new SoliditySource(IOUtils.toString(file, Charsets.UTF_8));
        } catch (IOException e) {
            throw new IOError(e);
        }
    }

    public static SoliditySource from(String source) {
        return new SoliditySource(source);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SoliditySource that = (SoliditySource) o;

        return source != null ? source.equals(that.source) : that.source == null;

    }

    @Override
    public int hashCode() {
        return source != null ? source.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "source:" + source;
    }
}
