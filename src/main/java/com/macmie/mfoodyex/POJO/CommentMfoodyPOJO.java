package com.macmie.mfoodyex.POJO;

import com.macmie.mfoodyex.Model.CommentMfoody;
import com.macmie.mfoodyex.Util.InputChecker;

public class CommentMfoodyPOJO {
    private int idComment;
    private int ratingComment;
    private String contentComment;
    private int idUser;
    private int idProduct;

    public CommentMfoody renderCommentMfoody() {
        CommentMfoody newCommentMfoody = new CommentMfoody();
        newCommentMfoody.setIdComment(this.getIdComment());
        newCommentMfoody.setRatingComment(this.getRatingComment());
        newCommentMfoody.setContentComment(this.getContentComment());
        return newCommentMfoody;
    }

    public boolean checkCommentMfoodyValidAttributes() {
        if(InputChecker.isStringValid(this.contentComment)) {
            return true;
        }
        return  false;
    }

    public int getIdComment() {
        return idComment;
    }

    public void setIdComment(int idComment) {
        this.idComment = idComment;
    }

    public int getRatingComment() {
        return ratingComment;
    }

    public void setRatingComment(int ratingComment) {
        this.ratingComment = ratingComment;
    }

    public String getContentComment() {
        return contentComment;
    }

    public void setContentComment(String contentComment) {
        this.contentComment = contentComment;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }
}
