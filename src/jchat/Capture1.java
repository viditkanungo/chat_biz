/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jchat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.*;
import javax.swing.JButton;
import javax.swing.JTextField;

class Capture1 implements Runnable {
TargetDataLine line1;
Thread thread1;
AudioInputStream audioInputStream1;
String errStr1;
double duration1, seconds1;
private boolean bigendian1;

public void start() {
errStr1 = null;
thread1 = new Thread(this);
thread1.setName("Capture");
thread1.start();
}
public void stop() {
thread1 = null;
}
private void shutDown(String message) {
if ((errStr1 = message) != null && thread1 != null) {
thread1 = null;
//playB.setEnabled(true);
//captB.setText("Record");
System.err.println(errStr1);
}
}
public void run() {
duration1 = 0;
audioInputStream1 = null;
// define the required attributes for our line,
// and make sure a compatible line is supported.
AudioFormat.Encoding encoding = AudioFormat.Encoding.PCM_SIGNED;
float rate = 44100.0f;
int channels = 2;
int frameSize = 4;
int sampleSize = 16;
boolean bigEndian = true;
AudioFormat format = new AudioFormat(encoding, rate, sampleSize, channels, (sampleSize / 8) * channels, rate, bigendian1);
DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
if (!AudioSystem.isLineSupported(info)) {
shutDown("Line matching " + info + " not supported.");
return;
}
// get and open the target data line for capture.
try {
line1 = (TargetDataLine) AudioSystem.getLine(info);
line1.open(format, line1.getBufferSize());
} catch (LineUnavailableException ex) {
shutDown("Unable to open the line: " + ex);
return;
} catch (SecurityException ex) {
shutDown(ex.toString());
//JavaSound.showInfoDialog();
return;
} catch (Exception ex) {
shutDown(ex.toString());
return;
}
// play back the captured audio data
ByteArrayOutputStream out = new ByteArrayOutputStream();
int frameSizeInBytes = format.getFrameSize();
int bufferLengthInFrames = line1.getBufferSize() / 8;
int bufferLengthInBytes = bufferLengthInFrames * frameSizeInBytes;
byte[] data = new byte[bufferLengthInBytes];
int numBytesRead;
line1.start();
while (thread1 != null) {
if ((numBytesRead = line1.read(data, 0, bufferLengthInBytes)) == -1) {
break;
}
out.write(data, 0, numBytesRead);
}
// we reached the end of the stream.
// stop and close the line.
line1.stop();
line1.close();
line1 = null;
// stop and close the output stream
try {
out.flush();
out.close();
} catch (IOException ex) {
ex.printStackTrace();
}
// load bytes into the audio input stream for playback
byte audioBytes[] = out.toByteArray();
ByteArrayInputStream bais = new ByteArrayInputStream(audioBytes);
audioInputStream1 = new AudioInputStream(bais, format, audioBytes.length / frameSizeInBytes);
long milliseconds = (long) ((audioInputStream1.getFrameLength() * 1000) / format.getFrameRate());
duration1 = milliseconds / 1000.0;
try {
AudioSystem.write(audioInputStream1, AudioFileFormat.Type.WAVE, new File("voice1.wav"));
} catch (Exception ex) {
ex.printStackTrace();
return;
}
}
public static void main(String args[])
{
    Capture1 cap=new Capture1();
    cap.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Capture.class.getName()).log(Level.SEVERE, null, ex);
        }
    cap.stop();
}
}