package sg.edu.iss.jam;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;


import sg.edu.iss.jam.model.User;
import sg.edu.iss.jam.repo.ChannelRepository;
import sg.edu.iss.jam.repo.CommentsRepository;
import sg.edu.iss.jam.repo.MediaRepository;
import sg.edu.iss.jam.repo.OrderDetailsRepository;
import sg.edu.iss.jam.repo.PlaylistsRepository;
import sg.edu.iss.jam.repo.ProductRepository;
import sg.edu.iss.jam.repo.RolesRepository;
import sg.edu.iss.jam.repo.ShoppingCartDetailsRepository;
import sg.edu.iss.jam.repo.ShoppingCartRepository;
import sg.edu.iss.jam.repo.TagRepository;
import sg.edu.iss.jam.repo.UserHistoryRepository;
import sg.edu.iss.jam.repo.UserRepository;

@SpringBootApplication
@EnableAutoConfiguration
public class JamadProjApplication {

	@Autowired
	RolesRepository rolesrepo;

	@Autowired
	PlaylistsRepository plrepo;

	@Autowired
	MediaRepository mediarepo;

	@Autowired
	UserRepository urepo;

	@Autowired
	TagRepository tagrepo;

	@Autowired
	CommentsRepository commentsrepo;

	@Autowired
	ChannelRepository channelrepo;

	@Autowired
	UserHistoryRepository uhrepo;

	@Autowired
	RolesRepository rrepo;

	@Autowired
	ProductRepository prepo;

	@Autowired
	OrderDetailsRepository odrepo;

	@Autowired
	MediaRepository mrepo;

	@Autowired
	ShoppingCartRepository srepo;

	@Autowired
	ShoppingCartDetailsRepository shdrepo;

	@Autowired
	ChannelRepository crepo;

	@Bean
	public RestTemplate getRestTemplage() {
		return new RestTemplate();
	}

	public static void main(String[] args) {
		SpringApplication.run(JamadProjApplication.class, args);
	}
	
	public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
	    Set<Object> seen = ConcurrentHashMap.newKeySet();
	    return t -> seen.add(keyExtractor.apply(t));
	}

	@Bean
	CommandLineRunner runner() {

		return args -> {
			
		};
	}
	
}
