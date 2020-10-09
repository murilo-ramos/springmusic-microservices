package tech.murilo.springmusic.musicuploadservice.service;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.S3ClientOptions;
import org.apache.kafka.common.utils.Bytes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tech.murilo.springmusic.musicdata.music.Music;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Service
public class UploadS3Service {

    public static final String MP3 = ".mp3";

    @Value("${s3.access.key}")
    private String accessKey;

    @Value("${s3.secret.key}")
    private String secretKey;

    @Value("${s3.host}")
    private String s3Host;

    @Value("${s3.bucket}")
    private String s3Bucket;

    public Music execute(String idCustomer, String idMusic, Bytes aByte) throws IOException {
        var credentials = new BasicAWSCredentials(
                accessKey,
                secretKey
        );

        var newClient = new AmazonS3Client(credentials, new ClientConfiguration().withSignerOverride("S3SignerType"));
        newClient.setS3ClientOptions(S3ClientOptions.builder().setPathStyleAccess(true).build());
        newClient.setEndpoint(s3Host);

        var nameFile = buildFileName(idCustomer, idMusic);

        var file = new File(nameFile);
        var path = file.toPath();
        Files.write(path, aByte.get());

        newClient.putObject(s3Bucket, nameFile, path.toFile());

        return Music
                .builder()
                .id(idMusic)
                .idCustomer(idCustomer)
                .path(nameFile)
                .size(aByte.get().length)
                .build();
    }

    private String buildFileName(String idCustomer, String idMusic) {
        return idCustomer.concat("-").concat(idMusic).concat(MP3);
    }
}
