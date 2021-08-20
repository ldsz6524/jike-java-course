package io.kimmking.rpcfx.api;

import lombok.Data;
import org.apache.commons.lang.ArrayUtils;

@Data
public class RpcfxRequest {
  private String serviceClass;
  private String method;
  private Object[] params;
}
