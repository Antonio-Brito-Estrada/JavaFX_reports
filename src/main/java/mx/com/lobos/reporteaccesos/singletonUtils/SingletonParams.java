package mx.com.lobos.reporteaccesos.singletonUtils;

import java.util.HashMap;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SingletonParams {

    private HashMap<String, String> parametrosHsm;
    
    private static class singletonParamsHolder {
        public static SingletonParams instance = new SingletonParams();
    }
    
    private SingletonParams() {}
    
    public static SingletonParams getInstance() {
        return singletonParamsHolder.instance;
    }
    
}
