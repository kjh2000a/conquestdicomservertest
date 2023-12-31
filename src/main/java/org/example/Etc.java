package org.example;

import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;
import org.dcm4che3.data.VR;
import org.dcm4che3.net.*;
import org.dcm4che3.net.pdu.AAssociateRJ;
import org.dcm4che3.net.pdu.PresentationContext;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class Etc {
    public static void main(String[] args) throws IOException {

        /*
        class BadSocketException extends SocketException {
        }

        class DummyInputStream extends InputStream {

            Semaphore semaphore = new Semaphore(-1);

            @Override
            public int read() throws IOException {
                try {
                    // Simulate the blocking read for nextPDU
                    semaphore.acquire();
                } catch (InterruptedException e) {
                }
                return 0;
            }
        }

        class BadOutputStream extends OutputStream {

            @Override
            public void write(int b) throws IOException {
                throw new BadSocketException();
            }
        }

        class BadSocket extends Socket {

            @Override
            public InputStream getInputStream() {
                return new DummyInputStream();
            }

            @Override
            public OutputStream getOutputStream() {
                return new BadOutputStream();
            }
        }


        Socket socket = new BadSocket();

        ExecutorService executorService = Executors.newFixedThreadPool(1);

        Device device = new Device();
        device.setExecutor(executorService);
        device.setAssociationMonitor(new AssociationMonitor() {
            @Override
            public void onAssociationEstablished(Association as) {
            }

            @Override
            public void onAssociationFailed(Association as, Throwable e) {
            }

            @Override
            public void onAssociationRejected(Association as, AAssociateRJ aarj) {
            }

            @Override
            public void onAssociationAccepted(Association as) {
            }
        });

        Connection connection = new Connection("test1", "10.20.8.95", 5678);
        //connection.setDevice(device);

        ApplicationEntity ae = new ApplicationEntity("aeae");

        Association association = Association(ae, connection, socket);
        association.handle(new AAssociateAC());

        PresentationContext presentationContext = new PresentationContext(1, "as", "ts");
        Attributes cmd = new Attributes();
        cmd.setInt(Tag.CommandField, VR.US, 0x8021);
        cmd.setInt(Tag.Status, VR.US, Status.Success);
         */

        /*
        int performingCount = association.getPerformingOperationCount();
        Assert.assertThrows("OutputStream did not throw exception", BadSocketException.class, new ThrowingRunnable() {
            @Override public void run() throws Throwable {
                association.writeDimseRSP(presentationContext, cmd);
            }
        });

        Assert.assertEquals("Performing Operation count did not decrement",
                performingCount - 1, association.getPerformingOperationCount());
        executorService.shutdownNow();
         */


        /*
        Association ass = new Association();
        Connection con = new Connection("Ee", "10.20.8.95", 5678);
        String s = con.getHostname();
        System.out.println(s);
         */
    }
}
