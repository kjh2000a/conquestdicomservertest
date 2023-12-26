package org.example;

import org.dcm4che3.net.Device;
import org.dcm4che3.net.Association;
import org.dcm4che3.net.AssociationMonitor;
import org.dcm4che3.net.Connection;
import org.dcm4che3.net.ApplicationEntity;
import org.dcm4che3.net.Status;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;
import org.dcm4che3.data.VR;
import org.dcm4che3.net.pdu.AAssociateAC;
import org.dcm4che3.net.pdu.AAssociateRJ;
import org.dcm4che3.net.pdu.PresentationContext;
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.function.ThrowingRunnable;





public class Main_Backup {

    public class Inner_Test_Backup {

    }

    public static void main(String[] args) throws IOException {
        System.out.println("Hello world!");
    }
}


class Test_Backup {

}