package kr.mini_project.Svc;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

@Service
public class YoutubeSvc {

    private static final String APPLICATION_NAME = "YouTube API Demo";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static YouTube youtube;

    public YoutubeSvc() throws GeneralSecurityException, IOException {
        youtube = new YouTube.Builder(GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, null)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public List<SearchResult> searchMusical(String query, long maxResults, String apiKey) throws IOException {
        YouTube.Search.List search = youtube.search().list("id,snippet");
        search.setKey(apiKey);
        search.setQ(query);
        search.setRegionCode("KR");
        search.setVideoCategoryId("10"); // 음악 카테고리 ID
        search.setType("video");
        search.setVideoDuration("medium"); // 4~20분 길이의 영상
        search.setMaxResults(10L); // 가져올 최대 결과 수
        SearchListResponse response = search.execute();
        return response.getItems();
    }

    public List<SearchResult> searchMovies(String query, long maxResults, String apiKey) throws IOException {
        YouTube.Search.List search = youtube.search().list("id,snippet");
        search.setKey(apiKey);
        search.setQ(query);
        search.setRegionCode("KR");
        search.setVideoCategoryId("30"); // 영화 카테고리 ID
        search.setType("video");
        search.setMaxResults(10L); // 가져올 최대 결과 수
        SearchListResponse response = search.execute();
        return response.getItems();
    }


    public List<SearchResult> getMusical(String apiKey) throws IOException {
        //인기 뮤지컬 무대 가져오기 (유튜브)
        YouTube.Search.List search = youtube.search().list("id,snippet");
        search.setKey(apiKey);
        search.setRegionCode("KR");
        search.setVideoCategoryId("10"); // 음악 카테고리 ID
        search.setQ("뮤지컬 무대");
        search.setType("video");
        search.setOrder("viewCount"); // 조회수 기준으로 정렬
        search.setVideoDuration("medium"); // 4~20분 길이의 영상
        search.setMaxResults(10L); // 가져올 최대 결과 수

        return this.searchExecute(search);
    }


    public List<SearchResult> getMovies(String apiKey) throws IOException {
        //인기 영화 목록 가져오기 (유튜브)
        YouTube.Search.List search = youtube.search().list("id,snippet");
        search.setKey(apiKey);
        search.setRegionCode("KR");
        search.setVideoCategoryId("30"); // 영화 카테고리 ID
        search.setType("video");
        search.setOrder("viewCount"); // 조회수 기준으로 정렬
        search.setMaxResults(10L); // 가져올 최대 결과 수

        return this.searchExecute(search);
    }

    private List<SearchResult> searchExecute(YouTube.Search.List search) throws IOException {

        SearchListResponse response = search.execute();

        return response.getItems();
    }

}
