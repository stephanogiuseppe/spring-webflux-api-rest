package com.apirest.webflux;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.apirest.webflux.document.Playlist;
import com.apirest.webflux.services.PlaylistService;

import reactor.core.publisher.Mono;

@Component
public class PlaylistHandler {
	
	@Autowired
	PlaylistService playlistService;
	
	public Mono<ServerResponse> findAll(ServerRequest request) {
		return ServerResponse.ok()
			.contentType(MediaType.APPLICATION_JSON)
			.body(playlistService.findAll(), Playlist.class);
	}
	
	public Mono<ServerResponse> findById(ServerRequest request) {
		String id = request.pathVariable("id");
		return ServerResponse.ok()
			.contentType(MediaType.APPLICATION_JSON)
			.body(playlistService.findById(id), Playlist.class);
	}
	
	public Mono<ServerResponse> save(ServerRequest request) {
		final Mono<Playlist> playlist = request.bodyToMono(Playlist.class);
		return ServerResponse.ok()
			.contentType(MediaType.APPLICATION_JSON)
			.body(BodyInserters.fromPublisher(playlist.flatMap(playlistService::save), Playlist.class));
	}
	
}
