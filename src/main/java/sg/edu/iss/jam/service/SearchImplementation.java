package sg.edu.iss.jam.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.iss.jam.model.Album;
import sg.edu.iss.jam.model.Media;
import sg.edu.iss.jam.model.MediaType;
import sg.edu.iss.jam.model.User;
import sg.edu.iss.jam.repo.AlbumRepository;
import sg.edu.iss.jam.repo.MediaRepository;
import sg.edu.iss.jam.repo.UserRepository;

@Service
public class SearchImplementation implements SearchInterface {

	@Autowired
	UserRepository uRepo;

	@Autowired
	MediaRepository mRepo;

	@Autowired
	AlbumRepository aRepo;

	@Override
	public Collection<User> SearchUsersbyName(String name) {
		// TODO Auto-generated method stub
		return uRepo.getusersbyname(name);
	}

	@Override
	public Collection<Media> SearchMediabyVarious(String name, MediaType mediaType) {
		// TODO Auto-generated method stub
		return mRepo.getMediasfromVarious(name, mediaType);
	}

	@Override
	public Collection<Album> SearchAlbumbyName(String name) {
		// TODO Auto-generated method stub
		return aRepo.findAlbumsLikeName(name);
	}

}
