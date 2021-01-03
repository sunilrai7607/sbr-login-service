package com.sbr.platform.api.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbr.platform.api.utility.CipherUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CustomRedisDataSerializer implements RedisSerializer<Object> {

    private final ObjectMapper objectMapper;
    private final String CHIPER_ALORITH = "AES/ECB/PKCS5Padding";
    private RedisSerializer<Object> objectRedisSerializer;
    private boolean encryptionEnabled = true;
    private final String secreteKey;

    public CustomRedisDataSerializer() {
        this.secreteKey = "testing";
        this.objectMapper = new ObjectMapper();
        this.objectRedisSerializer = new JdkSerializationRedisSerializer();
    }

    /**
     * Serialize the given object to binary data.
     *
     * @param data object to serialize. Can be {@literal null}.
     * @return the equivalent binary data. Can be {@literal null}.
     */
    @Override
    public byte[] serialize(Object data) throws SerializationException {
        byte[] serializeData = this.objectRedisSerializer.serialize(data);
        if (serializeData == null || !encryptionEnabled) return serializeData;
        if (log.isTraceEnabled()) log.trace("Default encryption applied");
        byte[] encrptedValue = CipherUtility.encrypt(serializeData, this.secreteKey);
        log.info("encrypt the bytes before serialize : {} ",encrptedValue);
        return encrptedValue;

    }


    /**
     * Deserialize an object from the given binary data.
     *
     * @param bytes object binary representation. Can be {@literal null}.
     * @return the equivalent object instance. Can be {@literal null}.
     */
    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null) return null;
        if(encryptionEnabled){
            log.info("decryption the bytes before deserialize");
            bytes = CipherUtility.decrypt(bytes,this.secreteKey);
        }
        return this.objectRedisSerializer.deserialize(bytes);
    }
}
