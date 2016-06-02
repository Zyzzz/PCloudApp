package imu.pcloud.app.model;


import imu.pcloud.app.been.Comment;

import java.util.ArrayList;
import java.util.List;

public class CommentList extends BaseModel {

	List<Comment> comments = new ArrayList();

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

}
