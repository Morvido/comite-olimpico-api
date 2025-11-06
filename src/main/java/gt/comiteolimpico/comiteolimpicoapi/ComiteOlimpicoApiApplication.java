package gt.comiteolimpico.comiteolimpicoapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class ComiteOlimpicoApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(ComiteOlimpicoApiApplication.class, args);
    }
}