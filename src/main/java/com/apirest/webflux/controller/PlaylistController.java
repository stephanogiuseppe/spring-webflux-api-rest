package com.apirest.webflux.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
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
	
}
