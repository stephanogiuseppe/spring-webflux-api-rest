package com.apirest.webflux;

import java.util.UUID;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.apirest.webflux.document.Playlist;
import com.apirest.webflux.repository.PlaylistRepository;

import reactor.core.publisher.Flux;

@Component
public class DummyData implements CommandLineRunner {
	private final PlaylistRepository playlistRepository;
	
	public DummyData(PlaylistRepository playlistRepository) {
		this.playlistRepository = playlistRepository;
	}
	
	@Override
	public void run(String... args) throws Exception {
		playlistRepository
			.deleteAll()
			.thenMany(
				Flux.just("First playlist", "Second playlist", "Nth playlist", "Best playlist")
					.map(playlistName -> new Playlist(UUID.randomUUID().toString(), playlistName))
					.flatMap(playlistRepository::save)
			).subscribe(System.out::println);
	}
}
