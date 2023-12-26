package org.example;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.dcm4che3.net.*;
import org.dcm4che3.net.pdu.*;

public class Main {
    public static void main(String[] args) throws IOException {

        ApplicationEntity locAE = new ApplicationEntity();
        locAE.setAETitle("DicomApp".toUpperCase());
        ApplicationEntity remAE = new ApplicationEntity();
        remAE.setAETitle("Sapp");

        Connection localConn = new Connection();
        //localConn.setHostname("localhost");
        localConn.setProtocol(Connection.Protocol.DICOM);
        locAE.addConnection(localConn);

        Connection remoteConn = new Connection();
        remoteConn.setHostname("10.20.8.95");
        remoteConn.setPort(5678);
        remoteConn.setProtocol(Connection.Protocol.DICOM);
        remAE.addConnection(remoteConn);

        // Creating a device with an executor for the session...
        Device device = new Device("test_device");
        device.addConnection(localConn);
        device.addApplicationEntity(locAE);
        ExecutorService exec = Executors.newSingleThreadExecutor();
        device.setExecutor(exec);

        // Setting the association request parameters for an SRR SOP...
        AAssociateRQ assocReq = new AAssociateRQ();
        assocReq.setCalledAET(remAE.getAETitle());
        assocReq.setCallingAET(locAE.getAETitle());
        //assocReq.setApplicationContext("1.2.840.10008.3.1.1.1");
        //assocReq.setImplClassUID("1.2.40.0.13.1.3");
        //assocReq.setImplVersionName("dcm4che-5.12.0");
        assocReq.addPresentationContext(new PresentationContext(
                1, "1.2.840.10008.1.1", "1.2.840.10008.1.2"));
//        assocReq.addPresentationContext(new PresentationContext(
//                1, "1.2.840.10008.5.1.4.1.2.2.2", "1.2.840.10008.1.2"));

        try {
            Association assoc = locAE.connect(localConn, remoteConn, assocReq);

            DimseRSP rsp = assoc.cecho();
            rsp.next(); // Consume reply, which may fail
            System.out.println("성공 : " + rsp);

//            Attributes atts = new Attributes();
//            atts.setString(0x00080052, VR.LO, "STUDY"); // on this Level
//            atts.setString(0x0020000D, VR.UI, // Study Instance UID
//                    "1.2.826.0.1.[...]");

            if (assoc.isReadyForDataTransfer()) {
                assoc.waitForOutstandingRSP();
                assoc.release();
            }
        }
        catch(Throwable ignore) {
            System.out.println("에러 : " + ignore);
        }
        finally {
//            System.out.println("릴리즈 : " + assoc);
//            assoc.release();
            exec.shutdown();
        }
    }
}





        /*
        String deviceName = "my_device";
        //Device device = new Device(deviceName);


        Connection local_c = new Connection();
        local_c.setHostname("127.0.0.1");
        //local_c.setPort(5678);
        local_c.setPort(Connection.NOT_LISTENING);


        Connection remote_c = new Connection();
        remote_c.setHostname("10.20.8.95");
        remote_c.setPort(5678);


        ApplicationEntity ae = new ApplicationEntity("DicomApp".toUpperCase());
        ae.addConnection(local_c); // on which we may not be listening
        ae.setAssociationInitiator(true);
        ae.setAssociationAcceptor(false);


        Device device = new Device("DicomAssociationExampleDevice".toLowerCase());
        device.addConnection(local_c);
        device.addApplicationEntity(ae);

        // Configure association
        AAssociateRQ rq = new AAssociateRQ();
        rq.setCallingAET("DicomApp".toUpperCase());
        rq.setCalledAET("CONQUESTSRV"); // e.g. "GEPACS"
        rq.setImplVersionName("MY-SCU-1.0"); // Max 16 chars


        try {
            Association association = ae.connect(local_c, remote_c, rq);

            DimseRSP rsp = association.cecho();
            rsp.next(); // Consume reply, which may fail
            System.out.println("Hello world! : " + rsp);


            // Still here? Success!
            // 3) Close the connection to the SCP
            if (association.isReadyForDataTransfer()) {
                association.waitForOutstandingRSP();
                association.release();
            }
        }
        catch(Throwable ignore) {
            // Failure
            System.out.println(ignore);

        }
        finally {

        }
         */





        /*
        ApplicationEntity locAE = new ApplicationEntity("DicomAssociationExampleApp");
        locAE.setInstalled(true);

        AAssociateRQ rq = new AAssociateRQ();




        String remoteAETitle = "REMOTE_AE_TITLE"; // 대상 시스템의 AE 제목
        String localAETitle = "LOCAL_AE_TITLE"; // 로컬 시스템의 AE 제목
        String callingAETitle = "CALLING_AE_TITLE"; // 호출 시스템의 AE 제목
        String remoteHost = "REMOTE_HOST"; // 대상 시스템의 호스트 주소
        int remotePort = 11112; // 대상 시스템의 포트 번호

        try {
            // DICOM 연결을 설정합니다.
            Connection connection = new Connection();
            connection.setHostname("10.20.8.95"); // 로컬 시스템의 호스트 주소
            connection.setPort(5678); // 로컬 시스템의 포트 번호 (임의의 유효한 포트)
            connection.setTlsProtocols(new String[]{"TLSv1.2"}); // TLS 프로토콜 설정 (필요에 따라)

            // 대상 시스템의 정보를 설정합니다.
            connection.createAssociation(
            connection.connect()
            Association association = connection.connect(
                    remoteAETitle, remoteHost, remotePort,
                    localAETitle, callingAETitle);

            // C-ECHO 요청을 보냅니다.
            CEchoRequest echoRequest = new CEchoRequest();
            association.negotiateAssociation();
            association.invoke(echoRequest);

            // C-ECHO 응답을 받습니다.
            DimseRSP dimseRSP = association.readDimseRSP();
            if (dimseRSP.getCommandField() == DIMSECOMMAND.C_ECHO_RSP) {
                System.out.println("C-ECHO response received: " + dimseRSP.getDataset().getString(Tags.Status));
            }

            // 연결을 종료합니다.
            association.release(true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    } catch (IncompatibleConnectionException e) {
            throw new RuntimeException(e);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
         */