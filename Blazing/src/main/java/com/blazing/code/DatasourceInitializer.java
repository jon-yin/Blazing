package com.blazing.code;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.blazing.objects.Celebrity;
import com.blazing.objects.CriticReview;
import com.blazing.objects.Movie;
import com.blazing.objects.MovieCharacter;
import com.blazing.objects.Roles;
import com.blazing.objects.User;
import com.blazing.repositories.CelebrityRepository;
import com.blazing.repositories.MovieCharacterRepository;
import com.blazing.repositories.MovieRepository;
import com.blazing.repositories.ReviewRepository;
import com.blazing.repositories.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class DatasourceInitializer implements ApplicationListener<ApplicationReadyEvent> {

	@Autowired
    private MovieRepository movieRepo;
    
	@Autowired
    private UserRepository userRepo;
	
	@Autowired
    private CelebrityRepository celebrityRepo;
	
	@Autowired
    private MovieCharacterRepository moviecharRepo;
	
	@Autowired
	private ReviewRepository reviewRepo;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	private HashMap<String, Long> celebURLtoID = new HashMap<>();
	private HashMap<String, Long> movieURLtoID = new HashMap<>(); 
	private HashMap<String, Long> criticNametoID = new HashMap<>();

    @Override
    @Transactional
    public void onApplicationEvent(ApplicationReadyEvent event) {
        // load json file, add to my sql db
        try {
            mapJSONtoCelebrities("data/celebs/pcelebs00.json");
            mapJSONtoCelebrities("data/celebs/pcelebs01.json");
            mapJSONtoMovie("data/movies/pmovies00.json");
            mapJSONtoMovie("data/movies/pmovies01.json");
            mapJSONtoReviews("data/reviews/pcritic00.json");
            mapJSONtoReviews("data/reviews/pcritic01.json");
            User user = new User();
            user.setEmailAddress("a@a.com");
            user.setPassword(encoder.encode("a"));
            user.setEnabled(true);
            user.setRole(Roles.ADMIN);
            user.setFirstName("Admin");
            user.setLastName("guy");
            userRepo.save(user);
            User testUser = new User();
            testUser.setEmailAddress("b@b.com");
            testUser.setPassword(encoder.encode("b"));
            testUser.setEnabled(true);
            testUser.setFirstName("User");
            testUser.setLastName("guy");
            testUser.setRole(Roles.USER);
            userRepo.save(testUser);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void mapJSONtoCelebrities(String jsonFileName) throws IOException {
		Path jsonFilePath = Paths.get(jsonFileName);
		String jsonString = new String(Files.readAllBytes(jsonFilePath.toAbsolutePath()));
		ObjectMapper om = new ObjectMapper();
		JsonNode Tree = om.readTree(jsonString);
		Iterator<JsonNode> Iterator = Tree.iterator();
		while (Iterator.hasNext()) {
			JsonNode tree = Iterator.next();
			Celebrity celeb = new Celebrity();
			String name = tree.get("name").textValue();
			celeb.setName(name);
			String birthdate = tree.get("birthdate").textValue();
			DateTimeFormatter format1 = DateTimeFormatter.ofPattern("MMM dd, yyyy");
			DateTimeFormatter format2 = DateTimeFormatter.ofPattern("MMM d, yyyy");
			LocalDate birthdate2 = null;
			try{
				birthdate2 = LocalDate.parse(birthdate, format1);
			}
			catch(DateTimeParseException pe){
				birthdate2 = LocalDate.parse(birthdate, format2);
			}
			celeb.setBirthday(birthdate2);
			
			String summary = tree.get("summary").textValue();
			celeb.setDescription(summary);
			
			celeb.setCharacters(new HashSet<MovieCharacter>());
			
			celeb = celebrityRepo.save(celeb);
			
			String url = tree.get("url").textValue();
			celebURLtoID.put(url, celeb.getId());
		}
	}

	private void mapJSONtoMovie(String jsonFileName) throws IOException {
		Path jsonFilePath = Paths.get(jsonFileName);
		String jsonString = new String(Files.readAllBytes(jsonFilePath.toAbsolutePath()));
		ObjectMapper om = new ObjectMapper();
		JsonNode Tree = om.readTree(jsonString);
		Iterator<JsonNode> Iterator = Tree.iterator();
		while (Iterator.hasNext()) {
			JsonNode tree = Iterator.next();
			Movie movie = new Movie();
			
			String title = tree.get("title").textValue();
			movie.setTitle(title);
			String description = tree.get("description").textValue();
			movie.setDescription(description);
			String runtime = tree.get("runtime").textValue();
			movie.setRunTime(runtime);
			String airtime = tree.get("premiered").textValue();
			DateTimeFormatter format1 = DateTimeFormatter.ofPattern("MMM dd, yyyy");
			DateTimeFormatter format2 = DateTimeFormatter.ofPattern("MMM d, yyyy");
			LocalDate airtime2 = null;
			try{
				airtime2 = LocalDate.parse(airtime, format1);
			}
			catch(DateTimeParseException pe){
				airtime2 = LocalDate.parse(airtime, format2);
			}
			movie.getAirtimes()[0] = airtime2;
			
			movie = movieRepo.save(movie);
			
			JsonNode Cast = tree.get("cast");
			Iterator<JsonNode> Iteratorcast = Cast.iterator();
			while(Iteratorcast.hasNext()) {
				JsonNode castmember = Iteratorcast.next();
				String celeburl = castmember.get("celebrityURL").textValue();
				Long id = celebURLtoID.get(celeburl);
				if (id != null) {
					Optional<Celebrity> opceleb = celebrityRepo.findById(id);
					if (opceleb.isPresent()) {
						Celebrity celeb = opceleb.get();
						MovieCharacter moviechar = new MovieCharacter();
						moviechar.setSource(movie);
						moviechar.setActor(celeb);
						String charactername = "";
						try {
							charactername = castmember.get("character").textValue();
						}
						catch(NullPointerException e){
							charactername = "";
						}
						moviechar.setName(charactername);
						moviecharRepo.save(moviechar);
						celeb.getCharacters().add(moviechar);
						celebrityRepo.save(celeb);
					}
				}
			}
			
			movie = movieRepo.save(movie);
			
			String url = tree.get("url").textValue();
			movieURLtoID.put(url, movie.getId());
		}
    }
	
	private void mapJSONtoReviews(String jsonFileName) throws IOException {
		Path jsonFilePath = Paths.get(jsonFileName);
		String jsonString = new String(Files.readAllBytes(jsonFilePath.toAbsolutePath()));
		ObjectMapper om = new ObjectMapper();
		JsonNode Tree = om.readTree(jsonString);
		Iterator<JsonNode> Iterator = Tree.iterator();
		while (Iterator.hasNext()) {
			JsonNode treeiterator = Iterator.next();
			
			String movieurl = treeiterator.get("url").textValue();
			movieurl = movieurl.substring(0, movieurl.length() - 9 );
			Optional<Movie> opmovie = movieRepo.findById(movieURLtoID.get(movieurl));
			if (opmovie.isPresent()) {
				Movie movie = opmovie.get();
				JsonNode criticReviews = treeiterator.get("criticReviews");
				Iterator<JsonNode> ReviewIterator = criticReviews.iterator();
				while (ReviewIterator.hasNext()) {
					JsonNode criticReview = ReviewIterator.next();
					
					User user = new User();
					
					String name = criticReview.get("criticName").textValue();
					if (criticNametoID.containsKey(name)) {
						long id = criticNametoID.get(name);
						Optional<User> opuser = userRepo.findById(id);
						if (opuser.isPresent()) {
							user = opuser.get();
						}
					}
					else {
						user.setEmailAddress("setup@privilege.com");
					    user.setPassword(encoder.encode("a"));
					    user.setEnabled(true);
					    user.setRole(Roles.CRITIC);
					        
					    String[] names = name.split(" ");
					    if (names.length == 1) {
					        user.setFirstName(names[0]);
					        user.setLastName("");
					    }
					    else {
					        user.setFirstName(names[0]);
						    user.setLastName(names[names.length -1]);
					    }
					    user = userRepo.save(user);
					    criticNametoID.put(name, user.getId());
					}
					
					CriticReview review = new CriticReview();
					review.setPublication(criticReview.get("criticOrganization").textValue());
					review.setBlazing(true);
					review.setCustomScore(criticReview.get("criticScore").textValue());
					review.setBody(criticReview.get("criticPost").textValue());
					review.setUser(user);
					review.setSource(movie);
					if (criticReview.get("criticFresh").textValue().equals("true")) {
						review.setBlazing(true);
					}
					else {
						review.setBlazing(false);
					}
						
					String postDate = criticReview.get("criticReviewDate").textValue();
					DateTimeFormatter format1 = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
					DateTimeFormatter format2 = DateTimeFormatter.ofPattern("MMMM d, yyyy");
					LocalDate postDate2 = null;
					try{
						postDate2 = LocalDate.parse(postDate, format1);
					}
					catch(DateTimeParseException pe){
						postDate2 = LocalDate.parse(postDate, format2);
					}
					review.setDatetime(postDate2.atStartOfDay());
					review = reviewRepo.save(review);
					
					user.addToReviews(review);
					user = userRepo.save(user);
					
					movie.addCriticReview(review);
				}
				
				movie = movieRepo.save(movie);
			}
		}
	}
}
