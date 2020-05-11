package com.apirest.webflux.controller;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apirest.webflux.document.Playlist;
import com.apirest.webflux.services.PlaylistService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/v1")
public class PlaylistController {

	@Autowired
	PlaylistService playlistService;
	
	@GetMapping(value = "/playlists")
	public Flux<Playlist> getPlaylist() {
		return playlistService.findAll();
	}
	
	@GetMapping(value = "/playlist/{id}")
	public Mono<Playlist> getPlaylistById(@PathVariable String id) {
		return playlistService.findById(id);
	}
	
	@PostMapping(value = "/playlist")
	public Mono<Playlist> savePlaylist(@RequestBody Playlist playlist) {
		return playlistService.save(playlist);
	}
	
	@GetMapping(value="/playlists/async-test", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Tuple2<Long, Playlist>> getPlaylistByWebflux(){
		System.out.println("> Start get Playlists by async mode" + LocalDateTime.now());

		Flux<Long> interval = Flux.interval(Duration.ofSeconds(5));
        Flux<Playlist> playlist = playlistService.findAll();

        return Flux.zip(interval, playlist);
	}
	
	@GetMapping(value="/playlists/sync-test")
	public List<String> getPlaylistByMvc() throws InterruptedException {
		System.out.println("> Start get Playlists by sync mode" + LocalDateTime.now());

		List<String> playlists = Arrays.asList("Playlist 1", "Playlist 2", "Playlist 3", "Playlist 4");

		TimeUnit.SECONDS.sleep(5);

		return playlists;
	}
	
}
