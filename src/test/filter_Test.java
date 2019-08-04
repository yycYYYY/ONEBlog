import org.junit.Test;


import javax.servlet.Filter;
import java.net.URL;

public class filter_Test {
    @Test
    public void get(){
        URL url = Filter.class.getProtectionDomain().getCodeSource().getLocation();
        System.out.println("path:"+url.getPath()+"  name:"+url.getFile());
    }
}

