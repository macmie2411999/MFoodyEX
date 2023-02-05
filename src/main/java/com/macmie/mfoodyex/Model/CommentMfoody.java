package com.macmie.mfoodyex.Model;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

/* Handle Jackson – Bidirectional Relationships (Loop)
    @JsonIgnore: ignore Serialization
    @JsonBackReference: the back part of reference; it'll be omitted from serialization (for ManyToOne - Object)
    @JsonManagedReference: the forward part of reference, the one that gets serialized normally (for OneToMany - list)
    @JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
    */

@Entity
@Table(name= "`COMMENT_MFOODY`")
@RequiredArgsConstructor
public class CommentMfoody {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID_COMMENT")
    private int idComment;

    @NonNull
    @Column(name = "RATING_COMMENT")
    private int ratingComment;

    @NonNull
    @Column(name = "CONTENT_COMMENT")
    private String contentComment;

//    @NonNull
//    @Column(name = "ID_USER")
//    private String idUser;
//
//    @NonNull
//    @Column(name = "ID_PRODUCT")
//    private String idProduct;

    // Map to UserMfoody
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "ID_USER")
    private UserMfoody user;

    // Map to ProductMfoody
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "ID_PRODUCT")
    private ProductMfoody product;

    public CommentMfoody() {
    }

    public CommentMfoody(int idComment, @NonNull int ratingComment, @NonNull String contentComment, UserMfoody user, ProductMfoody product) {
        this.idComment = idComment;
        this.ratingComment = ratingComment;
        this.contentComment = contentComment;
        this.user = user;
        this.product = product;
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

    public UserMfoody getUser() {
        return user;
    }

    public void setUser(UserMfoody user) {
        this.user = user;
    }

    public ProductMfoody getProduct() {
        return product;
    }

    public void setProduct(ProductMfoody product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "CommentMfoody{" +
                "idComment=" + idComment +
                ", ratingComment=" + ratingComment +
                ", contentComment='" + contentComment + '\'' +
                ", user=" + user +
                ", product=" + product +
                '}';
    }
}
