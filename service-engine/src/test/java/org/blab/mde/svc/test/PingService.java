package org.blab.mde.svc.test;

import java.io.IOException;
import java.net.InetAddress;

import org.blab.mde.svc.annotation.Service;


@Service("pingSvc")
public interface PingService {
  default boolean ping(String host) {
    try {
      InetAddress address = InetAddress.getByName(host);
      return address.isReachable(2000);
    } catch (IOException e) {
      return false;
    }
  }
}
