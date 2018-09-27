/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jchat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.*;
import javax.swing.JButton;
import javax.swing.JTextField;

class Capture implements Runnable {
TargetDataLine line;
Thread thread;
AudioInputStream audioInputStream;
String errStr;
double duration, seconds;
private boolean bigendian;

public void start() {
errStr = null;
thread = new Thread(this);
thread.setName("Capture");
thread.start();
}
public void stop() {
thread = null;
}
private void shutDown(String message) {
if ((errStr = message) != null && thread != null) {
thread = null;
//playB.setEnabled(true);
//captB.setText("Record");
System.err.println(errStr);
}
}
public void run() {
duration = 0;
audioInputStream = null;
// define the required attributes for our line,
// and make sure a compatible line is supported.
AudioFormat.Encoding encoding = AudioFormat.Encoding.PCM_SIGNED;
float rate = 44100.0f;
int channels = 2;
int frameSize = 4;
int sampleSize = 16;
boolean bigEndian = true;
AudioFormat format = new AudioFormat(encoding, rate, sampleSize, channels, (sampleSize / 8) * channels, rate, bigendian);
DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
if (!AudioSystem.isLineSupported(info)) {
shutDown("Line matching " + info + " not supported.");
return;
}
// get and open the target data line for capture.
try {
line = (TargetDataLine) AudioSystem.getLine(info);
line.open(format, line.getBufferSize());
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
int bufferLengthInFrames = line.getBufferSize() / 8;
int bufferLengthInBytes = bufferLengthInFrames * frameSizeInBytes;
byte[] data = new byte[bufferLengthInBytes];
int numBytesRead;
line.start();
while (thread != null) {
if ((numBytesRead = line.read(data, 0, bufferLengthInBytes)) == -1) {
break;
}
out.write(data, 0, numBytesRead);
}
// we reached the end of the stream.
// stop and close the line.
line.stop();
line.close();
line = null;
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
audioInputStream = new AudioInputStream(bais, format, audioBytes.length / frameSizeInBytes);
long milliseconds = (long) ((audioInputStream.getFrameLength() * 1000) / format.getFrameRate());
duration = milliseconds / 1000.0;
try {
AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, new File("voice.wav"));
} catch (Exception ex) {
ex.printStackTrace();
return;
}
}
public static void main(String args[])
{
    Capture c=new Capture();
    c.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Capture.class.getName()).log(Level.SEVERE, null, ex);
        }
    c.stop();
}
}