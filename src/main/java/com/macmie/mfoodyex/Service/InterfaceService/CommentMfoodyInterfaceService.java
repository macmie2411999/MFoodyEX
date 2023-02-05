package com.macmie.mfoodyex.Service.InterfaceService;

import com.macmie.mfoodyex.Model.CommentMfoody;

import java.util.List;

public interface CommentMfoodyInterfaceService {
    public List<CommentMfoody> getListCommentMfoodys();

    public List<CommentMfoody> getListCommentMfoodysByIdProduct(int idProduct);

    public List<CommentMfoody> getListCommentMfoodysByIdUser(int idUser);

    public CommentMfoody getCommentMfoodyByID(int idCommentMfoody);

    public CommentMfoody saveCommentMfoody(CommentMfoody commentMfoody);

    public CommentMfoody updateCommentMfoody(CommentMfoody newCommentMfoody);

    public void deleteCommentMfoodyByID(int idCommentMfoody);

    public void deleteAllCommentsMfoodyByIdUser(int idUser);

    public void deleteAllCommentsMfoodyByIdProduct(int idProduct);
}
