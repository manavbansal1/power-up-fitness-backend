package com.project.power_up_fitness_backend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;
import java.net.URI;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import org.springframework.http.HttpStatus;
import java.io.IOException;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.PlaylistSimplified;
import jakarta.servlet.http.HttpServletResponse; 



@RestController
@RequestMapping("/api/spotify")
@CrossOrigin(origins = "http://localhost:3000")  // frontend URL here
public class SpotifyController {
    
    @Autowired
    private SpotifyApi spotifyApi;

    @GetMapping("/")
    public ResponseEntity<String> sayHi () {
        return ResponseEntity.ok("hello");
    }

    @GetMapping
    public ResponseEntity<String> sayHi2 () {
        return ResponseEntity.ok("hello");
    }
    
    @GetMapping("/login")
    public ResponseEntity<String> getSpotifyAuthUrl() {
        AuthorizationCodeUriRequest authorizationCodeUriRequest = spotifyApi.authorizationCodeUri()
                .scope("playlist-read-private playlist-read-collaborative streaming user-read-email app-remote-control user-read-private user-modify-playback-state user-read-playback-state")
                .show_dialog(true)
                .build();
        
        final URI uri = authorizationCodeUriRequest.execute();
        return ResponseEntity.ok(uri.toString());
    }

    // @GetMapping("/callback")
    // public ResponseEntity<Void> getSpotifyToken(@RequestParam("code") String code, HttpServletResponse response) {
    // try {
    //     AuthorizationCodeRequest authorizationCodeRequest = spotifyApi.authorizationCode(code).build();
    //     AuthorizationCodeCredentials credentials = authorizationCodeRequest.execute();
        
    //     // Set the access token and refresh token in SpotifyApi instance
    //     spotifyApi.setAccessToken(credentials.getAccessToken());
    //     spotifyApi.setRefreshToken(credentials.getRefreshToken());

    //     // return ResponseEntity.ok(credentials.getAccessToken());
    //     response.sendRedirect("http://localhost:3000/callback?code=" + credentials.getAccessToken());
    //     return ResponseEntity.ok().build();
    // } catch (IOException | SpotifyWebApiException | ParseException e) {
    //     // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    //     //         .body("Error during authentication: " + e.getMessage());
    //     // response.sendRedirect("http://localhost:3000/error?message=" + e.getMessage());
    //     // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();


    //     try {
    //         // Redirect to frontend with error
    //         response.sendRedirect("http://localhost:3000/error?message=" + e.getMessage());
    //         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    //     } catch (IOException redirectError) {
    //         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    //     }
    // }
    // }


    // @GetMapping("/callback")
    // public ResponseEntity<String> getSpotifyToken(@RequestParam("code") String code) {
    //     try {
    //         AuthorizationCodeRequest authorizationCodeRequest = spotifyApi.authorizationCode(code).build();
    //         AuthorizationCodeCredentials credentials = authorizationCodeRequest.execute();
            
    //         spotifyApi.setAccessToken(credentials.getAccessToken());
    //         spotifyApi.setRefreshToken(credentials.getRefreshToken());
            
    //         return ResponseEntity.ok(credentials.getAccessToken());
    //     } catch (IOException | SpotifyWebApiException | ParseException e) {
    //         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    //                 .body("Error during authentication: " + e.getMessage());
    //     }
    // }


    @GetMapping("/callback")
    public void getSpotifyToken(@RequestParam("code") String code, HttpServletResponse response) throws IOException {
    try {
        AuthorizationCodeRequest authorizationCodeRequest = spotifyApi.authorizationCode(code).build();
        AuthorizationCodeCredentials credentials = authorizationCodeRequest.execute();
        
        spotifyApi.setAccessToken(credentials.getAccessToken());
        spotifyApi.setRefreshToken(credentials.getRefreshToken());
        
        // Redirect back to frontend with the token in the URL
        String frontendUrl = "http://localhost:3000?token=" + credentials.getAccessToken();
        response.sendRedirect(frontendUrl);
        
    } catch (SpotifyWebApiException | ParseException e) {
        response.sendRedirect("http://localhost:3000/error");
    }
}

    @GetMapping("/playlists")
    public ResponseEntity<Object> getUserPlaylists() {
    try {
        Paging<PlaylistSimplified> playlists = spotifyApi.getListOfCurrentUsersPlaylists()
                .limit(5)  // Number of playlists to retrieve
                .build()
                .execute();
        
        return ResponseEntity.ok(playlists);
    } catch (IOException | SpotifyWebApiException | ParseException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error fetching playlists: " + e.getMessage());
    }
}
}

