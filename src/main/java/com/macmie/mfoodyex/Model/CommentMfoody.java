package com.macmie.mfoodyex.Model;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name= "`COMMENT_MFOODY`")
@Data
@RequiredArgsConstructor
public class CommentMfoody {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID_COMMENT")
    private int IdComment;

    @NonNull
    @Column(name = "RATING_COMMENT")
    private String RatingComment;

    @NonNull
    @Column(name = "CONTENT_COMMENT")
    private String ContentComment;

    @NonNull
    @Column(name = "ID_USER")
    private String IdUser;

    @NonNull
    @Column(name = "ID_PRODUCT")
    private String IdProduct;

    // Map to User
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "ID_USER", referencedColumnName = "ID_USER", insertable = false, updatable = false)
    private UserMfoody User;

    public CommentMfoody() {
    }

    public CommentMfoody(int idComment, @NonNull String ratingComment, @NonNull String contentComment, @NonNull String idUser, @NonNull String idProduct) {
        IdComment = idComment;
        RatingComment = ratingComment;
        ContentComment = contentComment;
        IdUser = idUser;
        IdProduct = idProduct;
    }

    public int getIdComment() {
        return IdComment;
    }

    public void setIdComment(int idComment) {
        IdComment = idComment;
    }

    public String getRatingComment() {
        return RatingComment;
    }

    public void setRatingComment(String ratingComment) {
        RatingComment = ratingComment;
    }

    public String getContentComment() {
        return ContentComment;
    }

    public void setContentComment(String contentComment) {
        ContentComment = contentComment;
    }

    public String getIdUser() {
        return IdUser;
    }

    public void setIdUser(String idUser) {
        IdUser = idUser;
    }

    public String getIdProduct() {
        return IdProduct;
    }

    public void setIdProduct(String idProduct) {
        IdProduct = idProduct;
    }
}
