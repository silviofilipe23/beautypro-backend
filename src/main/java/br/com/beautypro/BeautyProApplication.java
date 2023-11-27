package br.com.beautypro;

import com.twilio.Twilio;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class BeautyProApplication {
	public static final String ACCOUNT_SID = "AC690061ea68d5f2a9fd06a33aa0ca277f";
	public static final String AUTH_TOKEN = "64167f0365098a93b96a82bc2c67ea77";
	public static void main(String[] args) {

		TimeZone.setDefault(TimeZone.getTimeZone("Etc/UTC"));

		SpringApplication.run(BeautyProApplication.class, args);

		Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
	}

}
