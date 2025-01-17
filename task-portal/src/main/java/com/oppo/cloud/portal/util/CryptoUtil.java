/*
 * Copyright 2023 OPPO.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.oppo.cloud.portal.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

/**
 * pbkdf2_sha256加密验证算法
 * airflow各个版本的加解密包werkzeug中的常量值不同,如:
 * Werkzeug-0.14.1    DEFAULT_PBKDF2_ITERATIONS:50000
 * Werkzeug-1.0.1     DEFAULT_PBKDF2_ITERATIONS:150000
 * Werkzeug-2.2.1     DEFAULT_PBKDF2_ITERATIONS:260000
 * 目前默认使用的是 1.0.1 版本,如使用其他版本werkzeug,需变更参数值,
 * 在airflow安装节点上输入命令进行查询,如: pip index versions Werkzeug
 * 根据查询出来的INSTALLED版本进行变更即可;
 */
@Slf4j
@Component
public class CryptoUtil {
    /**
     * 默认迭代次数
     */
    private static Integer DEFAULT_ITERATIONS;

    public static Integer getDefaultIterations() {
        return DEFAULT_ITERATIONS;
    }

    @Value("${custom.default_iterations:150000}")
    public void setDefaultIterations(Integer defaultIterations) {
        DEFAULT_ITERATIONS = defaultIterations;
    }

    public static Integer getKeyLength() {
        return KEY_LENGTH;
    }

    public static String algorithm = "pbkdf2:sha256";

    private static final Integer KEY_LENGTH = 256;


    private static String getEncodedHash(String password, String salt, int iterations) {

        SecretKeyFactory keyFactory = null;
        try {
            keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        } catch (NoSuchAlgorithmException e) {
            log.error(e.getMessage());
            return "";
        }

        KeySpec keySpec =
                new PBEKeySpec(password.toCharArray(), salt.getBytes(StandardCharsets.UTF_8), iterations, KEY_LENGTH);
        SecretKey secret = null;
        try {
            secret = keyFactory.generateSecret(keySpec);
        } catch (InvalidKeySpecException e) {
            log.error(e.getMessage());
        }

        byte[] rawHash = secret.getEncoded();
        String hex = toHex(rawHash);
        return hex;
    }

    /**
     * @param password
     * @param salt
     * @param iterations
     * @return
     */
    private static String encode(String password, String salt, int iterations) {
        // 返回哈希密码，以及算法、迭代次数和salt
        String hash = getEncodedHash(password, salt, iterations);
        return String.format("%s:%d$%s$%s", algorithm, iterations, salt, hash);
    }

    /**
     * 校验密码
     *
     * @param password
     * @param hashedPassword
     * @return
     */
    public static boolean checkPassword(String password, String hashedPassword) {

        /*
         * <algorithm>$<iterations>$<salt>$<hash> 以美元字符分分隔并由哈希算法、算法迭代次数（工作因数）、随机的salt、以及生成的密码哈希值组成
         */

        String[] parts = hashedPassword.split("\\$");
        if (parts.length != 3) {
            // 格式错误
            return false;
        }

        Integer iterations = Integer.parseInt(hashedPassword.split(":")[2].split("\\$")[0]);
        String salt = parts[1];
        String hash = encode(password, salt, iterations);

        return hash.equals(hashedPassword);
    }

    /**
     * Converts a byte array into a hexadecimal string.
     *
     * @param array the byte array to convert
     * @return a length*2 character string encoding the byte array
     */
    private static String toHex(byte[] array) {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if (paddingLength > 0) {
            return String.format("%0" + paddingLength + "d", 0) + hex;
        } else {
            return hex;
        }
    }
}
