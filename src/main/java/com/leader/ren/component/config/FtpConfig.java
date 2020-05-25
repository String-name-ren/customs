package com.leader.ren.component.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class FtpConfig {
    @Value("${ftp.host}")
    private String host;

    @Value("${ftp.port}")
    private Integer port;

    @Value("${ftp.cargoNo.username}")
    private String cargoNoUsername;

    @Value("${ftp.cargoNo.password}")
    private String cargoNoPassword;

    @Value("${ftp.cargoNo.bakDir}")
    private String cargoNoBakDir;

    @Value("${ftp.cba.username}")
    private String cbaUsername;

    @Value("${ftp.cba.password}")
    private String cbaPassword;

    @Value("${ftp.cba.bakDir}")
    private String cbaBakDir;

}
