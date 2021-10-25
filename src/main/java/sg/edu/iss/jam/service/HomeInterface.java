package sg.edu.iss.jam.service;

import java.util.Collection;

import sg.edu.iss.jam.model.Post;

public interface HomeInterface {
	
	public Collection<Post> getPostsbyID(Long userID);
	
	public boolean deletepost(Post Post);
	
	public Post SavePost(Post Post);
	
	public Post getPostbyID(Long id);
	


}
