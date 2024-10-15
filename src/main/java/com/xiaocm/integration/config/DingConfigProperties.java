/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.xiaocm.integration.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 *
 * @author ASUS
 */
@Data
@ConfigurationProperties(prefix = "ding.open.app")
@Configuration
public class DingConfigProperties {
    private String clientId;
    private String clientSecret;
}
