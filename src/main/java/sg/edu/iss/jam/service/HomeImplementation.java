package sg.edu.iss.jam.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.iss.jam.model.Post;
import sg.edu.iss.jam.repo.PostRepository;
import sg.edu.iss.jam.repo.SubscribedRepository;

@Service
public class HomeImplementation implements HomeInterface {
	
	@Autowired
	PostRepository prepo;
	
	@Autowired
	UserInterface uService;
	
	@Autowired
	SubscribedRepository srepo;
	


	@Override
	public Collection<Post> getPostsbyID(Long userID) {
		// TODO Auto-generated method stub
		return prepo.findPostsbyuserid(userID);
	}

	@Override
	public boolean deletepost(Post Post) {
		
		try {
			prepo.delete(Post);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return false;
	}

	@Override
	public Post SavePost(Post Post) {
		// TODO Auto-generated method stub
		return prepo.save(Post);
	}

	@Override
	public Post getPostbyID(Long id) {
		// TODO Auto-generated method stub
		return prepo.getById(id);
	}


}
