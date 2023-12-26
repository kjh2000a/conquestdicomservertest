package org.example;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.dcm4che3.net.*;
import org.dcm4che3.net.pdu.*;

public class DicomAssociationExample {
    public static void main(String[] args) throws IOException {
        String deviceName = "my_device";
        //Device device = new Device(deviceName);


        Connection local_c = new Connection();
        local_c.setHostname("localhost");
        local_c.setPort(Connection.NOT_LISTENING);


        Connection remote_c = new Connection();
        remote_c.setHostname("10.20.8.95");
        remote_c.setPort(5678);


        ApplicationEntity ae = new ApplicationEntity("DicomAssociationExampleApp".toUpperCase());
        ae.addConnection(local_c); // on which we may not be listening
        ae.setAssociationInitiator(true);
        ae.setAssociationAcceptor(false);


        Device device = new Device("DicomAssociationExampleDevice".toLowerCase());
        device.addConnection(local_c);
        device.addApplicationEntity(ae);


        // Configure association
        AAssociateRQ rq = new AAssociateRQ();
        rq.setCallingAET("DicomAssociationExampleApp");
        rq.setCalledAET("CONQUESTSRV1"); // e.g. "GEPACS"
        rq.setImplVersionName("MY-SCU-1.0"); // Max 16 chars


        try {
            Association association = ae.connect(local_c, remote_c, rq);

            DimseRSP rsp = association.cecho();
            rsp.next(); // Consume reply, which may fail

            if (association.isReadyForDataTransfer()) {
                association.waitForOutstandingRSP();
                association.release();
            }
        }
        catch(Throwable ignore) {
            // Failure
        }
    }
}


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