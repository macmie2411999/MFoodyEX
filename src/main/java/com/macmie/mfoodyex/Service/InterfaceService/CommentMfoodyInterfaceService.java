package com.macmie.mfoodyex.Service.InterfaceService;

import com.macmie.mfoodyex.Model.CommentMfoody;

import java.util.List;

public interface CommentMfoodyInterfaceService {
    public List<CommentMfoody> getListCommentMfoodys();

    public CommentMfoody getCommentMfoodyByID(int ID_CommentMfoody);

    public CommentMfoody saveCommentMfoody(CommentMfoody commentMfoody);

    public CommentMfoody updateCommentMfoody(CommentMfoody newCommentMfoody);

    public void deleteCommentMfoodyByID(int ID_CommentMfoody);
}