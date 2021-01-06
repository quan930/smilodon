package cn.lilq.smilodon.serviceregistry;

import cn.lilq.smilodon.SmilodonRegister;
import org.springframework.cloud.client.serviceregistry.Registration;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @auther: Li Liangquan
 * @date: 2021/1/6 15:52
 * 登记类(注册表中的一条记录)
 */
public class SmilodonRegistration implements Registration {
    private SmilodonRegister smilodonRegister;
    private final Map<String, String> metadata;

    public SmilodonRegistration(SmilodonRegister smilodonRegister) {
        this.smilodonRegister = smilodonRegister;
        this.metadata = new ConcurrentHashMap<>();
    }

    public SmilodonRegistration() {
        this.metadata = new ConcurrentHashMap<>();
    }

    @Override
    public String getServiceId() {
        return smilodonRegister.getServiceId();
    }

    @Override
    public String getHost() {
        return smilodonRegister.getHost();
    }

    @Override
    public int getPort() {
        return smilodonRegister.getPort();
    }

    @Override
    public boolean isSecure() {
        return smilodonRegister.getSecure();
    }

    @Override
    public URI getUri() {
//        String scheme = isSecure() ? "https" : "http";
//        String uri = String.format("%s://%s:%s", scheme,getHost(), getPort());
        String uri = String.format("%s://%s:%s", getScheme(),getHost(), getPort());
        return URI.create(uri);
    }


    @Override
    public String getInstanceId() {
        return smilodonRegister.getInstanceId();
    }

    @Override
    public Map<String, String> getMetadata() {
        return this.metadata;
    }

    @Override
    public String getScheme() {
        return isSecure() ? "https" : "http";
    }
}
