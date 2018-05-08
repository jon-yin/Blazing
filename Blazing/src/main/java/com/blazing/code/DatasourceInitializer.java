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
import com.blazing.objects.Episode;
import com.blazing.objects.Movie;
import com.blazing.objects.MovieCharacter;
import com.blazing.objects.Roles;
import com.blazing.objects.Season;
import com.blazing.objects.TV;
import com.blazing.objects.User;
import com.blazing.repositories.CelebrityRepository;
import com.blazing.repositories.EpisodeRepository;
import com.blazing.repositories.MovieCharacterRepository;
import com.blazing.repositories.MovieRepository;
import com.blazing.repositories.ReviewRepository;
import com.blazing.repositories.SeasonRepository;
import com.blazing.repositories.TVRepository;
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
	private TVRepository tvRepo;
	
	@Autowired
	private SeasonRepository seasonRepo;
	
	@Autowired
	private EpisodeRepository episodeRepo;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	private HashMap<String, Long> celebURLtoID = new HashMap<>();
	private HashMap<String, Long> movieURLtoID = new HashMap<>(); 
	private HashMap<String, Long> criticNametoID = new HashMap<>();
	private HashMap<String, Long> tvURLtoID = new HashMap<>();
	private HashMap<String, Long> seasonURLtoID = new HashMap<>();

    @Override
    @Transactional
    public void onApplicationEvent(ApplicationReadyEvent event) {
        // load json file, add to my sql db
        /*try {
            mapJSONtoCelebrities("data/celebs/pcelebs00.json");
            mapJSONtoCelebrities("data/celebs/pcelebs01.json");
            mapJSONtoCelebrities("data/celebs/pcelebs02.json");
            mapJSONtoCelebrities("data/celebs/pcelebs03.json");
            mapJSONtoCelebrities("data/celebs/pcelebs04.json");
            mapJSONtoCelebrities("data/celebs/pcelebs19.json");
            
            System.out.println("Celeb Done");
            
            mapJSONtoMovie("data/movies/pmovies00.json");
            mapJSONtoMovie("data/movies/pmovies01.json");
            mapJSONtoMovie("data/movies/pmovies02.json");
            mapJSONtoMovie("data/movies/pmovies03.json");
            mapJSONtoMovie("data/movies/pmovies04.json");
            mapJSONtoMovie("data/movies/pmovies05.json");
            mapJSONtoMovie("data/movies/pmovies06.json");
            mapJSONtoMovie("data/movies/pmovies07.json");
            mapJSONtoMovie("data/movies/pmovies08.json");
            mapJSONtoMovie("data/movies/pmovies09.json");
            mapJSONtoMovie("data/movies/pmovies10.json");
            mapJSONtoMovie("data/movies/pmovies11.json");
            mapJSONtoMovie("data/movies/pmovies12.json");
            mapJSONtoMovie("data/movies/pmovies13.json");
            mapJSONtoMovie("data/movies/pmovies14.json");
            mapJSONtoMovie("data/movies/pmovies15.json");
            mapJSONtoMovie("data/movies/pmovies16.json");
            mapJSONtoMovie("data/movies/pmovies17.json");
            mapJSONtoMovie("data/movies/pmovies18.json");
            mapJSONtoMovie("data/movies/pmovies19.json");
            
            System.out.println("Movie Done");
            
            mapJSONtoReviews("data/reviews/pcritic00.json");
            mapJSONtoReviews("data/reviews/pcritic01.json");
            mapJSONtoReviews("data/reviews/pcritic02.json");
            mapJSONtoReviews("data/reviews/pcritic03.json");
            mapJSONtoReviews("data/reviews/pcritic04.json");
            mapJSONtoReviews("data/reviews/pcritic05.json");
            mapJSONtoReviews("data/reviews/pcritic06.json");
            mapJSONtoReviews("data/reviews/pcritic07.json");
            mapJSONtoReviews("data/reviews/pcritic08.json");
            mapJSONtoReviews("data/reviews/pcritic09.json");
            mapJSONtoReviews("data/reviews/pcritic10.json");
            mapJSONtoReviews("data/reviews/pcritic12.json");
            mapJSONtoReviews("data/reviews/pcritic13.json");
            mapJSONtoReviews("data/reviews/pcritic14.json");
            mapJSONtoReviews("data/reviews/pcritic15.json");
            mapJSONtoReviews("data/reviews/pcritic16.json");
            mapJSONtoReviews("data/reviews/pcritic17.json");
            mapJSONtoReviews("data/reviews/pcritic18.json");
            mapJSONtoReviews("data/reviews/pcritic19.json");
            
            System.out.println("Review Done");
            
            mapJSONtoTV("data/tvshows/ptvshows00.json");
            mapJSONtoTV("data/tvshows/ptvshows01.json");
            mapJSONtoTV("data/tvshows/ptvshows03.json");
            mapJSONtoTV("data/tvshows/ptvshows04.json");
            mapJSONtoTV("data/tvshows/ptvshows05.json");
            mapJSONtoTV("data/tvshows/ptvshows06.json");
            mapJSONtoTV("data/tvshows/ptvshows07.json");
            mapJSONtoTV("data/tvshows/ptvshows08.json");
            mapJSONtoTV("data/tvshows/ptvshows09.json");
            mapJSONtoTV("data/tvshows/ptvshows10.json");
            mapJSONtoTV("data/tvshows/ptvshows11.json");
            mapJSONtoTV("data/tvshows/ptvshows13.json");
            mapJSONtoTV("data/tvshows/ptvshows14.json");
            mapJSONtoTV("data/tvshows/ptvshows15.json");
            mapJSONtoTV("data/tvshows/ptvshows16.json");
            mapJSONtoTV("data/tvshows/ptvshows17.json");
            mapJSONtoTV("data/tvshows/ptvshows18.json");
            mapJSONtoTV("data/tvshows/ptvshows19.json");
            
            System.out.println("TV Done");
            
            mapJSONtoSeason("data/seasons/pseasons00.json");
            mapJSONtoSeason("data/seasons/pseasons01.json");
            mapJSONtoSeason("data/seasons/pseasons03.json");
            mapJSONtoSeason("data/seasons/pseasons04.json");
            mapJSONtoSeason("data/seasons/pseasons05.json");
            mapJSONtoSeason("data/seasons/pseasons06.json");
            mapJSONtoSeason("data/seasons/pseasons07.json");
            mapJSONtoSeason("data/seasons/pseasons08.json");
            mapJSONtoSeason("data/seasons/pseasons09.json");
            mapJSONtoSeason("data/seasons/pseasons10.json");
            mapJSONtoSeason("data/seasons/pseasons11.json");
            mapJSONtoSeason("data/seasons/pseasons13.json");
            mapJSONtoSeason("data/seasons/pseasons14.json");
            mapJSONtoSeason("data/seasons/pseasons15.json");
            mapJSONtoSeason("data/seasons/pseasons16.json");
            mapJSONtoSeason("data/seasons/pseasons17.json");
            mapJSONtoSeason("data/seasons/pseasons18.json");
            mapJSONtoSeason("data/seasons/pseasons19.json");
            
            System.out.println("Season Done");
            
            mapJSONtoSeasonReviews("data/seasonreviews/pseasonreviews00.json");
            mapJSONtoSeasonReviews("data/seasonreviews/pseasonreviews01.json");
            mapJSONtoSeasonReviews("data/seasonreviews/pseasonreviews03.json");
            mapJSONtoSeasonReviews("data/seasonreviews/pseasonreviews04.json");
            mapJSONtoSeasonReviews("data/seasonreviews/pseasonreviews05.json");
            mapJSONtoSeasonReviews("data/seasonreviews/pseasonreviews06.json");
            mapJSONtoSeasonReviews("data/seasonreviews/pseasonreviews07.json");
            mapJSONtoSeasonReviews("data/seasonreviews/pseasonreviews08.json");
            mapJSONtoSeasonReviews("data/seasonreviews/pseasonreviews09.json");
            mapJSONtoSeasonReviews("data/seasonreviews/pseasonreviews10.json");
            mapJSONtoSeasonReviews("data/seasonreviews/pseasonreviews11.json");
            mapJSONtoSeasonReviews("data/seasonreviews/pseasonreviews13.json");
            mapJSONtoSeasonReviews("data/seasonreviews/pseasonreviews14.json");
            mapJSONtoSeasonReviews("data/seasonreviews/pseasonreviews15.json");
            mapJSONtoSeasonReviews("data/seasonreviews/pseasonreviews16.json");
            mapJSONtoSeasonReviews("data/seasonreviews/pseasonreviews17.json");
            mapJSONtoSeasonReviews("data/seasonreviews/pseasonreviews18.json");
            mapJSONtoSeasonReviews("data/seasonreviews/pseasonreviews19.json");
            
            System.out.println("SeasonReview Done");
            
            mapJSONtoEpisode("data/episodes/pepisodes00.json");
            mapJSONtoEpisode("data/episodes/pepisodes01.json");
            mapJSONtoEpisode("data/episodes/pepisodes03.json");
            mapJSONtoEpisode("data/episodes/pepisodes04.json");
            mapJSONtoEpisode("data/episodes/pepisodes05.json");
            mapJSONtoEpisode("data/episodes/pepisodes06.json");
            mapJSONtoEpisode("data/episodes/pepisodes07.json");
            mapJSONtoEpisode("data/episodes/pepisodes08.json");
            mapJSONtoEpisode("data/episodes/pepisodes09.json");
            mapJSONtoEpisode("data/episodes/pepisodes10.json");
            mapJSONtoEpisode("data/episodes/pepisodes11.json");
            mapJSONtoEpisode("data/episodes/pepisodes13.json");
            mapJSONtoEpisode("data/episodes/pepisodes14.json");
            mapJSONtoEpisode("data/episodes/pepisodes15.json");
            mapJSONtoEpisode("data/episodes/pepisodes16.json");
            mapJSONtoEpisode("data/episodes/pepisodes17.json");
            mapJSONtoEpisode("data/episodes/pepisodes18.json");
            mapJSONtoEpisode("data/episodes/pepisodes19.json");
            
            System.out.println("Episodes Done");
            
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
            
            System.out.println("Started");
        } catch (IOException e) {
            e.printStackTrace();
        }*/
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
			String studio = tree.get("studio").textValue();
			movie.setStudio(studio);
			String rating = tree.get("rating").textValue();
			movie.setRating(rating);
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
			
			String genrestring = "";
			JsonNode genres = tree.get("genres");
			if (genres.isArray()) {
				for (JsonNode value : genres){
					genrestring = genrestring + value.asText() + " ";
				}
			}
			movie.setGenres(genrestring);
			
			String directorstring = "";
			JsonNode directors = tree.get("directors");
			if (directors.isArray()) {
				for (JsonNode value : directors){
					directorstring = directorstring + value.asText() + "/";
				}
			}
			movie.setDirector(directorstring);
			
			String writerstring = "";
			JsonNode writers = tree.get("writers");
			if (writers.isArray()) {
				for (JsonNode value : writers){
					writerstring = writerstring + value.asText() + "/";
				}
			}
			movie.setWriter(writerstring);
			
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
						moviechar = moviecharRepo.save(moviechar);
						celeb.getCharacters().add(moviechar);
						celeb.getFilmography().add(movie);
						celeb = celebrityRepo.save(celeb);
						movie.getCast().add(moviechar);
						movie = movieRepo.save(movie);
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
			if (movieURLtoID.get(movieurl) != null) {
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
						if (criticReview.get("criticScore") == null) {
							review.setCustomScore("");
						}
						else {
							review.setCustomScore(criticReview.get("criticScore").textValue());
						}
						String body = criticReview.get("criticPost").textValue();
						if (criticReview.get("postUrl") != null ) {
							body = body + "\n";
							body = body + criticReview.get("postUrl").textValue();  
						}
						review.setBody(body);
						review.setUser(user);
						review.setSource(movie);
						
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
	
	private void mapJSONtoTV(String jsonFileName) throws IOException {
		Path jsonFilePath = Paths.get(jsonFileName);
		String jsonString = new String(Files.readAllBytes(jsonFilePath.toAbsolutePath()));
		ObjectMapper om = new ObjectMapper();
		JsonNode Tree = om.readTree(jsonString);
		Iterator<JsonNode> Iterator = Tree.iterator();
		while (Iterator.hasNext()) {
			JsonNode tree = Iterator.next();
			
			TV tvshow = new TV();
			
			String title = tree.get("title").textValue();
			tvshow.setTitle(title);
			String description = tree.get("description").textValue();
			tvshow.setDescription(description);
			String network = tree.get("network").textValue();
			tvshow.setNetwork(network);
			String airtime = tree.get("premiere").textValue();
			DateTimeFormatter format1 = DateTimeFormatter.ofPattern("MMM dd, yyyy");
			DateTimeFormatter format2 = DateTimeFormatter.ofPattern("MMM d, yyyy");
			LocalDate airtime2 = null;
			try{
				airtime2 = LocalDate.parse(airtime, format1);
			}
			catch(DateTimeParseException pe){
				try {
					airtime2 = LocalDate.parse(airtime, format2);
				}
				catch(DateTimeParseException p) {
					airtime2 = null;
				}
			}
			tvshow.getAirtimes()[0] = airtime2;
			
			tvshow = tvRepo.save(tvshow);
			
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
						moviechar.setSource(tvshow);
						moviechar.setActor(celeb);
						String charactername = "";
						try {
							charactername = castmember.get("character").textValue();
						}
						catch(NullPointerException e){
							charactername = "";
						}
						moviechar.setName(charactername);
						moviechar = moviecharRepo.save(moviechar);
						celeb.getFilmography().add(tvshow);
						celeb.getCharacters().add(moviechar);
						celeb = celebrityRepo.save(celeb);
						tvshow.getCast().add(moviechar);
						tvshow = tvRepo.save(tvshow);
					}
				}
			}
			
			tvshow = tvRepo.save(tvshow);
			String url = tree.get("url").textValue();
			tvURLtoID.put(url, tvshow.getId());
		}
	}
	
	private void mapJSONtoSeason(String jsonFileName) throws IOException {
		Path jsonFilePath = Paths.get(jsonFileName);
		String jsonString = new String(Files.readAllBytes(jsonFilePath.toAbsolutePath()));
		ObjectMapper om = new ObjectMapper();
		JsonNode Tree = om.readTree(jsonString);
		Iterator<JsonNode> Iterator = Tree.iterator();
		while (Iterator.hasNext()) {
			JsonNode tree = Iterator.next();
		
			String url = tree.get("url").textValue();
			Season season = new Season();
			
			String sourceurl = url.substring(0, url.length() - 4);
			if (tvURLtoID.containsKey(sourceurl)) {
				Optional<TV> opsourcetv = tvRepo.findById(tvURLtoID.get(sourceurl));
				if (opsourcetv.isPresent()) {
					TV sourcetv = opsourcetv.get();
					sourcetv.getSeasons().add(season);
					sourcetv = tvRepo.save(sourcetv);
					season.setShow(sourcetv);
					
					String seasonnumber = url.substring(url.length() - 2, url.length() - 1);
					int seasonnumber2 = Integer.parseInt(seasonnumber);
					season.setSeasonNumber(seasonnumber2);
					
					season.setTitle(sourcetv.getTitle() + " Season " + seasonnumber);
					
					String description = tree.get("summary").textValue();
					season.setDescription(description);
					String network = tree.get("network").textValue();
					season.setNetwork(network);
					String airtime = tree.get("premiered").textValue();
					DateTimeFormatter format1 = DateTimeFormatter.ofPattern("MMM dd, yyyy");
					DateTimeFormatter format2 = DateTimeFormatter.ofPattern("MMM d, yyyy");
					LocalDate airtime2 = null;
					try{
						airtime2 = LocalDate.parse(airtime, format1);
					}
					catch(DateTimeParseException pe){
						try {
							airtime2 = LocalDate.parse(airtime, format2);
						}
						catch(DateTimeParseException p) {
							airtime2 = null;
						}
					}
					season.getAirtimes()[0] = airtime2;
					
					season = seasonRepo.save(season);
				}
			}
		}
	}
	
	private void mapJSONtoSeasonReviews(String jsonFileName) throws IOException {
		Path jsonFilePath = Paths.get(jsonFileName);
		String jsonString = new String(Files.readAllBytes(jsonFilePath.toAbsolutePath()));
		ObjectMapper om = new ObjectMapper();
		JsonNode Tree = om.readTree(jsonString);
		Iterator<JsonNode> Iterator = Tree.iterator();
		while (Iterator.hasNext()) {
			JsonNode treeiterator = Iterator.next();
			
			String seasonurl = treeiterator.get("url").textValue();
			seasonurl = seasonurl.substring(0, seasonurl.length() - 9 );
			if (seasonURLtoID.get(seasonurl) != null) {
				Optional<Season> opseason = seasonRepo.findById(seasonURLtoID.get(seasonurl));
				if (opseason.isPresent()) {
					Season season = opseason.get();
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
						String body = criticReview.get("criticPost").textValue();
						if (criticReview.get("postUrl") != null ) {
							body = body + "\n";
							body = body + criticReview.get("postUrl").textValue();  
						}
						review.setBody(body);
						review.setUser(user);
						review.setSource(season);
						
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
						
						season.addCriticReview(review);
					}
					season = seasonRepo.save(season);
				}
			}
		}
	}
	
	private void mapJSONtoEpisode(String jsonFileName) throws IOException {
		Path jsonFilePath = Paths.get(jsonFileName);
		String jsonString = new String(Files.readAllBytes(jsonFilePath.toAbsolutePath()));
		ObjectMapper om = new ObjectMapper();
		JsonNode Tree = om.readTree(jsonString);
		Iterator<JsonNode> Iterator = Tree.iterator();
		while (Iterator.hasNext()) {
			JsonNode tree = Iterator.next();
		
			String url = tree.get("url").textValue();
			Episode episode = new Episode();
			
			String sourceurl = url.substring(0, url.length() - 4);
			if (seasonURLtoID.containsKey(sourceurl)) {
				Optional<Season> opseason = seasonRepo.findById(seasonURLtoID.get(sourceurl));
				if (opseason.isPresent()) {
					Season sourceseason = opseason.get();
					sourceseason.getEpisodes().add(episode);
					sourceseason = seasonRepo.save(sourceseason);
					episode.setSeason(sourceseason);
					
					String episodenumber = url.substring(url.length() - 2, url.length() - 1);
					int episodenumber2 = Integer.parseInt(episodenumber);
					episode.setEpisodeNumber(episodenumber2);
					
					episode.setTitle(sourceseason.getTitle() + " Episode " + episodenumber2);
					
					String description = tree.get("summary").textValue();
					episode.setDescription(description);
					String airtime = tree.get("airDate").textValue();
					DateTimeFormatter format1 = DateTimeFormatter.ofPattern("MMM dd, yyyy");
					DateTimeFormatter format2 = DateTimeFormatter.ofPattern("MMM d, yyyy");
					LocalDate airtime2 = null;
					try{
						airtime2 = LocalDate.parse(airtime, format1);
					}
					catch(DateTimeParseException pe){
						try {
							airtime2 = LocalDate.parse(airtime, format2);
						}
						catch(DateTimeParseException p) {
							airtime2 = null;
						}
					}
					episode.getAirtimes()[0] = airtime2;
					
					episode = episodeRepo.save(episode);
				}
			}
		}
	}
	
	
}
