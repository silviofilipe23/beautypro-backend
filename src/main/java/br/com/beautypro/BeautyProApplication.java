package br.com.beautypro;

import com.twilio.Twilio;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class BeautyProApplication {
	public static final String ACCOUNT_SID = "AC690061ea68d5f2a9fd06a33aa0ca277f";
	public static final String AUTH_TOKEN = "3f0f18e2c717090316a395108620ad5e";
	public static void main(String[] args) {

		TimeZone.setDefault(TimeZone.getTimeZone("Etc/UTC"));

		SpringApplication.run(BeautyProApplication.class, args);

		Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
	}

}
