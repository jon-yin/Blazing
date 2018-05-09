package com.blazing.code;

import java.io.File;
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
        /*
    	try {
        	
        	User user = new User();
            user.setEmailAddress("a@a.com");
            user.setPassword(encoder.encode("a"));
            user.setEnabled(true);
            user.setRole(Roles.ADMIN);
            user.setFirstName("Jonathan");
            user.setLastName("Yin");
            userRepo.save(user);
            User testUser = new User();
            testUser.setEmailAddress("c@c.com");
            testUser.setPassword(encoder.encode("c"));
            testUser.setEnabled(true);
            testUser.setFirstName("Critic");
            testUser.setLastName("guy");
            testUser.setRole(Roles.USER);
            userRepo.save(testUser);
            User testUser2 = new User();
            testUser2.setEmailAddress("b@b.com");
            testUser2.setPassword(encoder.encode("b"));
            testUser2.setEnabled(true);
            testUser2.setFirstName("User");
            testUser2.setLastName("guy");
            testUser2.setRole(Roles.USER);
            userRepo.save(testUser2); 
        	
            mapJSONtoCelebrities("data/celebs/pcelebs00.json");
            mapJSONtoCelebrities("data/celebs/pcelebs01.json");
            mapJSONtoCelebrities("data/celebs/pcelebs02.json");
            mapJSONtoCelebrities("data/celebs/pcelebs03.json");
            mapJSONtoCelebrities("data/celebs/pcelebs04.json");
            mapJSONtoCelebrities("data/celebs/pcelebs19.json");
            
            System.out.println("Celeb Done");
            
            mapJSONtoMovie("data/movies/pmovies00.json", false);
            mapJSONtoMovie("data/movies/pmovies01.json", false);
            mapJSONtoMovie("data/movies/pmovies02.json", false);
            mapJSONtoMovie("data/movies/pmovies03.json", false);
            mapJSONtoMovie("data/movies/pmovies04.json", false);
            mapJSONtoMovie("data/movies/pmovies05.json", false);
            mapJSONtoMovie("data/movies/pmovies06.json", false);
            mapJSONtoMovie("data/movies/pmovies07.json", false);
            mapJSONtoMovie("data/movies/pmovies08.json", false);
            mapJSONtoMovie("data/movies/pmovies09.json", false);
            mapJSONtoMovie("data/movies/pmovies10.json", false);
            mapJSONtoMovie("data/movies/pmovies11.json", false);
            mapJSONtoMovie("data/movies/pmovies12.json", false);
            mapJSONtoMovie("data/movies/pmovies13.json", false);
            mapJSONtoMovie("data/movies/pmovies14.json", false);
            mapJSONtoMovie("data/movies/pmovies15.json", false);
            mapJSONtoMovie("data/movies/pmovies16.json", false);
            mapJSONtoMovie("data/movies/pmovies17.json", false);
            mapJSONtoMovie("data/movies/pmovies18.json", false);
            mapJSONtoMovie("data/movies/pmovies19.json", false);
            mapJSONtoMovie("data/awarded/pMoviesWinnersSearch.json", true);
            
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
            
            mapJSONtoTV("data/tvshows/ptvshows00.json", false);
            mapJSONtoTV("data/tvshows/ptvshows01.json", false);
            mapJSONtoTV("data/tvshows/ptvshows03.json", false);
            mapJSONtoTV("data/tvshows/ptvshows04.json", false);
            mapJSONtoTV("data/tvshows/ptvshows05.json", false);
            mapJSONtoTV("data/tvshows/ptvshows06.json", false);
            mapJSONtoTV("data/tvshows/ptvshows07.json", false);
            mapJSONtoTV("data/tvshows/ptvshows08.json", false);
            mapJSONtoTV("data/tvshows/ptvshows09.json", false);
            mapJSONtoTV("data/tvshows/ptvshows10.json", false);
            mapJSONtoTV("data/tvshows/ptvshows11.json", false);
            mapJSONtoTV("data/tvshows/ptvshows13.json", false);
            mapJSONtoTV("data/tvshows/ptvshows14.json", false);
            mapJSONtoTV("data/tvshows/ptvshows15.json", false);
            mapJSONtoTV("data/tvshows/ptvshows16.json", false);
            mapJSONtoTV("data/tvshows/ptvshows17.json", false);
            mapJSONtoTV("data/tvshows/ptvshows18.json", false);
            mapJSONtoTV("data/tvshows/ptvshows19.json", false);
            mapJSONtoTV("data/awarded/pTVShowsWinnersSearch.json", true);
            
            System.out.println("TV Done");
            
            mapJSONtoSeason("data/seasons/pseasons00.json", false);
            mapJSONtoSeason("data/seasons/pseasons01.json", false);
            mapJSONtoSeason("data/seasons/pseasons03.json", false);
            mapJSONtoSeason("data/seasons/pseasons04.json", false);
            mapJSONtoSeason("data/seasons/pseasons05.json", false);
            mapJSONtoSeason("data/seasons/pseasons06.json", false);
            mapJSONtoSeason("data/seasons/pseasons07.json", false);
            mapJSONtoSeason("data/seasons/pseasons08.json", false);
            mapJSONtoSeason("data/seasons/pseasons09.json", false);
            mapJSONtoSeason("data/seasons/pseasons10.json", false);
            mapJSONtoSeason("data/seasons/pseasons11.json", false);
            mapJSONtoSeason("data/seasons/pseasons13.json", false);
            mapJSONtoSeason("data/seasons/pseasons14.json", false);
            mapJSONtoSeason("data/seasons/pseasons15.json", false);
            mapJSONtoSeason("data/seasons/pseasons16.json", false);
            mapJSONtoSeason("data/seasons/pseasons17.json", false);
            mapJSONtoSeason("data/seasons/pseasons18.json", false);
            mapJSONtoSeason("data/seasons/pseasons19.json", false);
            mapJSONtoSeason("data/awarded/pSeasonsWinnersSearch.json", true);
            
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
            
            mapJSONtoEpisode("data/episodes/pepisodes00.json", false);
            mapJSONtoEpisode("data/episodes/pepisodes01.json", false);
            mapJSONtoEpisode("data/episodes/pepisodes03.json", false);
            mapJSONtoEpisode("data/episodes/pepisodes04.json", false);
            mapJSONtoEpisode("data/episodes/pepisodes05.json", false);
            mapJSONtoEpisode("data/episodes/pepisodes06.json", false);
            mapJSONtoEpisode("data/episodes/pepisodes07.json", false);
            mapJSONtoEpisode("data/episodes/pepisodes08.json", false);
            mapJSONtoEpisode("data/episodes/pepisodes09.json", false);
            mapJSONtoEpisode("data/episodes/pepisodes10.json", false);
            mapJSONtoEpisode("data/episodes/pepisodes11.json", false);
            mapJSONtoEpisode("data/episodes/pepisodes13.json", false);
            mapJSONtoEpisode("data/episodes/pepisodes14.json", false);
            mapJSONtoEpisode("data/episodes/pepisodes15.json", false);
            mapJSONtoEpisode("data/episodes/pepisodes16.json", false);
            mapJSONtoEpisode("data/episodes/pepisodes17.json", false);
            mapJSONtoEpisode("data/episodes/pepisodes18.json", false);
            mapJSONtoEpisode("data/episodes/pepisodes19.json", false);
            mapJSONtoEpisode("data/awarded/pEpisodesWinnersSearch.json", true);
            
            System.out.println("Episodes Done");
            
            
            System.out.println("Started"); 
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
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
			
			String posterpath = "data/celebposters/" + url.substring(11, url.length()) + "_poster.jpg";
			Path posterFilePath = Paths.get(posterpath);
			if (Files.exists(posterFilePath)) {
				String posterpath2 = url.substring(11,url.length()) + "_portrait.jpg";
				celeb.setPortrait(posterpath2);
			}
			
			celeb = celebrityRepo.save(celeb);
		}
	}

	private void mapJSONtoMovie(String jsonFileName, boolean awarded) throws IOException {
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
			
			String url = tree.get("url").textValue();
			movieURLtoID.put(url, movie.getId());
		
			String posterpath = "data/movieposters/" + url.substring(3, url.length()) + "_poster.jpg";
			Path posterFilePath = Paths.get(posterpath);
			if (Files.exists(posterFilePath)) {
				String posterpath2 = url.substring(3,url.length()) + "_poster.jpg";
				movie.setPoster(posterpath2);
				movie.getImages().add(posterpath2);
			}
	
			movie.setAwarded(awarded);
			
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
			System.out.println(movie.getPoster());
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
	
	private void mapJSONtoTV(String jsonFileName, boolean awarded) throws IOException {
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
			
			String url = tree.get("url").textValue();
			tvURLtoID.put(url, tvshow.getId());
			
			String posterpath = "data/tvposters/" + url.substring(4, url.length()) + "_poster.jpg";
			Path posterFilePath = Paths.get(posterpath);
			if (Files.exists(posterFilePath)) {
				String posterpath2 = url.substring(4,url.length()) + "_portrait.jpg";
				tvshow.setPoster(posterpath2);
				tvshow.getImages().add(posterpath2);
			}
			
			tvshow.setAwarded(awarded);
			
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
		}
	}
	
	private void mapJSONtoSeason(String jsonFileName,boolean awarded) throws IOException {
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
					season.setPoster(sourcetv.getPoster());
					season.getImages().add(sourcetv.getPoster());
					season.setAwarded(awarded);
					
					String seasonnumber = url.substring(url.length() - 2, url.length());
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
	
	private void mapJSONtoEpisode(String jsonFileName,boolean awarded) throws IOException {
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
					episode.setPoster(sourceseason.getPoster());
					episode.getImages().add(sourceseason.getPoster());
					episode.setAwarded(awarded);
					
					String episodenumber = url.substring(url.length() - 2, url.length());
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
