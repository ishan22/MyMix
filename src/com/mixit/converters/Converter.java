package com.mixit.converters;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Converter extends Decoder {
	
    public static void convert(String sourceFileName, String destFileName)
            throws IOException {
        InputStream fileIn = new FileInputStream(sourceFileName);
        BufferedInputStream in = new BufferedInputStream(fileIn, 128 * 1024);
        convert(in, destFileName);
        in.close();
    }

    private static void convert(InputStream sourceStream, String destFileName)
            throws IOException {
        int frameCount = Integer.MAX_VALUE;
        WavConverter decoder = new WavConverter();
        Bitstream stream = new Bitstream(sourceStream);
        frameCount = Integer.MAX_VALUE;
        try {
            for (int frame = 0; frame < frameCount; frame++) {
                Header header = stream.readFrame();
                if (header == null)
                    break;
                if (decoder.channels == 0) {
                    int channels = (header.mode() == Header.MODE_SINGLE_CHANNEL) ? 1
                            : 2;
                    int freq = header.frequency();
                    decoder.initOutputBuffer(channels, freq, destFileName);
                }
                decoder.decodeFrame(header, stream);
                stream.closeFrame();
            }
        } finally {
            decoder.close();
        }
    }

    private short[] buffer;
    private WaveFileWriter outWave;

    public void initOutputBuffer(int numberOfChannels, int freq, String fileName)
            throws IOException {
        super.initOutputBuffer(null, numberOfChannels);
        buffer = new short[BUFFER_SIZE];
        for (int i = 0; i < numberOfChannels; ++i)
            bufferPointer[i] = (short) i;
        outWave = new WaveFileWriter(fileName, freq, (short) 16,
                (short) numberOfChannels);
    }
    
    public void appendSamples(int channel, double[] f) {
        int p = bufferPointer[channel];
        for (int i = 0; i < 32; i++) {
            double sample = f[i];
            short s = ((sample > 32767.0f) ? 32767
                    : ((sample < -32768.0f) ? -32768 : (short) sample));
            buffer[p] = s;
            p += 2;
        }
        bufferPointer[channel] = p;
    }    

    public void writeBuffer() throws IOException {
        outWave.writeData(buffer, bufferPointer[0]);
        for (int i = 0; i < channels; ++i)
            bufferPointer[i] = i;
    }

    public void close() throws IOException {
        outWave.close();
    }
}