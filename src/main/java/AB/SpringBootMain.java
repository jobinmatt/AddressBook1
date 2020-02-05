package AB;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringBootMain {
    public static void main(String[] args){
        SpringApplicationBuilder builder = new SpringApplicationBuilder(SpringBootMain.class);
        builder.headless(false);
        ConfigurableApplicationContext context = builder.run(args);
    }
    //comment
}
